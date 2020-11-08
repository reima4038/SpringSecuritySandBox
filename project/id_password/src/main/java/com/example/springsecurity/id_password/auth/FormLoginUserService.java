package com.example.springsecurity.id_password.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class FormLoginUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional.ofNullable(username)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザー名が入力されていません。"));
        // TODO: 認証処理の実装 （FIXME: 以下のコードは仮）
        final String password = encoder.encode("4038");
        return new User(username, password, Collections.emptySet());
    }
}
