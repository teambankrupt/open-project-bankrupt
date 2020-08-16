package com.example.virtualfilesystem.domains.vfolders.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class VFolderDto : BaseDto() {

    @NotBlank
    @ApiModelProperty(required = true)
    lateinit var name: String

    @JsonProperty("description")
    var description: String? = null

    @ApiModelProperty(readOnly = true)
    var path: String? = null

    var thumbnail: String? = null

    @JsonProperty("accent_color")
    var accentColor: String? = null

    @JsonProperty("parent_id")
    var parentId: Long? = null

    @JsonProperty("root_id")
    @ApiModelProperty(readOnly = true)
    var rootId: Long? = null
}