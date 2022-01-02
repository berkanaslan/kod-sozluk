package com.berkanaslan.kodsozluk.model.core;

import com.berkanaslan.kodsozluk.util.I18NUtil;

import java.util.Locale;

public class LocalizedString extends Exception {
    private final String messageKey;
    private final Locale localeKey;

    public LocalizedString(String messageKey, Locale locale) {
        this.messageKey = messageKey;
        this.localeKey = locale;
    }

    public String getLocalizedMessage() {
        return I18NUtil.getMessageFromBundle(messageKey, localeKey);
    }
}

