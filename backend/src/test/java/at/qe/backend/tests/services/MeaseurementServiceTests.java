package at.qe.backend.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class MeaseurementServiceTests {

    @BeforeEach
    public void setup() {
        
    }

    @Test
    public void TestAddMeasurementSuccess() {

    }
}
