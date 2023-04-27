package at.qe.backend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username_modifier;

    @Column(nullable = false)
    private String entity_modified;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
