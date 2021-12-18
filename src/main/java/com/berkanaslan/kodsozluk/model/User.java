package com.berkanaslan.kodsozluk.model;

import com.berkanaslan.kodsozluk.audit.Auditable;
import com.berkanaslan.kodsozluk.config.LowerCase;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Locale;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(indexes = {@Index(columnList = "username")})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends Auditable implements BaseEntity {
    public static final String SYSTEM = "SYSTEM";
    public static final String SUPER_ADMIN_USERNAME = "superadmin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public enum Role {
        ADMIN, EDITOR, USER
    }

    @LowerCase
    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @LowerCase
    @Column(nullable = false)
    private String firstName;

    @LowerCase
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean blocked = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @PrePersist
    @PreUpdate
    public void preOperations() {
        setUsername(getUsername().toLowerCase(Locale.ROOT));
        setFirstName(getFirstName().toLowerCase(Locale.ROOT));
        setLastName(getLastName().toLowerCase(Locale.ROOT));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public interface Info extends BaseEntity.Info {
        String getUsername();
    }
}
