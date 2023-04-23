package at.qe.backend.ui.controllers.admin;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.dto.UserDTO;
import at.qe.backend.services.AccessPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AccessPointController {

    @Autowired
    private AccessPointService accessPointService;

    @GetMapping("/admin/get-all-access-points")
    public List<AccessPointDTO> getAllAccessPoints() {
        List<AccessPoint> accessPoints = accessPointService.getAllAccessPoints();
        //Exclude greenhouses from the response to reduce the response size, as they are not needed in the list view
        return accessPoints.stream().map(ap -> getAccessPointDtoFromAccessPoint(ap, false)).toList();
    }

    @DeleteMapping("/admin/delete-access-point/{uuid}")
    public void deleteAccessPointByUuid(@PathVariable String uuid) {
        accessPointService.deleteAccessPoint(accessPointService.loadAccessPoint(Long.parseLong(uuid)));
    }

    @GetMapping("/admin/get-access-point/{uuid}")
    public AccessPointDTO getAccessPointByUuid(@PathVariable String uuid) {
        return getAccessPointDtoFromAccessPoint(accessPointService.loadAccessPoint(Long.parseLong(uuid)), true);
    }

    @DeleteMapping("/admin/delete-greenhouse-by-id-and-access-point-uuid/{greenhouseId}/{accessPointUuid}")
    public void deleteGreenhouseByIdAndAccessPointUuid(@PathVariable String greenhouseId, @PathVariable String accessPointUuid) {
        accessPointService.deleteGreenhouseByIdAndAccessPointUuid(Long.parseLong(greenhouseId), Long.parseLong(accessPointUuid));
    }

    private AccessPointDTO getAccessPointDtoFromAccessPoint(AccessPoint accessPoint, boolean includeGreenhouses) {
        List<GreenhouseDTO> greenhouses = List.of();
        if (includeGreenhouses) {
            greenhouses = getGreenhouseDtoFromAccessPoint(accessPoint);
        }
        return new AccessPointDTO(
                accessPoint.getUuid(),
                accessPoint.getName(),
                accessPoint.getLocation(),
                accessPoint.getDescription(),
                accessPoint.getTransmissionIntervalSeconds(),
                greenhouses,
                JSONDateFormatHelper.format(accessPoint.getLastContact()),
                accessPoint.getStatus(),
                accessPoint.isPublished()
        );
    }

    private List<GreenhouseDTO> getGreenhouseDtoFromAccessPoint(AccessPoint accessPoint) {
        return accessPoint.getGreenhouses().stream().map(g -> new GreenhouseDTO(
                g.getUuid(),
                g.getIdInCluster(),
                g.getName(),
                g.getDescription(),
                g.getLocation(),
                new UserDTO(
                        g.getOwner().getCreateUserUsername(),
                        g.getOwner().getFirstName(),
                        g.getOwner().getLastName(),
                        JSONDateFormatHelper.format(g.getOwner().getCreateDate()),
                        g.getOwner().getRoles(),
                        g.getOwner().getEmail()),
                JSONDateFormatHelper.format(g.getLastContact()),
                g.getStatus(),
                g.isPublished()
        )).toList();
    }
}
