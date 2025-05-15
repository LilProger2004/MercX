package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;

public interface FreelancerService {
    boolean existById(String id);

    void save(Freelancers freelancer);
}
