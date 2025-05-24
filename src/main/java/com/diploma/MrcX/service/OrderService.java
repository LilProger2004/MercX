package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void save(Order order);
    List<Order> findAll();

    void addFreelancerToOrder(Freelancers freelancer, UUID orderId);

    boolean existByFreelancerId(String id);

    Order findBuId(UUID id);
}
