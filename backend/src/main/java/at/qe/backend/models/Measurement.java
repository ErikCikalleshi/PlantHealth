package at.qe.backend.models;

import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    @ManyToOne
    @JsonBackReference // exclude sensor from serialization
    private Sensor sensor;

//    public void setSensor(Sensor sensor){
//        this.sensor=sensor;
//    }

}
