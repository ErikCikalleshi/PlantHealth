package at.qe.backend.services;

import at.qe.backend.models.UploadImages;
import at.qe.backend.repositories.GreenhouseRepository;
import at.qe.backend.repositories.UploadImagesRepository;
import at.qe.backend.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadImagesService {
    @Autowired
    private UploadImagesRepository uploadImagesRepository;
    @Autowired
    private GreenhouseRepository greenhouseRepository;
    @Autowired
    private UserxRepository userxRepository;

    @Autowired
    private AuditLogService auditLogService;

    private UploadImages save(UploadImages uploadImages) {
        if (uploadImages.isNew()) {
            uploadImages.setUploadDate(new Date());
        }
        return uploadImagesRepository.save(uploadImages);
    }

    public UploadImages create(Long plantId, String username, String uploadLink) {
        var greenHouse = greenhouseRepository.findByUuid(plantId).orElse(null);
        if(greenHouse == null) {
            auditLogService.createNewAudit("create", "NA", "image", false);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "GreenhouseId is invalid.");
        }
        var user = userxRepository.findByUsername(username).orElse(null);
        if(user == null && !username.equals("guest")) {
            auditLogService.createNewAudit("create", "NA", "image", false);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is invalid.");
        }
        if(uploadLink.length() < 1) {
            auditLogService.createNewAudit("create", "NA", "image", false);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid upload link.");
        }
        UploadImages newImage = new UploadImages();
        newImage.setPlantId(plantId);

        newImage.setUserId((user != null) ? user.getId() : Long.valueOf(1L));
        newImage.setUploadLink(uploadLink);
        newImage = save(newImage);
        auditLogService.createNewAudit("create", Long.toString(newImage.getId()), "image", true);
        return newImage;
    }

    public List<String> getAllById(Long id) {
        var greenHouse = greenhouseRepository.findByUuid(id).orElse(null);
        if(greenHouse == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "GreenhouseId is invalid.");
        }
        var imagesList = uploadImagesRepository.getAllUploadLinksByPlantId(id);
        return imagesList.stream().map(UploadImages::getUploadLink).collect(Collectors.toList());
    }

    public void deleteImageByUrl(String url) {
        var greenHouseImage = uploadImagesRepository.findUploadImagesByUploadLink(url);
        if(greenHouseImage == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid url.");
        }
        auditLogService.createNewAudit("delete", Long.toString(greenHouseImage.getId()), "image", true);
        uploadImagesRepository.delete(greenHouseImage);
    }
}
