package com.example.virtualfilesystem.domains.vfiles.service

import com.example.virtualfilesystem.domains.vfiles.models.VFile

interface VFileService {
    fun getFilesInFolder(folderId: Long, page: Int, size: Int): List<VFile>
}