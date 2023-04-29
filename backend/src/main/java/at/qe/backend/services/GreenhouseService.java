package at.qe.backend.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import at.qe.backend.api.model.dto.SensorDTO;
import at.qe.backend.models.AccessPoint;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.Userx;
import at.qe.backend.models.request.CreateNewGreenhouseRequest;
import at.qe.backend.repositories.GreenhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;


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

    private Greenhouse saveGreenhouse(Greenhouse greenhouse) {
        if (greenhouse.isNew()) {
            greenhouse.setCreateDate(new Date());
            greenhouse.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            greenhouse.setIdInCluster(greenhouseRepository.countGreenhouseByAccesspoint(greenhouse.getAccesspoint()) + 1);
            auditLogService.createNewAudit("create", Long.toString(greenhouse.getUuid()), "greenhouse", true);
        } else {
            greenhouse.setUpdateDate(new Date());
            greenhouse.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            auditLogService.createNewAudit("update", Long.toString(greenhouse.getUuid()), "greenhouse", true);
        }
        greenhouse = greenhouseRepository.save(greenhouse);
        return greenhouse;
    }

    public List<Greenhouse> getAll() {
        return greenhouseRepository.findAll();
    }
    public String getNameById(Long id) {
        var gh = greenhouseRepository.findByUuid(id).orElse(null);
        if (gh == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid greenhouse id.");
        }
        return gh.getName();
    }

    public Greenhouse createGreenhouse(CreateNewGreenhouseRequest request) {
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
        Set<Sensor> sensors = new HashSet<>();
        for (SensorDTO sensorDTO : request.sensors()) {
            Sensor sensor = new Sensor();
            sensor.setSensorType(sensorDTO.sensorType());
            sensor.setLimitUpper(sensorDTO.limitUpper());
            sensor.setLimitLower(sensorDTO.limitLower());
            sensor.setLimitThresholdMinutes(sensorDTO.limitThresholdMinutes());
            sensor.setGreenhouse(greenhouse);
            sensors.add(sensor);

            sensorService.saveSensor(sensor);
        }


        greenhouse.setSensors(sensors);
        greenhouse = saveGreenhouse(greenhouse);
        return saveGreenhouse(greenhouse);

    }


    public Greenhouse loadGreenhouse(long greenhouseID, long accesspointUUID) {
        return greenhouseRepository.findFirstByIdInClusterAndAccesspoint_Uuid(greenhouseID, accesspointUUID);
    }

    public void updateLastContact(Greenhouse greenhouse){
        Date lastContact = new Date();
        greenhouse.setLastContact(lastContact);
        greenhouse.getAccesspoint().setLastContact(lastContact);
        greenhouseRepository.save(greenhouse);
        accessPointService.saveAccessPoint(greenhouse.getAccesspoint());
    }
}
