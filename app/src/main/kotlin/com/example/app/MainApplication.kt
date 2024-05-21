package com.example.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication(scanBasePackages = ["com.example"])
@EnableTransactionManagement
@EnableAsync
open class MainApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<MainApplication>(*args)
        }

    }

}
