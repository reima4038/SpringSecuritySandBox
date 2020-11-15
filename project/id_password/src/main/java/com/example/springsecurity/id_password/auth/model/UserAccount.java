package com.example.springsecurity.id_password.auth.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    public UserAccount() {}
    public UserAccount(String userId, String password, String fullName) {
        this.userId = Objects.requireNonNull(userId);
        this.password = Objects.requireNonNull(password);
        this.fullName = Objects.requireNonNull(fullName);
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
}
