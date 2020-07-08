package com.example.application.commons.utils

import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.notfound.NotFoundException

/**
 * Created by IntelliJ IDEA.
 * User: razzak
 * Date: 13/11/19
 * Time: 11:12 AM
 */

class ExceptionUtil {
    companion object {
        fun getNotFound(name: String, id: Long?): NotFoundException {
            return NotFoundException("Could not find $name with id: $id")
        }
        fun getNotFound(name: String, code: String?): NotFoundException {
            return NotFoundException("Could not find $name with id: $code")
        }
        fun forbidden(message: String): ForbiddenException {
            return ForbiddenException(message)
        }
    }
}