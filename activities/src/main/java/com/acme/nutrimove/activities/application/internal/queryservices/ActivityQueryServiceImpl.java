package com.acme.nutrimove.activities.application.internal.queryservices;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import com.acme.nutrimove.activities.domain.model.queries.GetActivityByIdQuery;
import com.acme.nutrimove.activities.domain.model.queries.GetAllActivityByNameQuery;
import com.acme.nutrimove.activities.domain.services.ActivityQueryService;
import com.acme.nutrimove.activities.infrastructure.persistence.jpa.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;

    public ActivityQueryServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> getAllActivities() {
        log.info("Fetching all activities");
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> handle(GetAllActivityByNameQuery query) {
        log.info("Fetching activities by name: {}", query.name());
        return activityRepository.findAllByName(query.name());
    }

    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        log.info("Fetching activity by ID: {}", query.id());
        return activityRepository.findById(query.id());
    }
}