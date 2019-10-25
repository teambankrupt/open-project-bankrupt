package com.example.webservice.controllers.api.RemoteClientConfiguration;

import com.example.webservice.entities.ClientConfiguration;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.services.ClientConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/client-config")
public class ClientConfigController {

    @Autowired
    private final ClientConfigService clientConfigService;

    public ClientConfigController(ClientConfigService clientConfigService) {
        this.clientConfigService = clientConfigService;
    }

    @GetMapping
    public List<ClientConfiguration> fetchAllClientConfig(){
        return clientConfigService.fetchAllClientConfig();
    }

    @GetMapping("{id}")
    public Optional<ClientConfiguration> fetchClientConfig(@PathVariable Long id){
        return clientConfigService.fetchClientConfig(id);
    }

    @PostMapping()
    public ClientConfiguration createClientConfig(@Valid ClientConfiguration clientConfiguration) {
        return clientConfigService.createClientConfig(clientConfiguration);
    }

    @PutMapping("{id}")
    public ClientConfiguration updateClientConfig(@PathVariable Long id, @Valid ClientConfiguration clientConfiguration) throws NotFoundException {
        return clientConfigService.updateClientConfig(id, clientConfiguration);
    }

    @DeleteMapping("{id}")
    public ClientConfiguration deleteClientConfig(@PathVariable Long id) throws NotFoundException {
        return clientConfigService.deleteClientConfig(id);
    }

}
