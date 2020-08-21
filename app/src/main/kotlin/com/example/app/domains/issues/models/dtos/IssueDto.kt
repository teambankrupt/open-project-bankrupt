package com.example.app.domains.issues.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty

class IssueDto : BaseDto() {

    lateinit var number: String

    @JsonProperty("folder_id")
    var folderId: Long = 0

}