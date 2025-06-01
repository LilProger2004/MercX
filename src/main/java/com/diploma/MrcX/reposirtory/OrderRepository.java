package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("select (count(o) > 0) from Order o where o.freelancer.id = ?1")
    boolean existsByFreelancer_Id(String id);

    @Transactional
    @Modifying
    @Query("update Order o set o.freelancer = ?1 where o.id = ?2")
    void updateFreelancerById(Freelancers freelancer, UUID id);

    @Query("select o from Order o where o.freelancer.id = ?1")
    List<Order> findByFreelancer_Id(String id);

    @Query("select o from Order o where o.client.id = ?1 and o.status = ?2")
    List<Order> findByClient_IdAndStatus(String id, Order.OrderStatus status);

    @Query("select o from Order o where o.freelancer.id = ?1 and o.status = ?2")
    List<Order> findByFreelancer_IdAndStatus(String id, Order.OrderStatus status);

    @Query("select o from Order o where o.status = ?1")
    List<Order> findByStatus(Order.OrderStatus status);
}