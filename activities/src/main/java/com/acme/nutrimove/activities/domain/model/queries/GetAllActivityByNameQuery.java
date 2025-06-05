package com.acme.nutrimove.activities.domain.model.queries;

public record GetAllActivityByNameQuery(String name) {
    public GetAllActivityByNameQuery {
        if (name == null)
            throw new NullPointerException("name cannot be null");
    }
}