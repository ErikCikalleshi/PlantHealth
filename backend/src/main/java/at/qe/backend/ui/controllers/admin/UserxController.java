package at.qe.backend.ui.controllers.admin;


import at.qe.backend.exceptions.Userx.LastAdminException;
import at.qe.backend.exceptions.Userx.UserAlreadyExistsException;
import at.qe.backend.exceptions.Userx.UserDoesNotExistException;
import at.qe.backend.models.Userx;
import at.qe.backend.models.dto.UserDTO;
import at.qe.backend.models.request.CreateNewUserRequest;
import at.qe.backend.services.AuditLogService;
import at.qe.backend.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class represents the REST controller for Userx-related operations in the admin panel.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UserxController {
    @Autowired
    UserxService userxService;
    @Autowired
    AuditLogService auditLogService;
    
    /**
     * Returns a collection of all users in the system.
     *
     * @return a collection of UserDTO objects representing all users in the system
     */
    @GetMapping("/admin/get-all-users")
    public Collection<UserDTO> getAllUsers() {
        Collection<UserDTO> users = new ArrayList<>();
        for (Userx user : userxService.getAllUsers()) {
            users.add(new UserDTO(user));
        }
        return users;
    }
    
    /**
     * Deletes a user with the given username.
     *
     * @param username the username of the user to be deleted
     * @throws UserDoesNotExistException if the user with the given username does not exist
     * @throws LastAdminException if the user to be deleted is the last admin in the system
     */
    @DeleteMapping("/admin/delete-user/{username}")
    public void deleteUserByUsername(@PathVariable String username) throws UserDoesNotExistException, LastAdminException {
        userxService.deleteUser(userxService.loadUser(username));
    }

    /**
     * Returns a UserDTO object representing the user with the given username.
     *
     * @param username the username of the user to be returned
     * @return a UserDTO object representing the user with the given username
     * @throws ResponseStatusException with HTTP status NOT_FOUND if the user with the given username does not exist
     */
    @GetMapping("/admin/get-single-user/{username}")
    public UserDTO getSingleUserByUsername(@PathVariable String username) {
        Userx user = userxService.loadUser(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new UserDTO(user);
    }

    /**
     * Updates the user with the given username with the information provided in the UserDTO object.
     *
     * @param userDTO a UserDTO object representing the updated user information
     * @return a UserDTO object representing the updated user
     * @throws UserAlreadyExistsException if a user with the updated username already exists
     * @throws ResponseStatusException with HTTP status BAD_REQUEST if the user with the given username does not exist
     */
    @PatchMapping("/admin/update-user/")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        Userx user = userxService.updateUser(userDTO.username(),
                userDTO.firstname(),
                userDTO.lastname(),
                userDTO.email(),
                userDTO.roles());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new UserDTO(user);
    }

    /**
     * Creates a new user with the information provided in the CreateNewUserRequest object.
     *
     * @param newUserRequest a CreateNewUserRequest object representing the new user information
     * @return a UserDTO object representing the newly created user
     * @throws UserAlreadyExistsException if a user with the new username already exists
     * @throws ResponseStatusException with HTTP status BAD_REQUEST if the new user information is invalid
     */
    @PostMapping("/admin/create-new-user/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody CreateNewUserRequest newUserRequest) throws UserAlreadyExistsException {
        Userx user = userxService.createUser(newUserRequest.newUser().username(),
                newUserRequest.newUser().firstname(),
                newUserRequest.newUser().lastname(),
                newUserRequest.newUser().email(),
                newUserRequest.newUser().roles(),
                newUserRequest.password());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new UserDTO(user);
    }
}
