package com.psrs.web;

import com.psrs.dto.Dtos.AuthResponse;
import com.psrs.dto.Dtos.LoginRequest;
import com.psrs.dto.Dtos.RegisterRequest;
import com.psrs.dto.Dtos.UserDto;
import com.psrs.model.Role;
import com.psrs.model.User;
import com.psrs.repo.UserRepository;
import com.psrs.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokens;

    public AuthController(UserRepository users, PasswordEncoder encoder,
                          AuthenticationManager authenticationManager, TokenService tokens) {
        this.users = users;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.tokens = tokens;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest body) {
        String email = body.email().trim().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "That email is already registered. Log in instead.");
        }
        User user = users.save(new User(body.name().trim(), email, encoder.encode(body.password()), Role.CITIZEN));
        return respond(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest body) {
        String email = body.email().trim().toLowerCase();
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email, body.password()));
        } catch (AuthenticationException e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.");
        }
        return respond(users.findByEmail(email).orElseThrow());
    }

    @GetMapping("/me")
    public UserDto me(Authentication auth) {
        return UserDto.from(users.findByEmail(auth.getName()).orElseThrow());
    }

    private AuthResponse respond(User user) {
        return new AuthResponse(tokens.issue(user), UserDto.from(user));
    }
}
