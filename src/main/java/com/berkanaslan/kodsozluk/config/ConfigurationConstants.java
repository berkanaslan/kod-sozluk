package com.berkanaslan.kodsozluk.config;


public interface ConfigurationConstants {
    // JWT
    String TOKEN_HEADER = "X-Token";
    String TOKEN_PARAMETER = "jwt";
    String AUTHORIZATION_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String SECRET = "SECRET_!23";

    long TOKEN_EXPIRATION_TIME = 262_980_000_000L;
    long TOKEN_REFRESH_TIME = 262_980_000_000L;

    // URL
    String LOGIN = "/login";
}