package at.qe.backend.models.dto;

import at.qe.backend.models.UserRole;

import java.util.Collection;

public record UserDTO(String username, String firstname, String lastname, String created, Collection<UserRole> roles,
                      String email) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String firstname() {
        return firstname;
    }

    @Override
    public String lastname() {
        return lastname;
    }

    @Override
    public String created() {
        return created;
    }

    @Override
    public Collection<UserRole> roles() {
        return roles;
    }

    @Override
    public String email() {
        return email;
    }
}
