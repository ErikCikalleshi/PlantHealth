package at.qe.backend.models.dto;

import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;

import java.util.Collection;

public record UserDTO(String username, String firstname, String lastname, String created, Collection<UserRole> roles, String email) {

    public UserDTO(Userx user) {
        this(user.getUsername(), user.getFirstName(), user.getLastName(), JSONDateFormatHelper.format(user.getCreateDate()), user.getRoles(), user.getEmail());
    }
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
