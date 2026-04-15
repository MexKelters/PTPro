package com.ptpro.dto.request;

import jakarta.validation.constraints.NotNull;

public class UpdateBookingRequest {

    @NotNull(message = "Status mag niet leeg zijn")
    private Boolean status;

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}