package at.qe.backend.ui.controllers.admin;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.services.AccessPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return accessPoints.stream().map(ap -> new AccessPointDTO(ap, false)).toList();
    }

    @DeleteMapping("/admin/delete-access-point/{uuid}")
    public void deleteAccessPointByUuid(@PathVariable String uuid) {
        accessPointService.deleteAccessPoint(accessPointService.loadAccessPoint(Long.parseLong(uuid)));
    }

    @GetMapping("/admin/get-access-point/{uuid}")
    public AccessPointDTO getAccessPointByUuid(@PathVariable String uuid) {
        return new AccessPointDTO(accessPointService.loadAccessPoint(Long.parseLong(uuid)), true);
    }

    @DeleteMapping("/admin/delete-greenhouse-by-id-and-access-point-uuid/{greenhouseId}/{accessPointUuid}")
    public void deleteGreenhouseByIdAndAccessPointUuid(@PathVariable String greenhouseId, @PathVariable String accessPointUuid) {
        accessPointService.deleteGreenhouseByIdAndAccessPointUuid(Long.parseLong(greenhouseId), Long.parseLong(accessPointUuid));
    }

    @PatchMapping("/admin/update-access-point/")
    public AccessPointDTO updateAccessPoint(@RequestBody AccessPointDTO accessPointDTO) {
        AccessPoint accessPoint = accessPointService.updateAccessPoint(accessPointDTO.id(),accessPointDTO.name(), accessPointDTO.location(), accessPointDTO.description(), accessPointDTO.transmissionInterval(), accessPointDTO.published());
        return new AccessPointDTO(accessPoint,true);
    }

    @PostMapping("/admin/create-new-access-point/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessPointDTO createNewAccessPoint(@RequestBody AccessPointDTO accessPointDTO) {
        AccessPoint accessPoint= accessPointService.createNewAccessPoint(accessPointDTO.name(), accessPointDTO.location(), accessPointDTO.description(), accessPointDTO.transmissionInterval(), accessPointDTO.published());
        return new AccessPointDTO(accessPoint);
    }
}
