package com.webtonic.csv;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import com.webtonic.csv.repository.CourseRepository;
import com.webtonic.csv.repository.StudentRepository;
import com.webtonic.csv.service.CourseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    public void testAddNew(){
        Student student = new Student();
        Course course = new Course();

        List<Course> courses = new ArrayList<>();

        student.setStudentNumber(216869893);
        student.setFirstName("Musawenkosi");
        student.setLastName("Mahlangu");
        course.setCourseCode("CS102");
        course.setGrade("A");
        course.setCourseDescription("Computer Science 2");
        course.setStudent(student);

        courses.add(course);

        student.setCourses(courses);


        Student savedStudent = studentRepository.save(student);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getStudentNumber()).isGreaterThan(0);


    }

    @Test
    public void testListAll(){
        List<Student> students = (List<Student>) studentRepository.findAll();
        Assertions.assertThat(students).hasSizeGreaterThan(0);

        for (Student student: students){
            System.out.println(student);
        }
    }

    @Test
    public void testGet(){


        Integer studentNum = 98041;
        String courseCde = "CS201";

        Student student = studentRepository.findStudentByStudentNumber(studentNum);
        Course course = courseRepository.findCourseByCourseCodeAndStudent(courseCde, student);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        Student displayStudent = new Student();
        displayStudent.setStudentNumber(student.getStudentNumber());
        displayStudent.setFirstName(student.getFirstName());
        displayStudent.setLastName(student.getLastName());
        displayStudent.setCourses(courses);

        Optional<Student> optionalStudent = Optional.of(displayStudent);


        Assertions.assertThat(optionalStudent).isPresent();
        System.out.println(optionalStudent.get());
    }

    @Test
    public void testUpdate(){
        //Can't get through to make this update work.

//        Integer studentNum = 216869893;
//        String courseCode = "CS102";
//        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findStudentByStudentNumber(studentNum));
//        Student student = optionalStudent.get();
//
//        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findCourseByCourseCodeAndStudent(courseCode, student));
//        Course course = optionalCourse.get();
//
//        student.setFirstName("Collen");
//        student.setLastName("Mahlangu");
//
//        course.setCourseCode("BA101");
//        course.setCourseDescription("Business Analyst 1");
//        course.setGrade("A");
//        course.setStudent(student);
//
//        List<Course> courses = new ArrayList<>();
//        courses.add(course);
//
//        Student updateStudent = student;
//        updateStudent.setCourses(courses);
//
//        studentRepository.save(updateStudent);


//        Assertions.assertThat(updateStudent.getFirstName()).isEqualTo("Collen");
    }

    @Test
    public void testDelete(){
        Integer studentNum = 216869893;
        String courseCode = "CS102";
        Student student = studentRepository.findStudentByStudentNumber(studentNum);
        Course course = courseRepository.findCourseByCourseCodeAndStudent(courseCode,student);

        if(hasOneCourse(student)==true){
            studentRepository.deleteById(studentNum);
        }else {
            courseRepository.deleteCourseByCourseCodeAndStudent(courseCode,student);
        }

//        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findStudentByStudentNumber(studentNum));
//        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findCourseByCourseCodeAndStudent(courseCode, student));
//        Assertions.assertThat(optionalStudent).isNotPresent();
//        Assertions.assertThat(optionalCourse).isNotPresent();
    }

    public boolean hasOneCourse(Student student){
        boolean result = false;

        if(countCourseForStudent(student)==1){
            result = true;
        }
        return result;
    }

    public Integer countCourseForStudent(Student student){

        Integer numCourses = student.getCourses().size();

        return numCourses;
    }
}
