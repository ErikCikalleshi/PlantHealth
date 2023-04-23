package at.qe.backend.models.dto;

import java.util.Collection;

public record AccessPointDTO (
        int id,
        String name,
        String location,
        String description,
        int transmissionInterval,
        Collection<GreenhouseDTO> greenhouses,
        String lastContact,
        String status,
        boolean published
){}