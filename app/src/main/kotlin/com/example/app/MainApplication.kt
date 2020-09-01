package com.example.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication(scanBasePackages = ["com.example"])
@EnableTransactionManagement
open class MainApplication {

    companion object {
        @Autowired
        var context: ConfigurableApplicationContext? = null

        @JvmStatic
        fun main(args: Array<String>) {
            context = runApplication<MainApplication>(*args)
        }

        @JvmStatic
        fun restart() {
            val args = context?.getBean(ApplicationArguments::class.java)

            val thread = Thread {
                context?.close()
                context = SpringApplication.run(MainApplication::class.java, *args?.sourceArgs)
            }

            thread.isDaemon = false
            thread.start()
        }

        @JvmStatic
        fun terminate() {
            this.context?.close()
        }

    }

}
