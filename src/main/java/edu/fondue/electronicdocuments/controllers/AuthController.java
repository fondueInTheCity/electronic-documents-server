package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.SignInDto;
import edu.fondue.electronicdocuments.dto.SignUpDto;
import edu.fondue.electronicdocuments.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInDto loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @PostMapping("signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpRequest) {
        return authService.signup(signUpRequest);
    }
}
