package at.qe.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "access_point")
@Getter
@Setter
public class AccessPoint implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int uuid;

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

    @Column(nullable = false)
    private int transmissionIntervalSeconds;
    @OneToMany(mappedBy = "accesspoint", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Greenhouse> greenhouses;

    private boolean published = false;
    private Date lastContact;

}
