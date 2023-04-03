package at.qe.backend.api.model.DTO;

import at.qe.backend.models.SensorType;
import lombok.Getter;
import lombok.Setter;

/**
 * This Class represents a Sensor DataTransmissionObject that contains all the settings for one Sensor of a Greenhouse
 * Objects of this Class are always sent back as children of the AccessPointSettingDTO
 */
@Getter
@Setter
public class SensorDTO {
    private long greenhouseID;
    private SensorType sensorType;
    private double limitUpper;
    private double limitLower;
    private int limitThresholdMinutes;
}
