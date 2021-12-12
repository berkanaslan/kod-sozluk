package com.berkanaslan.eksisozlukclone.model.core;

import com.berkanaslan.eksisozlukclone.util.ExceptionMessageUtil;

import java.util.Locale;

public class LocalizedException extends Exception {
    private final String messageKey;
    private final Locale localeKey;

    public LocalizedException(String messageKey, Locale locale) {
        this.messageKey = messageKey;
        this.localeKey = locale;
    }

    public String getLocalizedMessage() {
        return ExceptionMessageUtil.getMessageFromBundle(messageKey, localeKey);
    }
}

