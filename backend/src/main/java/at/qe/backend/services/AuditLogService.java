package at.qe.backend.services;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }


    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findAllByAction(action);
    }

    public AuditLog saveAuditLog(AuditLog auditLog) {
        auditLog.setTimestamp(new Date());
        auditLog.setUsernameModifier(SecurityContextHolder.getContext().getAuthentication().getName());
        return auditLogRepository.save(auditLog);
    }

    public AuditLog createNewAudit(String action, String EntityModified) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityModified(EntityModified);
        return saveAuditLog(auditLog);
    }

}
