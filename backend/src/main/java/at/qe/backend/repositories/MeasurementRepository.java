package at.qe.backend.repositories;

import at.qe.backend.models.Measurement;

import java.util.List;

public interface MeasurementRepository extends AbstractRepository<Measurement, String> {
    List<Measurement> findAllBySensorId(long sensorId);
}
