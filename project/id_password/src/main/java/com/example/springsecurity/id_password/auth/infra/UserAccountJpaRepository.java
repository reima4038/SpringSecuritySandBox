package com.example.springsecurity.id_password.auth.infra;

import com.example.springsecurity.id_password.auth.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, String> {
    public Optional<UserAccount> findByUserId(String userId);
}
