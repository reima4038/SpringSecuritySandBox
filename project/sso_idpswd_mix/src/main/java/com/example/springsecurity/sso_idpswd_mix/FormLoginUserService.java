package com.example.springsecurity.sso_idpswd_mix;

import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccount;
import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccountDetails;
import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Transactional
public class FormLoginUserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(FormLoginUserService.class);

    @Autowired
    private UserAccountRepository repos;

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
        return new UserAccountDetails(foundAccount);
    }
}
