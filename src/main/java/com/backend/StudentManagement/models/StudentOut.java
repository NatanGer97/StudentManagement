package com.backend.StudentManagement.models;

import com.backend.StudentManagement.util.*;
import com.fasterxml.jackson.annotation.*;
import org.joda.time.*;

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
    private String profilepicture;

    public Integer getSatScore() {
        return satscore;
    }

    public Double getGraduationScore() {
        return graduationscore;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilePicture() {
        return profilepicture;
    }


}
