package at.qe.backend.tests.services;

import at.qe.backend.configs.WebSecurityConfig;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import at.qe.backend.services.UserxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
class UserxServiceTests {

    @Mock
    UserxRepository userRepository;

    @InjectMocks
    UserxService userxService;

    private Userx testUser;
    private List<Userx> testUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new Userx();
        testUser.setUsername("testuser");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPassword("password");
        testUser.setEmail("testuser@example.com");
        testUser.getRoles().add(UserRole.USER);

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
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        Collection<Userx> allUsers = userxService.getAllUsers();
        assertEquals(testUsers.size(), allUsers.size());
        assertTrue(allUsers.containsAll(testUsers));
    }

    @Test
    void testLoadUser() {
        when(userRepository.findFirstByUsername(testUser.getUsername())).thenReturn(testUser);
        Userx loadedUser = userxService.loadUser(testUser.getUsername());
        assertEquals(testUser, loadedUser);
    }


    @Test
    void testCreateNewUser() {
        //this line is needed to get a return value in the tests otherwise the save method just returns null instead of a Userx object
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getCreateDate());
        assertNotNull(savedUser.getCreateUserUsername());
        assertEquals(testUser.getUsername(), savedUser.getUsername());
        assertEquals(testUser, savedUser);
        assertTrue(WebSecurityConfig.passwordEncoder().matches(testUser.getPassword(), savedUser.getPassword()) );
        assertTrue(savedUser.getRoles().contains(UserRole.USER));
    }

    @Test
    @WithMockUser(username = "adminuser", authorities = {"ADMIN", "GARDENER", "USER"})
    void testSaveUserUpdate() {
        Authentication auth = new UsernamePasswordAuthenticationToken("adminuser", null, List.of(() -> "ADMIN", () -> "GARDENER", () -> "USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        var tmp = userxService.getAuthenticatedUsername();
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.createUser(testUser.getUsername(), testUser.getFirstName(), testUser.getLastName(), testUser.getEmail(), testUser.getRoles(), testUser.getPassword());
        String firstname = "Test2";
        String lastname = "User2";
        String email = "testemail_new";
        Set<UserRole> roles = Set.of(UserRole.USER, UserRole.ADMIN);
        //TODO Add password to update method
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
    @WithMockUser(username = "adminuser", authorities = {"ADMIN", "GARDENER", "USER"})
    void testDeleteUser() {
        Userx adminUser = testUsers.get(1);
        // Test delete user
        assertTrue(userxService.deleteUser(testUser));
        assertNull(userxService.loadUser(testUser.getUsername()));
        // Test delete admin user when it is the only admin user
        assertFalse(userxService.deleteUser(adminUser));
    }

    @Test
    public void testDeleteLastAdmin() {
        testUser.getRoles().add(UserRole.ADMIN);
        when(userRepository.countUserxByRolesContaining(UserRole.ADMIN)).thenReturn(1);
        assertFalse(userxService.deleteUser(testUser));
    }
}
