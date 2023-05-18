package at.qe.backend.tests.api;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.models.SensorType;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MeasurementsTests {
    @LocalServerPort
    private int port;
    private String BaseURI;

    @BeforeEach
    public void setBaseURI() {
        BaseURI = "http://localhost:" + port;
    }


    @Test
    public void GreenhouseNotFound() {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setGreenhouseID(-1);
        measurement.setAccesspointUUID(2);
        measurement.setValue(23.0);
        measurement.setSensorType(SensorType.TEMPERATURE);
        measurement.setDate(new Date());

        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                body(measurement).
                when().
                post(BaseURI + "/api/measurements").
                then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(
                        "message", is("The greenhouse with the given ID does not exists")
                );
    }

    @Test
    public void GreenhouseNotPublished() {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setGreenhouseID(1);
        measurement.setAccesspointUUID(1);
        measurement.setValue(23.0);
        measurement.setSensorType(SensorType.TEMPERATURE);
        measurement.setDate(new Date());

        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                body(measurement).
                when().
                post(BaseURI + "/api/measurements").
                then().
                statusCode(HttpStatus.SC_FORBIDDEN).
                body(
                        "message", is("The greenhouse is not published")
                );
    }

    @Test
    public void AccessPointNotPublished() {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setGreenhouseID(1);
        measurement.setAccesspointUUID(2);
        measurement.setValue(23.0);
        measurement.setSensorType(SensorType.TEMPERATURE);
        measurement.setDate(new Date());

        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                body(measurement).
                when().
                post(BaseURI + "/api/measurements").
                then().
                statusCode(HttpStatus.SC_FORBIDDEN).
                body(
                        "message", is("The access-point is not published")
                );
    }


    @Test
    public void SubmitMeasurement() {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setGreenhouseID(27);
        measurement.setAccesspointUUID(1);
        measurement.setValue(23.0);
        measurement.setSensorType(SensorType.TEMPERATURE);
        measurement.setDate(new Date());

        given().auth().basic("admin", "passwd").
                contentType(ContentType.JSON).
                body(measurement).
                when().
                post(BaseURI + "/api/measurements").
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "measurementId", notNullValue(),
                        "accesspointUUID", is((int) measurement.getAccesspointUUID()),
                        "greenhouseID", is((int) measurement.getGreenhouseID()),
                        "value", is(measurement.getValue().floatValue()),
                        "sensorType", is(measurement.getSensorType().name()),
                        "date", notNullValue()
                );
    }

}
