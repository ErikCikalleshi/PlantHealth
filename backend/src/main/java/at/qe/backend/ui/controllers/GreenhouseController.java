package at.qe.backend.ui.controllers;

import at.qe.backend.services.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.request.CreateNewGreenhouseRequest;
import at.qe.backend.services.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class GreenhouseController {
    @Autowired
    private GreenhouseService greenhouseService;

    record GreenhouseData(Long id, String name, String location, String description, String status) {}

    @GetMapping("/greenhouse/get")
    public List<GreenhouseData> getAllGreenhouses() {
        var greenhouses = greenhouseService.getAll();
        return greenhouses.stream()
                .map(greenhouse -> new GreenhouseData(greenhouse.getUuid(), greenhouse.getName(), greenhouse.getLocation(), greenhouse.getDescription(), greenhouse.getStatus()))
                .toList();
    }
    @GetMapping("/greenhouse/get/{id}")
    public GreenhouseDTO getGreenhouseById(@PathVariable("id") Long id) {
        return new GreenhouseDTO(greenhouseService.loadGreenhouse(id));
    }

    @GetMapping("greenhouse/get-name/{id}")
    public String getGreenHouseNameById(@PathVariable("id") Long id) {
        return greenhouseService.getNameById(id);
    }
    @PostMapping("/admin/greenhouse/add")
    @ResponseStatus(HttpStatus.CREATED)
    public GreenhouseDTO addGreenhouse(@RequestBody CreateNewGreenhouseRequest request) {
        return new GreenhouseDTO(greenhouseService.createGreenhouse(request));
    }



    @PutMapping("/gardener/greenhouse/update")
    public GreenhouseDTO updateGreenhouse(@RequestBody GreenhouseDTO greenhouseDTO) {
        return new GreenhouseDTO(greenhouseService.updateGreenhouse(greenhouseDTO));
    }
}
