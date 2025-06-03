package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.reposirtory.FilesRepository;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.ClientService;
import com.diploma.MrcX.service.FreelancerService;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.diploma.MrcX.service.OrderService;
import com.diploma.MrcX.utils.FormatParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/freelancer")
public class FreelancerController {

    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    private final JwtUtils jwtUtils = new JwtUtils();

    @GetMapping("all")
    public String getUsers(Model model,Authentication authentication) throws JsonProcessingException {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("freelancers",freelancerService.findAll());
        return "freelancers";
    }

    @GetMapping("myActiveProgects")
    private String active(Model model, Authentication authentication){
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("orders", orderService.findAllActiveByFreelancerId(jwtUtils.getUserIdFromJWT(authentication), Order.OrderStatus.NEW));

        return "my_projects_for_freelance";
    }

    @GetMapping("myActiveProgects/{id}")
    private String activeProject(@PathVariable("id") UUID id , Model model, Authentication authentication){
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        Order order = orderService.findBuId(id);
        LocalDate deadline = order.getCreatedAt().plusDays(order.getDeadline());
        model.addAttribute("hasSolution",order.getFiles().isEmpty());
        model.addAttribute("hasSolutionComment",order.getSolutionComment() == null);
        model.addAttribute("deadline" , deadline);
        model.addAttribute("order",order);
        return "my-project-for-freelancer";
    }

    @GetMapping("new-description")
    public String getNew(){
        return "save-description";
    }

    @PostMapping("new-description")
    public String getNew(@RequestBody String body,Authentication authentication) throws JsonProcessingException {
        Freelancers freelancers = freelancerService.findById(jwtUtils.getUserIdFromJWT(authentication));
        freelancers.setAboutMe(new ObjectMapper().readValue(FormatParser.urlEncodedToJson(body), Freelancers.class).getAboutMe());
        freelancerService.save(freelancers);

        return "redirect:/me";
    }

    @GetMapping("{id}")
    public String getFreelancerById(@PathVariable("id") String id , Model model){
        Freelancers freelancer = freelancerService.findById(id);
        model.addAttribute("freelancer",freelancer);
        List<Order> completeOrders = orderService.findCompletedOrdersByFreelancerId(freelancer.getId());
        boolean hasCompleteOrders = !completeOrders.isEmpty();
        model.addAttribute("hasCompleteOrders" , hasCompleteOrders);
        model.addAttribute("completeProjects",completeOrders);
        model.addAttribute("hasCategory",freelancer.getCategory() != null);
        return "freelancer_profile_for_other";
    }
}
