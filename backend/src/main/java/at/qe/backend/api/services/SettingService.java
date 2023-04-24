package at.qe.backend.api.services;

import at.qe.backend.exceptions.AccessPointNotFoundException;
import at.qe.backend.exceptions.AccessPointNotPublishedException;
import at.qe.backend.api.model.dto.AccessPointSettingDTO;
import at.qe.backend.api.model.dto.SensorDTO;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * This service is responsible for providing the correct settings to the access points
 */
@Service
public class SettingService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private AccessPointRepository accessPointRepository;

    /**
     * @param accessPointId The UUID of the requesting AccessPoint
     * @return a new {@link AccessPointSettingDTO} Object containing the most recent configuration for the corresponding AP
     * @throws AccessPointNotFoundException if no AccessPoint with a given UUID could be found.
     */
    public AccessPointSettingDTO getSetting(long accessPointId) throws AccessPointNotFoundException, AccessPointNotPublishedException {
        AccessPoint accessPoint = accessPointRepository.findFirstByUuid(accessPointId);
        if (accessPoint==null){
            throw new AccessPointNotFoundException();
        }
        if (!accessPoint.isPublished()){
            throw new AccessPointNotPublishedException();
        }
        accessPoint.setLastContact(new Date());
        AccessPointSettingDTO accessPointSettingDTO = new AccessPointSettingDTO();

        accessPointSettingDTO.setAccessPointId(accessPoint.getUuid());
        accessPointSettingDTO.setTransmissionIntervalSeconds(accessPoint.getTransmissionIntervalSeconds());
        ArrayList<SensorDTO> sensorSettings = new ArrayList<>();
        for (Sensor sensor : sensorRepository.findAllByGreenhouse_Accesspoint_Uuid(accessPoint.getUuid())) {
            SensorDTO newSensorSetting = new SensorDTO();
            newSensorSetting.setGreenhouseID(sensor.getGreenhouse().getIdInCluster());
            newSensorSetting.setSensorType(sensor.getSensorType());
            newSensorSetting.setLimitUpper(sensor.getLimitUpper());
            newSensorSetting.setLimitLower(sensor.getLimitLower());
            newSensorSetting.setLimitThresholdMinutes(sensor.getLimitThresholdMinutes());
            sensorSettings.add(newSensorSetting);
        }
        accessPointSettingDTO.setSensorSettings(sensorSettings);
        return accessPointSettingDTO;
    }
}
