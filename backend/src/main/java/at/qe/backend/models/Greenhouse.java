package at.qe.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
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
    @JoinColumn(name="owner_id")
    private Userx owner;


    @ManyToOne
    @JoinColumn(nullable = false)
    private AccessPoint accesspoint;
    @OneToMany(mappedBy = "greenhouse", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Sensor> sensors;

    private boolean published=false;
    private Date lastContact;
}
