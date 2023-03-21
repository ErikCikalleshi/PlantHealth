package at.qe.backend.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "access_point")
public class AccessPoint implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int uuid;

    private String name;
    private String location;
    private String description;
    @ManyToOne(optional = false)
    private Userx createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    @ManyToOne(optional = true)
    private Userx updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @OneToMany(mappedBy = "accesspoint", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Greenhouse> greenhouses;
}
