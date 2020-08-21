package com.example.virtualfilesystem.domains.vfiles.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty

class VFileDto : BaseDto() {

    @JsonProperty("name")
    lateinit var name: String

    @JsonProperty("hidden")
    var hidden: Boolean = false

    @JsonProperty("ext_id")
    var extId: Long = 0

    @JsonProperty("ext_ext")
    lateinit var ext: String

    @JsonProperty("folder_id")
    var folderId: Long = 0

}