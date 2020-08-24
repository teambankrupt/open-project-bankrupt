package com.example.app.batch.userimport.configs

import com.example.acl.domains.users.models.dtos.UserRequest
import com.example.auth.entities.User
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
open class UserImportBatchConfig {

    private val jobName: String = "user-import-job"
    private val stepName: String = "user-import-job-step"

    @Bean
    open fun userImportJob(jobBuilderFactory: JobBuilderFactory,
                           stepBuilderFactory: StepBuilderFactory,
                           itemReader: ItemReader<UserRequest>,
                           processor: ItemProcessor<UserRequest, User>,
                           writer: ItemWriter<User>): Job {

        val step = stepBuilderFactory.get(stepName)
                .chunk<UserRequest, User>(100)
                .reader(itemReader)
                .processor(processor)
                .writer(writer)
                .build()

        return jobBuilderFactory.get(this.jobName)
                .incrementer(RunIdIncrementer())
                .start(step)
                .build()
    }


}
