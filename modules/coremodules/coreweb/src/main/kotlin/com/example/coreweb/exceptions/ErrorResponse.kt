package com.example.coreweb.exceptions

class ErrorResponse {

    var code: Int = 0
    var status: String
    var message: String

    var exception: Throwable? = null

    constructor(code: Int, status: String, message: String) {
        this.code = code
        this.status = status
        this.message = message
    }

    constructor(code: Int, status: String, message: String, exception: Throwable?) {
        this.code = code
        this.status = status
        this.message = message
        this.exception = exception
    }


}