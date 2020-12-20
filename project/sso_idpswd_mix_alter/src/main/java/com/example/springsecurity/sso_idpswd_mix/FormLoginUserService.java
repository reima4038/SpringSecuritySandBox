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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Transactional
public class FormLoginUserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(FormLoginUserService.class);

    @Autowired
    private UserAccountRepository repos;



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        logger.info(String.format("FORMログイン ユーザID: %s", userId));
        final String ssoMark = "_is_sso";
        if(Objects.nonNull(userId) && userId.matches("^T[0-9]+") && !userId.contains(ssoMark)) {
            throw new UsernameNotFoundException("このユーザーはFORMログインを許可されていません。");
        }

        String rawUserId = Optional.ofNullable(userId)
                .map(uid -> uid.replace(ssoMark, ""))
                .orElse("");

        if (StringUtils.isEmpty(rawUserId)) {
            logger.warn("ユーザー名が入力されていません。");
            throw new UsernameNotFoundException("ユーザー名が入力されていません。");
        }

        UserAccount foundAccount = repos.findByUserId(rawUserId)
                .orElseThrow(() -> {
                    logger.warn(String.format("指定されたユーザー(%s)は存在しません。", rawUserId));
                    throw new UsernameNotFoundException(String.format("指定されたユーザー(%s)は存在しません。", rawUserId));
                });
        return new UserAccountDetails(foundAccount);
    }

}
