package com.kodilla.project.repository;

import com.kodilla.project.domain.LogEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LogsRepository extends CrudRepository<LogEntity, String> {
    @Override
    List<LogEntity> findAll();

    @Override
    LogEntity save(LogEntity log);

    @Override
    Optional<LogEntity> findById(String id);

    @Override
    long count();
}
