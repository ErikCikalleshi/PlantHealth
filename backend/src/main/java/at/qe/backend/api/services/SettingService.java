package at.qe.backend.api.services;

import at.qe.backend.exceptions.AccessPoint.AccessPointNotFoundException;
import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.api.model.dto.AccessPointSettingDTO;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        AccessPoint accessPoint = accessPointRepository.findFirstByUuid(accessPointId).orElseThrow(AccessPointNotFoundException::new);
        if (!accessPoint.isPublished()){
            throw new AccessPointNotPublishedException();
        }
        accessPoint.setLastContact(new Date());
        return new AccessPointSettingDTO(accessPoint);
    }
}
