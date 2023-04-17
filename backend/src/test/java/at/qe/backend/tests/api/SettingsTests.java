package at.qe.backend.tests.api;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SettingsTests {

    @LocalServerPort
    private int port;
    private String BaseURI;

    @BeforeEach
    public void setBaseURI() {
        BaseURI = "http://localhost:" + port;
        System.out.println(BaseURI);
    }

    @Test
    public void GetSetting() {
        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                when().
                get(BaseURI + "/api/setting/1").
                then().
                statusCode(HttpStatus.SC_OK);
    }
    @Test
    public void GetSettingInvalidAP() {
        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                when().
                get(BaseURI + "/api/setting/151688").
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void GetSettingNotPublished() {
        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                when().
                get(BaseURI + "/api/setting/2").
                then().
                statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
