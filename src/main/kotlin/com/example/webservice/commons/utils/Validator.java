package com.example.webservice.commons.utils;

public class Validator {
    private Validator() {
    }

    public static boolean nullOrZero(Object object) {
        return object == null || object.equals(0);
    }

    public static boolean nullOrEmpty(String object) {
        return object == null || object.isEmpty();
    }

    public static boolean NOT_NULL_NOT_EMPTY(String text) {
        return text != null && !text.isEmpty();
    }

}
