package at.qe.backend.ui.controllers;

import at.qe.backend.api.model.dto.MeasurementDTO;
import at.qe.backend.configs.security.jwt.models.RefreshToken;
import at.qe.backend.configs.security.services.UserDetailsImpl;
import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.Measurement;
import at.qe.backend.models.SensorType;
import at.qe.backend.models.Userx;
import at.qe.backend.models.request.LoginRequest;
import at.qe.backend.models.response.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationController {
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.id());
        Userx user = userxService.loadUser(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), JSONDateFormatHelper.format(user.getCreateDate()), user.getEmail(), user.getRoles()));
    }



}
