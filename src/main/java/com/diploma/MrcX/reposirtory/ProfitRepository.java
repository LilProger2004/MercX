package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfitRepository extends JpaRepository<Profit, UUID> {
}