package com.example.springsecurity.sso_idpswd_mix.config;

import com.example.springsecurity.sso_idpswd_mix.FormLoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.function.Predicate;

public class AlterDaoAuthenticationProvider extends DaoAuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(AlterDaoAuthenticationProvider.class);

    public AlterDaoAuthenticationProvider() {
        super();
    }

    private final Predicate<String> isTrust = userId -> userId.matches("^T[0-9]+");
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        logger.info("============== 認証処理 ==============");
        logger.info(String.format("ユーザID: %s", userDetails.getUsername()));
        logger.info(String.format("所属: %s", isTrust.test(userDetails.getUsername())));

        if(isTrust.test(userDetails.getUsername())) {
            logger.info("--- T所属ユーザーの認証を実施 ---");
            // T所属のユーザーはパスワードのマッチングをしない
            authenticationChecksWithoutPassword(userDetails, authentication);
        } else {
            logger.info("--- B所属ユーザーの認証を実施 ---");
            // B所属のユーザーはデフォルトの認証を実施
            super.additionalAuthenticationChecks(userDetails, authentication);
        }

    }

    private void authenticationChecksWithoutPassword(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

}
