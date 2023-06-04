package at.qe.backend.services;

import at.qe.backend.models.*;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import at.qe.backend.models.dto.SensorDTO;
import at.qe.backend.models.request.CreateNewGreenhouseRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;


/**
 * Service for creating, updating, receiving and deleting greenhouses.
 */
@Service
public class GreenhouseService {
    @Autowired
    private GreenhouseRepository greenhouseRepository;
    @Autowired
    private AccessPointService accessPointService;
    @Autowired
    private UserxService userxService;
    @Autowired
    private SensorService sensorService;

    @Autowired
    private AuditLogService auditLogService;


    /**
     * @param greenhouse the greenhouse to be saved
     * sets the create date and user if the greenhouse is new, otherwise the update date and user
     * requires the {@link UserRole} Gardener or Admin
     * @return the saved greenhouse
     */
    @PreAuthorize("hasAuthority('GARDENER') or hasAuthority('ADMIN')")
    private Greenhouse saveGreenhouse(Greenhouse greenhouse) {
        if (greenhouse.isNew()) {
            greenhouse.setCreateDate(new Date());
            greenhouse.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            greenhouse.setIdInCluster(greenhouseRepository.countGreenhouseByAccesspoint(greenhouse.getAccesspoint()) + 1);
        } else {
            greenhouse.setUpdateDate(new Date());
            greenhouse.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        greenhouse = greenhouseRepository.save(greenhouse);
        return greenhouse;
    }


    /**
     * @return all greenhouses
     */
    public List<Greenhouse> getAll() {
        return greenhouseRepository.findAll();
    }

    /**
     * @param id the id of the greenhouse to be loaded
     * @return Greenhouse
     */
    public String getNameById(Long id) {
        var gh = greenhouseRepository.findByUuid(id).orElse(null);
        if (gh == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid greenhouse id.");
        }
        return gh.getName();
    }

    /**
     * @param request the request containing the greenhouse to be created and the access point id to be assigned to the greenhouse
     * @return the created greenhouse
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Greenhouse createGreenhouse(CreateNewGreenhouseRequest request) {
        AuditLog auditLog = auditLogService.createNewAudit("create", "NA", "greenhouse", false);
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setName(request.greenhouse().name());
        greenhouse.setLocation(request.greenhouse().location());
        greenhouse.setDescription(request.greenhouse().description());
        greenhouse.setPublished(request.greenhouse().published());

        AccessPoint accessPoint = accessPointService.loadAccessPoint(Long.parseLong(request.accessPointId()));
        greenhouse.setAccesspoint(accessPoint);
        Userx gardener = userxService.loadUser(request.greenhouse().gardener().username());
        greenhouse.setOwner(gardener);
        greenhouse = saveGreenhouse(greenhouse);
        Set<Sensor> sensors = getSensorsFromRequest(request, greenhouse);
        greenhouse.getSensors().clear();
        greenhouse.getSensors().addAll(sensors);
        greenhouse = saveGreenhouse(greenhouse);
        auditLog.setTargetID(Long.toString(greenhouse.getUuid()));
        auditLog.setSuccess(true);
        auditLogService.saveAuditLog(auditLog);
        return saveGreenhouse(greenhouse);

    }


    private Set<Sensor> getSensorsFromRequest(CreateNewGreenhouseRequest request, Greenhouse greenhouse) {
        Set<Sensor> sensors = new HashSet<>();
        for (SensorDTO sensorDTO : request.sensors()) {
            Sensor sensor = new Sensor();
            sensor.setSensorType(sensorDTO.sensorType());
            sensor.setLimitUpper(sensorDTO.limitUpper());
            sensor.setLimitLower(sensorDTO.limitLower());
            sensor.setLimitThresholdMinutes(sensorDTO.limitThresholdMinutes());
            sensor.setGreenhouse(greenhouse);
            sensor = sensorService.saveSensor(sensor);
            sensors.add(sensor);
        }
        return sensors;
    }


    /**
     * @param greenhouseID the id of the greenhouse to be loaded (id set on the arduino controller)
     * @param accesspointUUID the id of the access point to be loaded
     * @return the greenhouse with the given id and access point id
     */
    public Greenhouse loadGreenhouse(long greenhouseID, long accesspointUUID) {
        return greenhouseRepository.findFirstByIdInClusterAndAccesspoint_Uuid(greenhouseID, accesspointUUID);
    }

    /**
     * @param greenhouseUUID the uuid of the greenhouse to be loaded
     * @return the greenhouse with the given uuid
     */
    public Greenhouse loadGreenhouse(long greenhouseUUID) {
        return greenhouseRepository.findByUuid(greenhouseUUID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid greenhouse id."));
    }


    /**
     * Call this function when a greenhouse sends a measurement to the server
     * @param greenhouse the greenhouse to be updated
     */
    public void updateLastContact(Greenhouse greenhouse) {
        Date lastContact = new Date();
        greenhouse.setLastContact(lastContact);
        greenhouse.getAccesspoint().setLastContact(lastContact);
        greenhouseRepository.save(greenhouse);
        accessPointService.saveAccessPoint(greenhouse.getAccesspoint());
    }

    /**
     * @param greenhouseDTO the greenhouse to be updated
     * requires the {@link UserRole} Gardener or Admin
     * updates the name, location, description and published status of the greenhouse based on the uuid of the greenhouse in the request
     * @return the updated greenhouse
     */
    @PreAuthorize("hasAuthority('GARDENER') or hasAuthority('ADMIN')")
    public Greenhouse updateGreenhouse(GreenhouseDTO greenhouseDTO) {
        AuditLog auditLog = auditLogService.createNewAudit("update", "NA", "greenhouse", false);
        Greenhouse greenhouse = loadGreenhouse(greenhouseDTO.uuid());
        greenhouse.setName(greenhouseDTO.name());
        greenhouse.setLocation(greenhouseDTO.location());
        greenhouse.setDescription(greenhouseDTO.description());
        greenhouse.setPublished(greenhouseDTO.published());
        for (SensorDTO sensorDTO : greenhouseDTO.sensors()) {
            sensorService.updateSensor(sensorDTO);
        }
        auditLog.setTargetID(Long.toString(greenhouse.getUuid()));
        auditLog.setSuccess(true);
        auditLogService.saveAuditLog(auditLog);
        return saveGreenhouse(greenhouse);
    }


    /**
     * @return all greenhouses for the current user (Owner=currentUser) or all greenhouses if the current user is an admin
     * @throws ResponseStatusException if the current user is not logged in
     */
    @PreAuthorize("hasAuthority('GARDENER') or hasAuthority('ADMIN')")
    public List<Greenhouse> getAllForCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not logged in.");
        }
        Userx currentUser = userxService.loadUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not logged in. (invalid user)");
        }
        if (currentUser.getRoles().contains(UserRole.ADMIN)) {
            return getAll();
        }
        return greenhouseRepository.findAllByOwner_Username(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
