package com.example.gentool.web.views.enums;

public enum ButtonTypes {
    SUBMIT("Submit", "submit", "btn btn-success"),
    CANCEL("Cancel", "submit", "btn btn-danger"),
    RESET("Reset", "reset", "btn btn-default");

    private final String label;
    private final String type;
    private final String btnClass;

    ButtonTypes(String label, String type, String btnClass) {
        this.label = label;
        this.type = type;
        this.btnClass = btnClass;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public String getBtnClass() {
        return btnClass;
    }
}
