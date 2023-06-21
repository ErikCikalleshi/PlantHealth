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
    long greenhouse1 = -1;
    long greenhouse2 = -1;

    @PostMapping("/disabled")
    public void send_disabled_notification(@RequestBody GreenhouseRequestBody requestBody) throws IOException {
        long greenhouse = requestBody.getGreenhouse();
        System.out.println(requestBody);
        System.out.println("Notification received: " + greenhouse);

        this.greenhouse1 = greenhouse;
        this.greenhouse2 = greenhouse;
        //streamDateTime();
    }


    @GetMapping("/emitter")
    public SseEmitter eventEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                if (this.greenhouse1 > 0) {
                    emitter.send(greenhouse1);
                    this.greenhouse1 = -1;
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

    @GetMapping("/emitter2")
    public SseEmitter eventEmitter2() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                if (this.greenhouse2 > 0) {
                    emitter.send(greenhouse2);
                    this.greenhouse2 = -1;
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
