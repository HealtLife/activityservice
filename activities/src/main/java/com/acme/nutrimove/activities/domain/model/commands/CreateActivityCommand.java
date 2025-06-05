package com.acme.nutrimove.activities.domain.model.commands;

public record CreateActivityCommand(
        String name,
        String description,
        Integer duration,
        Long userId
) {
    public CreateActivityCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name cannot be null or empty");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description cannot be null or empty");
        if (duration == null || duration <= 0)
            throw new IllegalArgumentException("duration must be greater than 0");
        if (userId == null)
            throw new IllegalArgumentException("userId cannot be null");
    }
}