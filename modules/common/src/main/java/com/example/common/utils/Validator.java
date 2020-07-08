package com.example.common.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {
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

    @NotNull
    public static Boolean isValidPhoneNumber(@NotNull String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //BD is default country code for Bangladesh (used for number without 880 at the begginning)
            phoneNumber= phoneUtil.parse(phone, "BD");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phoneUtil.isValidNumber(phoneNumber); // returns true
    }

    @NotNull
    public static boolean isValidEmail(@NotNull String phoneOrEmail) {
        Pattern pattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
        Matcher matcher = pattern.matcher(phoneOrEmail);
        return matcher.matches();
    }
}
