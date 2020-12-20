package com.example.springsecurity.sso_idpswd_mix.config;

import com.example.springsecurity.sso_idpswd_mix.FormLoginUserService;
import com.example.springsecurity.sso_idpswd_mix.SSOAuthenticationUserDetailsService;
import com.example.springsecurity.sso_idpswd_mix.auth.SSOPreAuthenticatedProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
//        return new SSOAuthenticationUserDetailsService();
//    }
//    @Bean
//    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
//        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
//        return provider;
//    }

    @Bean
    public FormLoginUserService formLoginUserService() {
        return new FormLoginUserService();
    }

    // AlterDaoAuthenticationProviderがうまくいくならこちらは不要
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(formLoginUserService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
    // T所属のユーザーのパスワードを判定しないAuthenticationProvider
    @Bean
    public AlterDaoAuthenticationProvider daoAuthenticationProvider() {
        AlterDaoAuthenticationProvider provider = new AlterDaoAuthenticationProvider();
        provider.setUserDetailsService(formLoginUserService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

//    @Bean
//    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
//        SSOPreAuthenticatedProcessingFilter filter = new SSOPreAuthenticatedProcessingFilter();
//        filter.setAuthenticationManager(authenticationManager());
//        return filter;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // SSO認証とForm認証の複合
//        auth.authenticationProvider(preAuthenticatedAuthenticationProvider())
//                .authenticationProvider(daoAuthenticationProvider());
        // SSO認証もForm認証の経路を利用する。
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/sso_login").permitAll()
                .antMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/top").hasAnyAuthority("ROLE_ADMIN", "ROLE_NORMAL")
                .antMatchers("/error").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        // SSO認証も /sso_login を経由して /login に POST する。
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/top")
                .failureUrl("/login?error");

        http.logout()
                .logoutUrl("/logout")
                .permitAll();

        // SSO認証もFormログイン経由で認証するのでこの方法では下記 Filter は使わない。
//        http.addFilter(preAuthenticatedProcessingFilter())
//                .exceptionHandling()
//                .authenticationEntryPoint(new SSOAuthenticationEntryPoint());

        // h2-console有効化 参考：https://qiita.com/rubytomato@github/items/8eee9e3fa86c89dd305c
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}