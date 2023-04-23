package at.qe.backend.services;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("application")
public class AccessPointService {
    @Autowired
    private AccessPointRepository accessPointRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AccessPoint> getAllAccessPoints() {
        return accessPointRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAccessPoint(AccessPoint accessPoint) {
        accessPointRepository.delete(accessPoint);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint loadAccessPoint(Long uuid) {
        return accessPointRepository.findFirstByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGreenhouseByIdAndAccessPointUuid(long greenhouseId, long accessPointUuid) {
        AccessPoint accessPoint = loadAccessPoint(accessPointUuid);
        Greenhouse greenhouse = accessPoint.getGreenhouses().stream().filter(g -> g.getIdInCluster() == greenhouseId).findFirst().orElse(null);
        if (greenhouse != null) {
            accessPoint.getGreenhouses().remove(greenhouse);
            greenhouseRepository.delete(greenhouse);
            accessPointRepository.save(accessPoint);
        }else {
            throw new IllegalArgumentException("Greenhouse with id " + greenhouseId + " not found in access point with uuid " + accessPointUuid);
        }
    }
}
