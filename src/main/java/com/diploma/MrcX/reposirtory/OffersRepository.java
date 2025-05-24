package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OffersRepository extends JpaRepository<Offers, UUID> {
    @Query("select (count(o) > 0) from Offers o where o.freelancer.id = ?1")
    boolean existsByFreelancer_Id(String id);
}