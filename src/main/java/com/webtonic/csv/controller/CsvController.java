package com.webtonic.csv.controller;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import com.webtonic.csv.helper.CsvHelper;
import com.webtonic.csv.service.CourseService;
import com.webtonic.csv.service.CsvService;
import com.webtonic.csv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class CsvController {

    @Autowired
    CsvService csvService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @PostMapping("/upload")
    public ResponseEntity<List<Student>> uploadFile(@RequestParam("file") MultipartFile file) {

        if (CsvHelper.hasCSVFormat(file)) {
            try {
                List<Student> students = csvService.saveCsv(file);

                return new ResponseEntity<>(students, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{studentNumber}/{courseCode}")
    public ResponseEntity<Student> getStudent(@PathVariable(name = "studentNumber") Integer studentNumber, @PathVariable(name = "courseCode") String courseCode) {
        try {

            Student student = studentService.findStudent(studentNumber);

           Optional<Course> course = Optional.ofNullable(courseService.findCourseByCourseCodeAndStudent(courseCode, student));
           Optional<Student> optionalStudent = Optional.ofNullable(studentService.findStudentAndCourse(studentNumber, courseCode));

            return new ResponseEntity<>(optionalStudent.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        try {
            Student studentSave = studentService.saveStudent(student);
            return new ResponseEntity<>(studentSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{studentNumber}/{courseCode}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable(name = "studentNumber") Integer studentNumber, @PathVariable(name = "courseCode") String courseCode) {
        try {
            Student updatedStudent = studentService.updateStudent(student, studentNumber, courseCode);
//            Course updatedCourse = courseService.updateCourse(student,courseCode);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{studentNumber}/{courseCode}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("studentNumber") Integer studentNumber, @PathVariable("courseCode") String courseCode) {
        try {

            Student student = studentService.findStudent(studentNumber);
            Course course = courseService.findCourseByCourseCodeAndStudent(courseCode, student);

            if(hasOneCourse(student)==true){
                studentService.deleteStudent(studentNumber);
            }else {
                courseService.deleteCourse(course.getCourseCode(),student);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Iterable<Student>> getAllStudents() {
        try {
            Iterable<Student> students = studentService.findAllStudents();
            return new ResponseEntity<Iterable<Student>>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/all")
//    public ResponseEntity<Iterable<Course>> getAllCourses() {
//        try {
//            Iterable<Course> courses = courseService.findAllCourses();
//            return new ResponseEntity<Iterable<Course>>(courses, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public boolean hasOneCourse(Student student){
        boolean result = false;

        if(courseService.countCourseForStudent(student)==1){
            result = true;
        }
        return result;
    }

}
