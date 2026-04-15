
package com.ptpro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRoleRequest {

    @NotNull(message = "ID mag niet leeg zijn")
    private Long id;

    @NotBlank(message = "Naam mag niet leeg zijn")
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}