package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order save(Order order);
    List<Order> findAll();

    List<Order> findAllActiveByFreelancerId(String id, Order.OrderStatus status);

    List<Order> findAllActive();

    void addFreelancerToOrder(Freelancers freelancer, UUID orderId);

    List<Order> findActiveOrdersByClientId(String id , Order.OrderStatus status);

    List<Order> getSuccessOrders(Order.OrderStatus status,String clientId);

    List<Order> findCompletedOrdersByFreelancerId(String id);

    List<Order> findOrdersByFreeId(String id);

    Order findBuId(UUID id);
}
