package com.Ga3.infrastructure;

import com.Ga3.application.ports.ShareRepository;
import com.Ga3.domain.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * Infrastructure adapter that implements the {@link ShareRepository} port
 * using an in-memory map keyed by ticker symbol.
 *
 * Satisfies NFR1 (system functions without a network connection) in this
 * coursework. In production this would be replaced by a SQLite or JSON
 * adapter without changing any other layer.
 */
public class InMemoryShareRepository implements ShareRepository {

    private final Map<String, Share> store = new HashMap<>();

    @Override
    public void save(Share share) {
        if (share == null || share.getSymbol() == null) {
            return;
        }
        store.put(share.getSymbol(), share);
        System.out.println("[Repository] Saved " + share.getSymbol()
                + " (" + share.getPriceHistory().size() + " price points)");
    }

    @Override
    public Share findBySymbol(String symbol) {
        return store.get(symbol);
    }
}
