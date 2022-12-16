package com.backend.StudentManagement.models;

import java.util.*;

public class Pagination {
    private Integer page;
    private Integer ofPage;

    public Pagination() {
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getOfPage() {
        return this.ofPage;
    }



    public static Pagination of(Integer page, Integer ofPage) {
        Pagination res = new Pagination();
        res.page = page;
        res.ofPage = ofPage;
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Pagination that = (Pagination)o;
            return Objects.equals(this.page, that.page) && Objects.equals(this.ofPage, that.ofPage);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.page, this.ofPage});
    }

    public String toString() {
        return "Pagination{page=" + this.page + ", ofPage=" + this.ofPage + "}";
    }
}
