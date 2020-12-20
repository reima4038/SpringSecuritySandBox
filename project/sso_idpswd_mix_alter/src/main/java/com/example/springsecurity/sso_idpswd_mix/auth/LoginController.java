package com.example.springsecurity.sso_idpswd_mix.auth;

import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccount;
import com.example.springsecurity.sso_idpswd_mix.auth.model.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private final String REQUEST_HEADER_USER_ID = "x-uid";
    private static final String SSO_USER_ID = "username";

    // ログインエラー(/login?error)も一緒に処理するためワイルドカード(**)付加
    @GetMapping("/login**")
    public String login() {
        return "login";
    }

    @PostMapping("/sso_login")
    public String sso_login(@RequestHeader(value = REQUEST_HEADER_USER_ID, required = true) String userId, Model model) {
        model.addAttribute(SSO_USER_ID, userId);
        return "sso_login";
    }

    @GetMapping("/sso_login")
    public String sso_login(Model model) {
        model.addAttribute(SSO_USER_ID, "T10000000");
        return "sso_login";
    }

}
