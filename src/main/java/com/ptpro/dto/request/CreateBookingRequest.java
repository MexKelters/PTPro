package com.ptpro.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateBookingRequest {

    @NotNull(message = "Sessie ID mag niet leeg zijn")
    private Long sessionId;

    @NotNull(message = "User ID mag niet leeg zijn")
    private Long userId;

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}