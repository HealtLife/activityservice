package com.acme.nutrimove.activities.interfaces.rest;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import com.acme.nutrimove.activities.domain.model.commands.CreateActivityCommand;
import com.acme.nutrimove.activities.domain.model.commands.DeleteActivityCommand;
import com.acme.nutrimove.activities.domain.model.queries.GetActivityByIdQuery;
import com.acme.nutrimove.activities.domain.model.queries.GetAllActivityByNameQuery;
import com.acme.nutrimove.activities.domain.services.ActivityCommandService;
import com.acme.nutrimove.activities.domain.services.ActivityQueryService;
import com.acme.nutrimove.activities.interfaces.rest.resources.ActivityResource;
import com.acme.nutrimove.activities.interfaces.rest.resources.CreateActivityResource;
import com.acme.nutrimove.activities.interfaces.rest.transform.ActivityResourceFromEntityAssembler;
import com.acme.nutrimove.activities.interfaces.rest.transform.CreateActivityCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/activities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Activities", description = "Operations related to Activities Management")
@Slf4j
public class ActivityController {

    private final ActivityQueryService activityQueryService;
    private final ActivityCommandService activityCommandService;
    private final CreateActivityCommandFromResourceAssembler createActivityCommandFromResourceAssembler;

    public ActivityController(ActivityQueryService activityQueryService,
                              ActivityCommandService activityCommandService,
                              CreateActivityCommandFromResourceAssembler createActivityCommandFromResourceAssembler) {
        this.activityQueryService = activityQueryService;
        this.activityCommandService = activityCommandService;
        this.createActivityCommandFromResourceAssembler = createActivityCommandFromResourceAssembler;
    }

    @Operation(
            summary = "Create a new activity",
            description = "Creates a new activity for a specific user. The user must exist in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Activity created successfully",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityResource.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data or user not found"),
            @ApiResponse(responseCode = "409", description = "Activity with same name and description already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityResource> createActivity(@RequestBody CreateActivityResource resource) {

        log.info("Creating activity: {}", resource.name());

        try {
            CreateActivityCommand command = createActivityCommandFromResourceAssembler.toCommand(resource);
            Optional<Activity> activityOptional = activityCommandService.handle(command);

            return activityOptional
                    .map(activity -> {
                        ActivityResource activityResource = ActivityResourceFromEntityAssembler.toResourceFromEntity(activity);
                        return ResponseEntity.status(HttpStatus.CREATED).body(activityResource);
                    })
                    .orElseGet(() -> ResponseEntity.badRequest().build());

        } catch (IllegalArgumentException e) {
            log.error("Error creating activity: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Get activities by name",
            description = "Retrieve all activities that match the specified name"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Activities found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityResource.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No activities found with the specified name")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ActivityResource>> getAllActivityByName(
            @Parameter(description = "Name to search for", required = true)
            @RequestParam String name) {

        log.info("Searching activities by name: {}", name);

        var getAllActivityByNameQuery = new GetAllActivityByNameQuery(name);
        var activities = activityQueryService.handle(getAllActivityByNameQuery);

        if (activities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(activityResources);
    }

    @Operation(
            summary = "Get activity by ID",
            description = "Retrieve a specific activity by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Activity found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityResource.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResource> getActivityById(
            @Parameter(description = "Activity ID", required = true)
            @PathVariable Long id) {

        log.info("Fetching activity by ID: {}", id);

        Optional<Activity> activity = activityQueryService.handle(new GetActivityByIdQuery(id));
        return activity
                .map(a -> ResponseEntity.ok(ActivityResourceFromEntityAssembler.toResourceFromEntity(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get all activities",
            description = "Retrieve all activities in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Activities retrieved successfully",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityResource.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No activities found")
    })
    @GetMapping
    public ResponseEntity<List<ActivityResource>> getAllActivities() {
        log.info("Fetching all activities");

        var activities = activityQueryService.getAllActivities();

        if (activities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(activityResources);
    }

    @Operation(
            summary = "Delete an activity",
            description = "Delete a specific activity by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(
            @Parameter(description = "Activity ID to delete", required = true)
            @PathVariable Long id) {

        log.info("Deleting activity with ID: {}", id);

        Optional<Activity> activity = activityQueryService.handle(new GetActivityByIdQuery(id));
        if (activity.isPresent()) {
            activityCommandService.handle(new DeleteActivityCommand(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}