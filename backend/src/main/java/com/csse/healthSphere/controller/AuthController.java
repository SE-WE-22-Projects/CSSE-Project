package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.AuthUser;
import com.csse.healthSphere.dto.LoginRequest;
import com.csse.healthSphere.model.Person;
import com.csse.healthSphere.repository.PersonRepository;
import com.csse.healthSphere.service.AuthenticationService;
import com.csse.healthSphere.service.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(path = "api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    /**
     * Login
     *
     * @param request: the login request
     * @return the JWT token
     */
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()
                        )
                );

        AuthUser user = (AuthUser) authenticate.getPrincipal();

        return ResponseEntity.ok().body(jwtTokenUtil.generateAccessToken(user));
    }

    /**
     * Register
     *
     * @param request: the login request
     * @return the JWT token
     */
    @PostMapping("set_password")
    public ResponseEntity<String> setPassword(@RequestBody LoginRequest request) {
        // TODO: remove this method
        var person = personRepository.findPersonByEmail(request.getEmail()).orElseThrow();
        person.setPassword(passwordEncoder.encode(request.getPassword()));
        personRepository.save(person);
        return ResponseEntity.ok("changed password");
    }
}
