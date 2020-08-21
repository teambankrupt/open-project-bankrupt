package com.example.gentool.web;

import com.example.gentool.web.views.ButtonInput;
import com.example.gentool.web.views.enums.ButtonTypes;

import java.time.Instant;

public class TestClass {

    private String name;
    private int age;

    private Instant birthDate;

    private Double value;

    private ButtonTypes buttonType;

    public ButtonTypes getButtonType() {
        return buttonType;
    }

    public void setButtonType(ButtonTypes buttonType) {
        this.buttonType = buttonType;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
