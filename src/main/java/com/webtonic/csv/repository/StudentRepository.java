package com.webtonic.csv.repository;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student getByStudentNumber(Integer studentNumber);

    Student findStudentByStudentNumber(Integer studentNumber);


    @Query("select s from Student s,Course c where c.student.studentNumber = s.studentNumber")
    List<Student> getAllStudents();

    @Query("select s from Student s,Course c where s.studentNumber = c.student.studentNumber and s.studentNumber = ?1 and c.courseCode = ?2")
    Student getStudentByStudentNumberAndCourses(Integer studentNumber, String courseCode);


}
