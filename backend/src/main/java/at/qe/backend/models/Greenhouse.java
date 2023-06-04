package at.qe.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing Greenhouses/Plants
 * Each Greenhouse can hold one plant, so it should be named accordingly
 * A Greenhouse CAN have at most one owner, but it can also have none
 * The {@link Greenhouse#uuid} is unique globally, while the {@link Greenhouse#idInCluster} is only unique within greenhouses of a given access point
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"idInCluster", "accesspoint_uuid"})})
public class Greenhouse implements Serializable {
    //TODO PK should consist of uuid of greenhouse AND AP
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long uuid;
    @Column(nullable = false)
    private long idInCluster;
    private String name;
    private String location;
    private String description;

    @CreatedBy
    private String createUserUsername;
    @CreatedDate
    private Date createDate;
    @LastModifiedBy
    private String updateUserUsername;
    @LastModifiedDate
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Userx owner;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AccessPoint accesspoint;
    @OneToMany(mappedBy = "greenhouse", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Sensor> sensors = new HashSet<>();

    private boolean published = false;
    private Date lastContact;

    private Date lastLimitExceeded;

    public String getStatus() {
        if (lastContact == null) {
            return "OFFLINE";
        }
        long diff = new Date().getTime() - lastContact.getTime();
        if (diff > 1000L * accesspoint.getTransmissionIntervalSeconds() * 2) {
            //If last contact is more than 2x the transmission interval ago, we consider it offline
            return "OFFLINE";
        }
        return "ONLINE";
    }

    public boolean isNew() {
        return (createDate == null);
    }
}
