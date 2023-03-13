package at.qe.backend.api.services;

import at.qe.backend.api.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MeasurementService {
    private static final AtomicLong ID_COUNTER = new AtomicLong(1);
    private static final ConcurrentHashMap<Long, Measurement> measurements = new ConcurrentHashMap<>();

    public Measurement addMeasurement(Measurement measurement) {
        Measurement newMeasurement = new Measurement();
        newMeasurement.setId(ID_COUNTER.getAndIncrement());
        newMeasurement.setPlantID(measurement.getPlantID());
        newMeasurement.setUnit(measurement.getUnit());
        newMeasurement.setValue(measurement.getValue());
        newMeasurement.setType(measurement.getType());

        measurements.put(newMeasurement.getId(), newMeasurement);
        return newMeasurement;
    }
}
