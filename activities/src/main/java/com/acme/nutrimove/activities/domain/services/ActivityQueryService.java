package com.acme.nutrimove.activities.domain.services;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import com.acme.nutrimove.activities.domain.model.queries.GetActivityByIdQuery;
import com.acme.nutrimove.activities.domain.model.queries.GetAllActivityByNameQuery;

import java.util.List;
import java.util.Optional;

public interface ActivityQueryService {
    List<Activity> handle(GetAllActivityByNameQuery query);
    List<Activity> getAllActivities();
    Optional<Activity> handle(GetActivityByIdQuery query);
}