package at.qe.backend.ui.controllers;

import at.qe.backend.models.Greenhouse;
import at.qe.backend.models.dto.GreenhouseDTO;
import at.qe.backend.models.request.CreateNewGreenhouseRequest;
import at.qe.backend.services.GreenhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    /**
     * This class represents the controller for the Greenhouse entity.
     * It handles HTTP requests and responses related to Greenhouse objects.
     * The class provides endpoints for retrieving, creating, updating and deleting Greenhouses.
     */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class GreenhouseController {
    @Autowired
    private GreenhouseService greenhouseService;

    /**
     * This class represents the data for a Greenhouse object.
     * It is used to return a simplified version of the Greenhouse object in the getAllGreenhouses() method.
     */
    record GreenhouseData(Long id, String name, String location, String description, String status) {}



    /**
     * Returns a list of all Greenhouses in the system.
     * @return a list of GreenhouseData objects representing all Greenhouses in the system.
     */
    @GetMapping("/greenhouse/get")
    public List<GreenhouseData> getAllGreenhouses() {
        var greenhouses = greenhouseService.getAll();
        return greenhouses.stream()
                .map(greenhouse -> new GreenhouseData(greenhouse.getUuid(), greenhouse.getName(), greenhouse.getLocation(), greenhouse.getDescription(), greenhouse.getStatus()))
                .toList();
    }

    /**
     * Returns a Greenhouse object with the given id.
     * @param id the id of the Greenhouse to retrieve.
     * @return a GreenhouseDTO object representing the Greenhouse with the given id.
     */
    @GetMapping("/greenhouse/get/{id}")
    public GreenhouseDTO getGreenhouseById(@PathVariable("id") Long id) {
        return new GreenhouseDTO(greenhouseService.loadGreenhouse(id));
    }

    /**
     * Returns the name of the Greenhouse with the given id.
     * @param id the id of the Greenhouse to retrieve the name of.
     * @return a String representing the name of the Greenhouse with the given id.
     */
    @GetMapping("greenhouse/get-name/{id}")
    public String getGreenHouseNameById(@PathVariable("id") Long id) {
        return greenhouseService.getNameById(id);
    }

    /**
     * Creates a new Greenhouse object with the given parameters.
     * @param request a CreateNewGreenhouseRequest object containing the parameters for the new Greenhouse.
     * @return a GreenhouseDTO object representing the newly created Greenhouse.
     */
    @PostMapping("/admin/greenhouse/add")
    @ResponseStatus(HttpStatus.CREATED)
    public GreenhouseDTO addGreenhouse(@RequestBody CreateNewGreenhouseRequest request) {
        return new GreenhouseDTO(greenhouseService.createGreenhouse(request));
    }

    /**
     * Updates an existing Greenhouse object with the given parameters.
     * @param greenhouseDTO a GreenhouseDTO object containing the updated parameters for the Greenhouse.
     * @return a GreenhouseDTO object representing the updated Greenhouse.
     */
    @PutMapping("/gardener/greenhouse/update")
    public GreenhouseDTO updateGreenhouse(@RequestBody GreenhouseDTO greenhouseDTO) {
        return new GreenhouseDTO(greenhouseService.updateGreenhouse(greenhouseDTO));
    }

    /**
     * Returns a list of all Greenhouses belonging to the current user.
     * @return a list of GreenhouseDTO objects representing all Greenhouses belonging to the current user.
     */
    @GetMapping("/gardener/greenhouse/get-all")
    public List<GreenhouseDTO> getGreenhouseById() {
        List<Greenhouse> greenhouses =  greenhouseService.getAllForCurrentUser();
        return greenhouses.stream()
                .map(GreenhouseDTO::new)
                .toList();
    }
}
