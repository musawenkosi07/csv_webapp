package com.webtonic.csv.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Course> courses;

    public Student(){

    }

    public Student(Integer studentNumber, String firstName, String lastName) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Integer studentNumber, String firstName, String lastName, Course courses) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = Collections.singletonList(courses);
    }


    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonManagedReference
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }



    @Override
    public String toString() {
        return "Student{" +
                "studentNumber=" + studentNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courses=" + courses +
                '}';
    }
}
