package com.webtonic.csv;

import com.webtonic.csv.entity.Student;
import com.webtonic.csv.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CourseRepositoryTests {

//    @Autowired
//    private CourseRepository courseRepository;
//
//    public void numCourseForStudent(StudentAdd student){
//        courseRepository.getAllByStudent(student);
//    }
}
