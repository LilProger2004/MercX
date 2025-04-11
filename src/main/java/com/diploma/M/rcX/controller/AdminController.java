package com.diploma.M.rcX.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('admin')") // роль из Keycloak, например, "admin"
    @GetMapping("/dashboard")
    public String adminPanel() {
        return "Welcome to the admin dashboard!";
    }
}
