package com.example.coreweb.domains.address.models.dto

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class VillageDto : BaseDto() {
    @NotNull
    @NotEmpty
    @JsonProperty("name_en")
    var nameEn: String? = null

    @NotNull
    @NotEmpty
    @JsonProperty("name_bn")
    var nameBn: String? = null

    @NotNull
    @JsonProperty("union_id")
    var unionId: Long? = null
}