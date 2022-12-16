package com.backend.StudentManagement.models;

import java.util.*;

public class PaginationAndData {
    private Pagination pagination;
    private List<?> data;

    public PaginationAndData() {
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public List<?> getData() {
        return this.data;
    }

    public static PaginationAndData of(Pagination pagination, List<?> data) {
        PaginationAndData res = new PaginationAndData();
        res.pagination = pagination;
        res.data = data;
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            PaginationAndData that = (PaginationAndData)o;
            return Objects.equals(this.pagination, that.pagination) && Objects.equals(this.data, that.data);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.pagination, this.data});
    }

    public String toString() {
        return "PaginationAndData{pagination=" + this.pagination + ", data=" + this.data + "}";
    }
}
