package com.example.acl.domains.users.models.dtos;

import com.example.auth.entities.User;

public class UserSlice {
    private Long userId;
    private String name;
    private String username;
    private String phone;

    public UserSlice(User user) {
        if (user == null) return;
        this.userId = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.phone = user.getPhone();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
