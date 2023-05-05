package at.qe.backend.tests.services;

import at.qe.backend.exceptions.Userx.UserAlreadyExistsException;
import at.qe.backend.models.AuditLog;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.AuditLogRepository;
import at.qe.backend.services.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AuditLogTests {

    @Mock
    AuditLogService auditLogService;


    private Userx testUser1;

    private Userx testUser2;
    private List<AuditLog> testAudits;
    private List<AuditLog> createAudits;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @BeforeEach
    void setUp() {
        testUser1 = new Userx();
        testUser1.setUsername("testuser1");
        testUser1.setFirstName("Testt");
        testUser1.setLastName("Userr");
        testUser1.setPassword("password");
        testUser1.setEmail("testuser1@example.com");
        testUser1.setRoles(Set.of(UserRole.USER));
        testUser1.setId(25L);

        testUser2 = new Userx();
        testUser2.setUsername("testuser2");
        testUser2.setFirstName("Tes");
        testUser2.setLastName("Use");
        testUser2.setPassword("password");
        testUser2.setEmail("testuser2@example.com");
        testUser2.setRoles(Set.of(UserRole.USER));
        testUser2.setId(24L);

        createAudits = new ArrayList<>();
        testAudits = new ArrayList<>();
        AuditLog audit1 = auditLogService.createNewAudit("create", Long.toString(testUser1.getId()), "user", true);
        AuditLog audit2 = auditLogService.createNewAudit("update", Long.toString(testUser2.getId()), "user", true);
        createAudits.add(audit1);
        testAudits.add(audit1);
        testAudits.add(audit2);
    }

    @Test
    @DisplayName("createNewAudit")
    void testCreateNewAudit() {
        auditLogService.createNewAudit("create", Long.toString(testUser1.getId()), "user", true);
        assertNotNull(auditLogService.getAllAuditLogs());
    }

    @Test
    @DisplayName("getAuditLogsByAction")
    void testGetAuditLogsByAction() {
        when(auditLogService.getAuditLogsByAction("create")).thenReturn(createAudits);
        Collection<AuditLog> actionAudits = auditLogService.getAuditLogsByAction("create");
        assertEquals(1, actionAudits.size());
    }
}
