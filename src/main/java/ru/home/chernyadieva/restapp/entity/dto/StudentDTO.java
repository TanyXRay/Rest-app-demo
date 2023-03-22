package ru.home.chernyadieva.restapp.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    @NotBlank(message = "Field name shouldn't be empty")
    @Size(min = 2, max = 50, message = "Field lastName should be between 2 and 50 characters")
    @Column(name = "first_name")
    @JsonProperty(value = "firstName")
    private String firstName;

    @NotBlank(message = "Field lastName shouldn't be empty")
    @Column(name = "last_name")
    @JsonProperty(value = "lastName")
    private String lastName;

    @Min(value = 17, message = "Field age should be greater, than 17")
    @Max(value = 115, message = "Field age must be lower, than 115")
    @Column(name = "age")
    @JsonProperty(value = "age")
    private int age;

    @Column(name = "faculty")
    @JsonProperty(value = "faculty")
    private String faculty;

    @Column(name = "university")
    @JsonProperty(value = "university")
    private String university;

    @Override
    public String toString() {
        return "StudentDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", faculty='" + faculty + '\'' +
                ", university='" + university + '\'' +
                '}';
    }
}
