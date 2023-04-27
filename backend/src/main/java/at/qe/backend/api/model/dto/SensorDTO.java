package at.qe.backend.api.model.dto;

import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;

/**
 * This Class represents a Sensor DataTransmissionObject that contains all the settings for one Sensor of a Greenhouse
 * Objects of this Class are always sent back as children of the AccessPointSettingDTO
 */
public record SensorDTO(long greenhouseID, SensorType sensorType, double limitUpper, double limitLower, int limitThresholdMinutes, int id) {
    public SensorDTO(Sensor sensor){
        this(sensor.getGreenhouse().getUuid(), sensor.getSensorType(), sensor.getLimitUpper(), sensor.getLimitLower(), sensor.getLimitThresholdMinutes(), sensor.getId());
    }
}
