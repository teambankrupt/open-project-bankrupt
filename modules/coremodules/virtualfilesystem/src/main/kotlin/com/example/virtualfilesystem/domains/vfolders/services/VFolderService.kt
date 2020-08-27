package com.example.virtualfilesystem.domains.vfolders.services

import com.example.coreweb.domains.base.services.CrudService
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import org.springframework.data.domain.Page

interface VFolderService : CrudService<VFolder> {
    fun search(query: String, parentId: Long?, page: Int, size: Int): Page<VFolder>
}