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

    @Query("SELECT l FROM AuditLog l WHERE l.usernameModifier = :username_modifier")
    List <Userx> findAllLastModifiedBy(@Param("username_modifier") String username_modifier);

    @Query("SELECT l FROM AuditLog l WHERE l.action = :action")
    List <Userx> findByAction(@Param("action") String action);

    @Query("SELECT l FROM AuditLog l WHERE l.entityModified = :entity_modified")
    List <Userx> findEntityModified(@Param("entity_modified") String entity_modified);

    @Query("SELECT l FROM AuditLog l WHERE l.timestamp = :timestamp")
    List <Userx> findEntityModified(@Param("timestamp") Date timestamp);
}
