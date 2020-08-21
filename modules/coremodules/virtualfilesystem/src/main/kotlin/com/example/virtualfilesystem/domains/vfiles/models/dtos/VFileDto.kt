package com.example.virtualfilesystem.domains.vfiles.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.fasterxml.jackson.annotation.JsonProperty

class VFileDto : BaseDto() {

    @JsonProperty("name")
    lateinit var name: String

    @JsonProperty("v_extension_id")
    lateinit var ext: VExtension

    @JsonProperty("folder_id")
    var folderId: Long = 0

}