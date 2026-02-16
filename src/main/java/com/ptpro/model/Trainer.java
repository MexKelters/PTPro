package com.ptpro.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
public class Trainer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(name = "specialization", nullable = true)
    private String Specialization;

    @Column(name = "experience", nullable = true)
    private String experience;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<TrainingSchedule> trainingSchedules = new ArrayList<>();

    @CreationTimestamp
    private LocalDate createAt;
    @UpdateTimestamp
    private LocalDate updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
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

    public List<TrainingSchedule> getTrainingSchedules() {
        return trainingSchedules;
    }

    public void setTrainingSchedules(List<TrainingSchedule> trainingSchedules) {
        this.trainingSchedules = trainingSchedules;
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
}
