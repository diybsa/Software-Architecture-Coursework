package com.Ga3.application.usecases;

import com.Ga3.domain.Share;

import java.time.LocalDate;

/**
 * Use case: Compare Two Shares.
 *
 * Composes two {@link GetShareDataUseCase} executions to retrieve data for
 * two ticker symbols over the same date range. The result is a pair of
 * Share objects ready for the UI layer to render on the same chart.
 *
 * Delegating to GetShareDataUseCase keeps the validation and retrieval
 * rules in one place.
 */
public class CompareTwoSharesUseCase {

    private final GetShareDataUseCase getShareData;

    public CompareTwoSharesUseCase(GetShareDataUseCase getShareData) {
        this.getShareData = getShareData;
    }

    public Result execute(String symbolA, String symbolB,
                          LocalDate start, LocalDate end) {

        Share shareA = getShareData.execute(symbolA, start, end);
        Share shareB = getShareData.execute(symbolB, start, end);
        return new Result(shareA, shareB);
    }

    /** Immutable holder for the two retrieved shares. */
    public static final class Result {
        private final Share shareA;
        private final Share shareB;

        public Result(Share shareA, Share shareB) {
            this.shareA = shareA;
            this.shareB = shareB;
        }

        public Share getShareA() { return shareA; }
        public Share getShareB() { return shareB; }
    }
}
