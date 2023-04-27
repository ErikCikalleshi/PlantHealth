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

    public List<Userx> getAllLastModifiedBy(String usernameModifier) {
        return auditLogRepository.findAllLastModifiedBy(usernameModifier);
    }

    public List<Userx> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    public List<Userx> getEntityModified(String entityModified) {
        return auditLogRepository.findEntityModified(entityModified);
    }

    public AuditLog saveAuditLog(AuditLog auditLog) {
        auditLog.setTimestamp(new Date());
        auditLog.setUsernameModifier(SecurityContextHolder.getContext().getAuthentication().getName());
        return auditLogRepository.save(auditLog);
    }

}
