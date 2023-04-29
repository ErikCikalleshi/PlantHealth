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

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UserxController {
    @Autowired
    UserxService userxService;
    @Autowired
    AuditLogService auditLogService;

    /**
     * records are DTOs (Data Transfer Object) used to return only the fields we want back to the client
     * used for example to not send the password when a user logs in
     */

    //TODO add security check to this api
    @GetMapping("/admin/get-all-users")
    public Collection<UserDTO> getAllUsers() {
        Collection<UserDTO> users = new ArrayList<>();
        for (Userx user : userxService.getAllUsers()) {
            users.add(new UserDTO(user));
        }
        return users;
    }

    @DeleteMapping("/admin/delete-user/{username}")
    public void deleteUserByUsername(@PathVariable String username) throws UserDoesNotExistException, LastAdminException {
        userxService.deleteUser(userxService.loadUser(username));
    }

    @GetMapping("/admin/get-single-user/{username}")
    public UserDTO getSingleUserByUsername(@PathVariable String username) {
        Userx user = userxService.loadUser(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new UserDTO(user);
    }

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
        auditLogService.createNewAudit("update", Long.toString(user.getId()), "user", true);
        return new UserDTO(user);
    }

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
        auditLogService.createNewAudit("create", Long.toString(user.getId()), "user", true);
        return new UserDTO(user);
    }
}
