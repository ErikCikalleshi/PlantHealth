package at.qe.backend.models.dto;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.AccessPoint;

import java.util.Collection;
import java.util.List;

public record AccessPointDTO(
        int id,
        String name,
        String location,
        String description,
        int transmissionInterval,
        Collection<GreenhouseDTO> greenhouses,
        String lastContact,
        String status,
        boolean published
) {
    public AccessPointDTO(AccessPoint accessPoint) {
        this(accessPoint, true);
    }

    public AccessPointDTO(AccessPoint accessPoint, boolean includeGreenhouses) {
        this(accessPoint.getUuid(), accessPoint.getName(), accessPoint.getLocation(), accessPoint.getDescription(), accessPoint.getTransmissionIntervalSeconds(), includeGreenhouses ? accessPoint.getGreenhouses().stream().map(GreenhouseDTO::new).toList() : List.of(), JSONDateFormatHelper.format(accessPoint.getLastContact()), accessPoint.getStatus(), accessPoint.isPublished());
    }
}