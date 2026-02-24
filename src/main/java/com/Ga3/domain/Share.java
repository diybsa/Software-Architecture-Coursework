package com.Ga3.domain;

import java.util.List;

public class Share {

    private String symbol;
    private List<PriceData> priceHistory;

    public Share(String symbol, List<PriceData> priceHistory) {
        this.symbol = symbol;
        this.priceHistory = priceHistory;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<PriceData> getPriceHistory() {
        return priceHistory;
    }
}