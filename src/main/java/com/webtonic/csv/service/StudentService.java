package com.webtonic.csv.service;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import com.webtonic.csv.repository.CourseRepository;
import com.webtonic.csv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student findStudent(Integer studentNumber){
        return studentRepository.getByStudentNumber(studentNumber);
    }

    public Student findStudentAndCourse(Integer studentNumber, String courseCode){
        return studentRepository.getStudentByStudentNumberAndCourses(studentNumber, courseCode);
    }

    public Iterable<Student> findAllStudents(){
        return studentRepository.getAllStudents();
    }


    public Iterable<Student> findAll(){
        return studentRepository.findAll();
    }

    public void deleteStudent(Integer studentNumber){
        studentRepository.deleteById(studentNumber);
    }

    public Student updateStudent(Student student,Integer studentNumber, String courseCode){
        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findStudentByStudentNumber(studentNumber));
        Student studentData = optionalStudent.get();

        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findCourseByCourseCodeAndStudent(courseCode, optionalStudent.get()));
        Course course = optionalCourse.get();

        Student updateStudent = new Student();
        Course updateCourse = new Course();


        if(student.getFirstName()!=null){
            updateStudent.setFirstName(student.getFirstName());
        }
        if(student.getLastName()!=null){
            updateStudent.setLastName(student.getLastName());
        }


        if(student.getCourses()!=null){
            if(student.getCourses().get(0).getCourseCode()!=null){
               updateCourse.setCourseCode(student.getCourses().get(0).getCourseCode());
            }
            if(student.getCourses().get(0).getCourseDescription()!=null){
                updateCourse.setCourseCode(student.getCourses().get(0).getCourseDescription());
            }

            if(student.getCourses().get(0).getGrade()!=null){
                updateCourse.setCourseCode(student.getCourses().get(0).getGrade());
            }
        }

        List<Course> courses = new ArrayList<>();
        courses.add(updateCourse);

        updateStudent.setCourses(courses);


        return studentRepository.save(updateStudent);

    }
}
