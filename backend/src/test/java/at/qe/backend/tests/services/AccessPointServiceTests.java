package at.qe.backend.tests.services;


import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.services.AccessPointService;
import at.qe.backend.services.AuditLogService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AccessPointServiceTests {

    @Mock
    private AccessPointRepository accessPointRepository;

    @Mock
    private GreenhouseRepository greenhouseRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private AccessPointService accessPointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAccessPoints() {
        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint1 = new AccessPoint();
        accessPoint1.setUuid(1);
        AccessPoint accessPoint2 = new AccessPoint();
        accessPoint2.setUuid(2);
        accessPoints.add(accessPoint1);
        accessPoints.add(accessPoint2);
        when(accessPointRepository.findAll()).thenReturn(accessPoints);

        List<AccessPoint> result = accessPointService.getAllAccessPoints();

        assertEquals(accessPoints, result);
    }

    @Test
    public void testDeleteAccessPoint() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(1);

        accessPointService.deleteAccessPoint(accessPoint);

        verify(auditLogService).createNewAudit("delete", Long.toString(accessPoint.getUuid()), "accesspoint", true);
        verify(accessPointRepository).delete(accessPoint);
    }

    @Test
    public void testLoadAccessPoint() {
        long uuid = 1L;
        AccessPoint accessPoint = new AccessPoint();
        when(accessPointRepository.findFirstByUuid(uuid)).thenReturn(Optional.of(accessPoint));
        AccessPoint result = accessPointService.loadAccessPoint(uuid);
        assertEquals(accessPoint, result);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testSaveAccessPoint() {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(1);
        accessPoint.setName("Test Access Point");
        accessPoint.setLocation("Test Location");
        accessPoint.setDescription("Test Description");
        accessPoint.setTransmissionIntervalSeconds(60);
        accessPoint.setPublished(true);
        when(accessPointRepository.save(any(AccessPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        AccessPoint result = accessPointService.saveAccessPoint(accessPoint);

        assertEquals(accessPoint, result);
        assertEquals(username, accessPoint.getCreateUserUsername());
        assertNull(accessPoint.getUpdateUserUsername());
    }

    @Test
    public void testDeleteGreenhouseByIdAndAccessPointUuid() {
        int greenhouseId = 1;
        int accessPointUuid = 1;
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(accessPointUuid);
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setIdInCluster(greenhouseId);
        accessPoint.getGreenhouses().add(greenhouse);
        when(accessPointRepository.findFirstByUuid(accessPointUuid)).thenReturn(Optional.of(accessPoint));

        accessPointService.deleteGreenhouseByIdAndAccessPointUuid(greenhouseId, accessPointUuid);

        verify(auditLogService).createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", true);
        verify(greenhouseRepository).delete(greenhouse);
        verify(accessPointRepository).save(accessPoint);
    }

    @Test
    public void testDeleteGreenhouseByIdAndAccessPointUuidNotFound() {
        int greenhouseId = 1;
        int accessPointUuid = 1;
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(accessPointUuid);
        when(accessPointRepository.findFirstByUuid(accessPointUuid)).thenReturn(Optional.of(accessPoint));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accessPointService.deleteGreenhouseByIdAndAccessPointUuid(greenhouseId, accessPointUuid);
        });

        assertEquals("Greenhouse with id 1 not found in access point with uuid 1", exception.getMessage());
        verify(auditLogService).createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", false);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testCreateNewAccessPoint() {
        String name = "Test Access Point";
        String location = "Test Location";
        String description = "Test Description";
        int transmissionIntervalSeconds = 60;
        boolean published = true;
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setName(name);
        accessPoint.setLocation(location);
        accessPoint.setDescription(description);
        accessPoint.setTransmissionIntervalSeconds(transmissionIntervalSeconds);
        accessPoint.setPublished(published);
        when(accessPointRepository.save(any(AccessPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccessPoint result = accessPointService.createNewAccessPoint(name, location, description, transmissionIntervalSeconds, published);
        assertEquals(accessPoint.getName(), result.getName());
        assertEquals(accessPoint.getLocation(), result.getLocation());
        assertEquals(accessPoint.getDescription(), result.getDescription());
        assertEquals(accessPoint.getTransmissionIntervalSeconds(), result.getTransmissionIntervalSeconds());
        assertEquals(accessPoint.isPublished(), result.isPublished());
        verify(auditLogService).createNewAudit("create", Long.toString(accessPoint.getUuid()), "accesspoint", true);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testUpdateAccessPoint() {
        int id = 1;
        String name = "Test Access Point";
        String location = "Test Location";
        String description = "Test Description";
        int transmissionIntervalSeconds = 60;
        boolean published = true;
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUuid(1);
        accessPoint.setName("Old Name");
        accessPoint.setLocation("Old Location");
        accessPoint.setDescription("Old Description");
        accessPoint.setTransmissionIntervalSeconds(30);
        accessPoint.setPublished(false);
        when(accessPointRepository.findFirstByUuid(accessPoint.getUuid())).thenReturn(Optional.of(accessPoint));
        when(accessPointRepository.save(any(AccessPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccessPoint result = accessPointService.updateAccessPoint(id, name, location, description, transmissionIntervalSeconds, published);

        assertEquals(accessPoint, result);
        assertEquals(name, accessPoint.getName());
        assertEquals(location, accessPoint.getLocation());
        assertEquals(description, accessPoint.getDescription());
        assertEquals(transmissionIntervalSeconds, accessPoint.getTransmissionIntervalSeconds());
        assertEquals(published, accessPoint.isPublished());
        verify(auditLogService).createNewAudit("update", Long.toString(accessPoint.getUuid()), "accesspoint", true);
    }
}