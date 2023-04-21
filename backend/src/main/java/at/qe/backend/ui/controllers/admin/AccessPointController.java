package at.qe.backend.ui.controllers.admin;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.dto.AccessPointDTO;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.dto.UserDTO;
import at.qe.backend.services.AccessPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AccessPointController {

    @Autowired
    private AccessPointService accessPointService;
    @GetMapping("/admin/get-all-access-points")
    public List<AccessPointDTO> getAllAccessPoints() {
        List<AccessPoint> accessPoints = accessPointService.getAllAccessPoints();
        return accessPoints.stream().map(ap ->
                new AccessPointDTO(
                        ap.getUuid(),
                        ap.getName(),
                        ap.getLocation(),
                        ap.getDescription(),
                        ap.getTransmissionIntervalSeconds(),
                        ap.getGreenhouses().stream().map(g -> new GreenhouseDTO(g.getUuid(),g.getIdInCluster(),g.getName(),g.getLocation(),
                                new UserDTO(
                                        g.getOwner().getCreateUserUsername(),
                                        g.getOwner().getFirstName(),
                                        g.getOwner().getLastName(),
                                        JSONDateFormatHelper.format(g.getOwner().getCreateDate()),
                                        g.getOwner().getRoles(),
                                        g.getOwner().getEmail()),
                                JSONDateFormatHelper.format(g.getLastContact()),
                                g.getStatus())).toList(),
                        JSONDateFormatHelper.format(ap.getLastContact()),
                        ap.getStatus()
                )).toList();
    }
}
