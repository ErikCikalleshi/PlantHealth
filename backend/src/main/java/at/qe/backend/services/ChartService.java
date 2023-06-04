package at.qe.backend.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import at.qe.backend.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired GreenhouseService greenhouseService;
    @Autowired SensorService sensorService;

    public List<Measurement> getAllMeasurementsByGreenhouseUuidAndSensorType(long greenhouseUuid, SensorType sensorType){
        Greenhouse greenhouse = greenhouseService.loadGreenhouse(greenhouseUuid);
        Sensor sensor = sensorService.loadSensor(greenhouse, sensorType);
        return measurementRepository.findAllBySensorId(sensor.getId());
    }
    public List<Measurement> getAllMeasurements(){
        return measurementRepository.findAll();
    }
}
