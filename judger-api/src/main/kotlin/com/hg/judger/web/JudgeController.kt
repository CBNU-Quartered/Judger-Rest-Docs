package com.hg.judger.web

import com.hg.judger.service.JudgeService
import com.hg.judger.vo.ScoringResult
import com.hg.judger.vo.SubmissionInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/judge")
class JudgeController(private val judgeService: JudgeService) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(JudgeController::class.java)
    }

    @PostMapping
    fun grade(@RequestBody submissionInfo: SubmissionInfo): ResponseEntity<ScoringResult> {
        val scoringCode = judgeService.run(
            submissionInfo.source,
            submissionInfo.language,
            submissionInfo.input,
            submissionInfo.answer
        )
        val scoringResult = ScoringResult(scoringCode)

        logger.info("scoringResult: {}", scoringResult)

        return ResponseEntity.ok().body(scoringResult)
    }
}