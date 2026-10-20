package com.psrs.dto;

import com.psrs.model.ServiceRequest;
import com.psrs.model.StatusEvent;
import com.psrs.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

/** Request and response shapes for the JSON API (kept separate from the database entities). */
public final class Dtos {
    private Dtos() {}

    public record RegisterRequest(
            @NotBlank(message = "Name is required.") @Size(min = 2, max = 80, message = "Name must be 2-80 characters.") String name,
            @NotBlank(message = "Email is required.") @Email(message = "Enter a valid email address.") String email,
            @NotBlank(message = "Password is required.") @Size(min = 6, max = 100, message = "Password must be at least 6 characters.") String password) {}

    public record LoginRequest(
            @NotBlank(message = "Email is required.") String email,
            @NotBlank(message = "Password is required.") String password) {}

    public record CreateRequest(
            @NotBlank(message = "Choose a category.") String category,
            @NotBlank(message = "Location is required.") @Size(min = 3, max = 200, message = "Location must be 3-200 characters.") String location,
            @NotBlank(message = "Description is required.") @Size(min = 10, max = 2000, message = "Description must be 10-2000 characters.") String description,
            String image) {}

    public record UpdateRequest(
            @NotBlank(message = "Choose a status.") String status,
            @Size(max = 300, message = "Note must be 300 characters or fewer.") String note) {}

    public record UserDto(String id, String name, String email, String role) {
        public static UserDto from(User u) {
            return new UserDto(String.valueOf(u.getId()), u.getName(), u.getEmail(), u.getRole().name().toLowerCase());
        }
    }

    public record AuthResponse(String token, UserDto user) {}

    public record EventDto(String status, Instant at, String note) {
        static EventDto from(StatusEvent e) { return new EventDto(e.getStatus().getLabel(), e.getAt(), e.getNote()); }
    }

    public record RequestDto(String id, String ref, String userId, String userName, String category, String location,
                             String description, String image, String status, String note, Instant createdAt,
                             List<EventDto> history) {
        public static RequestDto from(ServiceRequest r) {
            return new RequestDto(String.valueOf(r.getId()), r.getRef(), String.valueOf(r.getUser().getId()),
                    r.getUser().getName(), r.getCategory(), r.getLocation(), r.getDescription(), r.getImage(),
                    r.getStatus().getLabel(), r.getNote(), r.getCreatedAt(),
                    r.getHistory().stream().map(EventDto::from).toList());
        }
    }
}
