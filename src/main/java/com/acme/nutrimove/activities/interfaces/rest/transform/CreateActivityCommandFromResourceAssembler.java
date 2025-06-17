package com.acme.nutrimove.activities.interfaces.rest.transform;

import com.acme.nutrimove.activities.domain.model.commands.CreateActivityCommand;
import com.acme.nutrimove.activities.interfaces.rest.resources.CreateActivityResource;
import org.springframework.stereotype.Component;

@Component
public class CreateActivityCommandFromResourceAssembler {

    public CreateActivityCommand toCommand(CreateActivityResource resource) {
        return new CreateActivityCommand(
                resource.name(),
                resource.description(),
                resource.duration(),
                resource.userId()
        );
    }
}