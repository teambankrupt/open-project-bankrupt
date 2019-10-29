package com.example.webservice.domains.users.models.entities;

import com.example.webservice.domains.common.models.entities.base.ValidationToken;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ac_validation_tokens")
public class AcValidationToken extends ValidationToken {
    @OneToOne
    private User user;
    private String reason;
    private String phone;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "AcValidationToken{" +
                "user=" + user +
                ", reason='" + reason + '\'' +
                ", phone='" + phone + '\'' +
                "} " + super.toString();
    }
}
