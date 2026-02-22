package com.ptpro.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainer trainer;

    @Column(
            name = "first_name"
    )
    private String firstName;
    @Column(
            name = "last_name"
    )
    private String lastName;

    @Column(
            name = "email",
            unique = true
    )
    private String email;
    @Column(
            name = "password",
            nullable = false
    )

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @CreationTimestamp
    private LocalDate createAt;


    @UpdateTimestamp
    private LocalDate updateAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_bookings",
            joinColumns = @JoinColumn(name = "user_id"), //FK naar deze class
            inverseJoinColumns = @JoinColumn(name = "booking_id") //FK naar andere class
    )
    private List<Booking> bookings;


    @OneToMany(
            mappedBy = "user", fetch = FetchType.LAZY
    )
    private List<TrainingSchedule> trainingSchedules = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
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
}
