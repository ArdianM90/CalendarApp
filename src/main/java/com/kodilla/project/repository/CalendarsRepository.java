package com.kodilla.project.repository;

import com.kodilla.project.domain.CalendarEntity;
import com.kodilla.project.domain.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarsRepository extends CrudRepository<CalendarEntity, String> {
    @Override
    List<CalendarEntity> findAll();

    @Override
    CalendarEntity save(CalendarEntity calendar);

//    @Override
//    Optional<CalendarEntity> findById(String id);
//
//    @Override
//    void deleteById(String id);
//
//    @Override
//    long count();
}
