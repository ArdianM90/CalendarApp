package com.kodilla.project.controller;

import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.EventDto;
import com.kodilla.project.mapper.CalendarMapper;
import com.kodilla.project.mapper.EventMapper;
import com.kodilla.project.service.google.CalendarsService;
import com.kodilla.project.service.google.EventsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/googlecalendar")
public class CalendarController {
    private final CalendarsService calendarService;
    private final EventsService eventService;
    private final CalendarMapper calendarMapper;
    private final EventMapper eventMapper;

    public CalendarController(@Qualifier("calendarsService") CalendarsService calendarService,
                              @Qualifier("eventsService") EventsService eventService,
                              CalendarMapper calendarMapper, EventMapper eventMapper) {
        this.calendarService = calendarService;
        this.eventService = eventService;
        this.calendarMapper = calendarMapper;
        this.eventMapper = eventMapper;
    }

    @GetMapping(value = "/calendars")
    public List<CalendarDto> getAllCalendars() {
        return calendarMapper.mapListEntriesToDtoList(calendarService.getAllCalendars());
    }

    @GetMapping(value = "/calendars/id={calendarId}")
    public CalendarDto getCalendarById(@PathVariable String calendarId) {
        return calendarMapper.mapListEntryToCalendarDto(calendarService.findById(calendarId));
    }

    @PostMapping(value = "/calendars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CalendarDto createCalendar(@RequestBody CalendarDto calendarDto) {
        return calendarMapper.mapGoogleObjToDto(calendarService.create(calendarDto));
    }

    @DeleteMapping("/calendars/id={calendarId}")
    public void deleteCalendar(@PathVariable String calendarId) {
        calendarService.delete(calendarId);
    }

    @PutMapping("/calendars")
    public CalendarDto updateCalendar(@RequestBody CalendarDto calendarDto) {
        return calendarMapper.mapGoogleObjToDto(calendarService.update(calendarDto));
    }

    @GetMapping(value = "/events")
    public List<EventDto> getAllEvents() {
        return eventMapper.mapGoogleObjListToDtoList(eventService.getAllEvents());
    }

    @GetMapping(value = "/events/calendarid={calendarId}")
    public List<EventDto> getAllEventsFromCalendar(@PathVariable String calendarId) {
        return eventMapper.mapGoogleObjListToDtoList(eventService.getAllFromCalendar(calendarId));
    }

    @GetMapping(value = "/events/id={id}")
    public EventDto findEventById(@PathVariable String id) {
        return eventMapper.mapGoogleObjToDto(eventService.findById(id));
    }

    @PostMapping(value = "/events/calendarid={calendarId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDto createEvent(@RequestBody EventDto eventDto, @PathVariable String calendarId) {
        return eventMapper.mapGoogleObjToDto(eventService.create(calendarId, eventDto));
    }

    @DeleteMapping("/events/id={eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        eventService.delete(eventId);
    }

    @PutMapping("/events")
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        return eventMapper.mapGoogleObjToDto(eventService.update(eventDto));
    }
}