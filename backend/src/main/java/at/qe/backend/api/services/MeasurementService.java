package at.qe.backend.api.services;

import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotRegisteredException;
import at.qe.backend.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.MeasurementRepository;
import at.qe.backend.repositories.SensorRepository;
import at.qe.backend.services.GreenhouseService;
import at.qe.backend.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This Service is responsible for saving the Measurements provided by the AccessPoints
 */
@Service
public class MeasurementService {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private GreenhouseService greenhouseService;
    @Autowired
    private MeasurementRepository measurementRepository;

    /**
     * Try to assign a measurement to the corresponding sensor/greenhouse/accesspoint
     * @param measurementDTO the raw MeasurementDTO from the AccessPoint
     * @return a new MeasurementDTO with the ID that was saved in the database. If it could not be saved an exception is thrown.
     * @throws GreenhouseNotRegisteredException The greenhouse hasn't been added to the database yet and therefore is not valid
     * @throws SensorNotFoundException The greenhouse doesn't have a sensor of provided type
     */
    public MeasurementDTO addMeasurement(MeasurementDTO measurementDTO) throws GreenhouseNotRegisteredException, SensorNotFoundException, AccessPointNotPublishedException, GreenhouseNotPublishedException {
        Greenhouse greenhouse = greenhouseService.loadGreenhouse(measurementDTO.getGreenhouseID(), measurementDTO.getAccesspointUUID());
        if (greenhouse == null) {
            throw new GreenhouseNotRegisteredException();
        }
        greenhouseService.updateLastContact(greenhouse);
        if (!greenhouse.getAccesspoint().isPublished()){
            throw new AccessPointNotPublishedException();
        }
        if (!greenhouse.isPublished()){
            throw new GreenhouseNotPublishedException();
        }
        Sensor sensor = sensorService.loadSensor(greenhouse, measurementDTO.getSensorType());
        if (sensor == null) {
            throw new SensorNotFoundException();
        }
        greenhouse.getAccesspoint().setLastContact(new Date());
        greenhouse.setLastContact(new Date());
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDTO.getValue());
        measurement.setSensor(sensor);
        measurement.setMeasurementDate(measurementDTO.getDate());
        measurement.setLimitExceededBy(measurementDTO.getLimitExceededBy());
        measurement = measurementRepository.save(measurement);
        sensor.addMeasurement(measurement);
        return new MeasurementDTO(measurement);
    }

    public List<MeasurementDTO> getMeasurement(int greenhouseId) {
        System.out.println("getMeasurement" + greenhouseId);
        //get all measurements from the database
        List<Measurement> measurements = measurementRepository.findAll();
        List<MeasurementDTO> greenhouseMeasurements = new ArrayList<>();

        for (Measurement measurement : measurements) {
            System.out.println(measurement.getSensor().getGreenhouse().getUuid());
            //if the measurement is from the specified greenhouse
            if (measurement.getSensor().getGreenhouse().getUuid() == greenhouseId) {
                //add the measurement to the list
                System.out.println("measurement added" + measurement.getId());
                greenhouseMeasurements.add(new MeasurementDTO(measurement));
            }
        }
        return greenhouseMeasurements;
    }
}
