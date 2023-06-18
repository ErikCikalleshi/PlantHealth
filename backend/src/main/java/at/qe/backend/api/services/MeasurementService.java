package at.qe.backend.api.services;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotRegisteredException;
import at.qe.backend.exceptions.SensorNotFoundException;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.MeasurementRepository;
import at.qe.backend.services.GreenhouseService;
import at.qe.backend.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
     *
     * @param measurementDTO the raw MeasurementDTO from the AccessPoint
     * @return a new MeasurementDTO with the ID that was saved in the database. If it could not be saved an exception is thrown.
     * @throws GreenhouseNotRegisteredException The greenhouse hasn't been added to the database yet and therefore is not valid
     * @throws SensorNotFoundException          The greenhouse doesn't have a sensor of provided type
     */
    public MeasurementDTO addMeasurement(MeasurementDTO measurementDTO) throws GreenhouseNotRegisteredException, SensorNotFoundException, AccessPointNotPublishedException, GreenhouseNotPublishedException {
        Greenhouse greenhouse = greenhouseService.loadGreenhouse(measurementDTO.getGreenhouseID(), measurementDTO.getAccesspointUUID());
        if (greenhouse == null) {
            throw new GreenhouseNotRegisteredException();
        }
        greenhouseService.updateLastContact(greenhouse);
        if (!greenhouse.getAccesspoint().isPublished()) {
            throw new AccessPointNotPublishedException();
        }
        if (!greenhouse.isPublished()) {
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
        measurement.setLimitLower(measurementDTO.getLowerLimit());
        measurement.setLimitUpper(measurementDTO.getUpperLimit());
        measurement.setLimitExceededBy(measurementDTO.getLimitExceededBy());
        if (measurement.getLimitExceededBy() != 0) {
            greenhouse.setLastLimitExceeded(new Date());
            sensor.setLastLimitExceeded(new Date());
        }
        measurement = measurementRepository.save(measurement);
        sensor.addMeasurement(measurement);
        return new MeasurementDTO(measurement);
    }

}
