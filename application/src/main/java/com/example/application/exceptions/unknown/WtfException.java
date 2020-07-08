package com.example.application.exceptions.unknown;

import com.example.application.exceptions.forbidden.ForbiddenException;

public class WtfException extends ForbiddenException{
    public WtfException() {
    }

    public WtfException(String message) {
        super(message);
    }
}
