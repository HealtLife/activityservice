package com.acme.nutrimove.activities.infrastructure.external;

import com.acme.nutrimove.activities.infrastructure.external.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Anotación FeignClient para la configuración automática
@FeignClient(name = "userservices", fallback = UserServiceClientFallback.class)
public interface UserServiceClient {

    // Método para obtener usuario por ID
    @GetMapping("/api/v1/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    // Método para verificar si el usuario existe
    @GetMapping("/api/v1/users/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);
}
