package at.qe.backend.models.request;

import at.qe.backend.models.dto.SensorDTO;
import at.qe.backend.models.dto.GreenhouseDTO;

import java.util.Collection;

public record CreateNewGreenhouseRequest (GreenhouseDTO greenhouse, Collection<SensorDTO> sensors, String accessPointId) {
}
