package at.qe.backend.ui.controllers.admin;

import at.qe.backend.models.AuditLog;
import at.qe.backend.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/admin/get-all-audit-logs")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/admin/get-audit-logs-by-action/{action}")
    public List<AuditLog> getAuditLogsByAction(@PathVariable String action) {
        return auditLogService.getAuditLogsByAction(action);
    }
}


