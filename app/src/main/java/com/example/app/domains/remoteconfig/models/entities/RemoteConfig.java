package com.example.app.domains.remoteconfig.models.entities;

import com.example.coreweb.domains.base.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getCurrentAppVersion() {
        return currentAppVersion;
    }

    public void setCurrentAppVersion(String currentAppVersion) {
        this.currentAppVersion = currentAppVersion;
    }

    public String getPreviousAppVersion() {
        return previousAppVersion;
    }

    public void setPreviousAppVersion(String previousAppVersion) {
        this.previousAppVersion = previousAppVersion;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Boolean getTurnedOff() {
        return turnedOff;
    }

    public void setTurnedOff(Boolean turnedOff) {
        this.turnedOff = turnedOff;
    }
}
