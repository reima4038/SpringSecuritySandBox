package com.example.springsecurity.id_password.auth;

import com.example.springsecurity.id_password.auth.model.UserAccount;
import com.example.springsecurity.id_password.auth.model.UserAccountDetails;
import com.example.springsecurity.id_password.auth.model.UserAccountRepository;
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

@Service
public class FormLoginUserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(FormLoginUserService.class);

    @Autowired
    private UserAccountRepository repos;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userId)) {
            logger.warn("ユーザー名が入力されていません。");
            throw new UsernameNotFoundException("ユーザー名が入力されていません。");
        }

        UserAccount foundAccount = repos.findByUserId(userId)
                .orElseThrow(() -> {
                    logger.warn(String.format("指定されたユーザー(%s)は存在しません。", userId));
                    throw new UsernameNotFoundException(String.format("指定されたユーザー(%s)は存在しません。", userId));
                });
        //TODO: 権限を管理する
        return new UserAccountDetails(foundAccount);
//        return new User(foundAccount.getUserId(), foundAccount.getPassword(), Collections.emptySet());
    }
}
