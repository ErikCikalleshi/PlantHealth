package at.qe.backend.ui.controllers;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.models.Measurement;
import at.qe.backend.services.MeasurementServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class MeasurementChartController {

    @Autowired
    private MeasurementServiceV2 measurementService;

    @GetMapping("measurements/get-measurements-by-greenhouse-id/{greenhouseId}")
    public List<MeasurementDTO> getMeasurements(@PathVariable String greenhouseId) {
        long greenhouseUUID = Long.parseLong(greenhouseId);
        System.out.println("getMeasurement" + greenhouseId);
        //get all measurements from the database
        List<Measurement> measurements = measurementService.getAllMeasurements();

        List<MeasurementDTO> greenhouseMeasurements = new ArrayList<>();

        for (Measurement measurement : measurements) {
            //if the measurement is from the specified greenhouse
            if (measurement.getSensor().getGreenhouse().getUuid() == greenhouseUUID) {
                //add the measurement to the list
                System.out.println("measurement added" + measurement.getId());
                greenhouseMeasurements.add(new MeasurementDTO(measurement));
            }
        }

        return greenhouseMeasurements;
    }
}
