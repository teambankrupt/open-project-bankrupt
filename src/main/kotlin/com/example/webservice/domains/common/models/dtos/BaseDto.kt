package com.example.webservice.domains.common.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

import java.util.*

open class BaseDto : Serializable {
        @JsonProperty("id")
        @ApiModelProperty(hidden = true)
        var id: Long? = null

        @ApiModelProperty(notes = "It's read only")
        @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
        lateinit var created: Date

        @JsonProperty("last_updated", access = JsonProperty.Access.READ_ONLY)
        @ApiModelProperty(notes = "It's read only")
        lateinit var lastUpdated: Date
}
