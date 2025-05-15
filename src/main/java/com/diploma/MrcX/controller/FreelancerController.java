package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectweb.asm.TypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/freelancer")
public class FreelancerController {

    private final JwtUtils jwtUtils = new JwtUtils();

    public FreelancerController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("all")
    public String getUsers(Model model,Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        //List<User> users = new ObjectMapper().readValue(keycloakAdminService.getAllUsers(), new TypeReference({}));
        return "freelancers";
    }
}
