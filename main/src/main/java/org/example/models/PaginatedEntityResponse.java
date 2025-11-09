package org.example.models;

import java.util.List;
import java.util.Objects;

public class PaginatedEntityResponse {

    private List<EntityResponse> entity;
    private Integer page;
    private Integer perPage;

    public PaginatedEntityResponse() {
    }

    public PaginatedEntityResponse(List<EntityResponse> entity, Integer page, Integer perPage) {
        this.entity = entity;
        this.page = page;
        this.perPage = perPage;
    }

    public List<EntityResponse> getEntity() {
        return entity;
    }

    public void setEntity(List<EntityResponse> entity) {
        this.entity = entity;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaginatedEntityResponse that)) return false;
        return Objects.equals(entity, that.entity) && Objects.equals(page, that.page) && Objects.equals(perPage, that.perPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, page, perPage);
    }

    @Override
    public String toString() {
        return "PaginatedEntityResponse{" +
                "entity=" + entity +
                ", page=" + page +
                ", perPage=" + perPage +
                '}';
    }
}
