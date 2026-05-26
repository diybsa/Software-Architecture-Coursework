package com.Ga3.presentation;

import com.Ga3.domain.Share;
import java.time.LocalDate;

/**
 * Provided interface of the Presentation component.
 *
 * Any client (the Main composition root, an HTTP framework, or a UI layer)
 * talks to the system through this contract. Concrete implementation:
 * {@link ShareController}.
 */
public interface ShareRequestHandler {

    /**
     * Handle a user request for share data over a date range.
     *
     * @return the populated Share, or throws IllegalArgumentException if
     *         the input fails business validation.
     */
    Share requestShare(String symbol, LocalDate start, LocalDate end);
}
