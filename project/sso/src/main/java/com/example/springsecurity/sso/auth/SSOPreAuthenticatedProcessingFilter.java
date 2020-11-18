package com.example.springsecurity.sso.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SSOPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {
    Logger logger = LoggerFactory.getLogger(SSOPreAuthenticatedProcessingFilter.class);

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        // TODO: Principalの使い方も調べておく
        return "";
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String credentials = request.getHeader("x-uid");
        logger.debug(": " + credentials);
        return Objects.isNull(credentials) ? "" : credentials;
    }
}
