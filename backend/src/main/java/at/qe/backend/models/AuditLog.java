package at.qe.backend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String usernameModifier;

    @Column(nullable = false)
    private String entityModified;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Date timestamp;

    public String getUsernameModifier() {
        return usernameModifier;
    }

    public void setUsernameModifier(String usernameModifier) {
        this.usernameModifier = usernameModifier;
    }

    public String getEntityModified() {
        return entityModified;
    }

    public void setEntityModified(String entityModified) {
        this.entityModified = entityModified;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
