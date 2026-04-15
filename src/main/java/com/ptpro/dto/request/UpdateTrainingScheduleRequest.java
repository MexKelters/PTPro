package com.ptpro.dto.request;

public class UpdateTrainingScheduleRequest {

    private Long userId;
    private Long sessionId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
}