package com.example.webservice.domains.users.models.dtos;

import com.example.webservice.domains.common.models.dtos.BaseDto;

import java.util.List;

public class RoleDto extends BaseDto {
    private String name;
    private List<PrivilegeDto> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrivilegeDto> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeDto> privileges) {
        this.privileges = privileges;
    }
}
