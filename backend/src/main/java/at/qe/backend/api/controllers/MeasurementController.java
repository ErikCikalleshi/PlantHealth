package at.qe.backend.api.controllers;

import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotRegisteredException;
import at.qe.backend.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.api.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Interface for the measurement API
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    /**
     * This method is used by the AccessPoints to submit the measurements of the individual Sensors of their Greenhouses.
     * The AccessPoint should check for a successful HTTP Status before deleting the measurement in its local database
     * @param measurementDTO measurement in JSON format.
     *                       Required parameters:
     *                       "greenhouseID": int,
     *                       "value": double,Ah
     *                       "sensorType": SensorType enum as String
     *                       "date": "yyyy-MM-dd HH:mm",
     *                       "limitExceededBy": double
     * @return returns the MeasurementDTO with the idInCluster if it was saved successfully. Otherwise, an exception is thrown.
     * @throws SensorNotFoundException The sensor of SensorType could not be found for the specified Greenhouse
     * @throws GreenhouseNotRegisteredException The specified Greenhouse does not yet exist in the database
     */
    @PostMapping("/measurements")
    public MeasurementDTO createMeasurement(@RequestBody MeasurementDTO measurementDTO) throws SensorNotFoundException, GreenhouseNotRegisteredException, AccessPointNotPublishedException, GreenhouseNotPublishedException {
        return measurementService.addMeasurement(measurementDTO);
    }
}
