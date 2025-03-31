package com.vjezba.Sustav_upisa_studenata.Course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vjezba.Sustav_upisa_studenata.Enrollment.Enrollment;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;
    private String name;
    private String description;
    private int ects;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    public Course() {}

    public Course(String name, String description, int ects) {
        this.name = name;
        this.description = description;
        this.ects = ects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getECTS() {
        return ects;
    }

    public void setECTS(int ects) {
        this.ects = ects;
    }
}
