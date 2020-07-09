package com.example.common.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class Shell private constructor() {


    companion object {

        @JvmStatic
        @Throws(IOException::class, InterruptedException::class)
        fun exec(command: String?): String? {
            val run = Runtime.getRuntime()
            val pr = run.exec(command)
            pr.waitFor()
            val buf = BufferedReader(InputStreamReader(pr.inputStream))
            val log = StringBuilder()
            var line: String?
            while (buf.readLine().also { line = it } != null) {
                log.append(line).append("\n")
                //            System.out.println(line);
            }
            return log.toString()
        }

    }

}