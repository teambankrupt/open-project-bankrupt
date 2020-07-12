package com.example.app.exceptions.unknown;

public class UnknownException extends Throwable{
    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }
}
