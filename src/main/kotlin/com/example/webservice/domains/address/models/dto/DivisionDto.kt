package com.example.webservice.domains.address.models.dto

import com.example.webservice.domains.common.models.dto.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class DivisionDto : BaseDto() {

    @NotBlank
    @JsonProperty("name_en")
    var nameEn: String? = null

    @NotBlank
    @JsonProperty("name_bn")
    var nameBn: String? = null

}
