package ru.home.chernyadieva.restapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.chernyadieva.restapp.entity.StudentEntity;
import ru.home.chernyadieva.restapp.repository.StudentRepository;
import ru.home.chernyadieva.restapp.util.StudentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Класс взаимодействия с БД по запросам с помощью репозитория JPA
 */
@Service
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Метод поиска всех студентов из БД
     *
     * @return
     */
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    public StudentEntity findById(int id) {
        Optional<StudentEntity> foundStudent = studentRepository.findById(id);

        return foundStudent.orElseThrow(StudentNotFoundException::new);
    }

    @Transactional
    public void save(StudentEntity studentEntity) {
        studentEntity.setTimeCreate(LocalDateTime.now());

        studentRepository.save(studentEntity);
    }

    @Transactional
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public void update(StudentEntity updatedStudentEntity, int id) {
        updatedStudentEntity.setId(id);
        updatedStudentEntity.setTimeCreate(LocalDateTime.now());

        studentRepository.save(updatedStudentEntity);
    }
}
