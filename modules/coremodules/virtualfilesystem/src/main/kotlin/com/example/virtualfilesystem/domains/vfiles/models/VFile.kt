package com.example.virtualfilesystem.domains.vfiles.models

import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class VFile : BaseEntity() {

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    lateinit var folder: VFolder
}