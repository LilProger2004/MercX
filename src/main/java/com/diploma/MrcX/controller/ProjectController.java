package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.*;
import com.diploma.MrcX.utils.FormatParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final OrderService orderService;

    private final OffersService offersService;

    private final FreelancerService freelancerService;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final ClientService clientService;

    @GetMapping("all")
    public String getAllProjects(Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("orders",orderService.findAll());
        return "project_list";
    }

    @PostMapping("add")
    public String addProject(@RequestBody String body,Authentication authentication) throws JsonProcessingException {
        Order order = new ObjectMapper().readValue(FormatParser.urlEncodedToJson(body), Order.class);
        order.setClient(clientService.findById(jwtUtils.getUserIdFromJWT(authentication)));
        orderService.save(order);
        return "redirect:/me";
    }

    @GetMapping("add")
    public String viewAddProject() {
        return "project_add";
    }
    @GetMapping("{id}")
    public String getProject(@PathVariable("id") UUID id, Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        boolean hasProject = offersService.existByFreelancerId(jwtUtils.getUserIdFromJWT(authentication));
        boolean isClient = clientService.existById(jwtUtils.getUserIdFromJWT(authentication));
        model.addAttribute("isClient", isClient);
        model.addAttribute("freelancer", hasProject);
        Order order = orderService.findBuId(id);
        model.addAttribute("project",order);
        LocalDate deadline = order.getCreatedAt().toLocalDate().plusDays(order.getDeadline());
        model.addAttribute("dateEnd",deadline);
        return "project_profile";
    }

    @GetMapping("addProjectToFreelncer/{orderId}")
    public String addProjectToFreelancer(@PathVariable("orderId") UUID orderId,Authentication authentication){
        offersService.addFreelancerToOrder(freelancerService.findById(jwtUtils.getUserIdFromJWT(authentication)), orderService.findBuId(orderId));
        return "redirect:/project/" + orderId;
    }

}
