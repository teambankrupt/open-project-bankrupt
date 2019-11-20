package com.example.webservice.domains.common.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

import java.util.*

open class BaseDto(
        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
        var created: Date? = null,

        @JsonProperty("last_updated", access = JsonProperty.Access.READ_ONLY)
        var lastUpdated: Date? = null
) : Serializable
