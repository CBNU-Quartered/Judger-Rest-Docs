import com.hg.judger.Application
import com.hg.judger.vo.SubmissionInfo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
internal class JudgeControllerTest(@Autowired private val webTestClient: WebTestClient) {
    private val logger = LoggerFactory.getLogger(JudgeControllerTest::class.java)

    @Test
    internal fun name() {
        println("asd")
    }

    @Test
    @DisplayName("C 코드 채점 테스트")
    fun grade() {
        val code = "#include<stdio.h>\n" +
                "int main(){\n" +
                "\tint a, b;\n" +
                "\tscanf(\"%d %d\", &a, &b);\n" +
                "\tprintf(\"%d\", a+b);\n" +
                "\treturn 0;\n" +
                "}"
        val submissionInfo = SubmissionInfo(code, "c", "1 2", "3")

        webTestClient
            .post()
            .uri("/api/judge")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(submissionInfo), SubmissionInfo::class.java)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .jsonPath("$.scoringCode").isEqualTo("CORRECT ANSWER !!")
    }
}