package com.ptpro.dto.response;

import com.ptpro.model.Booking;
import com.ptpro.model.Role;
import com.ptpro.model.Trainer;
import com.ptpro.model.TrainingSchedule;

import java.util.List;

public class UserResponse {

    private Trainer trainer;
    private String firstNAme;
    private String lastName;
    private String email;
    private List<Booking> bookings;
    private List<TrainingSchedule> trainingSchedules;
    private Role role;

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getFirstNAme() {
        return firstNAme;
    }

    public void setFirstNAme(String firstNAme) {
        this.firstNAme = firstNAme;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<TrainingSchedule> getTrainingSchedules() {
        return trainingSchedules;
    }

    public void setTrainingSchedules(List<TrainingSchedule> trainingSchedules) {
        this.trainingSchedules = trainingSchedules;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
