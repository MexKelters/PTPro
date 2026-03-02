package com.ptpro.dto.response;

import com.ptpro.model.Session;
import com.ptpro.model.User;

import java.util.List;

public class TrainerResponse {

    private UserResponse user;
    private String specialization;
    private String experience;
    private String description;
    private Session session;
//    private List<TrainingScheduleResponse> trainingSchedule;


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

//    public List<TrainingScheduleResponse> getTrainingSchedule() {
//        return trainingSchedule;
//    }
//
//    public void setTrainingSchedule(List<TrainingScheduleResponse> trainingSchedule) {
//        this.trainingSchedule = trainingSchedule;
//    }





}
