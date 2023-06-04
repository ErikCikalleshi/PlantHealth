package at.qe.backend.ui.controllers.admin;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.services.AccessPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller for managing access points.
 * It provides endpoints for creating, updating, deleting and retrieving access points.
 * The endpoints are secured and can only be accessed by users with the 'ADMIN' role.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AccessPointController {

    /**
     * Autowired instance of the AccessPointService.
     */
    @Autowired
    private AccessPointService accessPointService;

    /**
     * Returns a list of all access points.
     * Greenhouses are excluded from the response to reduce the response size, as they are not needed in the list view.
     *
     * @return a list of all access points
     */
    @GetMapping("/admin/get-all-access-points")
    public List<AccessPointDTO> getAllAccessPoints() {
        List<AccessPoint> accessPoints = accessPointService.getAllAccessPoints();
        return accessPoints.stream().map(ap -> new AccessPointDTO(ap, false)).toList();
    }

    /**
     * Deletes an access point by UUID.
     *
     * @param uuid the UUID of the access point to delete
     */
    @DeleteMapping("/admin/delete-access-point/{uuid}")
    public void deleteAccessPointByUuid(@PathVariable String uuid) {
        accessPointService.deleteAccessPoint(accessPointService.loadAccessPoint(Long.parseLong(uuid)));
    }

    /**
     * Returns an access point by UUID.
     *
     * @param uuid the UUID of the access point to retrieve
     * @return the access point with the specified UUID
     */
    @GetMapping("/admin/get-access-point/{uuid}")
    public AccessPointDTO getAccessPointByUuid(@PathVariable String uuid) {
        return new AccessPointDTO(accessPointService.loadAccessPoint(Long.parseLong(uuid)), true);
    }

    @GetMapping("/admin/get-access-point-by-greenhouse/{uuid}")
    public AccessPointDTO getAccessPointByGreenhouseUuid(@PathVariable String uuid) {
        return new AccessPointDTO(accessPointService.getAccesspointByGreenhouseUuid(Long.parseLong(uuid)));
    }

    /**
     * Deletes a greenhouse by ID and access point UUID.
     *
     * @param greenhouseId    the ID of the greenhouse to delete
     * @param accessPointUuid the UUID of the access point to which the greenhouse belongs
     */
    @DeleteMapping("/admin/delete-greenhouse-by-id-and-access-point-uuid/{greenhouseId}/{accessPointUuid}")
    public void deleteGreenhouseByIdAndAccessPointUuid(@PathVariable String greenhouseId, @PathVariable String accessPointUuid) {
        accessPointService.deleteGreenhouseByIdAndAccessPointUuid(Long.parseLong(greenhouseId), Long.parseLong(accessPointUuid));
    }

    /**
     * Updates an access point.
     *
     * @param accessPointDTO the DTO containing the updated access point information
     * @return the updated access point
     */
    @PatchMapping("/admin/update-access-point/")
    public AccessPointDTO updateAccessPoint(@RequestBody AccessPointDTO accessPointDTO) {
        AccessPoint accessPoint = accessPointService.updateAccessPoint(accessPointDTO.id(), accessPointDTO.name(), accessPointDTO.location(), accessPointDTO.description(), accessPointDTO.transmissionInterval(), accessPointDTO.published());
        return new AccessPointDTO(accessPoint, true);
    }

    /**
     * Creates a new access point.
     *
     * @param accessPointDTO the DTO containing the new access point information
     * @return the newly created access point
     */
    @PostMapping("/admin/create-new-access-point/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessPointDTO createNewAccessPoint(@RequestBody AccessPointDTO accessPointDTO) {
        AccessPoint accessPoint = accessPointService.createNewAccessPoint(accessPointDTO.name(), accessPointDTO.location(), accessPointDTO.description(), accessPointDTO.transmissionInterval(), accessPointDTO.published());
        return new AccessPointDTO(accessPoint);
    }
}