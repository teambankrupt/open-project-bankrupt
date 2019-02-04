package com.example.webservice.entities.pojo;

public class ApartmentSlice {
    private Long id;
    private String apartmentNumber;
    private long totalVisitCount;
    private long visitCountAfterMarkedAvailable;

    public ApartmentSlice() {
    }

    public ApartmentSlice(Long id, String apartmentNumber, long totalVisitCount, long visitCountAfterMarkedAvailable) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.totalVisitCount = totalVisitCount;
        this.visitCountAfterMarkedAvailable = visitCountAfterMarkedAvailable;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTotalVisitCount() {
        return totalVisitCount;
    }

    public void setTotalVisitCount(long totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }

    public long getVisitCountAfterMarkedAvailable() {
        return visitCountAfterMarkedAvailable;
    }

    public void setVisitCountAfterMarkedAvailable(long visitCountAfterMarkedAvailable) {
        this.visitCountAfterMarkedAvailable = visitCountAfterMarkedAvailable;
    }
}
