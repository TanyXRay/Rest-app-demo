package ru.home.chernyadieva.restapp.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.home.chernyadieva.restapp.entity.dto.StudentDTO;
import ru.home.chernyadieva.restapp.entity.StudentEntity;
import ru.home.chernyadieva.restapp.service.StudentService;
import ru.home.chernyadieva.restapp.util.exception.StudentErrorResponse;
import ru.home.chernyadieva.restapp.util.exception.StudentNotCreatedException;
import ru.home.chernyadieva.restapp.util.exception.StudentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StudentController {
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    //GET
    @RequestMapping("/students")
    public ResponseEntity<List<StudentDTO>> getStudent() {
        List<StudentDTO> studentDTOList = studentService.findAll().stream()
                .map(this::convertToStudentDTO)
                .toList();

        if (studentDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    //GET (id)
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentFromId(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(convertToStudentDTO(studentService.findById(id)), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/create")
    public ResponseEntity<HttpStatus> createStudent(@RequestBody @Valid StudentDTO studentDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            throw new StudentNotCreatedException(error);
        }

        studentService.save(convertToStudentEntity(studentDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") int id) {
        studentService.findById(id);
        studentService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //PATCH
    @PatchMapping("/create/{id}")
    public ResponseEntity<HttpStatus> updateStudent(@RequestBody @Valid StudentDTO studentDTO,
                                                    BindingResult bindingResult,
                                                    @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            throw new StudentNotFoundException();
        }

        studentService.update(convertToStudentEntity(studentDTO), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * DTO конвертируем в сущность
     *
     * @param studentDTO
     * @return
     */
    private StudentEntity convertToStudentEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, StudentEntity.class);
    }

    /**
     * Сущность конвертируем в DTO
     *
     * @param studentEntity
     * @return
     */
    private StudentDTO convertToStudentDTO(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentDTO.class);
    }

    /**
     * Метод возврата ответа ошибки при несуществующем человеке с таким id
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    private ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException e) {
        StudentErrorResponse response = new StudentErrorResponse("StudentEntity with this id wasn't found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод возврата ответа ошибки при невозможности создать человека
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    private ResponseEntity<StudentErrorResponse> handleException(StudentNotCreatedException e) {
        StudentErrorResponse response = new StudentErrorResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
