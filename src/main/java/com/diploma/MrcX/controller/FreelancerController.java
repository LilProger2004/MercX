package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.ClientService;
import com.diploma.MrcX.service.FreelancerService;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/freelancer")
public class FreelancerController {

    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private ClientService clientService;

    private final JwtUtils jwtUtils = new JwtUtils();

    public FreelancerController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("all")
    public String getUsers(Model model,Authentication authentication) throws JsonProcessingException {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("freelancers",freelancerService.findAll());
        return "freelancers";
    }
}
