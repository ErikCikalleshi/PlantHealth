package at.qe.backend.ui.controllers;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.SensorType;
import at.qe.backend.services.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class ChartController {

    @Autowired
    private ChartService measurementService;

    @GetMapping("charts/get-measurements-by-greenhouse-id-and-sensor-type/{greenhouseId}/{sensorType}")
    public List<MeasurementDTO> getMeasurements(@PathVariable String greenhouseId, @PathVariable String sensorType) {
        SensorType requestSensorType = SensorType.valueOf(sensorType);
        long greenhouseUUID = Long.parseLong(greenhouseId);
        List<Measurement> measurements = measurementService.getAllMeasurementsByGreenhouseUuidAndSensorType(greenhouseUUID, requestSensorType);
        List<MeasurementDTO> greenhouseMeasurements = measurements.stream().map(MeasurementDTO::new).toList();
        return greenhouseMeasurements;
    }


}
