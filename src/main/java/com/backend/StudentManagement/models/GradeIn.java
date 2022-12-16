package com.backend.StudentManagement.models;

import org.hibernate.validator.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

public class GradeIn {


    @Length(max = 60)
    private String courseName;

    @Min(10)
    @Max(100)
    private Integer courseScore;

    public StudentGrade toGrade(Student student) {
        return StudentGrade.StudentGradeBuilder
                .aStudentGrade().student(student).courseName(courseName).courseScore(courseScore).build();
    }

    public void updateStudentGrade(StudentGrade studentGrade) {
        studentGrade.setCourseName(courseName);
        studentGrade.setCourseScore(courseScore);
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCourseScore() {
        return courseScore;
    }
}