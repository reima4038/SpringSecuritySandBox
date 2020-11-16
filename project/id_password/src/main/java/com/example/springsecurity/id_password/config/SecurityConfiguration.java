package com.example.springsecurity.id_password.config;

import com.example.springsecurity.id_password.auth.FormLoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/top").hasAnyAuthority("ROLE_ADMIN", "ROLE_NORMAL")
                .antMatchers("/error").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/top")
                .failureUrl("/login?error");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

        // h2-console有効化 参考：https://qiita.com/rubytomato@github/items/8eee9e3fa86c89dd305c
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}