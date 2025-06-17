package com.acme.nutrimove.activities.interfaces.rest.transform;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import com.acme.nutrimove.activities.interfaces.rest.resources.ActivityResource;

public class ActivityResourceFromEntityAssembler {

    public static ActivityResource toResourceFromEntity(Activity activity) {
        return new ActivityResource(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getDuration(),
                activity.getUserId(),
                activity.getCreatedAt() != null ? activity.getCreatedAt().toString() : null,
                activity.getUpdatedAt() != null ? activity.getUpdatedAt().toString() : null
        );
    }
}