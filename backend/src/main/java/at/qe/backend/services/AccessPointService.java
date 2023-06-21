package at.qe.backend.services;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.UserRole;
import at.qe.backend.repositories.AccessPointRepository;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * Service for creating, updating and deleting access points.
 * Requires the {@link UserRole} Admin.
 */
@Component
@Scope("application")
public class AccessPointService {

    @Autowired
    private AccessPointRepository accessPointRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * @return all access points
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AccessPoint> getAllAccessPoints() {
        return accessPointRepository.findAll();
    }

    /**
     * @param accessPoint the access point to be deleted
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAccessPoint(AccessPoint accessPoint) {
        accessPointRepository.delete(accessPoint);
        auditLogService.createNewAudit("delete", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
    }

    /**
     * @param uuid the uuid of the access point to be loaded
     * @return AccessPoint
     * @throws IllegalArgumentException if no access point with the given uuid exists
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint loadAccessPoint(Long uuid) {
        return accessPointRepository.findFirstByUuid(uuid).orElseThrow(IllegalArgumentException::new);
    }


    /**
     * saves the given access point and sets the create date and user if it is a new access point or the update date and user if it is an existing access point
     * @param accessPoint the access point to be saved
     * @return the saved access point(with a new uuid if it was a new access point)
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint saveAccessPoint(AccessPoint accessPoint) {
        if (accessPoint.isNew()) {
            accessPoint.setCreateDate(new Date());
            accessPoint.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            accessPoint.setUpdateDate(new Date());
            accessPoint.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return accessPointRepository.save(accessPoint);
    }


    /**
     * @param greenhouseId    the id of the greenhouse to be deleted(id set on arduino board)
     * @param accessPointUuid the uuid of the access point to be deleted
     *                        Deletes the greenhouse with the given id belonging to the access point with the given uuid
     * @throws IllegalArgumentException if no greenhouse with the given id exists in the access point with the given uuid
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGreenhouseByIdAndAccessPointUuid(long greenhouseId, long accessPointUuid) {
        AccessPoint accessPoint = loadAccessPoint(accessPointUuid);
        Greenhouse greenhouse = accessPoint.getGreenhouses().stream().filter(g -> g.getIdInCluster() == greenhouseId).findFirst().orElse(null);
        if (greenhouse != null) {
            accessPoint.getGreenhouses().remove(greenhouse);
            greenhouseRepository.delete(greenhouse);
            accessPointRepository.save(accessPoint);
            auditLogService.createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", true);
        } else {
            auditLogService.createNewAudit("delete", Long.toString(greenhouseId), "greenhouse", false);
            throw new IllegalArgumentException("Greenhouse with id " + greenhouseId + " not found in access point with uuid " + accessPointUuid);
        }
    }


    /**
     * @param name                        the name of the access point to be created
     * @param location                    the location of the access point to be created
     * @param description                 the description of the access point to be created
     * @param transmissionIntervalSeconds the transmission interval in seconds of the access point to be created
     * @param published                   wether the accesspoint should be published or not
     * @return the created access point with a new uuid
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint createNewAccessPoint(String name, String location, String description, int transmissionIntervalSeconds, boolean published) {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setName(name);
        accessPoint.setLocation(location);
        accessPoint.setDescription(description);
        accessPoint.setTransmissionIntervalSeconds(transmissionIntervalSeconds);
        accessPoint.setPublished(published);
        auditLogService.createNewAudit("create", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
        return saveAccessPoint(accessPoint);
    }


    /**
     * @param id                          the id of the access point to be updated
     * @param name                        the new name of the access point
     * @param location                    the new location of the access point
     * @param description                 the new description of the access point
     * @param transmissionIntervalSeconds the new transmission interval in seconds of the access point
     * @param published                   the new published state of the access point
     * @return the updated access point
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccessPoint updateAccessPoint(int id, String name, String location, String description, int transmissionIntervalSeconds, boolean published) {
        AccessPoint accessPoint = loadAccessPoint((long) id);
        accessPoint.setName(name);
        accessPoint.setLocation(location);
        accessPoint.setDescription(description);
        accessPoint.setTransmissionIntervalSeconds(transmissionIntervalSeconds);
        accessPoint.setPublished(published);
        auditLogService.createNewAudit("update", Integer.toString(accessPoint.getUuid()), "accesspoint", true);
        return saveAccessPoint(accessPoint);
    }

    @PreAuthorize("hasAuthority('GARDENER')")
    public AccessPoint getAccesspointByGreenhouseUuid(long greenhouseId) {
    	return accessPointRepository.findFirstByGreenhouseUuid(greenhouseId);
    }
}