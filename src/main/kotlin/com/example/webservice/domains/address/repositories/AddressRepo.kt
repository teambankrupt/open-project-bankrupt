package com.example.webservice.domains.address.repositories

import com.example.webservice.domains.address.models.entities.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AddressRepo : JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.id = :id AND a.deleted = false")
    fun find(@Param("id") id: Long) : Optional<Address>
}
