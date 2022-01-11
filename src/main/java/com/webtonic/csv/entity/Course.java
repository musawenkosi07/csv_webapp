package com.webtonic.csv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "grade")
    private String grade;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_number")
    private Student student;

    public Course(){

    }

    public Course(String courseCode, String courseDescription, String grade, Student student) {
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.grade = grade;
        this.student = student;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @JsonBackReference
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", grade='" + grade +
                '}';
    }
}
