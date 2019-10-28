package com.example.webservice.services.impl;

import com.example.webservice.entities.ClientConfiguration;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.repositories.ClientConfigRepository;
import com.example.webservice.services.ClientConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientConfigServiceImpl implements ClientConfigService {

    @Autowired
    private final ClientConfigRepository clientConfigRepository;

    public ClientConfigServiceImpl(ClientConfigRepository clientConfigRepository) {
        this.clientConfigRepository = clientConfigRepository;
    }


    @Override
    public List<ClientConfiguration> fetchAllClientConfig() {
        return  clientConfigRepository.findAll();
    }

    @Override
    public Optional<ClientConfiguration> fetchClientConfig(Long clientId) {
        return clientConfigRepository.findById(clientId);
    }

    @Override
    public ClientConfiguration updateClientConfig(Long id, ClientConfiguration clientConfiguration) throws NotFoundException {
        clientConfigRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found with Id : "+ id));
        clientConfiguration.setId(id);
        return clientConfigRepository.save(clientConfiguration);
    }

    @Override
    public ClientConfiguration createClientConfig(ClientConfiguration clientConfiguration) {
        return clientConfigRepository.save(clientConfiguration);
    }

    @Override
    public ClientConfiguration deleteClientConfig(Long id)  throws NotFoundException {
       ClientConfiguration clientConfiguration =  clientConfigRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found with Id : "+ id));
       clientConfigRepository.deleteById(id);
       return clientConfiguration;
    }
}
