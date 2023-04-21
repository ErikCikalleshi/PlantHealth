package at.qe.backend.models.dto;

public record GreenhouseDTO(long uuid, long id, String name, String location, UserDTO gardener, String lastContact, String status){}
