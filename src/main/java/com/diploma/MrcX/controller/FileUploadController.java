package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Files;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.FilesRepository;
import com.diploma.MrcX.service.BidService;
import com.diploma.MrcX.service.BidService2;
import com.diploma.MrcX.service.DocumentService;
import com.diploma.MrcX.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final OrderService orderService;

    private final BidService bidService;

    private final BidService2 bidService2;

    @PostMapping(value = "project/submit-solution/{orderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String processBidForm(
            @PathVariable UUID orderId,
            @RequestParam("solutionText") String comment,
            @RequestParam("documents") List<MultipartFile> documents,
            Model model) {
        Order order = orderService.findBuId(orderId);
        order.setSolutionComment(comment);

        try {
            bidService.placeBid(orderId, documents);
            return "redirect:/freelancer/myActiveProgects/" + orderId + "?success";
        } catch (Exception e) {

            return "redirect:/freelancer/myActiveProgects/" + orderId + "?fail";
        }
    }

    @PostMapping(value = "project/submit-comment/{orderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String processBidForm2(
            @PathVariable UUID orderId,
            @RequestParam("documents") List<MultipartFile> documents,
            Model model) {
        Order order = orderService.findBuId(orderId);

        try {
            bidService2.placeBid(order, documents);
            return "success";
        } catch (Exception e) {

            return "redirect:/freelancer/myActiveProgects/" + orderId + "?fail";
        }
    }

    @GetMapping(value = "project/submit-comment/{orderId}")
    public String get(@PathVariable UUID orderId,Model model){
        model.addAttribute("orderId",orderId);
        return "project_file_comment";
    }

}