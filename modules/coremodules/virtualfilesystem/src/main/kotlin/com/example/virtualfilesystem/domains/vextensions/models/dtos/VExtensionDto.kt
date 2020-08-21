package com.example.virtualfilesystem.domains.vextensions.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty

class VExtensionDto : BaseDto() {

    @JsonProperty("name")
    lateinit var name: String

    @JsonProperty("ext")
    lateinit var ext: String

}