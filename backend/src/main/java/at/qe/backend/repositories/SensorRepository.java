package at.qe.backend.repositories;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;

import java.util.List;

public interface SensorRepository extends AbstractRepository<Sensor, String>{
    Sensor findFirstByGreenhouseAndSensorType(Greenhouse greenhouse, SensorType sensorType);
    List<Sensor> findAllByGreenhouse_Accesspoint_Uuid(long accessPoint);
}
