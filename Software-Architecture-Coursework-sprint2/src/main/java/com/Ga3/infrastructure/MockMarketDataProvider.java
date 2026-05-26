package com.Ga3.infrastructure;

import com.Ga3.application.ports.MarketDataProvider;
import com.Ga3.domain.PriceData;
import com.Ga3.domain.Share;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Infrastructure adapter that implements the {@link MarketDataProvider} port
 * using deterministic mock data.
 *
 * In a production deployment this class would be replaced by an adapter that
 * calls a real source such as Yahoo Finance — no changes to any other layer
 * would be required.
 */
public class MockMarketDataProvider implements MarketDataProvider {

    @Override
    public Share fetchShareData(String symbol, LocalDate start, LocalDate end) {

        List<PriceData> prices = new ArrayList<>();

        // Walk day-by-day across the requested range and produce a price
        // that drifts deterministically from a symbol-derived base value.
        double base = 100.0 + Math.abs(symbol.hashCode() % 50);
        LocalDate current = start;
        int day = 0;

        while (!current.isAfter(end)) {
            double price = base + Math.sin(day / 5.0) * 5.0 + (day * 0.10);
            prices.add(new PriceData(current, Math.round(price * 100.0) / 100.0));
            current = current.plusDays(1);
            day++;
        }

        return new Share(symbol, prices);
    }
}
