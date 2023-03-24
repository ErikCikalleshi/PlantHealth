package at.qe.backend.api.services;

import at.qe.backend.api.exceptions.InvalidTokenError;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

/**
 * Class to generate JWT Tokens
 * */
@Getter
public class JwtToken {
    private final String token;

    private JwtToken(String token) {
        this.token = token;
    }

    public static JwtToken of(String username, Long validityInMinutes, String secretKey) {
        var issueDate = Instant.now();
        return new
                JwtToken(Jwts.builder()
                .claim("username", username)
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact());
    }

    public static String from(String token, String secret)  {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public static JwtToken of(String token) {
        return new JwtToken(token);
    }

}
