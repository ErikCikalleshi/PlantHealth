package at.qe.backend.services;

import at.qe.backend.models.dto.SensorDTO;
import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.Sensor;
import at.qe.backend.models.SensorType;
import at.qe.backend.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SensorService {
    
    /**
     * The repository for accessing sensor data.
     */
    @Autowired
    private SensorRepository sensorRepository;

    /**
     * The service for managing audit logs.
     */
    @Autowired
    private AuditLogService auditLogService;

    /**
     * Saves a sensor to the database.
     * 
     * @param sensor The sensor to be saved.
     * @return The saved sensor.
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GARDENER')")
    public Sensor saveSensor(Sensor sensor) {
        if (sensor.isNew()) {
            sensor.setCreateDate(new Date());
            sensor.setCreateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            sensor.setUpdateDate(new Date());
            sensor.setUpdateUserUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        sensor = sensorRepository.save(sensor);
        return sensor;
    }
    
    /**
     * Loads a sensor from the database by its ID.
     * 
     * @param id The ID of the sensor to be loaded.
     * @return The loaded sensor.
     */
    public Sensor loadSensor(int id) {
        return sensorRepository.findFirstById(id);
    }
    
    /**
     * Loads a sensor from the database by its greenhouse and sensor type.
     * 
     * @param greenhouse The greenhouse of the sensor to be loaded.
     * @param sensorType The type of the sensor to be loaded.
     * @return The loaded sensor.
     */
    public Sensor loadSensor(Greenhouse greenhouse, SensorType sensorType) {
        return sensorRepository.findFirstByGreenhouseAndSensorType(greenhouse, sensorType);
    }

    /**
     * Deletes a sensor from the database.
     * 
     * @param sensor The sensor to be deleted.
     */
    public void deleteSensor(Sensor sensor) {
        auditLogService.createNewAudit("delete", Integer.toString(sensor.getId()), "sensor", true);
        sensorRepository.delete(sensor);
    }

    /**
     * Creates a new sensor and saves it to the database.
     * 
     * @param sensorType The type of the sensor to be created.
     * @param limitUpper The upper limit of the sensor.
     * @param limitLower The lower limit of the sensor.
     * @param limitThresholdMinutes The threshold time in minutes for the sensor.
     * @param greenhouse The greenhouse where the sensor is located.
     * @return The created and saved sensor.
     */
    public Sensor createSensor(SensorType sensorType, double limitUpper, double limitLower, int limitThresholdMinutes, Greenhouse greenhouse) {
        Sensor sensor = new Sensor();
        sensor.setSensorType(sensorType);
        sensor.setLimitUpper(limitUpper);
        sensor.setLimitLower(limitLower);
        sensor.setLimitThresholdMinutes(limitThresholdMinutes);
        sensor.setGreenhouse(greenhouse);
        auditLogService.createNewAudit("create", Integer.toString(sensor.getId()), "sensor", true);
        return saveSensor(sensor);
    }

    /**
     * Updates an existing sensor and saves it to the database.
     * 
     * @param sensorDTO The data transfer object containing the updated sensor information.
     * @return The updated and saved sensor.
     */
    public Sensor updateSensor(SensorDTO sensorDTO) {
        Sensor sensor = loadSensor(sensorDTO.id());
        sensor.setLimitUpper(sensorDTO.limitUpper());
        sensor.setLimitLower(sensorDTO.limitLower());
        sensor.setLimitThresholdMinutes(sensorDTO.limitThresholdMinutes());
        return saveSensor(sensor);
    }
}
