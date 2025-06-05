package com.acme.nutrimove.activities.infrastructure.external;

import com.acme.nutrimove.activities.infrastructure.external.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public UserDto getUserById(Long id) {
        log.warn("Fallback: getUserById called for user ID: {}", id);
        return new UserDto(id, "Unknown User", "unknown@example.com");
    }

    @Override
    public Boolean userExists(Long id) {
        log.warn("Fallback: userExists called for user ID: {}", id);
        return false; // Conservative approach - assume user doesn't exist
    }
}