package com.example.springsecurity.sso.auth.model;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository {
    public Optional<UserAccount> findById(String id);
    public Optional<UserAccount> findByUserId(String userId);
    public List<UserAccount> findAll();
    public void save(UserAccount userAccount);
    public void deleteById(String id);
    public void deleteAll();
}
