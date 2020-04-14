package com.hg.judger.utils

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "judge.shell.command")
class ShellCommandProperties {
    lateinit var testerDir: String
    lateinit var dockerDir: String
    lateinit var localInitCommand: String
    lateinit var cCompileCommand: String
    lateinit var cRunCommand: String
}