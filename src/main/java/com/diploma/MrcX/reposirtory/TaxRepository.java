package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaxRepository extends JpaRepository<Tax, UUID> {
}