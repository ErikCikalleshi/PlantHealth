package at.qe.backend.services;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Scope("application")
public class AccessPointService {
    @Autowired
    private AccessPointRepository accessPointRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;

    @Autowired
    private AuditLogService auditLogService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AccessPoint> getAllAccessPoints() {
        return accessPointRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAccessPoint(AccessPoint accessPoint) {
        accessPointRepository.delete(accessPoint);
        auditLogService.createNewAudit("delete", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint loadAccessPoint(Long uuid) {
        return accessPointRepository.findFirstByUuid(uuid);
    }


    public AccessPoint saveAccessPoint(AccessPoint accessPoint) {
        if (accessPoint.isNew()) {
            accessPoint.setCreateDate(new Date());
            accessPoint.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            auditLogService.createNewAudit("create", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
        } else {
            accessPoint.setUpdateDate(new Date());
            accessPoint.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return accessPointRepository.save(accessPoint);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGreenhouseByIdAndAccessPointUuid(long greenhouseId, long accessPointUuid) {
        AccessPoint accessPoint = loadAccessPoint(accessPointUuid);
        Greenhouse greenhouse = accessPoint.getGreenhouses().stream().filter(g -> g.getIdInCluster() == greenhouseId).findFirst().orElse(null);
        if (greenhouse != null) {
            accessPoint.getGreenhouses().remove(greenhouse);
            greenhouseRepository.delete(greenhouse);
            accessPointRepository.save(accessPoint);
            auditLogService.createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", true);
        }else {
            auditLogService.createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", false);
            throw new IllegalArgumentException("Greenhouse with id " + greenhouseId + " not found in access point with uuid " + accessPointUuid);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint createNewAccessPoint(String name, String location, String description, int i, boolean published) {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setName(name);
        accessPoint.setLocation(location);
        accessPoint.setDescription(description);
        accessPoint.setTransmissionIntervalSeconds(i);
        accessPoint.setPublished(published);
        auditLogService.createNewAudit("create", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
        return saveAccessPoint(accessPoint);
    }

    public AccessPoint updateAccessPoint(int id, String name, String location, String description, int i, boolean published) {
        AccessPoint accessPoint = loadAccessPoint((long) id);
        accessPoint.setName(name);
        accessPoint.setLocation(location);
        accessPoint.setDescription(description);
        accessPoint.setTransmissionIntervalSeconds(i);
        accessPoint.setPublished(published);
        auditLogService.createNewAudit("update", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
        return saveAccessPoint(accessPoint);
    }
}