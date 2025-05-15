package com.diploma.MrcX.controller;

import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.diploma.MrcX.service.KeycloakGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final KeycloakGroupService keycloakGroupService;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("my-projects")
    public String getAllClients() {
        return "news";
    }
}