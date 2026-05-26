package com.Ga3.application;

import com.Ga3.application.usecases.CompareTwoSharesUseCase;
import com.Ga3.application.usecases.GetShareDataUseCase;
import com.Ga3.domain.Share;

import java.time.LocalDate;

/**
 * Application service (façade) over the available use cases.
 *
 * The Presentation layer talks to ShareService; ShareService delegates to
 * the relevant use case object. This keeps the controller free of business
 * rules and keeps use cases independently testable.
 *
 * Provided interface: getShareData, compareShares.
 * Required ports (indirectly, via use cases): MarketDataProvider, ShareRepository.
 */
public class ShareService {

    private final GetShareDataUseCase getShareData;
    private final CompareTwoSharesUseCase compareTwoShares;

    public ShareService(GetShareDataUseCase getShareData,
                        CompareTwoSharesUseCase compareTwoShares) {
        this.getShareData = getShareData;
        this.compareTwoShares = compareTwoShares;
    }

    /** Business operation: get data for a single share over a date range. */
    public Share getShareData(String symbol, LocalDate start, LocalDate end) {
        return getShareData.execute(symbol, start, end);
    }

    /** Business operation: compare two shares over the same date range. */
    public CompareTwoSharesUseCase.Result compareShares(String symbolA,
                                                        String symbolB,
                                                        LocalDate start,
                                                        LocalDate end) {
        return compareTwoShares.execute(symbolA, symbolB, start, end);
    }
}
