package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.Files;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.FilesRepository;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final KeycloakGroupService keycloakGroupService;

    private final OrderService orderService;

    private final OffersService offersService;

    private final FreelancerService freelancerService;

    private final FilesRepository filesRepository;

    private final DocumentService documentService;

    private final ComputingProcentService computingProcentService;

    private final JwtUtils jwtUtils = new JwtUtils();


    private final ClientService clientService;


    @GetMapping("my-projects")
    public String getAllClients(Authentication authentication, Model model) {
        model.addAttribute("orders", orderService.findActiveOrdersByClientId(jwtUtils.getUserIdFromJWT(authentication), Order.OrderStatus.NEW));
        return "my_projects";
    }

    @GetMapping("my-projects/{id}")
    public String getProject(@PathVariable("id") UUID id, Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        Order order = orderService.findBuId(id);
        model.addAttribute("project",order);
        model.addAttribute("files", filesRepository.findByOrder_Id(id));
        model.addAttribute("offers",offersService.findByOrderId(id));
        model.addAttribute("hasFreelancer" , order.getFreelancer() != null);
        LocalDate deadline = order.getCreatedAt().plusDays(order.getDeadline());
        model.addAttribute("dateEnd",deadline);
        return "my-project-for-client";
    }

    @GetMapping("my-projects/addFreelancer/{freelancerId}/toOrder/{orderId}")
    public String add(@PathVariable("freelancerId") String freelancerId , @PathVariable("orderId") UUID orderId,Authentication authentication){
        Clients clients = clientService.findById(jwtUtils.getUserIdFromJWT(authentication));
        Order order = orderService.findBuId(orderId);
        clients.setBalance(clients.getBalance() - order.getPrice());
        offersService.deleteFrelancer(freelancerService.findById(freelancerId), order);
        orderService.addFreelancerToOrder(freelancerService.findById(freelancerId) , orderId);
        clientService.save(clients);
        return "redirect:/client/my-projects/" + orderId;
    }

    @GetMapping("my-projects/file/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        Files document = filesRepository.findById(id).orElseThrow();
        Resource resource = documentService.loadFileAsResource(document.getFilePath());

        String encodedFileName = URLEncoder.encode(document.getName(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("my-projects/success/{id}")
    public String success(@PathVariable("id") UUID id){
        Order order = orderService.findBuId(id);
        order.setStatus(Order.OrderStatus.COMPLETED);
        Freelancers freelancer = order.getFreelancer();
        double tax = (order.getPrice() * computingProcentService.getTaxPercent()) / 100;
        double profit = (order.getPrice() * computingProcentService.getTaxPercent()) / 100;
        freelancer.setBalance(order.getPrice() - tax - profit);
        orderService.save(order);
        freelancerService.save(freelancer);
        return "redirect:/me";
    }
}