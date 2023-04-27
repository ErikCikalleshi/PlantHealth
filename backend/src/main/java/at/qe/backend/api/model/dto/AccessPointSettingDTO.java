package at.qe.backend.api.model.dto;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.dto.SensorDTO;

import java.util.Collection;
import java.util.List;

/**
 * This Record represents the DataTransmissionObject that is returned after an access point makes a getSetting Request
 * {@link AccessPointSettingDTO#greenhouses} Contains all Sensors and their individual settings which are assigned to the greenhouses connected with the requesting AP
 */
public record AccessPointSettingDTO(long accessPointId, int transmissionIntervalSeconds,
                                    List<GreenhouseApiDTO> greenhouses) {
    public AccessPointSettingDTO(AccessPoint accessPoint) {
        this(accessPoint.getUuid(), accessPoint.getTransmissionIntervalSeconds(), accessPoint.getGreenhouses().stream().map(GreenhouseApiDTO::new).toList());
    }
}


record GreenhouseApiDTO(long id, boolean published, Collection<SensorDTO> sensors) {
    public GreenhouseApiDTO(Greenhouse greenhouse) {
        this(greenhouse.getIdInCluster(), greenhouse.isPublished(), greenhouse.getSensors().stream().map(SensorDTO::new).toList());
    }
}
