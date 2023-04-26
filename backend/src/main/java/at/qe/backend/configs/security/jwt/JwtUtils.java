package at.qe.backend.configs.security.jwt;

import at.qe.backend.configs.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
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
    private static final Logger jwtLogger = LoggerFactory.getLogger(JwtUtils.class);


    public String generateJwtToken(UserDetailsImpl user) {
        return generateTokenFromUsername(user.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        var issueTimestamp = Instant.now();
        var issueDate = Date.from(issueTimestamp);
        var expirationDate = Date.from(issueTimestamp.plus(jwtExpirationMs, ChronoUnit.MILLIS));
        return Jwts.builder().setSubject(username).setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8))), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            byte[] signingKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8))).getEncoded();
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(authToken).getBody();
            return true;
        } catch (MalformedJwtException e) {
            jwtLogger.error("Invalid JWT token: ", e);
        } catch (ExpiredJwtException e) {
            jwtLogger.error("JWT token is expired: ", e);
        } catch (UnsupportedJwtException e) {
            jwtLogger.error("JWT token is unsupported: ", e);
        } catch (IllegalArgumentException e) {
            jwtLogger.error("JWT claims string is empty: ", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
