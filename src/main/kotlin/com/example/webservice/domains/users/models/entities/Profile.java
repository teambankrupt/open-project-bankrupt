package com.example.webservice.domains.users.models.entities;

import com.example.webservice.commons.utils.DateUtil;
import com.example.webservice.domains.common.models.entities.base.BaseEntity;
import com.example.webservice.domains.common.models.entities.pojo.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profilePicturePath;
    private String name;
    private Date birthDate;
    private String gender;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String photoIdentityPath;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String age;

    @Embedded
    private Address address;
    @OneToOne
    @JsonIgnore
    private User user;

    @PrePersist
    @PreUpdate
    private void onProfileUpdated() {
        Period period = DateUtil.getAge(this.birthDate);
        this.age = (period.getYears() + " Years, " + period.getMonths() + " Months");
    }

    public boolean hasAuthorizedAccess() {
        return getCurrentUser().isAdmin()
                || Objects.equals(getCurrentUser().getId(), this.getUser().getId());
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getProfilePicturePath() {
        return profilePicturePath == null ? "" : profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getPhotoIdentityPath() {
        return photoIdentityPath == null ? "" : photoIdentityPath;
    }

    public void setPhotoIdentityPath(String photoIdentityPath) {
        this.photoIdentityPath = photoIdentityPath;
    }
}
