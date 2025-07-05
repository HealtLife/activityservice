package com.acme.nutrimove.activities.application.internal.commandservices;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import com.acme.nutrimove.activities.domain.model.commands.CreateActivityCommand;
import com.acme.nutrimove.activities.domain.model.commands.DeleteActivityCommand;
import com.acme.nutrimove.activities.domain.services.ActivityCommandService;
import com.acme.nutrimove.activities.infrastructure.persistence.jpa.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ActivityCommandServiceImpl implements ActivityCommandService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityCommandServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        log.info("Creating activity for user ID: {}", command.userId());

        // Verificación de si la actividad ya existe en el repositorio
        if (activityRepository.existsByNameAndDescription(command.name(), command.description())) {
            log.error("Activity with same name and description already exists");
            throw new IllegalArgumentException("Activity with same name and description already exists");
        }

        // Crear la nueva actividad y guardarla
        var activity = new Activity(command);
        var savedActivity = activityRepository.save(activity);

        log.info("Activity created successfully with ID: {}", savedActivity.getId());
        return Optional.of(savedActivity);
    }

    @Override
    public void handle(DeleteActivityCommand command) {
        log.info("Deleting activity with ID: {}", command.activityId());

        activityRepository.deleteById(command.activityId());

        log.info("Activity deleted successfully");
    }
}
