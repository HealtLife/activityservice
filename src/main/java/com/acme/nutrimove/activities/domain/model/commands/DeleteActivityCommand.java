package com.acme.nutrimove.activities.domain.model.commands;

public record DeleteActivityCommand(Long activityId) {
    public DeleteActivityCommand {
        if (activityId == null)
            throw new IllegalArgumentException("activityId cannot be null");
    }
}