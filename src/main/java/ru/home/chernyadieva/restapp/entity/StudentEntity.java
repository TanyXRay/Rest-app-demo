package ru.home.chernyadieva.restapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Field firstName shouldn't be empty")
    @Size(min = 2, max = 50, message = "Field firstName should be between 2 and 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Field lastName shouldn't be empty")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 17, message = "Field age should be greater, than 17")
    @Max(value = 115, message = "Field age must be lower, than 115")
    @Column(name = "age")
    private int age;

    @Column(name = "university")
    private String university;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "time_create")
    private LocalDateTime timeCreate;

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", timeCreate=" + timeCreate +
                '}';
    }
}
