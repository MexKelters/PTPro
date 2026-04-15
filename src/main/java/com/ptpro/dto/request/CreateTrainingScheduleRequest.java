package com.ptpro.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateTrainingScheduleRequest {

    @NotNull(message = "Trainer ID mag niet leeg zijn")
    private Long trainerId;

    @NotNull(message = "User ID mag niet leeg zijn")
    private Long userId;

    private Long sessionId;

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
}