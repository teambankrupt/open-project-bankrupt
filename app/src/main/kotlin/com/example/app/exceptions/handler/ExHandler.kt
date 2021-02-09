package com.example.app.exceptions.handler

import com.example.common.exceptions.exists.AlreadyExistsException
import com.example.common.exceptions.notfound.NotFoundException
import com.example.coreweb.exceptions.BaseExHandler
import com.example.coreweb.exceptions.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExHandler @Autowired constructor(env: Environment) : BaseExHandler(env) {


    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(ex: AlreadyExistsException): ResponseEntity<ErrorResponse> {
        return buildResponse(HttpStatus.CONFLICT, ex)
    }

}