package br.com.ismyburguer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {Application.class, TestSecurityConfig.class})
class ApplicationTest {

    @Test
    void applicationContextLoads() {
    }

}
