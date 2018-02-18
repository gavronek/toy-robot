package com.gavronek.toyrobot;

import org.springframework.http.HttpStatus;
import java.time.OffsetDateTime;
import java.util.Objects;

public class ApiError {
    private HttpStatus status;
    private OffsetDateTime timestamp;
    private String message;
    private String debugMessage;

    private ApiError() {
        timestamp = OffsetDateTime.now();
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ApiError apiError = (ApiError) o;
        return status == apiError.status &&
                Objects.equals(timestamp, apiError.timestamp) &&
                Objects.equals(message, apiError.message) &&
                Objects.equals(debugMessage, apiError.debugMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, timestamp, message, debugMessage);
    }
}
