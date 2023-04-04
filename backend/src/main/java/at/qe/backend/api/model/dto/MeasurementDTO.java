package at.qe.backend.api.model.dto;

import at.qe.backend.models.Measurement;
import at.qe.backend.models.SensorType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class represents the Measurement DataTransmissionObject that is returned to an AccessPoint after receiving a Measurement from it and successfully storing it in the database
 */
@Getter
@Setter
public class MeasurementDTO {
    public MeasurementDTO() {

    }

    /**
     * @param measurement Create a new MeasurementDTO from a Measurement without exposing too much data
     */
    public MeasurementDTO(Measurement measurement) {
        this.greenhouseID = measurement.getSensor().getGreenhouse().getIdInCluster();
        this.accesspointUUID = measurement.getSensor().getGreenhouse().getAccesspoint().getUuid();
        this.value = measurement.getValue();
        this.sensorType = measurement.getSensor().getSensorType();
        this.measurementId = measurement.getId();
        this.date = measurement.getMeasurementDate();
        this.limitExceededBy =measurement.getLimitExceededBy();
    }

    private long measurementId;
    private long accesspointUUID;
    private long greenhouseID;
    private Double value;
    private SensorType sensorType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private double limitExceededBy;
}
