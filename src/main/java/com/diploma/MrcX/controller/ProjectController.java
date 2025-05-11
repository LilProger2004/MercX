package com.diploma.MrcX.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/")
    public String getAllProjects() {
        return "project_list";
    }

    @GetMapping("/project")
    public String getProject() {
        return "project_profile";
    }

}
