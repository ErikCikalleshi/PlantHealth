package at.qe.backend.api.services;

import at.qe.backend.api.exceptions.AccessPointNotFoundException;
import at.qe.backend.api.exceptions.GreenhouseNotRegisteredException;
import at.qe.backend.api.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.AccessPointSettingDTO;
import at.qe.backend.api.model.MeasurementDTO;
import at.qe.backend.api.model.SensorDTO;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.MeasurementRepository;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    public AccessPointSettingDTO getSetting(long accessPointId) throws AccessPointNotFoundException {
        AccessPoint accessPoint = accessPointRepository.findFirstByUuid(accessPointId);
        if (accessPoint==null){
            throw new AccessPointNotFoundException();
        }
        AccessPointSettingDTO accessPointSettingDTO = new AccessPointSettingDTO();

        accessPointSettingDTO.setAccessPointId(accessPoint.getUuid());
        accessPointSettingDTO.setTransmissionIntervalSeconds(accessPoint.getTransmissionIntervalSeconds());
        ArrayList<SensorDTO> sensorSettings = new ArrayList<>();
        for (Sensor sensor : sensorRepository.findAllByGreenhouse_Accesspoint_Uuid(accessPoint.getUuid())) {
            SensorDTO newSensorSetting = new SensorDTO();
            newSensorSetting.setGreenhouseID(sensor.getGreenhouse().getId());
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
