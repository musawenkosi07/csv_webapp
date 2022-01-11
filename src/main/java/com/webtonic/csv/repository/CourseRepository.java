package com.webtonic.csv.repository;

import com.webtonic.csv.entity.Course;
import com.webtonic.csv.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course,Integer> {

    @Query("select count(c) from Course c where c.student = ?1")
    long getAllByStudent(Student student);



    @Query("select c from Course c where c.courseCode = ?1 and c.student = ?2")
    Course findCourseByCourseCodeAndStudent(String courseCode, Student student);

    @Query("select c from Course c, Student s where c.student.studentNumber = s.studentNumber")
    List<Course> getAllCourses();



    @Transactional
    @Modifying
    @Query("delete from Course c where c.courseCode = ?1 and c.student = ?2")
    void deleteCourseByCourseCodeAndStudent(String courseCode, Student student);
}
