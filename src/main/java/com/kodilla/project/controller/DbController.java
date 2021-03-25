package com.kodilla.project.controller;

import com.kodilla.project.domain.*;
import com.kodilla.project.mapper.CalendarMapper;
import com.kodilla.project.mapper.EventMapper;
import com.kodilla.project.mapper.LogMapper;
import com.kodilla.project.service.db.DbService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/googlecalendar")
public class DbController {
    private final DbService dbService;
    private final CalendarMapper calendarMapper;
    private final EventMapper eventMapper;
    private final LogMapper logMapper;

    public DbController(DbService dbService, CalendarMapper calendarMapper, EventMapper eventMapper, LogMapper logMapper) {
        this.dbService = dbService;
        this.calendarMapper = calendarMapper;
        this.eventMapper = eventMapper;
        this.logMapper = logMapper;
    }

    @PostMapping(value = "/db/calendars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CalendarDto> saveAllCalendars(@RequestBody List<CalendarDto> calendars) {
        List<CalendarEntity> entities = new ArrayList<>(
                dbService.saveAllCalendars(calendarMapper.mapDtoListToEntityList(calendars)));
        return calendarMapper.mapEntityListToDtoList(entities);
    }

    @PostMapping(value = "/db/calendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CalendarDto saveCalendar(@RequestBody CalendarDto calendarDto) {
        CalendarEntity createdCalendar = dbService.createCalendar(calendarMapper.mapDtoToEntity(calendarDto));
        return calendarMapper.mapEntityToDto(createdCalendar);
    }

    @DeleteMapping("/db/calendar/id={calendarId}")
    public void deleteCalendar(@PathVariable String calendarId) {
        dbService.deleteCalendar(calendarId);
    }

    @PutMapping("/db/calendar")
    public CalendarDto updateCalendar(@RequestBody CalendarDto calendarDto) {
        CalendarEntity updatedCalendar = dbService.updateCalendar(calendarMapper.mapDtoToEntity(calendarDto));
        return calendarMapper.mapEntityToDto(updatedCalendar);
    }

    @PostMapping(value = "/db/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> saveAllEvents(@RequestBody List<EventDto> events) {
        List<EventEntity> entities = new ArrayList<>(
                dbService.saveAllEvents(eventMapper.mapDtoListToEntityList(events)));
        return eventMapper.mapEntityListToDtoList(entities);
    }

    @PostMapping(value = "/db/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDto saveEvent(@RequestBody EventDto eventDto) {
        EventEntity createdEvent = dbService.createEvent(eventMapper.mapDtoToEntity(eventDto));
        return eventMapper.mapEntityToDto(createdEvent);
    }

    @DeleteMapping("/db/event/id={eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        dbService.deleteEvent(eventId);
    }

    @PutMapping("/db/event")
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        EventEntity updatedEvent = dbService.updateEvent(eventMapper.mapDtoToEntity(eventDto));
        return eventMapper.mapEntityToDto(updatedEvent);
    }

    @GetMapping(value = "/db/logs")
    public List<LogDto> getAllLogs() {
        return logMapper.mapEntitiesToDtoList(dbService.getAllLogs());
    }

    @PostMapping(value = "/db/logs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LogDto saveLog(@RequestBody LogDto logDto) {
        LogEntity createdLog = dbService.createLog(logMapper.mapDtoToEntity(logDto));
        return logMapper.mapEntityToDto(createdLog);
    }
}
