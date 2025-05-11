package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.pojo.Client;
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

@Controller
@RequiredArgsConstructor
public class PublicController {

    private final KeycloakGroupService keycloakGroupService;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/")
    public String chooseType() {
        return "redirect:/chooseSide";
    }

    @GetMapping("/public/main")
    public String index(Model model,Authentication authentication) {
        boolean auth = false;
        if (authentication != null){
            auth = true;
        }
        model.addAttribute("auth",auth);
        return "index2";
    }

    @GetMapping("/chooseSide")
    public String test(Authentication authentication) {
        if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "freelancers")
                ||
                keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "clients")) {
            return "redirect:/public/main";
        }
        return "login";
    }

    @GetMapping("/freelancer")
    public String addToFreelancerGroup(Authentication authentication) {
        keycloakGroupService.addUserToGroup(jwtUtils.getUserIdFromJWT(authentication),keycloakGroupService.getGroupIdByName("freelancers"));
        return "redirect:/public/main";
    }

    @GetMapping("/client")
    public String addToClientGroup(Authentication authentication) {
        keycloakGroupService.addUserToGroup(jwtUtils.getUserIdFromJWT(authentication),keycloakGroupService.getGroupIdByName("clients"));
        return "redirect:/public/main";
    }

    @GetMapping("/me")
    public String whoMe(Model model,Authentication authentication) throws JsonProcessingException {
        if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "freelancers")){
            User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),User.class);
            model.addAttribute("id",user);
            return "freelancer_profile";
        } else if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "clients")) {
            Client client = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),Client.class);
            model.addAttribute("id",client);
            return "client-profile";
        }
            return "redirect:/chooseSide";
    }
}