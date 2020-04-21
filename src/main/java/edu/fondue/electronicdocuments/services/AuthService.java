package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.SignInDto;
import edu.fondue.electronicdocuments.dto.SignUpDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticate(SignInDto request);
    ResponseEntity<String> signup(SignUpDto request);
}
