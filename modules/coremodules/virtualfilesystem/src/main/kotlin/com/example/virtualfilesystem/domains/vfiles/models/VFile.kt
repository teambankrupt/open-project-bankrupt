package com.example.virtualfilesystem.domains.vfiles.models

import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import javax.persistence.*

@MappedSuperclass
abstract class VFile : BaseEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "hidden", nullable = false)
    var hidden: Boolean = false

    @OneToOne
    @JoinColumn(name = "v_extension_id", nullable = false)
    lateinit var ext: VExtension

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    lateinit var folder: VFolder
}