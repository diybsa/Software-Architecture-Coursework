package com.Ga3;

import com.Ga3.application.ShareService;
import com.Ga3.application.ports.MarketDataProvider;
import com.Ga3.application.ports.ShareRepository;
import com.Ga3.application.usecases.CompareTwoSharesUseCase;
import com.Ga3.application.usecases.GetShareDataUseCase;
import com.Ga3.domain.PriceData;
import com.Ga3.domain.Share;
import com.Ga3.infrastructure.InMemoryShareRepository;
import com.Ga3.infrastructure.MockMarketDataProvider;
import com.Ga3.presentation.ShareController;
import com.Ga3.presentation.ShareRequestHandler;

import java.time.LocalDate;

/**
 * Composition root.
 *
 * Wires concrete infrastructure adapters into the use cases, then into the
 * application service, then into the presentation controller. This is the
 * only place in the system where outer-layer classes are instantiated.
 *
 * Demonstrates that the Clean Architecture wiring runs end to end.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== Share Price Comparison System ===\n");

        // Infrastructure adapters (implementations of application ports)
        MarketDataProvider marketData = new MockMarketDataProvider();
        ShareRepository repository = new InMemoryShareRepository();

        // Use cases (the business rules)
        GetShareDataUseCase getShareData =
                new GetShareDataUseCase(marketData, repository);
        CompareTwoSharesUseCase compareShares =
                new CompareTwoSharesUseCase(getShareData);

        // Application façade
        ShareService service = new ShareService(getShareData, compareShares);

        // Presentation entry point (exposed via its provided interface)
        ShareRequestHandler controller = new ShareController(service);

        // --- Demo: Get Share Data ----------------------------------------
        Share apple = controller.requestShare(
                "AAPL",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 7));

        System.out.println("\nData for " + apple.getSymbol() + ":");
        for (PriceData p : apple.getPriceHistory()) {
            System.out.println("  " + p.getDate() + " | Close: " + p.getClosePrice());
        }

        // --- Demo: Compare Two Shares ------------------------------------
        CompareTwoSharesUseCase.Result comparison = service.compareShares(
                "AAPL",
                "MSFT",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 7));

        System.out.println("\nComparison: "
                + comparison.getShareA().getSymbol() + " vs "
                + comparison.getShareB().getSymbol());
        System.out.println("  "
                + comparison.getShareA().getPriceHistory().size()
                + " price points each.");

        System.out.println("\nSystem executed successfully.");
    }
}
