package com.example.virtualfilesystem.domains.vfolders.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "vfs_folders")
class VFolder : BaseEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null

    @Column(name = "path")
    var path: String? = null

    var thumbnail: String? = null

    var accentColor: String? = null

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: VFolder? = null

    @PrePersist
    @PreUpdate
    fun onAgentPersist() {
        if (parent != null) {
            path = if (parent!!.path == null || parent!!.path!!.isEmpty()) parent!!.id.toString()
            else "${parent!!.path}:${parent!!.id}"
        }
    }

    fun getRootId(): Long {
        if (this.parent == null || this.path == null || this.path!!.isEmpty()) return this.id
        val rootId = this.path!!.split(":")[0]
        return rootId.toLong()
    }

    fun hasParent(folder: VFolder): Boolean {
        return if (parent == null) false else parent!!.id == folder.id
    }

    fun getAbsolutePath(): String {
        if (path == null) return "$id"
        return "$path:$id"
    }

    fun getThumbnailElseParent(): String? {
        if (this.thumbnail != null && this.thumbnail?.isNotBlank()!!)
            return this.thumbnail
        return this.parent?.getThumbnailElseParent()
    }

}