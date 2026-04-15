package com.ptpro.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateRoleRequest {

    @NotBlank(message = "Naam mag niet leeg zijn")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}