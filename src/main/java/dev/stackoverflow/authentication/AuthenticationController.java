package dev.stackoverflow.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return  service.authenticate(request);
    }
}
