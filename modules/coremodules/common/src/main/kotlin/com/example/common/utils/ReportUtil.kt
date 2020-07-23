package com.example.common.utils

import java.io.File
import java.io.IOException


class ReportUtil private constructor() {

    companion object {

        @Throws(IOException::class, InterruptedException::class)
        fun generatePdf(url: String): File? {
            val fileName = "report.pdf"
            Shell.exec("wkhtmltopdf $url $fileName")
            return File(fileName)
        }

    }

}