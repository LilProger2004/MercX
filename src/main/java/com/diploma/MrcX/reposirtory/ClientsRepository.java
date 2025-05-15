package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, String> {
}