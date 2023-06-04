package at.qe.backend.models.dto;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.Greenhouse;

import java.util.Collection;

public record GreenhouseDTO(
        long uuid,
        long id,
        String name,
        String description,
        String location,
        UserDTO gardener,
        String lastContact,
        String status,
        boolean published,
        Collection<SensorDTO> sensors,

        boolean displayLimitExceeded
) {
    public GreenhouseDTO(Greenhouse greenhouse) {
        this(greenhouse.getUuid(),
                greenhouse.getIdInCluster(),
                greenhouse.getName(),
                greenhouse.getDescription(),
                greenhouse.getLocation(),
                new UserDTO(greenhouse.getOwner()),
                JSONDateFormatHelper.format(greenhouse.getLastContact()),
                greenhouse.getStatus(),
                greenhouse.isPublished(),
                greenhouse.getSensors().stream().map(SensorDTO::new).toList(),
                greenhouse.getLastLimitExceeded() != null && greenhouse.getLastLimitExceeded().getTime() > System.currentTimeMillis() - 1000 * 60 * 60); // 1 hour
    }
}
