package com.example.springsecurity.sso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

    @GetMapping("/top")
    public String top() {
        return "top";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
