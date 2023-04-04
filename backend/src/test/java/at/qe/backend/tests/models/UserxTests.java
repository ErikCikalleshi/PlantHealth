package at.qe.backend.tests.models;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.UserRole;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserxTests {
    private Userx user;

    @Before
    public void setUp() {
        user = new Userx();
        user.setUsername("testuser");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("testuser@planthealth.at");
        user.setPhone("+1234567890");
        user.setPassword("password");
        user.setEnabled(true);
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);
        user.setRoles(roles);
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    public void testUserEqualsAndHashCode() {
        Userx user1 = new Userx();
        user1.setUsername("testuser");
        Userx user2 = new Userx();
        user2.setUsername("testuser");
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testUserToString() {
        String expected = "at.qe.skeleton.model.User[ idInCluster=testuser ]";
        assertEquals(expected, user.toString());
    }


    @Test
    public void setUsernameAndGetUsername() {
        Userx user = new Userx();
        user.setUsername("johndoe");
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    public void setPasswordAndGetPassword() {
        Userx user = new Userx();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    public void setFirstNameAndGetFirstName() {
        Userx user = new Userx();
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void setLastNameAndGetLastName() {
        Userx user = new Userx();
        user.setLastName("Doe");
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void setEmailAndGetEmail() {
        Userx user = new Userx();
        user.setEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", user.getEmail());
    }

    @Test
    public void setPhoneAndGetPhone() {
        Userx user = new Userx();
        user.setPhone("123-456-7890");
        assertEquals("123-456-7890", user.getPhone());
    }

    @Test
    public void setEnabledAndGetEnabled() {
        Userx user = new Userx();
        user.setEnabled(true);
        assertTrue(user.isEnabled());
    }

    @Test
    public void setRolesAndGetRoles() {
        Userx user = new Userx();
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);
        roles.add(UserRole.GARDENER);
        user.setRoles(roles);
        assertEquals(roles, user.getRoles());
    }

    @Test
    public void setGreenhousesAndGetGreenhouses() {
        Userx user = new Userx();
        Set<Greenhouse> greenhouses = new HashSet<>();
        Greenhouse greenhouse1 = new Greenhouse();
        Greenhouse greenhouse2 = new Greenhouse();
        greenhouses.add(greenhouse1);
        greenhouses.add(greenhouse2);
        user.setGreenhouses(greenhouses);
        assertEquals(greenhouses, user.getGreenhouses());
    }
}

