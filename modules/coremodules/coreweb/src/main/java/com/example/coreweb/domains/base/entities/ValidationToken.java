package com.example.coreweb.domains.base.entities;


import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class ValidationToken extends BaseEntity {
    private String token;
    private boolean tokenValid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenValidUntil;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTokenValid() {
        if (tokenValidUntil == null) return false;
        return tokenValid && !new Date().after(tokenValidUntil);
    }

    public void setTokenValid(boolean tokenValid) {
        this.tokenValid = tokenValid;
    }

    public Date getTokenValidUntil() {
        return tokenValidUntil;
    }

    public void setTokenValidUntil(Date tokenValidUntil) {
        this.tokenValidUntil = tokenValidUntil;
    }

    @Override
    public String toString() {
        return "ValidationToken{" +
                "token='" + token + '\'' +
                ", tokenValid=" + tokenValid +
                "} " + super.toString();
    }

}
