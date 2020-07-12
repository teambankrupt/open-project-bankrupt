package com.example.app.domains.remoteconfig.services;

import com.example.app.domains.remoteconfig.models.entities.RemoteConfig;
import com.example.app.domains.remoteconfig.repositories.RemoteConfigRepository;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemoteConfigServiceImpl implements RemoteConfigService {

    @Autowired
    private final RemoteConfigRepository remoteConfigRepository;

    public RemoteConfigServiceImpl(RemoteConfigRepository remoteConfigRepository) {
        this.remoteConfigRepository = remoteConfigRepository;
    }


    @Override
    public List<RemoteConfig> fetchAllRemoteConfigs() {
        return remoteConfigRepository.findAll();
    }

    @Override
    public Optional<RemoteConfig> fetchRemoteConfig(Long clientId) {
        return remoteConfigRepository.findById(clientId);
    }

    @Override
    public RemoteConfig updateRemoteConfig(Long id, RemoteConfig remoteConfig) throws NotFoundException {
        remoteConfigRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found with Id : " + id));
        remoteConfig.setId(id);
        return remoteConfigRepository.save(remoteConfig);
    }

    @Override
    public RemoteConfig createRemoteConfig(RemoteConfig remoteConfig) {
        return remoteConfigRepository.save(remoteConfig);
    }

    @Override
    public RemoteConfig deleteRemoteConfig(Long id) throws NotFoundException {
        RemoteConfig remoteConfig = remoteConfigRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found with Id : " + id));
        remoteConfigRepository.deleteById(id);
        return remoteConfig;
    }
}
