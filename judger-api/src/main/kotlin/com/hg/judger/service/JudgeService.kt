package com.hg.judger.service

import com.hg.judger.utils.ShellCommandProperties
import com.hg.judger.utils.ShellCommandUtils
import com.hg.judger.vo.ScoringResult
import com.hg.judger.vo.SubmissionInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter

@Service
class JudgeService(@Autowired private val shellCommandProperties: ShellCommandProperties) {

    fun run(submissionInfo: SubmissionInfo): ScoringResult {
        ShellCommandUtils.execCommand(shellCommandProperties.localInitCommand)

        createInputFile(submissionInfo.input)
        createSourceFile(submissionInfo.source, submissionInfo.language)
        ShellCommandUtils.execCommand(shellCommandProperties.cCompileCommand)
        ShellCommandUtils.execCommand(shellCommandProperties.cRunCommand)

        return ScoringResult(checkAnswer(submissionInfo.answer)!!)
    }

    private fun createSourceFile(source: String, language: String) {
        val fileWriter = FileWriter(shellCommandProperties.testerDir + "/" + shellCommandProperties.testFileName + "." + language)
        createFile(fileWriter, source)
    }

    private fun createInputFile(input: String) {
        val fileWriter = FileWriter(shellCommandProperties.testerDir + "/" + shellCommandProperties.inputFileName)
        createFile(fileWriter, input)
    }

    private fun createFile(fileWriter: FileWriter, content: String) {
        fileWriter.write(content)
        fileWriter.close()
    }

    private fun checkAnswer(answer: String): String? {
        val br = BufferedReader(FileReader(shellCommandProperties.testerDir + "/" + shellCommandProperties.outputFileName))
        var output = ""
        var temp: String?
        while (br.readLine().also { temp = it } != null) output += temp

        if (output == answer) {
            return "CORRECT ANSWER !!"
        }
        return "WRONG"
    }
}