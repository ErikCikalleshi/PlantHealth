package at.qe.backend.services;

import at.qe.backend.models.AuditLog;
import at.qe.backend.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Service for accessing and creating AuditLogs.
 *
 */

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * @return a collection of all audit logs.
     */
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }


    /**
     * Gets all audits identified with the same action.
     *
     * @param action the action to search for.
     * @return the audit logs with the given username.
     */
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findAllByAction(action);
    }

    /**
     * Saves the user. This method will also set {@link AuditLog#getDate()} for new entities.
     *
     * @param auditLog the audit log to save.
     * @return the saved audit log.
     */
    public AuditLog saveAuditLog(AuditLog auditLog) {
        auditLog.setDate(new Date());
        auditLog.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return auditLogRepository.save(auditLog);
    }

    /**
     * Create an audit log.
     *
     * @param action the action to search for.
     * @return the audit logs with the given username.
     */
    public AuditLog createNewAudit(String action,String targetID, String targetType, Boolean success) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setTargetID(targetID);
        auditLog.setTargetType(targetType);
        auditLog.setSuccess(success);
        return saveAuditLog(auditLog);
    }
}
