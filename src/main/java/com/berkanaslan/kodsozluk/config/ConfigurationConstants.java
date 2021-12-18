package com.berkanaslan.kodsozluk.config;


public interface ConfigurationConstants {

    // JWT
    String TOKEN_HEADER = "X-Token";
    String TOKEN_PARAMETER = "jwt";
    String AUTHORIZATION_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String SECRET = "SECRET_!23";
    long TOKEN_EXPIRATION_TIME = 28_800_000;
    long TOKEN_REFRESH_TIME = 3_600_000; // 60 minutes - before expire token

    // Header
    String AUTHENTICATION_ERROR_HEADER_KEY = "X-Auth-Error";

    // Authentication Error Values
    String AUTHENTICATION_ERROR_BAD_CRED = "BadCredentials";
    String AUTHENTICATION_ERROR_DISABLED = "Disabled";
    String AUTHENTICATION_ERROR_LOCKED = "Locked";
    String AUTHENTICATION_ERROR_INTERNAL_SERVICE_ERROR = "ServiceError";
    String AUTHENTICATION_ERROR_USER_NOT_FOUND_ERROR = "UserNotFoundError";

    // URL
    String PERMIT_ALL = "/**";
    String LOGIN_URL = "/login";
    String ENTRY = "/entry";
    String TITLE = "/title";

    String TITLE_URL = TITLE + PERMIT_ALL;
    String ENTRY_URL = ENTRY + PERMIT_ALL;
}
