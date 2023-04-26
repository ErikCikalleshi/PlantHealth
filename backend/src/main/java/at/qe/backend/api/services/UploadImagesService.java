package at.qe.backend.api.services;

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

    public UploadImages create(Long plantId, String username, String uploadLink) {
        var greenHouse = greenhouseRepository.findByUuid(plantId).orElse(null);
        if(greenHouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GreenhouseId is invalid.");
        }
        var user = userxRepository.findByUsername(username).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username is invalid.");
        }
        if(uploadLink.length() < 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid upload link.");
        }
        var newImage = new UploadImages();
        newImage.setUploadDate(new Date());
        newImage.setPlantId(plantId);
        newImage.setUserId(user.getId());
        newImage.setUploadLink(uploadLink);
        uploadImagesRepository.save(newImage);
        return newImage;
    }

    public List<String> getAllById(Long id) {
        var greenHouse = greenhouseRepository.findByUuid(id).orElse(null);
        if(greenHouse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GreenhouseId is invalid.");
        }
        var imagesList = uploadImagesRepository.getAllUploadLinksByPlantId(id);
        return imagesList.stream().map(UploadImages::getUploadLink).collect(Collectors.toList());
    }

}
