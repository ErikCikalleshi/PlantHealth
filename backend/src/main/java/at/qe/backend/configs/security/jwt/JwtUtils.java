package at.qe.backend.configs.security.jwt;

import at.qe.backend.configs.security.services.UserDetailsImpl;
import at.qe.backend.models.Userx;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtils {
    @Value("${application.security.access-secret}")
    private String accessSecret;
    @Value("${application.security.refresh-secret}")
    private String refreshSecret;
    @Value("${application.security.jwt-expiration-ms}")
    private Long jwtExpirationMs;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public String generateJwtToken(UserDetailsImpl user) {
        return generateTokenFromUsername(user.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        var issueTimestamp = Instant.now();
        var issueDate = Date.from(issueTimestamp);
        var expirationDate = Date.from(issueTimestamp.plus(jwtExpirationMs, ChronoUnit.MILLIS));
        return Jwts.builder().setSubject(username).setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8))).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }

//        return false;
    }

    public String getUserNameFromJwtToken(String jwt) {
        String value = Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        return value;
    }


    //TODO Add Logger


}
