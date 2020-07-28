package com.example.coreweb.domains.activities.models.entities;

import com.example.auth.entities.User;
import com.example.auth.entities.UserAuth;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "activity_logs")
public class Activity extends BaseEntity {
    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ip")
    private String ip;

    @Column(name = "expires")
    private String expires;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    private Long totalVisitors;

    public void setUser(UserAuth auth) {
        if (auth == null) throw new IllegalArgumentException("Auth can't be null!");
        this.user = new User(auth);
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Long getTotalVisitors() {
        return totalVisitors == null ? 0 : totalVisitors;
    }

    public void setTotalVisitors(Long totalVisitors) {
        this.totalVisitors = totalVisitors;
    }
}
