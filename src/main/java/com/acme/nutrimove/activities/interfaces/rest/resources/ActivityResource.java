package com.acme.nutrimove.activities.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Activity resource representation")
public record ActivityResource(
        @Schema(description = "Activity unique identifier", example = "1")
        Long id,

        @Schema(description = "Activity name", example = "Morning Run")
        String name,

        @Schema(description = "Activity description", example = "30-minute morning run in the park")
        String description,

        @Schema(description = "Activity duration in minutes", example = "30")
        Integer duration,

        @Schema(description = "User ID who owns this activity", example = "123")
        Long userId,

        @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
        String createdAt,

        @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00")
        String updatedAt
) {}