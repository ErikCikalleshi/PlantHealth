package at.qe.backend.services;

import at.qe.backend.models.Measurement;
import at.qe.backend.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementServiceV2 {
    @Autowired
    private MeasurementRepository measurementRepository;

    public List<Measurement> getAllMeasurements(){
        return measurementRepository.findAll();
    }
}
