package com.Ga3.application.ports;

import com.Ga3.domain.Share;

/**
 * Port for persisting and retrieving share price data locally.
 *
 * Owned by the application layer. Concrete implementations live in the
 * infrastructure layer (in-memory mock, SQLite, JSON file, etc.). This
 * keeps the application layer independent of the chosen storage technology.
 */
public interface ShareRepository {

    /**
     * Persist the given Share so it can be retrieved later, including
     * when the system has no network connection.
     */
    void save(Share share);

    /**
     * Look up a previously stored Share by its ticker symbol.
     *
     * @return the stored Share, or null if none has been saved
     */
    Share findBySymbol(String symbol);
}
