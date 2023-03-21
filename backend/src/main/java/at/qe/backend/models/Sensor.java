package at.qe.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
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

    @ManyToOne(optional = false)
    private Userx createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    @ManyToOne(optional = true)
    private Userx updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "greenhouse_uuid", nullable = false)
    @JsonBackReference // exclude greenhouse from serialization
    private Greenhouse greenhouse;
    @OneToMany(mappedBy = "sensor", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Measurement> measurements=Set.of();

    public void addMeasurement(Measurement measurement){
        measurements.add(measurement);
    }
}
