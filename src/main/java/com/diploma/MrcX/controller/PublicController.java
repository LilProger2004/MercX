package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.ClientService;
import com.diploma.MrcX.service.FreelancerService;
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

    private final FreelancerService freelancerService;

    private final ClientService clientService;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/")
    public String chooseType() {
        return "redirect:/chooseSide";
    }

    @GetMapping("/public/main")
    public String index(Model model,Authentication authentication) {
        boolean auth = authentication != null;
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
    public String addToFreelancerGroup(Authentication authentication) throws JsonProcessingException {
        String id = jwtUtils.getUserIdFromJWT(authentication);
        keycloakGroupService.addUserToGroup(id,keycloakGroupService.getGroupIdByName("freelancers"));
        if (!freelancerService.existById(id)){
            User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(id), User.class);
            Freelancers newFreelancer = new Freelancers();
            newFreelancer.setId(user.getId());
            newFreelancer.setFirstName(user.getFirstName());
            newFreelancer.setLastName(user.getLastName());
            newFreelancer.setEmail(user.getEmail());
            freelancerService.save(newFreelancer);
        }
        return "redirect:/public/main";
    }

    @GetMapping("/client")
    public String addToClientGroup(Authentication authentication) throws JsonProcessingException {
        String id = jwtUtils.getUserIdFromJWT(authentication);
        keycloakGroupService.addUserToGroup(id,keycloakGroupService.getGroupIdByName("clients"));
        if (!clientService.existById(id)){
            User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(id), User.class);
            Clients newClient = new Clients();
            newClient.setId(user.getId());
            newClient.setName(user.getUsername());
            newClient.setEmail(user.getEmail());
            newClient.setFirstName(user.getFirstName());
            newClient.setLastName(user.getLastName());
            clientService.save(newClient);
        }
        return "redirect:/public/main";
    }

    @GetMapping("/me")
    public String whoMe(Model model,Authentication authentication) throws JsonProcessingException {
        if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "freelancers")){
            User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),User.class);
            model.addAttribute("user",user);
            return "freelancer_profile";
        } else if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "clients")) {
            boolean projectHave = false;
            Clients client = clientService.findById(jwtUtils.getUserIdFromJWT(authentication));
            model.addAttribute("client",client);
            if (client.getOrders().size() == 1){
                model.addAttribute("order0" , client.getOrders().get(0));
                projectHave = true;
            }if (client.getOrders().size() == 2){
                model.addAttribute("order1" , client.getOrders().get(1));
                projectHave = true;
            }

            return "client-profile";
        }
            return "redirect:/chooseSide";
    }

    @GetMapping("/news")
    public String viewNews(Model model,Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        return "news";
    }
}