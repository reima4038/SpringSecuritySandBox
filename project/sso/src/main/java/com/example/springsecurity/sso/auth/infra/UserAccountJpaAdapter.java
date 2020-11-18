package com.example.springsecurity.sso.auth.infra;

import com.example.springsecurity.sso.auth.model.UserAccount;
import com.example.springsecurity.sso.auth.model.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountJpaAdapter implements UserAccountRepository {

    @Autowired
    private UserAccountJpaRepository repos;

    @Override
    public Optional<UserAccount> findById(String id) {
        return repos.findById(id);
    }

    @Override
    public Optional<UserAccount> findByUserId(String userId) {
        return repos.findByUserId(userId);
    }

    @Override
    public List<UserAccount> findAll() {
        return repos.findAll();
    }

    @Override
    public void save(UserAccount userAccount) {
        repos.save(userAccount);
    }

    @Override
    public void deleteById(String id) {
        repos.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repos.deleteAll();
    }
}
