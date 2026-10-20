package com.psrs.web;

import com.psrs.dto.Dtos.CreateRequest;
import com.psrs.dto.Dtos.RequestDto;
import com.psrs.dto.Dtos.UpdateRequest;
import com.psrs.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequestDto> list(Authentication auth) {
        return service.list(auth.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@Valid @RequestBody CreateRequest body, Authentication auth) {
        return service.create(auth.getName(), body);
    }

    /** Admin only - enforced in SecurityConfig. */
    @PatchMapping("/{id}")
    public RequestDto update(@PathVariable Long id, @Valid @RequestBody UpdateRequest body) {
        return service.update(id, body);
    }
}
