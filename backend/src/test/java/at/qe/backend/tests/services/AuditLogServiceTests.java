package at.qe.backend.tests.services;

import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.AuditLogRepository;
import at.qe.backend.services.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AuditLogServiceTests {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAuditLogs() {
        List<AuditLog> auditLogs = new ArrayList<>();
        AuditLog auditLog1 = new AuditLog();
        auditLog1.setAction("Login");
        AuditLog auditLog2 = new AuditLog();
        auditLog2.setAction("Logout");
        auditLogs.add(auditLog1);
        auditLogs.add(auditLog2);
        when(auditLogRepository.findAll()).thenReturn(auditLogs);

        List<AuditLog> result = auditLogService.getAllAuditLogs();

        assertEquals(auditLogs, result);
    }

    @Test
    public void testGetAuditLogsByAction() {
        String action = "Login";
        List<AuditLog> auditLogs = new ArrayList<>();
        AuditLog auditLog1 = new AuditLog();
        auditLog1.setAction("Login");
        AuditLog auditLog2 = new AuditLog();
        auditLog2.setAction("Login");
        auditLogs.add(auditLog1);
        auditLogs.add(auditLog2);
        when(auditLogRepository.findAllByAction(action)).thenReturn(auditLogs);

        List<AuditLog> result = auditLogService.getAuditLogsByAction(action);

        assertEquals(auditLogs, result);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ADMIN"})
    public void testSaveAuditLog() {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction("Login");
        auditLog.setTargetID("123");
        auditLog.setTargetType("User");
        auditLog.setSuccess(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(auditLogRepository.save(any(AuditLog.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AuditLog result = auditLogService.saveAuditLog(auditLog);
        assertEquals("user", result.getUser());
        assertEquals(auditLog.getAction(), result.getAction());
        assertEquals(auditLog.getTargetID(), result.getTargetID());
        assertEquals(auditLog.getTargetType(), result.getTargetType());
        assertEquals(auditLog.getSuccess(), result.getSuccess());

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities = {"ADMIN"})
    public void testCreateNewAudit() {
        String action = "Login";
        String targetID = "123";
        String targetType = "User";
        Boolean success = true;
        Date date = new Date();

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setTargetID(targetID);
        auditLog.setTargetType(targetType);
        auditLog.setSuccess(success);
        auditLog.setDate(date);
        when(auditLogRepository.save(any(AuditLog.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AuditLog result = auditLogService.createNewAudit(action, targetID, targetType, success);

        assertEquals("user", result.getUser());
        assertEquals(auditLog.getAction(), result.getAction());
        assertEquals(auditLog.getTargetID(), result.getTargetID());
        assertEquals(auditLog.getTargetType(), result.getTargetType());
        assertEquals(auditLog.getSuccess(), result.getSuccess());


    }
}