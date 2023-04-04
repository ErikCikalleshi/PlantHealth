package at.qe.backend.tests.models;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessPointTests {

    @Test
    public void setNameAndGetName() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setName("AP1");
        assertEquals("AP1", accessPoint.getName());
    }

    @Test
    public void setLocationAndGetLocation() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setLocation("Building A, Floor 2");
        assertEquals("Building A, Floor 2", accessPoint.getLocation());
    }
    @Test
    public void setDescriptionAndGetDescription() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setDescription("Access point for greenhouse monitoring");
        assertEquals("Access point for greenhouse monitoring", accessPoint.getDescription());
    }
    @Test
    public void setTransmissionIntervalSecondsAndGetTransmissionIntervalSeconds() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setTransmissionIntervalSeconds(60);
        assertEquals(60, accessPoint.getTransmissionIntervalSeconds());
    }
}