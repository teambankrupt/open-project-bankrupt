package com.example.common.exceptions.unknown;


import com.example.common.exceptions.forbidden.ForbiddenException;

public class WtfException extends ForbiddenException {
    public WtfException() {
    }

    public WtfException(String message) {
        super(message);
    }
}
