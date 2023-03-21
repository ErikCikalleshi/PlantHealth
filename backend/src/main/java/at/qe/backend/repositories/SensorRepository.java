package at.qe.backend.repositories;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import at.qe.backend.models.Userx;

public interface SensorRepository extends AbstractRepository<Sensor, String>{
    Sensor findFirstByGreenhouseAndSensorType(Greenhouse greenhouse, SensorType sensorType);
}
