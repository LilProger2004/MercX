package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.CommentFiles;
import com.diploma.MrcX.model.entity.Files;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.model.pojo.CategoryPojo;
import com.diploma.MrcX.reposirtory.CategoryRepository;
import com.diploma.MrcX.reposirtory.CommentFilesRepository;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.*;
import com.diploma.MrcX.utils.FormatParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final OrderService orderService;

    private final OffersService offersService;

    private final FreelancerService freelancerService;

    private final CommentFilesRepository commentFilesRepository;

    private final DocumentService documentService;

    private final BidService2 bidService2;

    private final CategoryRepository categoryRepository;

    private final JwtUtils jwtUtils = new JwtUtils();

    private final ClientService clientService;

    @GetMapping("all")
    public String getAllProjects(Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        model.addAttribute("orders",orderService.findAllActive());
        return "project_list";
    }

    @PostMapping(value = "add")
    public String addProject(Model model , @RequestBody String body, Authentication authentication) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        System.out.println(FormatParser.urlEncodedToJson(body));
        Order order = mapper.readValue(FormatParser.urlEncodedToJson(body), Order.class);
        order.setCategori(categoryRepository.findById(mapper.readValue(FormatParser.urlEncodedToJson(body), CategoryPojo.class).getCategoryId()).orElseThrow(null));
        Clients clients = clientService.findById(jwtUtils.getUserIdFromJWT(authentication));
        order.setStatus(Order.OrderStatus.NEW);
        order.setCreatedAt(LocalDate.now());
        if (order.getPrice() > clients.getBalance()){
            model.addAttribute("errorMessage","Не достаточно средств");
            model.addAttribute("href","add");
            return "error-custom";
        }
        if (order.getCreatedAt().plusDays(order.getDeadline()).getDayOfMonth() <= LocalDate.now().getDayOfMonth() &&
                order.getCreatedAt().plusDays(order.getDeadline()).getMonthValue() <= LocalDate.now().getMonthValue() &&
                order.getCreatedAt().plusDays(order.getDeadline()).getYear() <= LocalDate.now().getYear()){
            model.addAttribute("errorMessage","Дедлайн не может быть раньше сегодняшней даты");
            model.addAttribute("href","add");
            return "error-custom";
        }
        clients.setBalance(clients.getBalance() - order.getPrice());
        order.setClient(clients);
        ;
        return "redirect:submit-comment/" + orderService.save(order).getId();
    }

    @GetMapping("add")
    public String viewAddProject(Model model ,Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        return "project_add";
    }
    @GetMapping("{id}")
    public String getProject(@PathVariable("id") UUID id, Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        boolean canAdd = !offersService.existByFreelancerId(jwtUtils.getUserIdFromJWT(authentication), id) && !clientService.existById(jwtUtils.getUserIdFromJWT(authentication));
        model.addAttribute("result", canAdd);
        Order order = orderService.findBuId(id);
        model.addAttribute("project",order);
        LocalDate deadline = order.getCreatedAt().plusDays(order.getDeadline());
        model.addAttribute("dateEnd",deadline);
        model.addAttribute("commentFiles", commentFilesRepository.findByOrder_Id(id));
        return "project_profile";
    }

    @GetMapping("file/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        CommentFiles document = commentFilesRepository.findById(id).orElseThrow();
        Resource resource = documentService.loadFileAsResource(document.getFilePath());

        String encodedFileName = URLEncoder.encode(document.getName(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("addProjectToFreelncer/{orderId}")
    public String addProjectToFreelancer(@PathVariable("orderId") UUID orderId,Authentication authentication,Model model){
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        offersService.addFreelancerToOrder(freelancerService.findById(jwtUtils.getUserIdFromJWT(authentication)), orderService.findBuId(orderId));
        return "redirect:/project/" + orderId;
    }

}
