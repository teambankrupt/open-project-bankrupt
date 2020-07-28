package com.example.app.batch.userimport.controllers

import com.example.app.routing.Route
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserImportController @Autowired constructor(
        private val jobLauncher: JobLauncher,
        @Qualifier("userImportJob") val job: Job
) {

    @Throws(JobExecutionAlreadyRunningException::class, JobRestartException::class, JobInstanceAlreadyCompleteException::class, JobParametersInvalidException::class)
    @PostMapping(Route.V1.IMPORT_USERS)
    fun importUsers(): BatchStatus {
        val params = JobParametersBuilder().addString("path", "csv/imports/users.csv").toJobParameters()
        val execution = this.jobLauncher.run(this.job, params)
        println("JobExecution: ${execution.status}")
        while (execution.isRunning)
            println("...")
        return execution.status
    }

}
