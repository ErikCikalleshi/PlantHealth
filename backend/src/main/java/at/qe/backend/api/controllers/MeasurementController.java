package at.qe.backend.api.controllers;

import at.qe.backend.api.model.Measurement;
import at.qe.backend.api.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @PostMapping("/measurements")
    private Measurement createMeasurement(@RequestBody Measurement measurement) {
        System.out.println(measurement.getPlantID() + " " + measurement.getValue());
        return measurementService.addMeasurement(measurement);
    }
}
