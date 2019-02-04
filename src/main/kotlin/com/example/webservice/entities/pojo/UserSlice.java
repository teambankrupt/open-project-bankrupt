package com.example.webservice.entities.pojo;

import com.example.webservice.entities.User;

public class UserSlice {
    private Long userId;
    private String name;
    private String username;
    private String phoneNumber;

    public UserSlice(User user) {
        if (user == null) return;
        this.userId = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserSlice{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
