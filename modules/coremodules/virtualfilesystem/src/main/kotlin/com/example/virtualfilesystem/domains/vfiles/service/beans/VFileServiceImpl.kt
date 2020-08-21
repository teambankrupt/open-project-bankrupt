package com.example.virtualfilesystem.domains.vfiles.service.beans

import com.example.virtualfilesystem.domains.vfiles.daos.VFileDao
import com.example.virtualfilesystem.domains.vfiles.models.VFile
import com.example.virtualfilesystem.domains.vfiles.service.VFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VFileServiceImpl @Autowired constructor(
        private val fileDao: VFileDao
) : VFileService {

    override fun getFilesInFolder(folderId: Long, page: Int, size: Int): List<VFile> {
        return this.fileDao.getFiles(folderId, page, size)
    }

}