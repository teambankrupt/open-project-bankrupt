package com.example.webservice.domains.users.models.dtos;

import com.example.webservice.domains.common.models.dtos.BaseDto;

public class PrivilegeDto extends BaseDto {
    private String name;
    private String label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
