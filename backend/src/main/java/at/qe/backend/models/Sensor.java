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

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"sensor_type", "greenhouse_uuid"})})
public class Sensor implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;
    private double limitUpper;
    private double limitLower;
    private int limitThresholdMinutes;

    @CreatedBy
    private String createUserUsername;
    @CreatedDate
    private Date createDate;
    @LastModifiedBy
    private String updateUserUsername;
    @LastModifiedDate
    private Date updateDate;

    private Date lastLimitExceeded;

    @ManyToOne
    @JoinColumn(name = "greenhouse_uuid", nullable = false)
    @JsonBackReference // exclude greenhouse from serialization
    private Greenhouse greenhouse;
    @OneToMany(mappedBy = "sensor", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Measurement> measurements= new HashSet<>();
    public void addMeasurement(Measurement measurement){
        measurements.add(measurement);
    }

    public boolean isNew() {
        return (null == createDate);
    }
}
