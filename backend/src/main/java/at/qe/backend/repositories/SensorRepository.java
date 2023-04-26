package at.qe.backend.repositories;

import at.qe.backend.models.*;

import java.util.List;

public interface SensorRepository extends AbstractRepository<Sensor, String>{
    Sensor findFirstByGreenhouseAndSensorType(Greenhouse greenhouse, SensorType sensorType);
    List<Sensor> findAllByGreenhouse_Accesspoint_Uuid(long accessPoint);
    Sensor findFirstById(int id);
}
