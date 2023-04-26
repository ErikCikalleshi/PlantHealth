package at.qe.backend.ui.controllers;

import at.qe.backend.services.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class GreenhouseController {
    @Autowired
    private GreenhouseService greenhouseService;

    record GreenhouseData(Long id, String name, String location, String description, String status) {
    }

    ;

    @GetMapping("/greenhouse/get")
    public List<GreenhouseData> getAllGreenhouses() {
        var greenhouses = greenhouseService.getAll();
        return greenhouses.stream()
                .map(greenhouse -> new GreenhouseData(greenhouse.getUuid(), greenhouse.getName(), greenhouse.getLocation(), greenhouse.getDescription(), greenhouse.getStatus()))
                .toList();
    }

    @GetMapping("greenhouse/get-name/{id}")
    public String getGreenHouseNameById(@PathVariable("id") Long id) {
        return greenhouseService.getNameById(id);
    }
}
