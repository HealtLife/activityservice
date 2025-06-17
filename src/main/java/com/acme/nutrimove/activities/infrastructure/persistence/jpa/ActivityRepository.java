package com.acme.nutrimove.activities.infrastructure.persistence.jpa;

import com.acme.nutrimove.activities.domain.model.aggregates.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for Activity entity
 * @summary
 * This interface extends JpaRepository, which provides CRUD operations for Activity entity.
 * It extends JpaRepository<Activity, Long>, where Activity is the entity and Long is the type of the primary key.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByName(String name);
    boolean existsByNameAndDescription(String name, String description);
    List<Activity> findAllByUserId(Long userId);
}