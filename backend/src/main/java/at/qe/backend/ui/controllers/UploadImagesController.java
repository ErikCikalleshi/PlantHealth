package at.qe.backend.ui.controllers;

import at.qe.backend.services.UploadImagesService;
import at.qe.backend.models.UploadImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UploadImagesController {
    @Autowired
    private UploadImagesService uploadImagesService;
    record UploadImageData(String username, String uploadLink, Long plantId){}

    @PostMapping("/upload")
    public UploadImages upload(@RequestBody UploadImageData data) {
        return uploadImagesService.create(data.plantId, data.username, data.uploadLink);
    }

    @GetMapping("/greenhouse/get-all/{id}")
    public List<String> getAllImagesByGreenhouseId(@PathVariable("id") Long id) {
        return uploadImagesService.getAllById(id);
    }

    record DeleteData(String url){}
    @DeleteMapping("/greenhouse/delete")
    public void deleteImage(@RequestBody DeleteData data) {
        if(data.url.length() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image url is missing.");
        }
        uploadImagesService.deleteImageByUrl(data.url);
    }
}
