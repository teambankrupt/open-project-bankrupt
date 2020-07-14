package com.example.app.domains.remoteconfig.services;

import com.example.app.domains.remoteconfig.models.entities.RemoteConfig;
import com.example.common.exceptions.notfound.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface RemoteConfigService {

    List<RemoteConfig> fetchAllRemoteConfigs();

    Optional<RemoteConfig> fetchRemoteConfig(Long clientId);

    RemoteConfig updateRemoteConfig(Long id, RemoteConfig remoteConfig) throws NotFoundException;

    RemoteConfig createRemoteConfig(RemoteConfig remoteConfig);

    RemoteConfig deleteRemoteConfig(Long id) throws NotFoundException;
}
