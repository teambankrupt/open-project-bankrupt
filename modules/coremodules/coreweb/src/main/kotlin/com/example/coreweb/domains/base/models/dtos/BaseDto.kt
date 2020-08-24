package com.example.coreweb.domains.base.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.time.Instant

open class BaseDto : Serializable {
    @JsonProperty("id")
    @ApiModelProperty(readOnly = true, example = "1")
    var id: Long? = null

    @JsonProperty(value = "created_at")
    @ApiModelProperty(readOnly = true, example = "1533115869000", notes = "Date when this entity was first created.")
    lateinit var createdAt: Instant

    @JsonProperty("updated_at")
    @ApiModelProperty(readOnly = true, example = "1596274269000", notes = "Date when this entity was last updated")
    lateinit var updatedAt: Instant
}
