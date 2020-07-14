package com.example.app.batch.userimport.io

import com.example.acl.domains.users.models.dtos.UserRequest
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
open class UserReader {

    @Bean
    @StepScope
    open fun configure(@Value("#{jobParameters['path']}") path: String): FlatFileItemReader<UserRequest> {
        val itemReader = FlatFileItemReader<UserRequest>()
        val resource = ClassPathResource(path)
        itemReader.setResource(resource)
        itemReader.setName("csv-reader")
        itemReader.setLinesToSkip(1)
        itemReader.setLineMapper(lineMapper())
        return itemReader
    }

    private fun lineMapper(): LineMapper<UserRequest> {
        val lineMapper = DefaultLineMapper<UserRequest>()
        val lineTokenizer = DelimitedLineTokenizer()
        lineTokenizer.setDelimiter(",")
        lineTokenizer.setStrict(false)
        lineTokenizer.setNames("name", "username", "password", "gender", "role", "phone", "email")

        val fieldSetMapper = BeanWrapperFieldSetMapper<UserRequest>()
        fieldSetMapper.setTargetType(UserRequest::class.java)

        lineMapper.setFieldSetMapper(fieldSetMapper)
        lineMapper.setLineTokenizer(lineTokenizer)
        return lineMapper
    }
}
