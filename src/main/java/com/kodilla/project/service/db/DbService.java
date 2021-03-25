package com.kodilla.project.service.db;

import com.kodilla.project.domain.CalendarEntity;
import com.kodilla.project.domain.EventEntity;
import com.kodilla.project.domain.LogEntity;
import com.kodilla.project.repository.CalendarsRepository;
import com.kodilla.project.repository.EventsRepository;
import com.kodilla.project.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbService {
    private static final String TYPE_GET = "GET";
    private static final String TYPE_CREATE = "CREATE (POST)";
    private static final String TYPE_DELETE = "DELETE";
    private static final String TYPE_UPDATE = "UPDATE (PUT)";

    @Autowired
    private CalendarsRepository calendarsRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private LogsRepository logsRepository;

    public List<CalendarEntity> saveAllCalendars(List<CalendarEntity> calendars) {
        createLog(new LogEntity(TYPE_CREATE, "Saving to db list of calendars."));
        List<CalendarEntity> result = new ArrayList<>();
        for (CalendarEntity calendar : calendars) {
            result.add(calendarsRepository.save(calendar));
        }
        return result;
    }

    public List<EventEntity> saveAllEvents(List<EventEntity> events) {
        createLog(new LogEntity(TYPE_CREATE, "Saving to db list of events."));
        List<EventEntity> result = new ArrayList<>();
        for (EventEntity event : events) {
            result.add(eventsRepository.save(event));
        }
        return result;
    }

    public CalendarEntity createCalendar(CalendarEntity calendar) {
        createLog(new LogEntity(TYPE_CREATE, "Saving to db new calendar."));
        return calendarsRepository.save(calendar);
    }

    public void deleteCalendar(String calendarId) {
        createLog(new LogEntity(TYPE_DELETE, "Delete calendar from db."));
        calendarsRepository.deleteById(calendarId);
    }

    public CalendarEntity updateCalendar(CalendarEntity calendar) {
        createLog(new LogEntity(TYPE_UPDATE, "Updating calendar that already exists in db."));
        return calendarsRepository.save(calendar);
    }

    public EventEntity createEvent(EventEntity event) {
        createLog(new LogEntity(TYPE_CREATE, "Saving to db new event."));
        return eventsRepository.save(event);
    }

    public void deleteEvent(String eventId) {
        createLog(new LogEntity(TYPE_DELETE, "Delete event from db."));
        eventsRepository.deleteById(eventId);
    }

    public EventEntity updateEvent(EventEntity event) {
        createLog(new LogEntity(TYPE_UPDATE, "Updating event that already exists in db."));
        return eventsRepository.save(event);
    }

    public List<LogEntity> getAllLogs() {
        createLog(new LogEntity(TYPE_GET, "Access to list of logs."));
        return logsRepository.findAll();
    }

    public LogEntity createLog(LogEntity log) {
        return logsRepository.save(log);
    }
}
