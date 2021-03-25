package com.kodilla.project.repository;

import com.kodilla.project.domain.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventsRepository extends CrudRepository<EventEntity, String> {
    @Override
    List<EventEntity> findAll();

    @Override
    EventEntity save(EventEntity event);

    @Override
    Optional<EventEntity> findById(String id);

    @Override
    void deleteById(String id);

    @Override
    long count();
}
