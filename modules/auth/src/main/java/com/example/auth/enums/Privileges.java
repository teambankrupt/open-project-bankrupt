package com.example.auth.enums;

public enum Privileges {
    ADMINISTRATION("Administration"), ACCESS_USER_RESOURCES("Access User Resources");

    String label;

    Privileges(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
