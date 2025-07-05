package com.acme.nutrimove.activities.infrastructure.external;

import com.acme.nutrimove.activities.infrastructure.external.dto.UserDto;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserServiceClientFallback implements UserServiceClient {

    // Implementación del método en caso de que el servicio no esté disponible
    @Override
    public UserDto getUserById(Long id) {
        log.warn("Fallback: getUserById called for user ID: {}", id);
        return new UserDto(id, "Unknown User", "unknown@example.com");  // Valor por defecto
    }

    // Implementación del método en caso de que el servicio no esté disponible
    @Override
    public Boolean userExists(Long id) {
        log.warn("Fallback: userExists called for user ID: {}", id);
        return true;  // Valor por defecto, el usuario no existe
    }
}
