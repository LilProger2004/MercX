package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;

import java.util.List;

public interface FreelancerService {
    boolean existById(String id);

    void save(Freelancers freelancer);

    Freelancers findById(String id);

    List<Freelancers> findAll();
}
