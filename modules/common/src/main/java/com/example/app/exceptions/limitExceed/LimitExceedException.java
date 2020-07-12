package com.example.app.exceptions.limitExceed;


import com.example.app.exceptions.invalid.InvalidException;

public class LimitExceedException extends InvalidException {
    public LimitExceedException() {
    }

    public LimitExceedException(String message) {
        super(message);
    }

}
