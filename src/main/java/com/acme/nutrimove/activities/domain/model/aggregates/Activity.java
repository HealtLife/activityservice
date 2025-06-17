package com.acme.nutrimove.activities.domain.model.aggregates;

import com.acme.nutrimove.activities.domain.model.commands.CreateActivityCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private Integer duration;

    @Setter
    @Column(name = "user_id", nullable = false)
    private Long userId; // Changed from User entity to Long userId

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Activity(CreateActivityCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.duration = command.duration();
        this.userId = command.userId();
    }

    // Constructor vacío requerido por JPA
    public Activity() {}
}