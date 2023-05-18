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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
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
        when(greenhouseRepository.save(any(Greenhouse.class))).thenAnswer(invocation -> invocation.getArgument(0));
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
        when(greenhouseRepository.save(any(Greenhouse.class))).thenAnswer(invocation -> invocation.getArgument(0));

        greenhouseService.updateLastContact(greenhouse);

        verify(greenhouseRepository).save(greenhouse);
        verify(accessPointService).saveAccessPoint(accessPoint);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
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
        Sensor sensor = new Sensor();
        sensor.setId(1);
        sensor.setSensorType(SensorType.TEMPERATURE);
        SensorDTO sensorDTO = new SensorDTO(sensor);
        greenhouse.getSensors().add(sensor);
        GreenhouseDTO greenhouseDTOnew = new GreenhouseDTO(greenhouse);

        when(greenhouseRepository.findByUuid(greenhouseDTOnew.uuid())).thenReturn(Optional.of(greenhouse));
        when(sensorService.updateSensor(sensorDTO)).thenReturn(sensor);
        when(greenhouseRepository.save(any(Greenhouse.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditLogService.createNewAudit(anyString(), anyString(), anyString(), anyBoolean()))
                .thenReturn(mock(AuditLog.class));
        Greenhouse result = greenhouseService.updateGreenhouse(greenhouseDTOnew);

        assertEquals(greenhouse.getName(), result.getName());
        assertEquals(greenhouse.getLocation(), result.getLocation());
        assertEquals(greenhouse.getDescription(), result.getDescription());
        assertEquals(greenhouse.isPublished(), result.isPublished());
        assertEquals(greenhouse.getSensors().size(), result.getSensors().size());
        assertEquals(greenhouse.getSensors().iterator().next().getId(), result.getSensors().iterator().next().getId());
        assertEquals(greenhouse.getSensors().iterator().next().getSensorType(),
                result.getSensors().iterator().next().getSensorType());
        verify(auditLogService).createNewAudit("update", "NA", "greenhouse", false);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetAllForCurrentUserAsAdmin() {
        List<Greenhouse> greenhouses = new ArrayList<>();
        Greenhouse greenhouse1 = new Greenhouse();
        greenhouse1.setIdInCluster(1L);
        Greenhouse greenhouse2 = new Greenhouse();
        greenhouse2.setIdInCluster(2L);
        greenhouses.add(greenhouse1);
        greenhouses.add(greenhouse2);
        when(greenhouseRepository.findAll()).thenReturn(greenhouses);

        Userx currentUser = new Userx();
        currentUser.setUsername("admin");
        currentUser.setRoles(new HashSet<>());
        currentUser.getRoles().add(UserRole.ADMIN);
        when(userxService.loadUser("admin")).thenReturn(currentUser);
        List<Greenhouse> result = greenhouseService.getAllForCurrentUser();
        assertEquals(greenhouses, result);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"GARDENER"})
    public void testGetAllForCurrentUserAsOwner() {
        List<Greenhouse> greenhouses = new ArrayList<>();
        Greenhouse greenhouse1 = new Greenhouse();
        greenhouse1.setIdInCluster(1L);
        greenhouse1.setOwner(new Userx());
        greenhouse1.getOwner().setUsername("user");
        Greenhouse greenhouse2 = new Greenhouse();
        greenhouse2.setIdInCluster(2L);
        greenhouse2.setOwner(new Userx());
        greenhouse2.getOwner().setUsername("user");
        greenhouses.add(greenhouse1);
        greenhouses.add(greenhouse2);
        when(greenhouseRepository.findAllByOwner_Username("user")).thenReturn(greenhouses);

        Userx currentUser = new Userx();
        currentUser.setUsername("user");
        currentUser.setRoles(new HashSet<>());
        when(userxService.loadUser("user")).thenReturn(currentUser);

        List<Greenhouse> result = greenhouseService.getAllForCurrentUser();

        assertEquals(greenhouses, result);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"GARDENER"})
    public void testGetAllForCurrentUserUnauthorized() {
        when(userxService.loadUser(any())).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            greenhouseService.getAllForCurrentUser();
        });
        assertEquals("401 UNAUTHORIZED \"You are not logged in. (invalid user)\"", exception.getMessage());

                try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)
        ) {
            SecurityContext securityContext = mock(SecurityContext.class);
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);
            ResponseStatusException exception2 = assertThrows(ResponseStatusException.class, () -> {
                greenhouseService.getAllForCurrentUser();
            });
            assertEquals("401 UNAUTHORIZED \"You are not logged in.\"", exception2.getMessage());
        }
    }
}
