package com.example.webservice.domains.remoteconfig.services;

import com.example.webservice.domains.remoteconfig.models.entities.RemoteConfig;
import com.example.webservice.exceptions.notfound.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface RemoteConfigService {

    List<RemoteConfig> fetchAllRemoteConfigs();

    Optional<RemoteConfig> fetchRemoteConfig(Long clientId);

    RemoteConfig updateRemoteConfig(Long id, RemoteConfig remoteConfig) throws NotFoundException;

    RemoteConfig createRemoteConfig(RemoteConfig remoteConfig);

    RemoteConfig deleteRemoteConfig(Long id) throws NotFoundException;
}
