package com.berkanaslan.eksisozlukclone.model;


public class Principal {

    private static final String DELIMITER = "::";

    private final long userId;
    private final String username;
    private final String jwtString;


    private Principal(long userId, String username) {
        this.userId = userId;
        this.username = username;
        this.jwtString = toJWTString();
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }


    public String getJwtString() {
        return jwtString;
    }

    private String toJWTString() {
        return userId + DELIMITER + username;
    }


    public static Principal createFrom(User user) {
        if (user.getFirstName() != null) {
            return new Principal(user.getId(), user.getUsername());
        }

        return new Principal(user.getId(), user.getUsername());
    }

    public static Principal parseFromJWTString(String jwtString) {

        String[] splitedAuthenticatedUserStr = jwtString.split(DELIMITER);
        long id = Long.parseLong(splitedAuthenticatedUserStr[0]);
        String username = splitedAuthenticatedUserStr[1];

        return new Principal(id, username);
    }
}
