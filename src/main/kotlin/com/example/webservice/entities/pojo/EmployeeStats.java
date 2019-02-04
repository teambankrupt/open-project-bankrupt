package com.example.webservice.entities.pojo;

import java.util.Date;
import java.util.Map;

public class EmployeeStats {
    private int buildingInfoCount;
    private int apartmentInfoCount;
    private int totalHandledRequestCount;
    private int successRequestCount;
    private Map<Date, Byte> buildingCountMap;
    private Map<Date, Byte> apartmentCountMap;

    public EmployeeStats(int buildingInfoCount, int apartmentInfoCount, int totalHandledRequestCount, int successRequestCount) {
        this.buildingInfoCount = buildingInfoCount;
        this.apartmentInfoCount = apartmentInfoCount;
        this.totalHandledRequestCount = totalHandledRequestCount;
        this.successRequestCount = successRequestCount;
    }

    public int getBuildingInfoCount() {
        return buildingInfoCount;
    }

    public int getApartmentInfoCount() {
        return apartmentInfoCount;
    }

    public int getTotalHandledRequestCount() {
        return totalHandledRequestCount;
    }

    public int getSuccessRequestCount() {
        return successRequestCount;
    }


    public Map<Date, Byte> getBuildingCountMap() {
        return buildingCountMap;
    }

    public void setBuildingCountMap(Map<Date, Byte> buildingCountMap) {
        this.buildingCountMap = buildingCountMap;
    }

    public Map<Date, Byte> getApartmentCountMap() {
        return apartmentCountMap;
    }

    public void setApartmentCountMap(Map<Date, Byte> apartmentCountMap) {
        this.apartmentCountMap = apartmentCountMap;
    }
}
