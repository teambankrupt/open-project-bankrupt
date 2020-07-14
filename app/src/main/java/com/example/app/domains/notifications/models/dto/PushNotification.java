package com.example.app.domains.notifications.models.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "to",
        "data"
})
public class PushNotification {
    @JsonProperty("to")
    private String to;
    @JsonProperty("data")
    private NotificationData data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public PushNotification() {
    }

    public PushNotification(String to, NotificationData data) {
        this.to = to;
        this.data = data;
    }

    public enum Type {
        GENERAL("general"),
        ALERT("alert"),
        LOGOUT("logout"),
        PROMOTION("promotion"),
        ADMIN_NOTIFICATIONS("admin_notifications"),
        NEW_AVAILABLE_ALERT("new_alert");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty("data")
    public NotificationData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(NotificationData data) {
        this.data = data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
