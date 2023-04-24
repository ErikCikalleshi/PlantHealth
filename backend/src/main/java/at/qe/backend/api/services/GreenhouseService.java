package at.qe.backend.api.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GreenhouseService {
    @Autowired
    private GreenhouseRepository greenhouseRepository;

    public List<Greenhouse> getAll() {
        return greenhouseRepository.findAll();
    }
}
