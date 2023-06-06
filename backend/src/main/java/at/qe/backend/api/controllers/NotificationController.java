package at.qe.backend.api.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class NotificationController {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private SseEmitter sseEmitter;
    private boolean sendNotification = false;

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
    long greenhouse = -1;

    @PostMapping("/disabled")
    public void send_disabled_notification(@RequestBody GreenhouseRequestBody requestBody) throws IOException {
        long greenhouse = requestBody.getGreenhouse();
        System.out.println(requestBody);
        System.out.println("Notification received: " + greenhouse);

        // Set the flag to true to indicate that a notification should be sent
        sendNotification = true;
        this.greenhouse = greenhouse;
        //streamDateTime();
    }

    public SseEmitter streamDateTime() throws IOException {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .name("notification");
        emitter.send(event);
        System.out.println("Notification sent");
        return emitter;
    }

    @GetMapping("/emitter")
    public SseEmitter eventEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); //12000 here is the timeout and it is optional   //create a single thread for sending messages asynchronously
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                if (this.greenhouse > 0) {
                    emitter.send(greenhouse);
                    this.greenhouse = -1;
                } else {
                    emitter.send("No greenhouse selected");
                }
            } catch(Exception e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        executor.shutdown();
        return emitter;}
}
