package com.backend.StudentManagement.models;

import java.util.*;

public class MessageAndEmails {
    String message;
    List<String> emails;

    public String getMessage() {
        return message;
    }

    public List<String> getEmails() {
        return emails;
    }

    public MessageAndEmails(String message, List<String> emails) {
        this.message = message;
        this.emails = emails;
    }
}