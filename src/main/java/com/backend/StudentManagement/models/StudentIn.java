package com.backend.StudentManagement.models;

import com.backend.StudentManagement.util.*;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.*;
import org.joda.time.*;

import javax.validation.constraints.*;
import java.io.*;

import static com.backend.StudentManagement.models.Student.StudentBuilder.aStudent;

public class StudentIn {

    @Length(max = 60)
    private String fullname;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Min(100)
    @Max(800)
    private Integer satScore;

    @Min(30)
    @Max(110)
    private Double graduationScore;

    @Length(max = 20)
    private String phone;


    public Student toStudent() {
        return aStudent().birthDate(Dates.atUtc(birthDate)).fullname(fullname)
                .createdAt(Dates.nowUTC())
                .satScore(satScore).graduationScore(graduationScore)
                .phone(phone)
                .build();
    }

    public void updateStudent(Student student) {
        student.setBirthDate(Dates.atUtc(birthDate));
        student.setFullname(fullname);
        student.setSatScore(satScore);
        student.setGraduationScore(graduationScore);
        student.setPhone(phone);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSatScore() {
        return satScore;
    }

    public void setSatScore(Integer satScore) {
        this.satScore = satScore;
    }

    public Double getGraduationScore() {
        return graduationScore;
    }

    public void setGraduationScore(Double graduationScore) {
        this.graduationScore = graduationScore;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}