package at.qe.backend.api.controllers;

import at.qe.backend.api.services.UploadImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UploadImagesController {
    @Autowired
    private UploadImagesService uploadImagesService;
    record UploadImageData(Long userId, String uploadLink, Long plantId){};

    @PostMapping("/upload")
    public void upload(@RequestBody UploadImageData data) {
        uploadImagesService.create(data.plantId, data.userId, data.uploadLink);
    }

    @GetMapping("/greenhouse/get-all/{id}")
    public List<String> getAllImagesByGreenhouseId(@PathVariable("id") Long id) {
        return uploadImagesService.getAllById(id);
    }
}
