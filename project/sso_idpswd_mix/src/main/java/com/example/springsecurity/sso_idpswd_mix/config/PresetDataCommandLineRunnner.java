package com.example.springsecurity.sso_idpswd_mix.config;

import com.example.springsecurity.sso_idpswd_mix.auth.model.Role;
import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccount;
import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class PresetDataCommandLineRunnner implements CommandLineRunner {

    @Autowired
    private UserAccountRepository repos;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        repos.deleteAll();
        final Set<Role> adminRoles = new HashSet<>(Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_NORMAL")));
        final Set<Role> normalRoles = new HashSet<>(Arrays.asList(new Role("ROLE_NORMAL")));
        repos.save(new UserAccount("T10000000", encoder.encode("password"), "テストユーザ1", adminRoles));
        repos.save(new UserAccount("T10000001", encoder.encode("password"), "テストユーザ2", normalRoles));
        repos.save(new UserAccount("T10000002", encoder.encode("password"), "テストユーザ3", normalRoles));
    }
}
