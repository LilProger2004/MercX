package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Offers;
import com.diploma.MrcX.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OffersRepository extends JpaRepository<Offers, UUID> {
    @Query("select (count(o) > 0) from Offers o where o.freelancer.id = ?1 and o.order.id = ?2")
    boolean existsByFreelancer_Id(String id,UUID orderId);

    @Query("select o from Offers o where o.order.id = ?1")
    List<Offers> findByOrder_Id(UUID id);

    @Transactional
    @Modifying
    @Query("delete from Offers o where o.freelancer.id = ?1")
    void deleteByFreelancer(String freelancer);

    @Transactional
    @Modifying
    @Query("delete from Offers o where o.freelancer = ?1 and o.order = ?2")
    int deleteByFreelancerAndOrder(Freelancers freelancer, Order order);
}