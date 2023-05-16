package at.qe.backend.tests.security;

import at.qe.backend.configs.security.jwt.models.RefreshToken;
import at.qe.backend.configs.security.jwt.services.RefreshTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public class JWTTests {
    @LocalServerPort
    private int port;
    private String BaseURI;


    @BeforeEach
    public void setBaseURI() {
        BaseURI = "http://localhost:" + port;
        System.out.println(BaseURI);
    }

    private final String username = "admin";
    private final String password = "passwd";

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Test
    public void testLoginLogoutSuccess() {
        String token = given().auth().none().
                contentType(ContentType.JSON).
                body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}").
                when().
                post(BaseURI + "/login").
                then().
                statusCode(HttpStatus.SC_OK).
                body("token", notNullValue()).
                body("username", is(username)).
                body("refreshToken", notNullValue()).
                body("token", notNullValue()).extract().path("refreshToken");
        assertNotNull(token);
        assertNotNull(refreshTokenService.findByToken(token));
        given().auth().none().contentType(ContentType.JSON).
                body("{\"refreshToken\":\"" + token + "\"}").
                when().
                post(BaseURI + "/logout-user").
                then().
                statusCode(HttpStatus.SC_OK).body("response", is("Logout successful!"));
        assertNull(refreshTokenService.findByToken(token).orElse(null));
    }

    @Test
    public void testLoginFailure() {
        given().auth().none().
                contentType(ContentType.JSON).
                body("{\"username\":\"" + username + "\",\"password\":\"invalid password\"}").
                when().
                post(BaseURI + "/login").
                then().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void testRefreshToken() {
        given().auth().none().
                header("Authorization", "Bearer " + "invalidToken"). // Access token (Body missing = invalid request)
                contentType(ContentType.JSON).
                when().
                post(BaseURI + "/refreshtoken").
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

        given().auth().none().
                contentType(ContentType.JSON).
                body("{\"refreshToken\":\"" + "f7701b8d-4fb9-47e1-a73b-a01b5cfa7fcc" + "\"}"). //Invalid refreshToken
                when().
                post(BaseURI + "/refreshtoken").
                then().
                statusCode(HttpStatus.SC_FORBIDDEN);

        Map.Entry<String,RefreshToken> tokens = getAccessAndRefreshToken();
        given().auth().none().
                contentType(ContentType.JSON).
                body("{\"refreshToken\":\"" + tokens.getValue().getToken() + "\"}"). //Valid refreshToken
                when().
                post(BaseURI + "/refreshtoken").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    private Map.Entry<String, RefreshToken> getAccessAndRefreshToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", headers);
        var tmp = restTemplate.exchange(BaseURI + "/login", HttpMethod.POST,request, String.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        String accessToken;
        RefreshToken refreshToken;
        try {
            JsonNode root = mapper.readTree(tmp);
            refreshToken = refreshTokenService.findByToken(root.path("refreshToken").asText()).orElseThrow();
            accessToken = root.path("token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(refreshToken.getToken());
        return new AbstractMap.SimpleEntry<>(accessToken, refreshToken);
    }
}
