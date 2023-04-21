package at.qe.backend.models.dto;

import at.qe.backend.models.Greenhouse;

import java.util.List;

public record AccessPointDTO (long accessPointId, String name, String location, String description, int transmissionIntervalSeconds, List<GreenhouseDTO> greenhouses, String lastContact, String status){}