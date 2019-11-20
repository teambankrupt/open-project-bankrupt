package com.example.webservice.commons.utils

import com.example.webservice.exceptions.notfound.NotFoundException

/**
 * Created by IntelliJ IDEA.
 * User: razzak
 * Date: 13/11/19
 * Time: 11:12 AM
 */

class MessageUtil {
    companion object {
        fun getErrorMessageWithId(name: String, id: Long?): String {
            return "Could not find $name with id: $id"
        }

        fun getNotFoundMessage(name: String, id: Long?): NotFoundException {
            return NotFoundException("Could not find $name with id: $id")
        }

        fun getErrorMessageWithName(name: String): String {
            return "$name could not found"
        }
    }
}