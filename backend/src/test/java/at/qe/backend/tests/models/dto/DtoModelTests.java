package at.qe.backend.tests.models.dto;

import at.qe.backend.models.*;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.models.dto.GreenhouseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DtoModelTests {

    private AccessPoint accessPoint;
    private Greenhouse greenhouse;

    @BeforeEach
    public void setUp() {
        Userx user = new Userx();
        user.setUsername("user");
        accessPoint = new AccessPoint();
        accessPoint.setName("AP1");
        accessPoint.setLocation("Building A, Floor 2");
        accessPoint.setDescription("Access point for greenhouse monitoring");
        accessPoint.setTransmissionIntervalSeconds(60);
        accessPoint.setPublished(true);
        accessPoint.setUuid(1);
        HashSet<Greenhouse> greenhouses = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            Greenhouse greenhouse = new Greenhouse();
            greenhouse.setUuid(i);
            greenhouse.setIdInCluster(i);
            greenhouse.setOwner(user);
            greenhouse.setName("Greenhouse " + i);
            HashSet<Sensor> sensors = new HashSet<>();
            for (SensorType sensorType : SensorType.values()) {
                Sensor sensor = new Sensor();
                sensor.setSensorType(sensorType);
                sensor.setId(1);
                sensor.setLimitLower(0);
                sensor.setLimitUpper(100);
                sensor.setLimitThresholdMinutes(5);
                sensors.add(sensor);
            }
            greenhouse.setSensors(sensors);
            greenhouses.add(greenhouse);
        }
        accessPoint.setGreenhouses(greenhouses);
        greenhouse = greenhouses.stream().filter(greenhouse1 -> greenhouse1.getUuid() == 0).findFirst().orElse(null);
    }

    @Test
    public void testConstructorAccessPoint1() {
        AccessPointDTO accessPointDTO = new AccessPointDTO(accessPoint);
        assertEquals(1, accessPointDTO.id());
        assertEquals("AP1", accessPointDTO.name());
        assertEquals("Building A, Floor 2", accessPointDTO.location());
        assertEquals("Access point for greenhouse monitoring", accessPointDTO.description());
        assertEquals(60, accessPointDTO.transmissionInterval());
        assertTrue(accessPointDTO.published());
        assertEquals(4, accessPointDTO.greenhouses().size());
        assertNotNull(accessPointDTO.lastContact());
    }

    @Test
    public void testConstructorAccessPoint2() {
        AccessPointDTO accessPointDTO = new AccessPointDTO(accessPoint, false);
        assertEquals(1, accessPointDTO.id());
        assertEquals("AP1", accessPointDTO.name());
        assertEquals("Building A, Floor 2", accessPointDTO.location());
        assertEquals("Access point for greenhouse monitoring", accessPointDTO.description());
        assertEquals(60, accessPointDTO.transmissionInterval());
        assertTrue(accessPointDTO.published());
        assertEquals(0, accessPointDTO.greenhouses().size());
        assertNotNull(accessPointDTO.lastContact());
    }

    @Test
    public void testConstructorGreenhouse1() {
        GreenhouseDTO greenhouseDTO = new GreenhouseDTO(greenhouse);
        assertEquals(greenhouseDTO.id(), 0);
        assertEquals("Greenhouse 0", greenhouseDTO.name());
        assertEquals("user", greenhouseDTO.gardener().username());
        assertEquals(SensorType.values().length, greenhouseDTO.sensors().size());
    }
}
