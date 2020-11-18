package com.example.springsecurity.sso;

import com.example.springsecurity.sso.auth.model.UserAccount;
import com.example.springsecurity.sso.auth.model.UserAccountDetails;
import com.example.springsecurity.sso.auth.model.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class SSOAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    Logger logger = LoggerFactory.getLogger(SSOAuthenticationUserDetailsService.class);

    @Autowired
    private UserAccountRepository repos;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String credentials = token.getCredentials().toString();
        if(StringUtils.isEmpty(credentials)) {
            logger.warn("認証情報が入力されていません。");
            throw new UsernameNotFoundException("認証情報が入力されていません。");
        }

        UserAccount foundAccount = repos.findByUserId(credentials)
                .orElseThrow(() -> {
                    logger.warn(String.format("指定されたユーザー(%s)は存在しません。", credentials));
                    throw new UsernameNotFoundException(String.format("指定されたユーザー(%s)は存在しません。", credentials));
                });
        return new UserAccountDetails(foundAccount);
    }
}
