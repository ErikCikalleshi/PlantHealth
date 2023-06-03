package at.qe.backend.api.controllers;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotRegisteredException;
import at.qe.backend.exceptions.SensorNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class NotificationController {
    @PostMapping("/disabled")
    public void send_disabled_notification(@RequestBody long greenhouse) throws SensorNotFoundException, GreenhouseNotRegisteredException, AccessPointNotPublishedException, GreenhouseNotPublishedException {
        System.out.println("Notification received: " + greenhouse);
    }
}
