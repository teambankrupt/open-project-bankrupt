package com.example.webservice.exceptions.unknown;

import com.example.webservice.exceptions.forbidden.ForbiddenException;

public class WtfException extends ForbiddenException{
    public WtfException() {
    }

    public WtfException(String message) {
        super(message);
    }
}
