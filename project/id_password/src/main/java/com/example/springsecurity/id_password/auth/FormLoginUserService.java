package com.example.springsecurity.id_password.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Objects;

@Service
public class FormLoginUserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(FormLoginUserService.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            logger.warn("ユーザー名が入力されていません。");
            throw new UsernameNotFoundException("ユーザー名が入力されていません。");
        }

        // TODO: 認証処理の実装 （FIXME: 以下のコードは仮）
        switch (username) {
            case "user":
                final String password = encoder.encode("4038");
                return new User(username, password, Collections.emptySet());
            default:
                logger.warn(String.format("指定されたユーザー(%s)は存在しません。", username));
                throw new UsernameNotFoundException(String.format("指定されたユーザー(%s)は存在しません。", username));
        }
    }
}
