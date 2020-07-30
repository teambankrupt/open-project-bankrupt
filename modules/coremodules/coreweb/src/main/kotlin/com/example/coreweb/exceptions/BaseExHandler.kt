package com.example.coreweb.exceptions

import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.invalid.InvalidException
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
open class BaseExHandler @Autowired constructor(
        private val env: Environment
) {

    fun debug(): Boolean {
        val profiles = env.activeProfiles
        return !profiles.contains("prod")
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val response = if (debug()) ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name,
                ex.message ?: "",
                ex
        ) else ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name,
                ex.message ?: "")
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.name,
                ex.message ?: "",
                ex
        )
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(InvalidException::class)
    fun handleInvalidException(ex: InvalidException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name,
                ex.message ?: "",
                ex
        )
        return ResponseEntity.badRequest().body(response)
    }

}