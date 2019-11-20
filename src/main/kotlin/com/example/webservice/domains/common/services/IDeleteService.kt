package com.example.webservice.domains.common.services

interface IDeleteService {
    fun delete(id: Long)
    fun softDelete(id: Long)
}