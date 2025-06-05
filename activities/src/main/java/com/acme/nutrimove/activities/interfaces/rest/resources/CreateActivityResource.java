package com.acme.nutrimove.activities.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating a new activity")
public record CreateActivityResource(
        @Schema(description = "Activity name", example = "Morning Run", required = true)
        String name,

        @Schema(description = "Activity description", example = "30-minute morning run in the park", required = true)
        String description,

        @Schema(description = "Activity duration in minutes", example = "30", required = true)
        Integer duration,

        @Schema(description = "User ID who will own this activity", example = "123", required = true)
        Long userId
) {
        // Validación manual simple en el constructor
        public CreateActivityResource {
                if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be null or empty");
                }
                if (description == null || description.trim().isEmpty()) {
                        throw new IllegalArgumentException("Description cannot be null or empty");
                }
                if (duration == null || duration <= 0) {
                        throw new IllegalArgumentException("Duration must be greater than 0");
                }
                if (userId == null) {
                        throw new IllegalArgumentException("User ID cannot be null");
                }
        }
}