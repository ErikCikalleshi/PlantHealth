package at.qe.backend.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Entity representing Greenhouses/Plants
 * Each Greenhouse can hold one plant, so it should be named accordingly
 * A Greenhouse CAN have at most one owner, but it can also have none
 */
@Entity
@Getter
@Setter



public class Greenhouse implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long uuid;
    private String name;
    private String location;
    private String description;
    @ManyToOne(optional = false)
    @JsonBackReference // exclude user from serialization
    private Userx createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    @ManyToOne(optional = true)
    private Userx updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @ManyToOne(optional = true)
    private Userx owner;

    @ManyToOne
    private AccessPoint accesspoint;
    @OneToMany(mappedBy = "greenhouse", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Sensor> sensors;

    @ManyToMany
    @JoinTable(name = "greenhouse_gardener",
            joinColumns = @JoinColumn(name = "greenhouse_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Userx> gardeners;
}
