package com.webtonic.csv.service;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import com.webtonic.csv.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course){
        return courseRepository.save(course);
    }

    public Course findCourseByCourseCodeAndStudent(String courseCode, Student student){
        return courseRepository.findCourseByCourseCodeAndStudent(courseCode, student);
    }

    public Iterable<Course> findAllCourses(){
        return courseRepository.getAllCourses();
    }


    public Course updateCourse(Student student, String courseCode){

        Optional<Course> optionalCourse = Optional.ofNullable(courseRepository.findCourseByCourseCodeAndStudent(courseCode, student));

        Course updateCourse = optionalCourse.get();

//        if(student.getCourses()!=null){
//            if(student.getCourses().get(0).getCourseCode()!=null){
//                updateCourse.setCourseCode(student.getCourses().get(0).getCourseCode());
//            }
//            if(student.getCourses().get(0).getCourseDescription()!=null){
//                updateCourse.setCourseCode(student.getCourses().get(0).getCourseDescription());
//            }
//
//            if(student.getCourses().get(0).getGrade()!=null){
//                updateCourse.setCourseCode(student.getCourses().get(0).getGrade());
//            }
//        }



        return courseRepository.save(updateCourse);
    }

    public void deleteCourse(String courseCode, Student student) {
        courseRepository.deleteCourseByCourseCodeAndStudent(courseCode,student);
    }

    public Integer countCourseForStudent(Student student){

        Integer numCourses = student.getCourses().size();

        return numCourses;
    }
}
