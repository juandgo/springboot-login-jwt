package com.devtec.book.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }

//    ----------------- to try  the functinoality ----------------------------------------
//@GetMapping("/test-token")
//public ResponseEntity<String> testToken() {
//    // Simula un usuario cualquiera
//    org.springframework.security.core.userdetails.UserDetails userDetails =
//            org.springframework.security.core.userdetails.User
//                    .withUsername("juan")
//                    .password("123456789")
//                    .authorities("USER")
//                    .build();
//
//    String token = service.generateTestToken(userDetails); // usa tu JwtService internamente
//    return ResponseEntity.ok(token);
//}
//
//    @GetMapping("/validate-token")
//    public ResponseEntity<Boolean> validateToken(
//            @RequestParam String token,
//            @RequestParam String username
//    ) {
//        // Simula el UserDetails de nuevo
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername(username)
//                .password("123456789")
//                .authorities("USER")
//                .build();
//
//        boolean isValid = service.isTokenValid(token, userDetails);
//        return ResponseEntity.ok(isValid);
//    }

}
