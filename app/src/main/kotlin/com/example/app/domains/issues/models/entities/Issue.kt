package com.example.app.domains.issues.models.entities

import com.example.virtualfilesystem.domains.vfiles.models.VFile
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "issues")
class Issue : VFile() {

    @Column("issue_number")
    lateinit var number: String

}