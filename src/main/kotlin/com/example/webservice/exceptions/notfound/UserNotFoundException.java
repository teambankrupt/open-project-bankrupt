package com.example.webservice.exceptions.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
