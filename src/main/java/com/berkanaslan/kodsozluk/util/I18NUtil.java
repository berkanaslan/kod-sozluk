package com.berkanaslan.kodsozluk.util;


import com.berkanaslan.kodsozluk.config.LocaleContextHolder;
import com.berkanaslan.kodsozluk.model.core.LocalizedString;

import java.util.Locale;
import java.util.ResourceBundle;


public class I18NUtil {
    private static final String BASE_PROPERTY_NAME = "i18n";

    public static String getMessageByLocale(String messageKey, Object... args) {
        return String.format(new LocalizedString(messageKey, Locale.forLanguageTag(LocaleContextHolder.getLocaleKey())).getLocalizedMessage(), args);
    }

    public static String getMessageFromBundle(String messageKey, Locale locale) {
        return ResourceBundle.getBundle(BASE_PROPERTY_NAME, locale).getString(messageKey);
    }
}
