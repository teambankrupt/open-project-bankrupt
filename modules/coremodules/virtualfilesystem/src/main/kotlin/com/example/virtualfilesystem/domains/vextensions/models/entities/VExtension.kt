package com.example.virtualfilesystem.domains.vextensions.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "vfs_extensions",schema = "vfs")
class VExtension : BaseEntity() {

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "ext")
    lateinit var ext: String

    @Column(name = "url_prefix")
    lateinit var urlPrefix: String

}