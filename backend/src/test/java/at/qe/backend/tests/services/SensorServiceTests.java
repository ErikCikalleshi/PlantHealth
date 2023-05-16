package at.qe.backend.tests.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import at.qe.backend.models.dto.SensorDTO;
import at.qe.backend.repositories.SensorRepository;
import at.qe.backend.services.AuditLogService;
import at.qe.backend.services.SensorService;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
public class SensorServiceTests {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSaveSensor() {
        Sensor sensor = new Sensor();
        sensor.setId(1);
        sensor.setSensorType(SensorType.TEMPERATURE);
        sensor.setLimitUpper(30.0);
        sensor.setLimitLower(10.0);
        sensor.setLimitThresholdMinutes(5);
        sensor.setGreenhouse(new Greenhouse());
        when(sensorRepository.save(sensor)).thenReturn(sensor);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Sensor result = sensorService.saveSensor(sensor);

        assertEquals(sensor, result);
        assertEquals(username, sensor.getCreateUserUsername());
        assertNull(sensor.getUpdateUserUsername());

        result = sensorService.saveSensor(result);
        assertEquals(sensor, result);
        assertEquals(username, sensor.getCreateUserUsername());
        assertEquals(username, sensor.getUpdateUserUsername());


    }

    @Test
    public void testLoadSensorById() {
        int sensorId = 1;
        Sensor sensor = new Sensor();
        when(sensorRepository.findFirstById(sensorId)).thenReturn(sensor);

        Sensor result = sensorService.loadSensor(sensorId);

        assertEquals(sensor, result);
    }

    @Test
    public void testLoadSensorByGreenhouseAndSensorType() {
        Greenhouse greenhouse = new Greenhouse();
        SensorType sensorType = SensorType.TEMPERATURE;
        Sensor sensor = new Sensor();
        when(sensorRepository.findFirstByGreenhouseAndSensorType(greenhouse, sensorType)).thenReturn(sensor);

        Sensor result = sensorService.loadSensor(greenhouse, sensorType);

        assertEquals(sensor, result);
    }

    @Test
    public void testDeleteSensor() {
        Sensor sensor = new Sensor();
        sensor.setId(1);

        sensorService.deleteSensor(sensor);

        verify(auditLogService).createNewAudit("delete", Integer.toString(sensor.getId()), "sensor", true);
        verify(sensorRepository).delete(sensor);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN","GARDENER"})
    public void testCreateSensor() {
        SensorType sensorType = SensorType.TEMPERATURE;
        double limitUpper = 30.0;
        double limitLower = 10.0;
        int limitThresholdMinutes = 5;
        Greenhouse greenhouse = new Greenhouse();
        Sensor sensor = new Sensor();
        sensor.setSensorType(sensorType);
        sensor.setLimitUpper(limitUpper);
        sensor.setLimitLower(limitLower);
        sensor.setLimitThresholdMinutes(limitThresholdMinutes);
        sensor.setGreenhouse(greenhouse);
        when(sensorRepository.save(any())).thenReturn(sensor);

        Sensor result = sensorService.createSensor(sensorType, limitUpper, limitLower, limitThresholdMinutes, greenhouse);

        assertEquals(sensor, result);
        assertEquals(sensorType, result.getSensorType());
        assertEquals(limitUpper, result.getLimitUpper());
        assertEquals(limitLower, result.getLimitLower());
        assertEquals(limitThresholdMinutes, result.getLimitThresholdMinutes());
        assertEquals(greenhouse, result.getGreenhouse());
        verify(auditLogService).createNewAudit("create", Integer.toString(sensor.getId()), "sensor", true);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN","GARDENER"})
    public void testUpdateSensor() {
        Sensor sensorNew = new Sensor();
        sensorNew.setId(1);
        sensorNew.setSensorType(SensorType.TEMPERATURE);
        sensorNew.setLimitUpper(35.0);
        sensorNew.setLimitLower(15.0);
        sensorNew.setLimitThresholdMinutes(10);


        Sensor sensorOld = new Sensor();
        sensorOld.setId(1);
        sensorOld.setLimitUpper(30.0);
        sensorOld.setLimitLower(10.0);
        sensorOld.setLimitThresholdMinutes(5);
        sensorOld.setSensorType(SensorType.TEMPERATURE);

        SensorDTO sensorDTO = new SensorDTO(sensorNew);

        when(sensorRepository.save(sensorOld)).thenReturn(sensorOld);
        when(sensorRepository.findFirstById(sensorDTO.id())).thenReturn(sensorOld);
        when(sensorRepository.save(sensorNew)).thenReturn(sensorNew);

        Sensor result = sensorService.updateSensor(sensorDTO);
        assertEquals(sensorNew.getSensorType(), result.getSensorType());
        assertEquals(sensorNew.getLimitUpper(), result.getLimitUpper());
        assertEquals(sensorNew.getLimitLower(), result.getLimitLower());
        assertEquals(sensorNew.getLimitThresholdMinutes(), result.getLimitThresholdMinutes());
        assertEquals(sensorNew.getId(), result.getId());

    }
}