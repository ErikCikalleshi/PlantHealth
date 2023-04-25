package at.qe.backend.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This Class represents the DataTransmissionObject that is returned after an access point makes a getSetting Request
 * {@link AccessPointSettingDTO#sensorSettings} Contains all Sensors and their individual settings which are assigned to the greenhouses connected with the requesting AP
 */
@Getter
@Setter
public class AccessPointSettingDTO {
    private long accessPointId;
    private int transmissionIntervalSeconds;
    private List<SensorDTO> sensorSettings;
}
