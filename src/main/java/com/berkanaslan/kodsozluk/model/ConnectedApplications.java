package com.berkanaslan.kodsozluk.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@NoArgsConstructor
public class ConnectedApplications {
    private final static String FACEBOOK_PREFIX = "https://www.facebook.com/";
    private final static String TWITTER_PREFIX = "https://twitter.com/";
    private final static String INSTAGRAM_PREFIX = "https://www.instagram.com/";
    private final static String GITHUB_PREFIX = "https://github.com/";

    private String facebook;
    private String twitter;
    private String instagram;
    private String github;

    public String getFacebook() {
        return FACEBOOK_PREFIX + facebook;
    }

    public String getTwitter() {
        return TWITTER_PREFIX + twitter;
    }

    public String getInstagram() {
        return INSTAGRAM_PREFIX + instagram;
    }

    public String getGithub() {
        return GITHUB_PREFIX + github;
    }
}
