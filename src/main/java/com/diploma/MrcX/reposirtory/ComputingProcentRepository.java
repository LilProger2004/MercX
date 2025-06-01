package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.ComputingProcent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComputingProcentRepository extends JpaRepository<ComputingProcent, String> {
}