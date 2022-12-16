package com.backend.StudentManagement.models;

public enum eSortOrder {
    Id("id"),
    CreatedAt("createdAt"),
    FullName("fullName"),
    BirthDate("birthDate"),
    SatScore("satScore"),
    GraduationScore("graduationScore");

    public String getValue() {
        return value;
    }

    private final String value;

    eSortOrder(String value) {
        this.value = value;
    }
}
