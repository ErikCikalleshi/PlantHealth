package at.qe.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
public class Measurement implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    private Double value;
    @Column(nullable = false)

    @CreatedDate
    private Date createDate = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date measurementDate;
    @ManyToOne
    @JsonBackReference // exclude sensor from serialization
    private Sensor sensor;

    private double LimitExceededBy;
    private double LimitLower;
    private double LimitUpper;
}
