package com.example.application.exceptions.notfound;

public class ProfileNotFoundException extends NotFoundException {
    public ProfileNotFoundException() {
    }

    public ProfileNotFoundException(String s) {
        super(s);
    }
}
