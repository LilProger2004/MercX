package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.model.entity.Clients;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.reposirtory.ClientsRepository;
import com.diploma.MrcX.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImplements implements ClientService {
    private final ClientsRepository clientsRepository;
    @Override
    public boolean existById(String id) {
        return clientsRepository.existsById(id);
    }

    @Override
    public void save(Clients client) {
        client.setBalance(0);
        clientsRepository.save(client);
    }

    @Override
    public List<Clients> findAll() {
        return clientsRepository.findAll();
    }

    @Override
    public Clients findById(String clientId) {
        return clientsRepository.findById(clientId).orElseThrow();
    }
}
