package at.qe.backend.api.model;

import at.qe.backend.models.SensorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawMeasurement {
    private int greenhouseID;
    private Double value;
    private SensorType sensorType;
}
