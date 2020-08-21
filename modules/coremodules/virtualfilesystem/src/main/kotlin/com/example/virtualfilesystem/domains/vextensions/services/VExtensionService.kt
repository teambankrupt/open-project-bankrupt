package com.example.virtualfilesystem.domains.vextensions.services

import com.example.coreweb.domains.base.services.CrudService
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vfiles.models.VFile

interface VExtensionService : CrudService<VExtension> {
    fun findOrCreate(cls: Class<out VFile>): VExtension
}