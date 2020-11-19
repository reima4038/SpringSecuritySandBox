package com.example.springsecurity.sso_idpswd_mix.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Predicate;

public class SSOAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(SSOAuthenticationEntryPoint.class);

    final String REQUEST_HEADER_USER_ID = "x-uid";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 認証失敗時後の制御としてリダイレクト先を変えるサンプル
        final String redirectURLA = "https://www.google.com/?hl=ja";
        final String redirectURLB = "https://www.yahoo.co.jp/";
        final String redirectURLC = "https://www.youtube.com/";
        final String credential = request.getHeader(REQUEST_HEADER_USER_ID);
        Predicate<String> isTUser = v -> v.contains("T");
        Predicate<String> isBUser = v -> v.contains("B");

        if(StringUtils.isEmpty(credential)) {
            logger.warn("ログインに失敗しました。認証情報が設定されていません。");
            httpResponse.sendRedirect(redirectURLA);
        } else if(isTUser.test(credential)) {
            logger.debug("ログインに失敗しました。（T区分ユーザ）");
            httpResponse.sendRedirect(redirectURLB);
        } else if(isBUser.test(credential)) {
            logger.debug("ログインに失敗しました。（B区分ユーザ）");
            httpResponse.sendRedirect(redirectURLC);
        } else {
            // nothing to do...
        }
    }
}
