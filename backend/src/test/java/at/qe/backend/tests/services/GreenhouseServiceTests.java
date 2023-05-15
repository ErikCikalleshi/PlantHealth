package at.qe.backend.tests.services;

import at.qe.backend.models.*;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.dto.SensorDTO;
import at.qe.backend.models.request.CreateNewGreenhouseRequest;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
public class GreenhouseServiceTests {
    @Mock
    GreenhouseRepository greenhouseRepository;
    @Mock
    AuditLogService auditLogService;
    @Mock
    AccessPointService accessPointService;
    @Mock
    UserxService userxService;
    @Mock
    SensorService sensorService;
    @InjectMocks
    GreenhouseService greenhouseService;

    Greenhouse greenhouse;

    @BeforeEach
    void init() {
        Userx user = new Userx();
        user.setUsername("user");
        greenhouse = new Greenhouse();
        greenhouse.setIdInCluster(1);
        greenhouse.setOwner(user);
        greenhouse.setName("Greenhouse " + 1);
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
    }

    @Test
    public void testGetAll() {
        List<Greenhouse> greenhouses = new ArrayList<>();
        greenhouses.add(new Greenhouse());
        greenhouses.add(new Greenhouse());
        when(greenhouseRepository.findAll()).thenReturn(greenhouses);

        List<Greenhouse> result = greenhouseService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetNameById() {
        Long id = 1L;
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setName("Test Greenhouse");
        when(greenhouseRepository.findByUuid(id)).thenReturn(Optional.of(greenhouse));

        String result = greenhouseService.getNameById(id);

        assertEquals("Test Greenhouse", result);
    }

    @Test
    public void testGetNameByIdNotFound() {
        Long id = 1L;
        when(greenhouseRepository.findByUuid(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            greenhouseService.getNameById(id);
        });
    }

    @DisplayName("Create new greenhouse")
    @WithMockUser(username = "adminuser", authorities = { "ADMIN" })
    @Test
    void testCreateNewGreenhouse() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(1);
        Userx gardener = new Userx();
        gardener.setUsername("Test Gardener");
        greenhouse.setOwner(gardener);
        greenhouse.setAccesspoint(accessPoint);
        CreateNewGreenhouseRequest request = new CreateNewGreenhouseRequest(new GreenhouseDTO(greenhouse),
                greenhouse.getSensors().stream().map(SensorDTO::new).toList(), "1");
        when(auditLogService.createNewAudit(anyString(), anyString(), anyString(), anyBoolean()))
                .thenReturn(mock(AuditLog.class));
        when(accessPointService.loadAccessPoint(anyLong())).thenReturn(accessPoint);
        when(userxService.loadUser(anyString())).thenReturn(gardener);
        when(sensorService.saveSensor(any(Sensor.class))).thenReturn(new Sensor());
        when(greenhouseRepository.save(any(Greenhouse.class))).thenReturn(greenhouse);
        when(accessPointService.loadAccessPoint(anyLong())).thenReturn(accessPoint);

        // when
        Greenhouse result = greenhouseService.createGreenhouse(request);
        assertNotNull(result);
        assertEquals(greenhouse.getName(), result.getName());
        assertEquals(greenhouse.getLocation(), result.getLocation());
        assertEquals(greenhouse.getDescription(), result.getDescription());
        assertEquals(greenhouse.isPublished(), result.isPublished());
        assertEquals(gardener, result.getOwner());
        assertEquals(accessPoint, result.getAccesspoint());
        assertNotNull(result.getSensors());
        assertFalse(result.getSensors().isEmpty());
    }

    @Test
    public void testLoadGreenhouseByIdAndAccessPointUUID() {
        long greenhouseID = 1L;
        long accesspointUUID = 2L;
        Greenhouse greenhouse = new Greenhouse();
        when(greenhouseRepository.findFirstByIdInClusterAndAccesspoint_Uuid(greenhouseID, accesspointUUID))
                .thenReturn(greenhouse);

        Greenhouse result = greenhouseService.loadGreenhouse(greenhouseID, accesspointUUID);

        assertEquals(greenhouse, result);
    }

    @Test
    public void testLoadGreenhouseByUUID() {
        long greenhouseUUID = 1L;
        Greenhouse greenhouse = new Greenhouse();
        when(greenhouseRepository.findByUuid(greenhouseUUID)).thenReturn(Optional.of(greenhouse));

        Greenhouse result = greenhouseService.loadGreenhouse(greenhouseUUID);

        assertEquals(greenhouse, result);
    }

    @Test
    public void testLoadGreenhouseByUUIDNotFound() {
        long greenhouseUUID = 1L;
        when(greenhouseRepository.findByUuid(greenhouseUUID)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            greenhouseService.loadGreenhouse(greenhouseUUID);
        });
    }

    @Test
    public void testUpdateLastContact() {
        Greenhouse greenhouse = new Greenhouse();
        AccessPoint accessPoint = new AccessPoint();
        greenhouse.setAccesspoint(accessPoint);
        when(greenhouseRepository.save(greenhouse)).thenReturn(greenhouse);

        greenhouseService.updateLastContact(greenhouse);

        verify(greenhouseRepository).save(greenhouse);
        verify(accessPointService).saveAccessPoint(accessPoint);
    }

    @Test
    @WithMockUser(username = "user", authorities = { "ADMIN" })
    public void testUpdateGreenhouse() {
        Greenhouse greenhouse = new Greenhouse();
        Userx user = new Userx();
        user.setUsername("user");
        greenhouse.setOwner(user);
        greenhouse.setUuid(1L);
        greenhouse.setName("Test Greenhouse");
        greenhouse.setLocation("Test Location");
        greenhouse.setDescription("Test Description");
        greenhouse.setPublished(false);
        Greenhouse oldGreenhouse = new Greenhouse();
        oldGreenhouse.setOwner(user);
        oldGreenhouse.setUuid(1L);
        oldGreenhouse.setName("Old Greenhouse");
        oldGreenhouse.setLocation("Old Location");
        oldGreenhouse.setDescription("Old Description");
        oldGreenhouse.setPublished(true);
        GreenhouseDTO greenhouseDTOnew = new GreenhouseDTO(greenhouse);
        List<SensorDTO> sensors = new ArrayList<>();
        Sensor sensor = new Sensor();
        sensor.setId(1);
        sensor.setSensorType(SensorType.TEMPERATURE);
        SensorDTO sensorDTO = new SensorDTO(sensor);
        greenhouse.getSensors().add(sensor);

        when(greenhouseRepository.findByUuid(greenhouseDTOnew.uuid())).thenReturn(Optional.of(greenhouse));
        when(sensorService.updateSensor(sensorDTO)).thenReturn(sensor);
        when(greenhouseRepository.save(greenhouse)).thenReturn(greenhouse);
        when(auditLogService.createNewAudit(anyString(), anyString(), anyString(), anyBoolean()))
                .thenReturn(mock(AuditLog.class));
        Greenhouse result = greenhouseService.updateGreenhouse(greenhouseDTOnew);

        assertEquals(greenhouse.getName(), result.getName());
        assertEquals(greenhouse.getLocation(), result.getLocation());
        assertEquals(greenhouse.getDescription(), result.getDescription());
        assertEquals(greenhouse.isPublished(), result.isPublished());
        assertEquals(greenhouse.getSensors().size(), result.getSensors().size());
        verify(auditLogService).createNewAudit("update", "NA", "greenhouse", false);

    }
}
