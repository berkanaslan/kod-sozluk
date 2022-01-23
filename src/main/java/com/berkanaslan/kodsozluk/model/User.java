package com.berkanaslan.kodsozluk.model;

import com.berkanaslan.kodsozluk.audit.Auditable;
import com.berkanaslan.kodsozluk.config.LowerCase;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_table", indexes = {@Index(columnList = "username")})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends Auditable implements BaseEntity {
    public static final String SYSTEM = "SYSTEM";
    public static final String SUPER_ADMIN_USERNAME = "superadmin";

    public enum Gender {FEMALE, MALE, OTHER, UNDEFINED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public enum Role {
        ADMIN, EDITOR, USER
    }

    @LowerCase
    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean blocked = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Embedded
    private ConnectedApplications connectedApplications;

    @JsonIgnore
    @JoinTable(name = "follower_relation",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")})
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> following = new HashSet<>();

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "following")
    private Set<User> followers = new HashSet<>();

    @Column(nullable = false)
    private long entryCount;

    @Column(nullable = false)
    private long followersCount;

    @Column(nullable = false)
    private long followingCount;

    @PrePersist
    @PreUpdate
    public void preOperations() {
        setUsername(getUsername().toLowerCase(Locale.ROOT));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public interface Info extends BaseEntity.Info {
        String getUsername();
    }
}
