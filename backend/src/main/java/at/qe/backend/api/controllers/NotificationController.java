package at.qe.backend.api.controllers;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.exceptions.AccessPoint.AccessPointNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotPublishedException;
import at.qe.backend.exceptions.Greenhouse.GreenhouseNotRegisteredException;
import at.qe.backend.exceptions.SensorNotFoundException;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class NotificationController {
    public static class GreenhouseRequestBody {
        private long greenhouse;

        public GreenhouseRequestBody() {
        }

        public long getGreenhouse() {
            return greenhouse;
        }

        public void setGreenhouse(long greenhouse) {
            this.greenhouse = greenhouse;
        }
    }



    @PostMapping("/disabled")
    public void send_disabled_notification(@RequestBody GreenhouseRequestBody requestBody) throws IOException {
        long greenhouse = requestBody.getGreenhouse();
        System.out.println(requestBody);
        System.out.println("Notification received: " + greenhouse);
        //send notification to greenhouse with server sent events
        //get_notification();
    }
}

