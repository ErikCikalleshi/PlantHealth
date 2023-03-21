package at.qe.backend.api.services;

import at.qe.backend.api.exceptions.GreenhouseNotRegisteredException;
import at.qe.backend.api.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.MeasurementDTO;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.MeasurementRepository;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This Service is responsible for saving the Measurements provided by the AccessPoints
 */
@Service
public class MeasurementService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    /**
     * Try to assign a measurement to the corresponding sensor/greenhouse/accesspoint
     * @param measurementDTO the raw MeasurementDTO from the AccessPoint
     * @return a new MeasurementDTO with the ID that was saved in the database. If it could not be saved an exception is thrown.
     * @throws GreenhouseNotRegisteredException The greenhouse hasn't been added to the database yet and therefore is not valid
     * @throws SensorNotFoundException The greenhouse doesn't have a sensor of provided type
     */
    public MeasurementDTO addMeasurement(MeasurementDTO measurementDTO) throws GreenhouseNotRegisteredException, SensorNotFoundException {
        Greenhouse greenhouse = greenhouseRepository.findFirstByIdAndAccesspoint_Uuid(measurementDTO.getGreenhouseID(), measurementDTO.getAccesspointUUID());
        if (greenhouse == null) {
            throw new GreenhouseNotRegisteredException();
        }
        Sensor sensor = sensorRepository.findFirstByGreenhouseAndSensorType(greenhouse, measurementDTO.getSensorType());
        if (sensor == null) {
            throw new SensorNotFoundException();
        }
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDTO.getValue());
        measurement.setSensor(sensor);
        measurement.setMeasurementDate(measurementDTO.getDate());
        measurement = measurementRepository.save(measurement);
        sensor.addMeasurement(measurement);
        return new MeasurementDTO(measurement);
    }
}
