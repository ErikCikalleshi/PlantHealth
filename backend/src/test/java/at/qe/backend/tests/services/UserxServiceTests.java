package at.qe.backend.tests.services;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
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
    void testSaveUserNew() {
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.saveUser(testUser);
        assertNotNull(savedUser.getCreateDate());
        assertNotNull(savedUser.getCreateUserUsername());
        assertEquals(testUser.getUsername(), savedUser.getUsername());
        assertEquals(testUser, savedUser);
        assertTrue(savedUser.getRoles().contains(UserRole.USER));
    }

    @Test
    @WithMockUser(username = "adminuser", authorities = {"ADMIN", "GARDENER", "USER"})
    void testSaveUserUpdate() {
        Authentication auth = new UsernamePasswordAuthenticationToken("adminuser", null, List.of(() -> "ADMIN", () -> "GARDENER", () -> "USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        var tmp = userxService.getAuthenticatedUsername();
        testUser.setId(1L);
        when(userRepository.save(any(Userx.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Userx savedUser = userxService.saveUser(testUser);
        assertNotNull(savedUser.getCreateDate());
        assertNull(savedUser.getUpdateDate());
        assertNotNull(savedUser.getCreateUserUsername());
        assertNull(savedUser.getUpdateUserUsername());
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
