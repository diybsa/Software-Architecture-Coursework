package com.Ga3.presentation;

import com.Ga3.application.ShareService;
import com.Ga3.domain.Share;

import java.time.LocalDate;

/**
 * Presentation-layer entry point.
 *
 * Implements the {@link ShareRequestHandler} provided interface and delegates
 * to the {@link ShareService} application façade. Contains no business logic
 * — its only job is to translate inbound requests into use case invocations.
 */
public class ShareController implements ShareRequestHandler {

    private final ShareService shareService;

    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @Override
    public Share requestShare(String symbol, LocalDate start, LocalDate end) {
        return shareService.getShareData(symbol, start, end);
    }
}
