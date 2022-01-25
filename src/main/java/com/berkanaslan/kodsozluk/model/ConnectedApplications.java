package com.berkanaslan.kodsozluk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class ConnectedApplications {
    private String facebook;
    private String twitter;
    private String instagram;
    private String github;
}
