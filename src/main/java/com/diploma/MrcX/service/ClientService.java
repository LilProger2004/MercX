package com.diploma.MrcX.service;

import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.Freelancers;

import java.util.List;

public interface ClientService {
    boolean existById(String id);

    void save(Clients client);

    List<Clients> findAll();

    Clients findById(String clientId);
}
