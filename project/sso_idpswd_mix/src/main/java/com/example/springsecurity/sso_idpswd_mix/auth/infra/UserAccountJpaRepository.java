package com.example.springsecurity.sso_idpswd_mix.auth.infra;

import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, String> {
    public Optional<UserAccount> findByUserId(String userId);
}
