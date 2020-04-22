package judger.judge

import com.hg.judger.Application
import com.hg.judger.service.JudgeService
import com.hg.judger.vo.SubmissionInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class])
internal class JudgeServiceTest(@Autowired private val judgeService: JudgeService) {

    @Test
    @DisplayName("C 코드 채점 테스트")
    fun run1() {
        val code = "#include<stdio.h>\n" +
                "int main(){\n" +
                "\tint a, b;\n" +
                "\tscanf(\"%d %d\", &a, &b);\n" +
                "\tprintf(\"%d\", a+b);\n" +
                "\treturn 0;\n" +
                "}"
        val submissionInfo = SubmissionInfo(code, "c", "1 2", "3")

        assertThat(judgeService.run(submissionInfo).scoringCode).contains("CORRECT")
    }

    @Test
    @DisplayName("CPP 코드 채점 테스트")
    internal fun run2() {
        val code = "#include <iostream>\n" +
                "using namespace std;\n" +
                "int main(){\n" +
                "\tint a, b;\n" +
                "\tcin >> a >> b;\n" +
                "\tcout << a+b;\n" +
                "\treturn 0;\n" +
                "}"
        val submissionInfo = SubmissionInfo(code, "cpp", "1 2", "3")

        assertThat(judgeService.run(submissionInfo).scoringCode).contains("CORRECT")
    }
}