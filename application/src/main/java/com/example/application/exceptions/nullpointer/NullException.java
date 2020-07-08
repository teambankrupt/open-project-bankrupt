package com.example.application.exceptions.nullpointer;

public class NullException extends Throwable{
    public NullException() {
        super();
    }

    public NullException(String message) {
        super(message);
    }
}
