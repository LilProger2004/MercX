package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.OrderRepository;
import com.diploma.MrcX.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceIml implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        order.setCreatedAt(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
       return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllActiveByFreelancerId(String id, Order.OrderStatus status) {
        return orderRepository.findByFreelancer_IdAndStatus(id, status);
    }

    @Override
    public List<Order> findAllActive() {
        return orderRepository.findByStatus(Order.OrderStatus.NEW);
    }

    @Override
    public void addFreelancerToOrder(Freelancers freelancer, UUID orderId) {
        orderRepository.updateFreelancerById(freelancer , orderId);
    }

    @Override
    public List<Order> findActiveOrdersByClientId(String id, Order.OrderStatus status) {
        return orderRepository.findByClient_IdAndStatus(id, status);
    }

    @Override
    public List<Order> getSuccessOrders(Order.OrderStatus status, String clientId) {
        return orderRepository.findByClient_IdAndStatus(clientId, status);
    }

    @Override
    public List<Order> findCompletedOrdersByFreelancerId(String id) {
        return orderRepository.findByFreelancer_IdAndStatus(id, Order.OrderStatus.COMPLETED);
    }

    @Override
    public List<Order> findOrdersByFreeId(String id) {
        return orderRepository.findByFreelancer_Id(id);
    }

    @Override
    public Order findBuId(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
