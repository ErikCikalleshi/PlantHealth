package at.qe.backend.models.dto;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.Greenhouse;

public record GreenhouseDTO(
        long uuid,
        long id,
        String name,
        String description,
        String location,
        UserDTO gardener,
        String lastContact,
        String status,
        boolean published
) {
    public GreenhouseDTO(Greenhouse greenhouse){
        this(greenhouse.getUuid(), greenhouse.getIdInCluster(), greenhouse.getName(), greenhouse.getDescription(), greenhouse.getLocation(), new UserDTO(greenhouse.getOwner()), JSONDateFormatHelper.format(greenhouse.getLastContact()), greenhouse.getStatus(), greenhouse.isPublished());
    }
}
