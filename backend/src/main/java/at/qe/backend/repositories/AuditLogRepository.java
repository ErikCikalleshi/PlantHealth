package at.qe.backend.repositories;

import at.qe.backend.models.AuditLog;
import at.qe.backend.models.Userx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository for managing {@link AuditLog} entities.
 *
 * has only find methods to get the audit logs from the database
 */

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("SELECT l FROM AuditLog l WHERE l.action = :action")
    List <AuditLog> findAllByAction(@Param("action") String action);
}
