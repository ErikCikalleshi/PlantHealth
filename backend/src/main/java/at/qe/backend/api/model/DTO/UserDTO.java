package at.qe.backend.api.model.DTO;

import at.qe.backend.models.UserRole;

import java.util.Collection;

public record UserDTO(String username, String firstname, String lastname, String created, Collection<UserRole> roles,
                      String email) {
}

