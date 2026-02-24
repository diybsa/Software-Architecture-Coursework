package com.Ga3.infrastructure;

import com.Ga3.domain.Share;

public interface ShareRepository {

    void save(Share share);

    Share findBySymbol(String symbol);
}