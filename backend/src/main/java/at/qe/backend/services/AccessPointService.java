package at.qe.backend.services;

import at.qe.backend.models.AccessPoint;
import at.qe.backend.repositories.AccessPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("application")
public class AccessPointService {
    @Autowired
    private AccessPointRepository accessPointRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AccessPoint> getAllAccessPoints() {
        return accessPointRepository.findAll();
    }

}
