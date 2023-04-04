package at.qe.backend.api.controllers;


import at.qe.backend.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//TODO remove class before deploying

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api")
public class DebugAPI {
    @Autowired
    UserxService userxService;
    @GetMapping("/debug/{username}")
    public Integer getUserGreenhouseCount(@PathVariable String username){
        return userxService.loadUser(username).getGreenhouses().size();
    }
}
