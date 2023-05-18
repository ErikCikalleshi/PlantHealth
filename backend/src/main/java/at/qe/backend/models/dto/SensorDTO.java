package at.qe.backend.models.dto;

import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;

/**
 * This Class represents a Sensor DataTransmissionObject that contains all the settings for one Sensor of a Greenhouse
 * Objects of this Class are always sent back as children of the AccessPointSettingDTO
 */
public record SensorDTO(int id, SensorType sensorType, double limitUpper, double limitLower, int limitThresholdMinutes) {
    public SensorDTO(Sensor sensor) {
        this(sensor.getId(), sensor.getSensorType(), sensor.getLimitUpper(), sensor.getLimitLower(), sensor.getLimitThresholdMinutes());
    }
}
