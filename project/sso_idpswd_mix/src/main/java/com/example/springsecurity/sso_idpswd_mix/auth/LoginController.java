package com.example.springsecurity.sso_idpswd_mix.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    // ログインエラー(/login?error)も一緒に処理するためワイルドカード(**)付加
    @GetMapping("/login**")
    public String login() {
        return "login";
    }

}
