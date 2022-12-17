package com.backend.StudentManagement.models;

import com.backend.StudentManagement.Services.*;
import com.backend.StudentManagement.util.*;
import com.fasterxml.jackson.annotation.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;

import javax.persistence.*;
import java.util.*;

@Entity
@SqlResultSetMapping(name = "StudentOut")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentOut {

    @Id
    private Long id;

    private Date createdat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdat")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdat);
    }

    private String fullname;
    private Date birthdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthdate")
    public LocalDateTime calcBirthDate() {
        return Dates.atLocalTime(birthdate);
    }

    private Integer satscore;
    private Double graduationscore;

    private String phone;
    private String email;
    private String profilepicture;

    private Double avgscore;

    public Long getId() {
        return id;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public String getFullname() {
        return fullname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Integer getSatscore() {
        return satscore;
    }

    public Double getGraduationscore() {
        return graduationscore;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public Double getAvgscore() {
        return avgscore;
    }

    public static StudentOut of(Student student) {
        StudentOut res = new StudentOut();

        res.id = student.getId();
        res.createdat = student.getCreatedAt();
        res.fullname = student.getFullname();
        res.birthdate = student.getBirthDate();
        res.satscore = student.getSatScore();
        res.graduationscore = student.getGraduationScore();
        res.phone = student.getPhone();
        res.email = student.getEmail();
        res.avgscore = student.getStudentGrades().stream()
                .mapToDouble(StudentGrade::getCourseScore)
                .average().orElse(0);

        return res;
    }

    public static StudentOut of(Student student, AwsService awsService) {
        StudentOut responseStudent = new StudentOut();
        responseStudent.id = student.getId();
        responseStudent.createdat = student.getCreatedAt();
        responseStudent.fullname = student.getFullname();
        responseStudent.birthdate = student.getBirthDate();
        responseStudent.satscore = student.getSatScore();
        responseStudent.graduationscore = student.getGraduationScore();
        responseStudent.phone = student.getPhone();
        responseStudent.avgscore = student.getStudentGrades().stream()
                .mapToDouble(StudentGrade::getCourseScore)
                .average().orElse(0);
        responseStudent.email = student.getEmail();
        responseStudent.profilepicture = awsService.generateLink(student.getProfilePicture());

        return responseStudent;
    }


}
