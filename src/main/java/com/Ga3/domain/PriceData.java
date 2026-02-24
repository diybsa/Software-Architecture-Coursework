package com.Ga3.domain;

import java.time.LocalDate;

public class PriceData {

    private LocalDate date;
    private double closePrice;

    public PriceData(LocalDate date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getClosePrice() {
        return closePrice;
    }
}