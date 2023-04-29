package at.qe.backend.services;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.SensorRepository;
import at.qe.backend.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AuditLogService auditLogService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GARDENER')")
    public Sensor saveSensor(Sensor sensor) {
        if (sensor.isNew()) {
            sensor.setCreateDate(new Date());
            sensor.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            auditLogService.createNewAudit("create", Integer.toString(sensor.getId()), "sensor", true);
        } else {
            sensor.setUpdateDate(new Date());
            sensor.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            auditLogService.createNewAudit("update", Integer.toString(sensor.getId()), "sensor", true);
        }
        sensor = sensorRepository.save(sensor);
        return sensor;
    }
    public Sensor loadSensor(int id) {
        return sensorRepository.findFirstById(id);
    }
    public Sensor loadSensor(Greenhouse greenhouse, SensorType sensorType) {
        return sensorRepository.findFirstByGreenhouseAndSensorType(greenhouse, sensorType);
    }

    public void deleteSensor(Sensor sensor) {
        auditLogService.createNewAudit("delete", Integer.toString(sensor.getId()), "sensor", true);
        sensorRepository.delete(sensor);
    }


    public Sensor createSensor(SensorType sensorType, double limitUpper, double limitLower, int limitThresholdMinutes, Greenhouse greenhouse) {
        Sensor sensor = new Sensor();
        sensor.setSensorType(sensorType);
        sensor.setLimitUpper(limitUpper);
        sensor.setLimitLower(limitLower);
        sensor.setLimitThresholdMinutes(limitThresholdMinutes);
        sensor.setGreenhouse(greenhouse);
        return saveSensor(sensor);
    }
}
