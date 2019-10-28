package com.example.webservice.entities;

import com.example.webservice.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_client_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientConfiguration extends BaseEntity  {

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