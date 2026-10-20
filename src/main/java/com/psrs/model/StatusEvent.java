package com.psrs.model;

import jakarta.persistence.*;
import java.time.Instant;

/** One entry in a request's status history. */
@Embeddable
public class StatusEvent {
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "event_at")
    private Instant at;

    @Column(length = 300)
    private String note;

    protected StatusEvent() {} // required by JPA

    public StatusEvent(Status status, Instant at, String note) {
        this.status = status;
        this.at = at;
        this.note = note;
    }

    public Status getStatus() { return status; }
    public Instant getAt() { return at; }
    public String getNote() { return note; }
}
