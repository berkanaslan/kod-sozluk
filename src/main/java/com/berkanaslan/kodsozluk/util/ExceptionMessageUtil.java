package com.berkanaslan.kodsozluk.util;


import com.berkanaslan.kodsozluk.config.LocaleContextHolder;
import com.berkanaslan.kodsozluk.model.core.LocalizedException;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionMessageUtil {
    private static final String BASE_PROPERTY_NAME = "messages";
    private static final String EN_US = "en-US";


    public static String getMessageByLocale(String messageKey) {
        String localeKey = LocaleContextHolder.getLocaleKey();

        if (localeKey == null) localeKey = EN_US;

        LocalizedException localizedException = new LocalizedException(messageKey, Locale.forLanguageTag(localeKey));
        return localizedException.getLocalizedMessage();
    }

    public static String getMessageFromBundle(String messageKey, Locale locale) {
        return ResourceBundle.getBundle(BASE_PROPERTY_NAME, locale).getString(messageKey);
    }
}
