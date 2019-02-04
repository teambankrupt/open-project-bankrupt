package com.example.webservice.entities.pojo;

import java.util.List;
import java.util.Map;

public class Statistics {
    private int noOfBuildings;
    private int noOfApartments;
    private int noOfAvailableApartments;

    private Map<String, Integer> noOfRequests;

    private List<ApartmentSlice> apartmentVisits;

    private Map<String, Integer> complains;

    public int getNoOfBuildings() {
        return noOfBuildings;
    }

    public void setNoOfBuildings(int noOfBuildings) {
        this.noOfBuildings = noOfBuildings;
    }

    public int getNoOfApartments() {
        return noOfApartments;
    }

    public void setNoOfApartments(int noOfApartments) {
        this.noOfApartments = noOfApartments;
    }

    public int getNoOfAvailableApartments() {
        return noOfAvailableApartments;
    }

    public void setNoOfAvailableApartments(int noOfAvailableApartments) {
        this.noOfAvailableApartments = noOfAvailableApartments;
    }


    public Map<String, Integer> getNoOfRequests() {
        return noOfRequests;
    }

    public void setNoOfRequests(Map<String, Integer> noOfRequests) {
        this.noOfRequests = noOfRequests;
    }

    public List<ApartmentSlice> getApartmentVisits() {
        return apartmentVisits;
    }

    public void setApartmentVisits(List<ApartmentSlice> apartmentVisits) {
        this.apartmentVisits = apartmentVisits;
    }

    public Map<String, Integer> getComplains() {
        return complains;
    }

    public void setComplains(Map<String, Integer> complains) {
        this.complains = complains;
    }
}
