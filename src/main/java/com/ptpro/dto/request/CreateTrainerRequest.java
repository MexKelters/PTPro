package com.ptpro.dto.request;

import com.ptpro.dto.response.UserResponse;
import com.ptpro.model.Session;

public class CreateTrainerRequest {

    private Long userId;
    private String specialization;
    private String experience;
    private String description;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
