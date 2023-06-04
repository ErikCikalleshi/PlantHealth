package at.qe.backend.services;

import at.qe.backend.configs.WebSecurityConfig;
import at.qe.backend.exceptions.Userx.LastAdminException;
import at.qe.backend.exceptions.Userx.UserAlreadyExistsException;
import at.qe.backend.exceptions.Userx.UserDoesNotExistException;
import at.qe.backend.exceptions.Userx.UserStillHasGreenhousesException;
import at.qe.backend.models.AuditLog;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private AuditLogService auditLogService;
    private final UserxRepository userRepository;

    @Autowired
    public UserxService(UserxRepository userRepository, AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
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
        AuditLog auditLog = auditLogService.createNewAudit("delete", "NA", "user", false);
        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new UserDoesNotExistException();
        }
        if (user.getRoles().contains(UserRole.ADMIN) && userRepository.countUserxByRolesContaining(UserRole.ADMIN) <= 1) {
            throw new LastAdminException(HttpStatus.BAD_REQUEST, "Cannot delete last admin!");
        }
        if (!user.getGreenhouses().isEmpty()){
            auditLog.setTargetID(user.getUsername());
            auditLogService.saveAuditLog(auditLog);
            throw new UserStillHasGreenhousesException(HttpStatus.BAD_REQUEST, "User is still responsible for at least one greenhouse.");
        }
        userRepository.delete(user);
        auditLog.setSuccess(true);
        auditLog.setTargetID(user.getUsername());
        auditLogService.saveAuditLog(auditLog);
    }


    public String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    /**
     * @param username the username of the user to update
     * @param firstname the new firstname
     * @param lastname the new lastname
     * @param email the new email
     * @param roles the new roles
     * @return the updated user
     * @throws UserAlreadyExistsException if the new email is already used by another user
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Userx updateUser(String username, String firstname, String lastname, String email, Collection<UserRole> roles) throws UserAlreadyExistsException {
        AuditLog auditLog = auditLogService.createNewAudit("update", username, "user", false);
        if (userRepository.existsByEmail(email) && !userRepository.findByEmail(email).getUsername().equals(username)) {
            throw new UserAlreadyExistsException();
        }
        Userx user = loadUser(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setRoles(new HashSet<>(roles));
        auditLog.setSuccess(true);
        auditLogService.saveAuditLog(auditLog);
        return saveUser(user);
    }


    /**
     * @param username the username of the user to create
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param roles the roles
     * @param password the password as plaintext (will be hashed)
     * @return the created user
     * @throws UserAlreadyExistsException if the username or email is already used by another user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Userx createUser(String username, String firstname, String lastname, String email, Collection<UserRole> roles, String password) throws UserAlreadyExistsException {
        AuditLog auditLog = auditLogService.createNewAudit("create", "NA", "user", false);
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
        auditLog.setSuccess(true);
        auditLog.setTargetID(username);
        auditLogService.saveAuditLog(auditLog);
        return saveUser(user);
    }
}