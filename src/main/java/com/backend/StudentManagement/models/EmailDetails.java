package com.backend.StudentManagement.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {
 
    // Class data members
    @JsonIgnore  private String recipient;

    private String msgBody;
    private String subject;


}
