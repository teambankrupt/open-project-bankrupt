package com.example.acl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication(scanBasePackages = ["com.example"])
@EnableTransactionManagement
open class AclApplication {

    companion object {
        @Autowired
        var context: ConfigurableApplicationContext? = null

        @JvmStatic
        fun main(args: Array<String>) {
            context = runApplication<AclApplication>(*args)
        }

        @JvmStatic
        fun restart() {
            val args = context?.getBean(ApplicationArguments::class.java)

            val thread = Thread {
                context?.close()
                context = SpringApplication.run(AclApplication::class.java, *args?.sourceArgs)
            }

            thread.isDaemon = false
            thread.start()
        }
    }

}
