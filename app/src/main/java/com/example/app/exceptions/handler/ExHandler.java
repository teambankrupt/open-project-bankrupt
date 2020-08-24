package com.example.app.exceptions.handler;

import com.example.coreweb.exceptions.BaseExHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExHandler extends BaseExHandler {

    @Autowired
    public ExHandler(@NotNull Environment env) {
        super(env);
    }

}
