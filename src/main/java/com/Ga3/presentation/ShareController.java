package com.Ga3.presentation;

import com.Ga3.application.ShareService;
import com.Ga3.domain.Share;

import java.time.LocalDate;

public class ShareController {

    private final ShareService shareService;

    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    public Share requestShare(String symbol,
                              LocalDate start,
                              LocalDate end) {

        return shareService.getShareData(symbol, start, end);
    }
}