package com.example.springsecurity.id_password.config;

import com.example.springsecurity.id_password.auth.model.UserAccount;
import com.example.springsecurity.id_password.auth.model.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PresetDataCommandLineRunnner implements CommandLineRunner {

    @Autowired
    private UserAccountRepository repos;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        repos.deleteAll();
        repos.save(new UserAccount("T10000000", encoder.encode("password"), "テストユーザ1"));
        repos.save(new UserAccount("T10000001", encoder.encode("password"), "テストユーザ2"));
        repos.save(new UserAccount("T10000002", encoder.encode("password"), "テストユーザ3"));
    }
}
