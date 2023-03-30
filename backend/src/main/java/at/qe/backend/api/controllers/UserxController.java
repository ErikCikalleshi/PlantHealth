package at.qe.backend.api.controllers;


import at.qe.backend.api.exceptions.UserNotDeleted;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UserxController {
    @Autowired
    UserxService userxService;

    /**
     * records are DTOs (Data Transfer Object) used to return only the fields we want back to the client
     * used for example to not send the password when a user logs in
     * */
    record UserDisplay(String username, String firstname, String lastname, String created, Collection<UserRole> roles, String email){}

    //TODO add security check to this api
    @GetMapping("/admin/get-all-users")
    private Collection<UserDisplay> getAllUsers() {
        Collection<UserDisplay> userDisplays= new ArrayList<>();
        for (Userx user:userxService.getAllUsers()) {
            userDisplays.add(new UserDisplay(user.getUsername(),user.getFirstName(), user.getLastName(), new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(user.getCreateDate()), user.getRoles(),user.getEmail()));
        }
        return userDisplays;
    }

    @PostMapping("/admin/delete-user/{username}")
    private void deleteUserByUsername(@PathVariable String username) throws UserNotDeleted {
        if(!userxService.deleteUser(userxService.loadUser(username))){
            throw new UserNotDeleted();
        }
    }

}
