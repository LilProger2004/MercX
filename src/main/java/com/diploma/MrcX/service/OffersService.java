package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;

public interface OffersService {

    void addFreelancerToOrder(Freelancers freelancers , Order order);
    boolean existByFreelancerId(String id);
}
