package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("select (count(o) > 0) from Order o where o.freelancer.id = ?1")
    boolean existsByFreelancer_Id(String id);

    @Transactional
    @Modifying
    @Query("update Order o set o.freelancer = ?1 where o.id = ?2")
    void updateFreelancerById(Freelancers freelancer, UUID id);
}