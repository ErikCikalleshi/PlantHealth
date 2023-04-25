package at.qe.backend.tests.services;

import at.qe.backend.configs.WebSecurityConfig;
import at.qe.backend.exceptions.Userx.LastAdminException;
import at.qe.backend.exceptions.Userx.UserAlreadyExistsException;
import at.qe.backend.exceptions.Userx.UserDoesNotExistException;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import at.qe.backend.services.UserxService;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class UserxServiceTests {

    @Autowired
    UserxService userxService;
    @MockBean
    UserxRepository userRepository;


    private Userx testUser;
    private List<Userx> testUsers;

    @BeforeEach
    void setUp() {
        testUser = new Userx();
        testUser.setUsername("testuser");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPassword("password");
        testUser.setEmail("testuser@example.com");
        testUser.setRoles(Set.of(UserRole.USER));

        Userx adminUser = new Userx();
        adminUser.setUsername("adminuser");
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setPassword("adminpassword");
        adminUser.setEmail("adminuser@example.com");
        adminUser.setRoles(Set.of(UserRole.ADMIN));

        testUsers = new ArrayList<>();
        testUsers.add(testUser);
        testUsers.add(adminUser);
    }

    @Test
    @DisplayName("Create new user")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testCreateNewUser() throws UserAlreadyExistsException {
        //this line is needed to get a return value in the tests otherwise the save method just returns null instead of a Userx object
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getCreateDate());
        assertNotNull(savedUser.getCreateUserUsername());
        assertEquals(testUser.getUsername(), savedUser.getUsername());
        assertEquals(testUser, savedUser);
        assertTrue(WebSecurityConfig.passwordEncoder().matches(testUser.getPassword(), savedUser.getPassword()));
        assertTrue(savedUser.getRoles().contains(UserRole.USER));
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword()));
    }

    @Test
    @DisplayName("Get all users")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        Collection<Userx> allUsers = userxService.getAllUsers();
        assertEquals(testUsers.size(), allUsers.size());
        assertTrue(allUsers.containsAll(testUsers));
    }

    @Test
    @DisplayName("Load user")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testLoadUser() {
        when(userRepository.findFirstByUsername(testUser.getUsername())).thenReturn(testUser);
        Userx loadedUser = userxService.loadUser(testUser.getUsername());
        assertEquals(testUser, loadedUser);
    }


    @Test
    @DisplayName("Update user")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testUpdateUser() throws UserAlreadyExistsException {
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword());
        String firstname = "Test2";
        String lastname = "User2";
        String email = "testemail_new";
        Set<UserRole> roles = Set.of(UserRole.USER, UserRole.ADMIN);
        when(userRepository.findFirstByUsername(testUser.getUsername())).thenReturn(savedUser);
        Userx updatedUser = userxService.updateUser(savedUser.getUsername(), firstname, lastname, email, roles);
        assertEquals(savedUser, updatedUser);
        assertEquals("Test2", updatedUser.getFirstName());
        assertEquals("User2", updatedUser.getLastName());
        assertEquals("testemail_new", updatedUser.getEmail());
        assertEquals(2, updatedUser.getRoles().size());
        assertTrue(updatedUser.getRoles().contains(UserRole.USER));
        assertTrue(updatedUser.getRoles().contains(UserRole.ADMIN));
        assertEquals(testUser.getUsername(), savedUser.getUsername());
    }

    @Test
    @DisplayName("Update email already in use")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testUpdateEmailAlreadyInUse() throws UserAlreadyExistsException {
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword());
        Userx secondUser = userxService.createUser("seconduser", "Second", "User", "secondemail", Set.of(UserRole.USER), "password");
        Set<UserRole> roles = Set.of(UserRole.USER, UserRole.ADMIN);
        when(userRepository.findFirstByUsername(savedUser.getUsername())).thenReturn(savedUser);
        when(userRepository.findFirstByUsername(secondUser.getUsername())).thenReturn(secondUser);
        when(userRepository.existsByEmail(savedUser.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(savedUser.getEmail())).thenReturn(savedUser);
        when(userRepository.findByEmail(secondUser.getEmail())).thenReturn(secondUser);
        assertThrows(UserAlreadyExistsException.class, () -> userxService.updateUser(secondUser.getUsername(), "Second", "User", savedUser.getEmail(), roles));
    }

    @Test
    @DisplayName("Delete user")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    void testDeleteUser() throws LastAdminException, UserDoesNotExistException {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        assertThrows(UserDoesNotExistException.class,() -> userxService.deleteUser(testUser));
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        assertDoesNotThrow(() -> userxService.deleteUser(testUser));
    }

    @Test
    @DisplayName("Delete last admin user")
    @WithMockUser(username = "adminuser", authorities = {"ADMIN"})
    public void testDeleteLastAdmin() {
        testUser.setRoles(Set.of(UserRole.ADMIN));
        when(userRepository.countUserxByRolesContaining(UserRole.ADMIN)).thenReturn(1);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        assertThrows(LastAdminException.class, () -> userxService.deleteUser(testUser));
    }
}
