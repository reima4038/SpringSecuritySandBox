package com.example.springsecurity.sso_idpswd_mix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TopController {

    @GetMapping("/top")
    public String top() {
        return "top";
    }

    @PostMapping("/top")
    public String top_post() {
        return "top";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
