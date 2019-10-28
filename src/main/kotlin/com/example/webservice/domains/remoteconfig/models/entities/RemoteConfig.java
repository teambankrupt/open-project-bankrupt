package com.example.webservice.domains.remoteconfig.models.entities;

import com.example.webservice.domains.common.models.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "remote_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RemoteConfig extends BaseEntity {

    @NotNull
    @NotEmpty
    private String appName;
    @NotNull
    @NotEmpty
    private String appPackage;
    @NotNull
    @NotEmpty
    private String currentAppVersion;

    @NotNull
    @NotEmpty
    private String previousAppVersion;

    private String appUrl;

    private Boolean forceUpdate = false;

    private Boolean turnedOff = false;


}
