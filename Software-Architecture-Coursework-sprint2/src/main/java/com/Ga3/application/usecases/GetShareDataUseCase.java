package com.Ga3.application.usecases;

import com.Ga3.application.ports.MarketDataProvider;
import com.Ga3.application.ports.ShareRepository;
import com.Ga3.domain.Share;

import java.time.LocalDate;

/**
 * Use case: Get Share Data.
 *
 * Encapsulates the application-specific rules for retrieving share data:
 *   1. Validate that start date is before end date
 *   2. Validate that the requested range does not exceed two years
 *   3. Fetch from the MarketDataProvider port
 *   4. Persist via the ShareRepository port
 *   5. Return the Share to the caller
 *
 * This is the single source of truth for the "Get Share Data" business rule.
 * ShareService delegates here so the rule is not duplicated.
 */
public class GetShareDataUseCase {

    private static final int MAX_RANGE_YEARS = 2;

    private final MarketDataProvider marketDataProvider;
    private final ShareRepository repository;

    public GetShareDataUseCase(MarketDataProvider marketDataProvider,
                               ShareRepository repository) {
        this.marketDataProvider = marketDataProvider;
        this.repository = repository;
    }

    public Share execute(String symbol, LocalDate start, LocalDate end) {

        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be empty");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (start.plusYears(MAX_RANGE_YEARS).isBefore(end)) {
            throw new IllegalArgumentException(
                    "Date range cannot exceed " + MAX_RANGE_YEARS + " years");
        }

        Share share = marketDataProvider.fetchShareData(symbol, start, end);
        repository.save(share);
        return share;
    }
}
