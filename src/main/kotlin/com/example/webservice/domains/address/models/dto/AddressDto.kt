package com.example.webservice.domains.address.models.dto

import com.example.webservice.domains.common.models.dto.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class AddressDto : BaseDto() {

    @NotNull
    @JsonProperty("division_id")
    var divisionId: Long? = null

    @NotNull
    @JsonProperty("district_id")
    var districtId: Long? = null

    @NotNull
    @JsonProperty("upazila_id")
    var upazilaId: Long? = null

    @NotNull
    @JsonProperty("union_id")
    var unionId: Long? = null

    @NotNull
    @JsonProperty("village_id")
    var villageId: Long? = null

    @JsonProperty("lat")
    var lat: Double? = null

    @JsonProperty("lng")
    var lng: Double? = null
}
