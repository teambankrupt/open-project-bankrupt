package com.example.app.exceptions.unknown;


import com.example.app.exceptions.forbidden.ForbiddenException;

public class WtfException extends ForbiddenException {
    public WtfException() {
    }

    public WtfException(String message) {
        super(message);
    }
}
