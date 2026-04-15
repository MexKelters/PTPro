package com.ptpro.dto.response;

import java.time.LocalDateTime;

public class BookingResponse {

    private Long id;
    private Long sessionId;
    private Boolean status;
    private LocalDateTime date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}