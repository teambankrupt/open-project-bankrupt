package com.example.webservice.entities.pojo;

import com.example.webservice.commons.utils.DateUtil;

import java.math.BigInteger;
import java.util.Date;

public class LatLngMeta {
    private Byte noOfBeds;
    private int rent;
    private boolean available;
    private boolean bachelorAllowed;
    private boolean verified;

    // for plasma blood database
    private String bloodGroup;
    private Date lastDonated;
    private Long userId;

    public LatLngMeta() {
    }

    public LatLngMeta(Byte noOfBeds, int rent) {
        this.noOfBeds = noOfBeds;
        this.rent = rent;
    }

    public LatLngMeta(Byte noOfBeds, int rent, boolean available, boolean bachelorAllowed, boolean verified) {
        this.noOfBeds = noOfBeds;
        this.rent = rent;
        this.available = available;
        this.bachelorAllowed = bachelorAllowed;
        this.verified = verified;
    }

    public LatLngMeta(String bloodGroup, Date lastDonated, BigInteger userId) {
        this.bloodGroup = bloodGroup;
        this.lastDonated = lastDonated;
        if (userId != null)
            this.userId = userId.longValue();
    }

    public void setNoOfBeds(Byte noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getBachelorAllowed() {
        return bachelorAllowed;
    }

    public void setBachelorAllowed(Boolean bachelorAllowed) {
        this.bachelorAllowed = bachelorAllowed;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setLastDonated(Date lastDonated) {
        this.lastDonated = lastDonated;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public Date getLastDonated() {
        return lastDonated;
    }

    public String getLastDonatedStr() {
        if (this.lastDonated == null) return "";
        return DateUtil.getServerDateTimeFormat().format(this.lastDonated);
    }

    public Byte getNoOfBeds() {
        return noOfBeds;
    }

    public int getRent() {
        return rent;
    }
}
