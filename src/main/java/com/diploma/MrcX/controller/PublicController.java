package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Category;
import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.reposirtory.CategoryRepository;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.*;
import com.diploma.MrcX.utils.FormatParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PublicController {

    private final KeycloakGroupService keycloakGroupService;

    private final FreelancerService freelancerService;

    private final ClientService clientService;

    private final OrderService orderService;

    private final CategoryRepository categoryRepository;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/")
    public String chooseType() {
        return "redirect:/chooseSide";
    }

    @GetMapping("/public/main")
    public String index(Model model,Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("frelancers",freelancerService.findAll());
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
            newFreelancer.setName(user.getUsername());
            newFreelancer.setFirstName(user.getFirstName());
            newFreelancer.setLastName(user.getLastName());
            newFreelancer.setEmail(user.getEmail());
            newFreelancer.setBalance(0);
            freelancerService.save(newFreelancer);
        }
        return "redirect:/set-category";
    }

    @GetMapping("/set-category")
    public String setCategory(Model model , Authentication authentication){
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("categories",categoryRepository.findAll());
        return "set-category";
    }

    @PostMapping("/set-category")
    public String setingCategory(@RequestBody String body , Authentication authentication) throws JsonProcessingException {
       Category category = new ObjectMapper().readValue(FormatParser.urlEncodedToJson(body), Category.class);
       freelancerService.updateCatogoryByFreelancerId(jwtUtils.getUserIdFromJWT(authentication),category);
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
            newClient.setBalance(0);
            clientService.save(newClient);
        }
        return "redirect:/public/main";
    }

    @GetMapping("/me")
    public String whoMe(Model model,Authentication authentication) throws JsonProcessingException {
        if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "freelancers")){
            User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),User.class);
            Freelancers freelancer = freelancerService.findById(user.getId());
            model.addAttribute("freelancer",freelancer);
            List<Order> completeOrders = orderService.findCompletedOrdersByFreelancerId(jwtUtils.getUserIdFromJWT(authentication));
            boolean hasCompleteOrders = !completeOrders.isEmpty();
            model.addAttribute("hasCompleteOrders" , hasCompleteOrders);
            model.addAttribute("completeProjects",completeOrders);
            model.addAttribute("hasCategory",freelancer.getCategory() != null);
            return "freelancer_profile";
        } else if (keycloakGroupService.isUserInGroup(jwtUtils.getUserIdFromJWT(authentication), "clients")) {
            boolean projectHave = false;
            Clients client = clientService.findById(jwtUtils.getUserIdFromJWT(authentication));
            model.addAttribute("client",client);
            List<Order> completeOrder = orderService.getSuccessOrders(Order.OrderStatus.COMPLETED, client.getId());
            boolean hasCompleteOrders = !completeOrder.isEmpty();
            model.addAttribute("hasCompleteOrders" , hasCompleteOrders);
            model.addAttribute("completeOrders", completeOrder);
            model.addAttribute("newOrders",orderService.getSuccessOrders(Order.OrderStatus.NEW, client.getId()));
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
    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("t",keycloakAdminService.getAllUsers());
        return "test";
    }

    @GetMapping("error")
    public String viewError(){
        return "error-custom";
    }
}