package com.hg.judger

import com.hg.judger.utils.ShellCommandProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ShellCommandProperties::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

