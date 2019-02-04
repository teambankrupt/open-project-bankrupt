package com.example.webservice.entities.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Charge {
    private int gasCharge;
    private int waterCharge;
    private String electricityCharge;
    private int securityCharge;
    @NotNull
    @Column(nullable = false)
    private int rent;
    private int advance;

    public int getGasCharge() {
        return gasCharge;
    }

    public void setGasCharge(int gasCharge) {
        this.gasCharge = gasCharge;
    }

    public int getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(int waterCharge) {
        this.waterCharge = waterCharge;
    }

    public String getElectricityCharge() {
        return electricityCharge;
    }

    public void setElectricityCharge(String electricityCharge) {
        this.electricityCharge = electricityCharge;
    }

    public int getSecurityCharge() {
        return securityCharge;
    }

    public void setSecurityCharge(int securityCharge) {
        this.securityCharge = securityCharge;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }
}
