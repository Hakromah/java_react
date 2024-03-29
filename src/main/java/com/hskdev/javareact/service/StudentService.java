package com.hskdev.javareact.service;

import com.hskdev.javareact.exception.StudentAlreadyExistsException;
import com.hskdev.javareact.exception.StudentNotFoundException;
import com.hskdev.javareact.model.Student;
import com.hskdev.javareact.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        if (studentAlreadyExists(student.getEmail())) {
            throw new StudentAlreadyExistsException(student.getEmail() + " Already Exists!");
        }
        return studentRepository.save(student);
    }


    @Override
    public Student updateStudent(Student student, Long id) {
        return studentRepository.findById(id).map(st -> {
            st.setFirstName(student.getFirstName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());
            st.setDepartment(student.getDepartment());
            return studentRepository.save(st);
        }).orElseThrow(() -> new StudentNotFoundException("Sorry, this student could not be found"));
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Sorry, there is no student with this Id: " + id));
    }

    // delete student
    @Override
    public void deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            // Student found, proceed with deletion
            studentRepository.deleteById(id);
        } else {
            // Student not found, throw exception
            throw new StudentNotFoundException("Sorry, student not found to be deleted");
        }
    }


    // Exception methods
    private boolean studentAlreadyExists(String email) {
        if (studentRepository.findByEmail(email).isPresent()) {
            throw new StudentAlreadyExistsException("Student with email " + email + " already exists");
        }
        return false;
    }
}

