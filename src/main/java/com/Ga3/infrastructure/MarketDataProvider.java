package com.Ga3.infrastructure;

import com.Ga3.domain.Share;
import java.time.LocalDate;

public interface MarketDataProvider {

    Share fetchShareData(String symbol,
                         LocalDate start,
                         LocalDate end);
}