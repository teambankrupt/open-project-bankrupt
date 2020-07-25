package com.example.app.domains.profiles.models.entities;

import com.example.coreweb.domains.address.models.entities.Address;
import com.example.auth.entities.User;
import com.example.common.utils.DateUtil;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {
    private String name;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    private String gender;

    private String photo;

    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(name = "photo_identity_url")
    private String photoIdentityUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String age;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JsonIgnore
    private User user;

    @PrePersist
    @PreUpdate
    private void onProfileUpdated() {
        Period period = DateUtil.getAge(this.birthday);
        if (period == null) return;
        this.age = (period.getYears() + " Years, " + period.getMonths() + " Months");
    }

//    public boolean hasAuthorizedAccess() {
//        return getCurrentUser().isAdmin()
//                || Objects.equals(getCurrentUser().getId(), this.getUser().getId());
//    }

    public enum BloodGroup {
        A_POSITIVE("A+"),
        A_NEGATIVE("A-"),
        B_POSITIVE("B+"),
        B_NEGATIVE("B-"),
        AB_POSITIVE("AB+"),
        AB_NEGATIVE("AB-"),
        O_POSITIVE("O+"),
        O_NEGATIVE("O-");

        private String value;

        BloodGroup(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoIdentityUrl() {
        return photoIdentityUrl;
    }

    public void setPhotoIdentityUrl(String photoIdentityUrl) {
        this.photoIdentityUrl = photoIdentityUrl;
    }
}
