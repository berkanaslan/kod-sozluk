package com.berkanaslan.eksisozlukclone.config;

import org.slf4j.MDC;

public class LocaleContextHolder {
    private static final String LOCALE_INFO = "localeInfo";
    private static final InheritableThreadLocal<String> LOCALE = new InheritableThreadLocal<>();

    private LocaleContextHolder() {
    }

    public static void setLocaleKey(final String localeKey) {
        LOCALE.set(localeKey);
        MDC.put(LOCALE_INFO, localeKey);
    }

    public static String getLocaleKey() {
        return LOCALE.get();
    }

    public static void clear() {
        LOCALE.remove();
        MDC.remove(LOCALE_INFO);
    }

}
