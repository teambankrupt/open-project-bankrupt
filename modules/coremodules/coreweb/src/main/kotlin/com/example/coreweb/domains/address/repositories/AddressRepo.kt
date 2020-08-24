package com.example.coreweb.domains.address.repositories

import com.example.coreweb.domains.address.models.entities.Address
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface AddressRepo : JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.id = :id AND a.deleted = false")
    fun find(@Param("id") id: Long): Optional<Address>

    @Query("SELECT a FROM Address a WHERE (:q IS NULL OR a.village.nameEn LIKE %:q%)")
    fun search(@Param("q") query: String, pageable: Pageable): Page<Address>
}
