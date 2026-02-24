package com.Ga3;

import com.Ga3.application.ShareService;
import com.Ga3.domain.PriceData;
import com.Ga3.domain.Share;
import com.Ga3.infrastructure.MarketDataProvider;
import com.Ga3.infrastructure.ShareRepository;
import com.Ga3.presentation.ShareController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Share Price Comparison System (Sprint 1) ===");

        MarketDataProvider mockProvider = new MockMarketDataProvider();
        ShareRepository mockRepository = new MockShareRepository();

        ShareService service = new ShareService(mockProvider, mockRepository);
        ShareController controller = new ShareController(service);

        Share share = controller.requestShare(
                "AAPL",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5)
        );

        System.out.println("\nData for: " + share.getSymbol());

        for (PriceData price : share.getPriceHistory()) {
            System.out.println(price.getDate() + " | Close: " + price.getClosePrice());
        }

        System.out.println("\nSystem executed successfully.");
    }



    static class MockMarketDataProvider implements MarketDataProvider {

        @Override
        public Share fetchShareData(String symbol,
                                    LocalDate start,
                                    LocalDate end) {

            List<PriceData> prices = new ArrayList<>();

            prices.add(new PriceData(start, 150.00));
            prices.add(new PriceData(start.plusDays(1), 152.75));
            prices.add(new PriceData(start.plusDays(2), 149.30));
            prices.add(new PriceData(start.plusDays(3), 151.20));
            prices.add(new PriceData(start.plusDays(4), 153.40));

            return new Share(symbol, prices);
        }
    }



    static class MockShareRepository implements ShareRepository {

        @Override
        public void save(Share share) {
            System.out.println("[Repository] Saving data for: " + share.getSymbol());
        }

        @Override
        public Share findBySymbol(String symbol) {
            return null;
        }
    }
}