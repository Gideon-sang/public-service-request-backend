package com.psrs.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ServiceRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref; // e.g. SR-2026-0001

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(nullable = false) private String category;
    @Column(nullable = false, length = 200) private String location;
    @Column(nullable = false, length = 2000) private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String image = ""; // resized photo as a data URL

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(length = 300)
    private String note = "";

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "request_history", joinColumns = @JoinColumn(name = "request_id"))
    @OrderBy("at ASC")
    private List<StatusEvent> history = new ArrayList<>();

    public Long getId() { return id; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Instant getCreatedAt() { return createdAt; }
    public List<StatusEvent> getHistory() { return history; }
}
