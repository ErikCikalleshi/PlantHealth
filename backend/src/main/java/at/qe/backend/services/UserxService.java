package at.qe.backend.services;

import at.qe.backend.configs.WebSecurityConfig;
import at.qe.backend.exceptions.Userx.LastAdminException;
import at.qe.backend.exceptions.Userx.UserAlreadyExistsException;
import at.qe.backend.exceptions.Userx.UserDoesNotExistException;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Service for accessing and manipulating user data.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * course "Software Architecture" offered by Innsbruck University.
 */
@Service
@Scope("application")
public class UserxService {

    private final UserxRepository userRepository;
    public UserxService(UserxRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns a collection of all users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Userx> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a single user identified by its username.
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Userx loadUser(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Saves the user. This method will also set {@link Userx#getCreateDate()} for new
     * entities or {@link Userx#getUpdateDate()} for updated entities. The user
     * requesting this operation will also be stored as {@link Userx#getCreateDate()}
     * or {@link Userx#getUpdateDate()} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public Userx saveUser(Userx user) {
        if (user.isNew()) {
            user.setCreateDate(new Date());
            user.setCreateUserUsername(getAuthenticatedUsername());
        } else {
            user.setUpdateDate(new Date());
            user.setUpdateUserUsername(getAuthenticatedUsername());
        }
        user = userRepository.save(user);
        return user;
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(Userx user) throws UserDoesNotExistException, LastAdminException {
        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new UserDoesNotExistException();
        }
        if (user.getRoles().contains(UserRole.ADMIN) && userRepository.countUserxByRolesContaining(UserRole.ADMIN) <= 1) {
            throw new LastAdminException();
        }
        userRepository.delete(user);
    }


    public String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Userx updateUser(String username, String firstname, String lastname, String email, Collection<UserRole> roles) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(email) && !userRepository.findByEmail(email).getUsername().equals(username)) {
            throw new UserAlreadyExistsException();
        }
        Userx user = loadUser(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setRoles(new HashSet<>(roles));
        return saveUser(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Userx createUser(String username, String firstname, String lastname, String email, Collection<UserRole> roles, String password) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
        Userx user = new Userx();
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setRoles(new HashSet<>(roles));
        user.setPassword(WebSecurityConfig.passwordEncoder().encode(password));
        return saveUser(user);
    }
}