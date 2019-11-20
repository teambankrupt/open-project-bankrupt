package com.example.webservice.domains.common.controller

import org.springframework.http.ResponseEntity


abstract class CrudParentController {
    abstract fun search(query: String, page: Int): ResponseEntity<Any>?
    abstract fun find(id: Long): ResponseEntity<Any>?
    abstract fun delete(id: Long): ResponseEntity<Any>?
}