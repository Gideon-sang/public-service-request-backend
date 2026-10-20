package com.psrs.model;

import com.psrs.web.ApiException;
import org.springframework.http.HttpStatus;

/** Request lifecycle: Pending -> In Progress -> Resolved. */
public enum Status {
    PENDING("Pending"), IN_PROGRESS("In Progress"), RESOLVED("Resolved");

    private final String label;

    Status(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static Status fromLabel(String label) {
        for (Status s : values()) {
            if (s.label.equals(label)) return s;
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "Choose a valid status.");
    }
}
