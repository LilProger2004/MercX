package com.diploma.MrcX.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
public class PublicController {

    @GetMapping("/")
    public String index() {
        return "index";
       /* OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new HashMap() {{
            put("hello", user.getAttribute("name"));
            put("your email is", user.getAttribute("email"));
        }};*/
    }

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello, world! This endpoint is public.";
    }
}