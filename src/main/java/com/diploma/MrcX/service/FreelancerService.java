package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Category;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;

import java.util.List;

public interface FreelancerService {
    boolean existById(String id);

    void updateCatogoryByFreelancerId(String id, Category category);

    void save(Freelancers freelancer);

    Freelancers findById(String id);

    List<Freelancers> findAll();
}
