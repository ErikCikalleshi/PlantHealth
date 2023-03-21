package at.qe.backend.api.controllers;

import at.qe.backend.api.exceptions.AccessPointNotFoundException;
import at.qe.backend.api.exceptions.GreenhouseNotRegisteredException;
import at.qe.backend.api.exceptions.SensorNotFoundException;
import at.qe.backend.api.model.AccessPointSettingDTO;
import at.qe.backend.api.model.MeasurementDTO;
import at.qe.backend.api.services.MeasurementService;
import at.qe.backend.api.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class SettingController {
    @Autowired
    SettingService settingService;

    @GetMapping("/setting/{accessPointId}")
    private AccessPointSettingDTO getSetting(@PathVariable long accessPointId) throws AccessPointNotFoundException {
        return settingService.getSetting(accessPointId);
    }
}
