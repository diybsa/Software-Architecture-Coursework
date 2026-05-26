package com.Ga3.application.ports;

import com.Ga3.domain.Share;
import java.time.LocalDate;

/**
 * Port for retrieving share price data from an external source.
 *
 * Owned by the application layer (this package). Concrete implementations
 * live in the infrastructure layer. This direction of dependency satisfies
 * the Dependency Inversion Principle: the application layer depends only
 * on this abstraction, never on a concrete data source.
 */
public interface MarketDataProvider {

    /**
     * Retrieve daily price history for the given share over the given range.
     *
     * @param symbol ticker symbol (e.g. "AAPL")
     * @param start  inclusive start date
     * @param end    inclusive end date
     * @return a Share populated with PriceData entries
     */
    Share fetchShareData(String symbol, LocalDate start, LocalDate end);
}
