package com.example.webservice.domains.activities.models.entities;

import com.example.webservice.domains.common.base.models.entities.BaseEntity;
import com.example.webservice.domains.users.models.UserAuth;
import com.example.webservice.domains.users.models.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity_logs")
public class Activity extends BaseEntity {
    private String userAgent;
    private String ip;
    private String expires;
    @OneToOne
    @JsonBackReference
    private User user;
    private String requestMethod;
    private String url;

    private Long totalVisitors;
    @ElementCollection
    @CollectionTable(name = "activity_log_tags")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<String> tags;

    public enum Tag {
        ALL
    }

    public void addTag(Tag tag) {
        if (tags == null) tags = new HashSet<>();
        tags.add(tag.name());
    }

    public void setUser(UserAuth auth) {
        if (auth==null) throw new IllegalArgumentException("Auth can't be null!");
        this.user = new User(auth);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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
