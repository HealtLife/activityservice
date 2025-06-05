package com.acme.nutrimove.activities.domain.model.queries;

public record GetActivityByIdQuery(Long id) {
    public GetActivityByIdQuery {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
    }
}