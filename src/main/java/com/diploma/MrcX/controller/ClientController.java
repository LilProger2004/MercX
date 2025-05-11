package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.Client;
import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.diploma.MrcX.service.KeycloakGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final KeycloakGroupService keycloakGroupService;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/me")
    public String infoAboutMe(Model model,Authentication authentication) throws JsonProcessingException {
        Client currentClient = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),Client.class);
        model.addAttribute("id",currentClient);
        return "client-profile";
    }
}