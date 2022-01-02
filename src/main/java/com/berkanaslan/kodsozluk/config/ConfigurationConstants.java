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


    // URL
    String PERMIT_ALL = "/**";
    String LOGIN = "/login";

}
