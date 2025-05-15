package com.diploma.MrcX.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("all")
    public String getAllProjects(Model model, Authentication authentication) {
        boolean auth = authentication != null;
        model.addAttribute("auth",auth);
        return "project_list";
    }

    @PostMapping("add")
    public String addProject() {
        return "project_add";
    }
    @PostMapping("get")
    public String getProject() {
        return "project_profile";
    }

}
