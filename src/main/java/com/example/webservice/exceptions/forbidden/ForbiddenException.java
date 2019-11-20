package com.example.webservice.exceptions.forbidden;

public class ForbiddenException extends Throwable{
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
