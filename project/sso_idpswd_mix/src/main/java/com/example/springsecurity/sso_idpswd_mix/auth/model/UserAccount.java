package com.example.springsecurity.sso_idpswd_mix.auth.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false, updatable = true)
    private String password;
    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false, updatable = true)
    private String fullName;
    @Column(nullable = false, updatable = true)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name="USER_ID"), foreignKey = @ForeignKey(name="FK_AUTH_ROLE"))
    private Set<Role> roles = new LinkedHashSet<>();

    public UserAccount() {}
    public UserAccount(String userId, String password, String fullName) {
        this.userId = Objects.requireNonNull(userId);
        this.password = Objects.requireNonNull(password);
        this.fullName = Objects.requireNonNull(fullName);
    }
    public UserAccount(String userId, String password, String fullName, Set<Role> roles) {
        this.userId = Objects.requireNonNull(userId);
        this.password = Objects.requireNonNull(password);
        this.fullName = Objects.requireNonNull(fullName);
        this.roles = Objects.requireNonNull(roles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
