package judger.judge

import com.hg.judger.Application
import com.hg.judger.utils.ShellCommandProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class])  //TODO : @SpringBootApplication 왜 못찾는지..?
internal class ShellCommandPropertiesTest(@Autowired private val shellCommandProperties: ShellCommandProperties) {

    @Test
    @DisplayName("Properties 바인딩 테스트 - testDir")
    fun getTestDir() {
        print(shellCommandProperties.testerDir)
        assertThat(shellCommandProperties.testerDir).isNotNull()
    }

    @Test
    @DisplayName("Properties 바인딩 테스트 - dockerDir")
    fun getDockerDir() {
        print(shellCommandProperties.dockerDir)
        assertThat(shellCommandProperties.dockerDir).isNotNull()
    }

    @Test
    @DisplayName("Properties 바인딩 테스트 - localInitCommand")
    fun getLocalInitCommand() {
        print(shellCommandProperties.localInitCommand)
        assertThat(shellCommandProperties.localInitCommand).isNotNull()
    }

    @Test
    @DisplayName("Properties 바인딩 테스트 - cCompileCommand")
    fun getCCompileCommand() {
        print(shellCommandProperties.cCompileCommand)
        assertThat(shellCommandProperties.cCompileCommand).isNotNull()
    }

    @Test
    @DisplayName("Properties 바인딩 테스트 - cRunCommand")
    fun getCRunCommand() {
        print(shellCommandProperties.cRunCommand)
        assertThat(shellCommandProperties.cRunCommand).isNotNull()
    }
}