package com.psrs.service;

import com.psrs.dto.Dtos.CreateRequest;
import com.psrs.dto.Dtos.RequestDto;
import com.psrs.dto.Dtos.UpdateRequest;
import com.psrs.model.*;
import com.psrs.repo.RequestRepository;
import com.psrs.repo.UserRepository;
import com.psrs.web.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.Year;
import java.util.List;

@Service
@Transactional
public class RequestService {
    private static final List<String> CATEGORIES =
            List.of("Streetlight", "Water issue", "Garbage collection", "Road damage", "Drainage / sewer", "Other");
    private static final List<String> IMAGE_PREFIXES =
            List.of("data:image/jpeg;base64,", "data:image/png;base64,", "data:image/webp;base64,");
    private static final int MAX_IMAGE_CHARS = 1_500_000;

    private final RequestRepository requests;
    private final UserRepository users;

    public RequestService(RequestRepository requests, UserRepository users) {
        this.requests = requests;
        this.users = users;
    }

    /** Citizens get their own requests; admins get everyone's. */
    @Transactional(readOnly = true)
    public List<RequestDto> list(String email) {
        User user = findUser(email);
        List<ServiceRequest> rows = user.getRole() == Role.ADMIN
                ? requests.findAllByOrderByIdDesc()
                : requests.findByUserOrderByIdDesc(user);
        return rows.stream().map(RequestDto::from).toList();
    }

    public RequestDto create(String email, CreateRequest body) {
        if (!CATEGORIES.contains(body.category())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Choose a valid category.");
        }
        String image = body.image() == null ? "" : body.image();
        if (!image.isEmpty() && (IMAGE_PREFIXES.stream().noneMatch(image::startsWith) || image.length() > MAX_IMAGE_CHARS)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Photo must be a JPEG, PNG or WebP image under 1 MB.");
        }
        ServiceRequest r = new ServiceRequest();
        r.setUser(findUser(email));
        r.setCategory(body.category());
        r.setLocation(body.location().trim());
        r.setDescription(body.description().trim());
        r.setImage(image);
        r.getHistory().add(new StatusEvent(Status.PENDING, Instant.now(), "Request submitted"));
        requests.save(r);
        r.setRef(String.format("SR-%d-%04d", Year.now().getValue(), r.getId()));
        return RequestDto.from(r);
    }

    /** Admin action: change status and leave a note for the citizen. */
    public RequestDto update(Long id, UpdateRequest body) {
        ServiceRequest r = requests.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Request not found."));
        Status status = Status.fromLabel(body.status());
        String note = body.note() == null ? "" : body.note().trim();
        if (r.getStatus() != status || !r.getNote().equals(note)) {
            r.getHistory().add(new StatusEvent(status, Instant.now(), note.isEmpty() ? "Status updated" : note));
        }
        r.setStatus(status);
        r.setNote(note);
        return RequestDto.from(r);
    }

    private User findUser(String email) {
        return users.findByEmail(email).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Please log in to continue."));
    }
}
