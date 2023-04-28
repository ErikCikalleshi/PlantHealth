package at.qe.backend.ui.controllers.admin;

import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Userx;
import at.qe.backend.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/auditlogs")
public class AuditLogController {
    private AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/admin/action/{action}")
    public List<AuditLog> getAuditLogsByAction(@PathVariable String action) {
        return auditLogService.getAuditLogsByAction(action);
    }
}

