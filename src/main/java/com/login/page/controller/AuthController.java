package com.login.page.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/auth")
    public Authentication getAuth(Authentication authentication) {
        return authentication;
    }
}
