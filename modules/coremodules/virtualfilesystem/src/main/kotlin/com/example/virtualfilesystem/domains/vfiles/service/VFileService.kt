package com.example.virtualfilesystem.domains.vfiles.service

import com.example.virtualfilesystem.domains.vfiles.models.VFile

interface VFileService {
    fun getFilesInFolder(folerId: Long): List<VFile>
}