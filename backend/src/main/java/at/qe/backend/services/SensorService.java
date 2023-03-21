package at.qe.backend.services;

import at.qe.backend.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class SensorService {
    @Autowired
    private UserxRepository userRepository;
}
