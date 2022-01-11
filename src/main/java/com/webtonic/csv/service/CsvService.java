package com.webtonic.csv.service;

import com.webtonic.csv.entity.Student;
import com.webtonic.csv.helper.CsvHelper;
import com.webtonic.csv.repository.CourseRepository;
import com.webtonic.csv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Student> saveCsv(MultipartFile file) {
        List<Student> students = new ArrayList<>();
        try {
            students = CsvHelper.csvToStudents(file.getInputStream());
            for (Student student: students) {
                studentRepository.save(student);

            }
            return (List<Student>) studentRepository.findAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
