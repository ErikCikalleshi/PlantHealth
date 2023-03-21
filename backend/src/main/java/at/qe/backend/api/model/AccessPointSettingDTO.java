package at.qe.backend.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This Class represents the DataTransmissionObject that is returned after an access point makes a getSetting Request
 */
@Getter
@Setter
public class AccessPointSettingDTO {
    private long accessPointId;
    private int transmissionIntervalSeconds;
    private List<SensorDTO> sensorSettings;
}
