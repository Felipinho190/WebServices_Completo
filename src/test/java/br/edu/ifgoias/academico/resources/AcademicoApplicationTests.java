package br.edu.ifgoias.academico; // Package deve vir primeiro

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:/application-test.properties")
class AcademicoApplicationTests {

    @Test
    void contextLoads() {
    }
}
