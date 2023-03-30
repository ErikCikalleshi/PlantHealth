package at.qe.backend.api.controllers;


import at.qe.backend.api.exceptions.UserNotDeletedException;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.services.UserxService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.boot.jaxb.internal.UrlXmlSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UserxController {
    @Autowired
    UserxService userxService;

    /**
     * records are DTOs (Data Transfer Object) used to return only the fields we want back to the client
     * used for example to not send the password when a user logs in
     */
    record UserDisplay(String username, String firstname, String lastname, String created, Collection<UserRole> roles,
                       String email) {
    }

    //TODO add security check to this api
    @GetMapping("/admin/get-all-users")
    private Collection<UserDisplay> getAllUsers(HttpServletRequest request) {
        System.out.println(request);
        var session = (Userx) request.getAttribute("user");
        if (!session.getRoles().contains(UserRole.ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Collection<UserDisplay> userDisplays = new ArrayList<>();
        for (Userx user : userxService.getAllUsers()) {
            userDisplays.add(new UserDisplay(user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(user.getCreateDate()),
                    user.getRoles(),
                    user.getEmail()));
        }
        return userDisplays;
    }

    @DeleteMapping("/admin/delete-user/{username}")
    private void deleteUserByUsername(@PathVariable String username) throws UserNotDeletedException {
        if (!userxService.deleteUser(userxService.loadUser(username))) {
            throw new UserNotDeletedException();
        }
    }

    @GetMapping("/admin/get-single-user/{username}")
    private UserDisplay getSingleUserByUsername(@PathVariable String username) {
        Userx user = userxService.loadUser(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new UserDisplay(user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(user.getCreateDate()),
                user.getRoles(),
                user.getEmail());
    }

    @PatchMapping("/admin/update-user/")
    private void updateUser(HttpServletRequest request, @RequestBody UserDisplay userDisplay) {
        if (userDisplay.roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Userx user = userxService.loadUser(userDisplay.username);
        user.setFirstName(userDisplay.firstname);
        user.setLastName(userDisplay.lastname);
        user.setEmail(userDisplay.email);
        Set<UserRole> rolesNew = new java.util.HashSet<>(Set.of());
        rolesNew.addAll(userDisplay.roles);
        user.setRoles(rolesNew);
        var session = (Userx) request.getAttribute("user");
        userxService.saveUser(user, session.getUsername());
    }
}
