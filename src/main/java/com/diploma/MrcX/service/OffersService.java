package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Offers;
import com.diploma.MrcX.model.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OffersService {

    void addFreelancerToOrder(Freelancers freelancers , Order order);
    boolean existByFreelancerId(String id, UUID orderId);

    void deleteFrelancer(Freelancers freelancer, Order order);

    List<Offers> findByOrderId(UUID id);
}
