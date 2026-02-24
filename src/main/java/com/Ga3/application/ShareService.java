package com.Ga3.application;

import com.Ga3.domain.Share;
import com.Ga3.infrastructure.MarketDataProvider;
import com.Ga3.infrastructure.ShareRepository;

import java.time.LocalDate;

public class ShareService {

    private final MarketDataProvider marketDataProvider;
    private final ShareRepository repository;

    public ShareService(MarketDataProvider marketDataProvider,
                        ShareRepository repository) {
        this.marketDataProvider = marketDataProvider;
        this.repository = repository;
    }

    public Share getShareData(String symbol,
                              LocalDate start,
                              LocalDate end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (start.plusYears(2).isBefore(end)) {
            throw new IllegalArgumentException("Date range cannot exceed 2 years");
        }

        Share share = marketDataProvider.fetchShareData(symbol, start, end);
        repository.save(share);

        return share;
    }
}