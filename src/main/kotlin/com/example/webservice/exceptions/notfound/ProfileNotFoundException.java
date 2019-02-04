package com.example.webservice.exceptions.notfound;

public class ProfileNotFoundException extends NotFoundException {
    public ProfileNotFoundException() {
    }

    public ProfileNotFoundException(String s) {
        super(s);
    }
}
