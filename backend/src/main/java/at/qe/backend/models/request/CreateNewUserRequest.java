package at.qe.backend.models.request;

import at.qe.backend.models.dto.UserDTO;

public record CreateNewUserRequest (UserDTO newUser, String password) {}
