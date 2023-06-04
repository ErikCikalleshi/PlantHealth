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
import java.util.HashSet;
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
    private Set<Greenhouse> greenhouses= new HashSet<>();

    private boolean published = false;
    private Date lastContact;


    public String getStatus() {
        if (lastContact == null) {
            return "OFFLINE";
        }
        long diff = new Date().getTime() - lastContact.getTime();
        if (diff > 1000L * getTransmissionIntervalSeconds()*2) {
            //If last contact is more than 2x the transmission interval ago, we consider it offline
            return "OFFLINE";
        }
        return "ONLINE";
    }
    public boolean isNew() {
        return (null == createDate);
    }

}
