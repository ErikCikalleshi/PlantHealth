package at.qe.backend.api.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class GreenhouseService {
    @Autowired
    private GreenhouseRepository greenhouseRepository;

    public List<Greenhouse> getAll() {
        return greenhouseRepository.findAll();
    }
    public String getNameById(Long id) {
        var gh = greenhouseRepository.findByUuid(id).orElse(null);
        if(gh == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid greenhouse id.");
        }
        return gh.getName();
    }
}
