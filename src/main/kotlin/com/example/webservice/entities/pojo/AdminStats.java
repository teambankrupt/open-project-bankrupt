package com.example.webservice.entities.pojo;

import java.util.Date;
import java.util.Map;

public class AdminStats {
    private Map<Date, Long> noOfUsers;
    private Map<Date, Integer> noOfRequests;
    private Map<Date, Integer> noOfConfirmedRequests;
    private Map<Date, Integer> noOfBuildings;
    private Map<Date, Integer> noOfApartments;

    private Long noOfHits;
    private int noOfOrders;


    public Map<Date, Long> getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(Map<Date, Long> noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public Map<Date, Integer> getNoOfRequests() {
        return noOfRequests;
    }

    public void setNoOfRequests(Map<Date, Integer> noOfRequests) {
        this.noOfRequests = noOfRequests;
    }

    public Map<Date, Integer> getNoOfConfirmedRequests() {
        return noOfConfirmedRequests;
    }

    public void setNoOfConfirmedRequests(Map<Date, Integer> noOfConfirmedRequests) {
        this.noOfConfirmedRequests = noOfConfirmedRequests;
    }

    public Map<Date, Integer> getNoOfBuildings() {
        return noOfBuildings;
    }

    public void setNoOfBuildings(Map<Date, Integer> noOfBuildings) {
        this.noOfBuildings = noOfBuildings;
    }

    public Map<Date, Integer> getNoOfApartments() {
        return noOfApartments;
    }

    public void setNoOfApartments(Map<Date, Integer> noOfApartments) {
        this.noOfApartments = noOfApartments;
    }

    public Long getNoOfHits() {
        return noOfHits;
    }

    public void setNoOfHits(Long noOfHits) {
        this.noOfHits = noOfHits;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(int noOfOrders) {
        this.noOfOrders = noOfOrders;
    }
}
