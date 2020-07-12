package com.example.app.exceptions.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
