package at.qe.backend.tests.security;

import at.qe.backend.configs.security.jwt.JwtUtils;
import at.qe.backend.configs.security.services.UserDetailsImpl;
import at.qe.backend.models.Userx;
import at.qe.backend.services.UserxService;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class JwtUtilsTests {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserxService userxService;

    private UserDetailsImpl user;

    @Value("${application.security.access-secret}")
    private String accessSecret;

    @BeforeEach
    public void setUp() {
        user = UserDetailsImpl.build(userxService.loadUser("admin"));
    }

    @Test
    public void testGenerateJwtToken() {
        String token = jwtUtils.generateJwtToken(user);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testValidateJwtTokenWithValidToken() {
        String token = jwtUtils.generateJwtToken(user);
        boolean result = jwtUtils.validateJwtToken(token);
        assertTrue(result);
    }

    @Test
    public void testValidateJwtTokenWithInvalidToken() {
        assertThrows(MalformedJwtException.class, () -> jwtUtils.validateJwtToken("invalidToken"));
    }

    @Test
    public void testValidateJwtTokenWithEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.validateJwtToken(null));
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.validateJwtToken(""));
    }

    @Test
    public void testValidateJwtTokenWithNonEncodedToken() {
        var issueTimestamp = Instant.now();
        String nonSignedToken = Jwts.builder().setSubject("admin").setIssuedAt(Date.from(issueTimestamp))
                .setExpiration(Date.from(issueTimestamp.plus(10, ChronoUnit.HOURS)))
                .compact();
        assertThrows(UnsupportedJwtException.class, () -> jwtUtils.validateJwtToken(nonSignedToken));
    }


    @Test
    public void testValidateJwtTokenWithExpiredToken() {
        var issueTimestamp = Instant.now().minus(1, java.time.temporal.ChronoUnit.DAYS);
        String expiredToken = Jwts.builder().setSubject("admin").setIssuedAt(Date.from(issueTimestamp))
                .setExpiration(Date.from(issueTimestamp.plus(10, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
        assertThrows(ExpiredJwtException.class, () -> jwtUtils.validateJwtToken(expiredToken));
    }



    @Test
    public void testGetUserNameFromJwtToken() {
        String token = jwtUtils.generateJwtToken(user);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals(user.getUsername(), username);
    }
}