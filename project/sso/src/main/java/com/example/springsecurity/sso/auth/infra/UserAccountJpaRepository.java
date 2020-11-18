package com.example.springsecurity.sso.auth.infra;

import com.example.springsecurity.sso.auth.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, String> {
    public Optional<UserAccount> findByUserId(String userId);
}
