package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.OrderRepository;
import com.diploma.MrcX.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceIml implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void addFreelancerToOrder(Freelancers freelancer, UUID orderId) {
        orderRepository.updateFreelancerById(freelancer , orderId);
    }

    @Override
    public boolean existByFreelancerId(String id) {
        return orderRepository.existsByFreelancer_Id(id);
    }

    @Override
    public Order findBuId(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
