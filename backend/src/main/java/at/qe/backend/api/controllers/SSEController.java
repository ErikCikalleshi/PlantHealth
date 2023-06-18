package at.qe.backend.api.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSEController {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

//    @GetMapping("/time")
//    @CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
//    public SseEmitter streamDateTime() {
//        System.out.println("SSEController.streamDateTime");
//        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
//
//        executor.execute(() -> {
//            try {
//                sseEmitter.send(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
//                sleep(1, sseEmitter);
//            } catch (IOException e) {
//                e.printStackTrace();
//                sseEmitter.completeWithError(e);
//            }
//
//            sseEmitter.complete();
//        });
//
//        return sseEmitter;
//    }
//
//    private void sleep(int seconds, SseEmitter sseEmitter) {
//        try {
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            sseEmitter.completeWithError(e);
//        }
//    }
}
