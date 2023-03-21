package at.qe.backend.api.controllers;

import at.qe.backend.api.exceptions.GreenhouseNotRegisteredException;
import at.qe.backend.api.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.RawMeasurement;
import at.qe.backend.models.Measurement;
import at.qe.backend.api.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @PostMapping("/measurements")
    private Measurement createMeasurement(@RequestBody RawMeasurement rawMeasurement) throws SensorNotFoundException, GreenhouseNotRegisteredException {
        return measurementService.addMeasurement(rawMeasurement);
    }
}
