package at.qe.backend.ui.controllers.admin;

import at.qe.backend.models.AuditLog;
import at.qe.backend.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the controller for the audit log functionality in the admin panel.
 * It provides REST endpoints for retrieving audit logs.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AuditLogController {

    /**
     * The service for retrieving audit logs.
     */
    @Autowired
    private AuditLogService auditLogService;

    /**
     * Retrieves all audit logs.
     *
     * @return a list of all audit logs
     */
    @GetMapping("/admin/get-all-audit-logs")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    /**
     * Retrieves audit logs by action.
     *
     * @param action the action to filter the audit logs by
     * @return a list of audit logs that match the specified action
     */
    @GetMapping("/admin/get-audit-logs-by-action/{action}")
    public List<AuditLog> getAuditLogsByAction(@PathVariable String action) {
        return auditLogService.getAuditLogsByAction(action);
    }
}


