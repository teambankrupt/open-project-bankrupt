package com.example.webservice.services;

import com.example.webservice.entities.ClientConfiguration;
import com.example.webservice.exceptions.notfound.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ClientConfigService {

    List<ClientConfiguration> fetchAllClientConfig();

    Optional<ClientConfiguration> fetchClientConfig(Long clientId);

    ClientConfiguration updateClientConfig(Long id, ClientConfiguration clientConfiguration) throws NotFoundException;

    ClientConfiguration createClientConfig(ClientConfiguration clientConfiguration);

    ClientConfiguration deleteClientConfig(Long id)  throws NotFoundException;
}
