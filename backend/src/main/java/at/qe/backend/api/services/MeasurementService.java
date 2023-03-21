package at.qe.backend.api.services;

import at.qe.backend.api.exceptions.GreenhouseNotRegisteredException;
import at.qe.backend.api.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.RawMeasurement;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.MeasurementRepository;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class MeasurementService {
    private static final ConcurrentHashMap<Long, Measurement> measurements = new ConcurrentHashMap<>();
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    public Measurement addMeasurement(RawMeasurement rawMeasurement) throws GreenhouseNotRegisteredException, SensorNotFoundException {
        Greenhouse greenhouse = greenhouseRepository.findFirstByUuid(rawMeasurement.getGreenhouseID());
        if (greenhouse == null) {
            throw new GreenhouseNotRegisteredException();
        }
        Sensor sensor = sensorRepository.findFirstByGreenhouseAndSensorType(greenhouse, rawMeasurement.getSensorType());
        if (sensor == null) {
            throw new SensorNotFoundException();
        }
        Measurement newMeasurement = new Measurement();
        newMeasurement.setValue(rawMeasurement.getValue());
        newMeasurement.setSensor(sensor);
        newMeasurement = measurementRepository.save(newMeasurement);
        sensor.addMeasurement(newMeasurement);
        return newMeasurement;
    }
}
