package com.example.virtualfilesystem.domains.vfiles.models

import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass
import javax.persistence.OneToOne

@MappedSuperclass
abstract class VFile : BaseEntity() {

    @OneToOne
    @JoinColumn(name = "v_extension_id")
    lateinit var ext: VExtension

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    lateinit var folder: VFolder
}