package com.hg.judger

import com.fasterxml.jackson.databind.ObjectMapper
import com.hg.judger.service.JudgeService
import com.hg.judger.vo.ScoringResult
import com.hg.judger.vo.SubmissionInfo
import com.hg.judger.web.JudgeController
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(JudgeController::class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class JudgeControllerTest(
    @Autowired private val mockMvc: MockMvc, @Autowired private val objectMapper: ObjectMapper
) {

    @MockBean
    lateinit var judgeService: JudgeService

    @Test
    @DisplayName("grade handler 문서화")
    fun grade() {
        //given
        val code = "#include<stdio.h>\n" +
                "int main(){\n" +
                "\tint a, b;\n" +
                "\tscanf(\"%d %d\", &a, &b);\n" +
                "\tprintf(\"%d\", a+b);\n" +
                "\treturn 0;\n" +
                "}"
        val submissionInfo = SubmissionInfo(code, "c", "1 2", "3")
        val scoringResult = ScoringResult("CORRECT ANSWER !!")

        given(judgeService.run(submissionInfo)).willReturn(scoringResult)

        //when
        val result = mockMvc.perform(
            post("/api/judge")
                .content(objectMapper.writeValueAsString(submissionInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        result.andExpect(status().isOk)
            .andDo(document(
                    "judge-grade",
                ApiDocumentUtils.documentRequest,
                ApiDocumentUtils.documentResponse,
                    requestFields(
                        fieldWithPath("source").type(JsonFieldType.STRING).description("소스코드"),
                        fieldWithPath("language").type(JsonFieldType.STRING).description("언어"),
                        fieldWithPath("input").type(JsonFieldType.STRING).description("입력값"),
                        fieldWithPath("answer").type(JsonFieldType.STRING).description("정답")
                    ),
                    responseFields(
                        fieldWithPath("scoringCode").type(JsonFieldType.STRING).description("채점코드")
                    )
                ))
    }
}