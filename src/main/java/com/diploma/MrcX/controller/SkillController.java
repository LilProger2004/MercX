package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Skills;
import com.diploma.MrcX.model.pojo.SkillPojo;
import com.diploma.MrcX.reposirtory.SkillsRepository;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.FreelancerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SkillController {
    @Autowired
    private SkillsRepository repository;

    @Autowired
    private FreelancerService freelancerService;

    private final JwtUtils jwtUtils = new JwtUtils();


    @GetMapping("/api/skills/all")
    public String getSkills() throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(repository.findAll());
    }

    @PostMapping("/api/skills")
    public void addSkills(@RequestBody String body , Authentication authentication) throws JsonProcessingException {
        Freelancers freelancers = freelancerService.findById(jwtUtils.getUserIdFromJWT(authentication));
        SkillPojo skills = new ObjectMapper().readValue(body, SkillPojo.class);
        freelancers.getSkills().add(repository.findByNameIgnoreCase(skills.getName()));
    }
}
