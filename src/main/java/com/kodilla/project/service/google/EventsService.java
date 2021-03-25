package com.kodilla.project.service.google;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.kodilla.project.config.GoogleOauthConfig;
import com.kodilla.project.domain.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("eventsService")
public class EventsService extends Authorization {
    @Autowired
    CalendarsService calendarsService;

    public EventsService(@Qualifier("appName") String appName) throws IOException, GeneralSecurityException {
        super(appName);
    }

    public List<Event> getAllEvents() {
        List<String> calendarsId =  calendarsService.getAllCalendars().stream()
                .map(CalendarListEntry::getId)
                .collect(Collectors.toList());
        List<Event> result = new ArrayList<>();
        calendarsId.forEach(calId -> {
            result.addAll(getAllFromCalendar(calId));
        });
        return result;
    }

    public List<Event> getAllFromCalendar(String calendarId) {
        List<Event> accumulatedEvents = new ArrayList<>();
        try {
            String pageToken = null;
            do {
                Events events = calendarService.events().list(calendarId).setPageToken(pageToken).execute();
                accumulatedEvents.addAll(events.getItems());
                pageToken = events.getNextPageToken();
            } while (pageToken != null);
        }
        catch (IOException e) {
            System.out.println("IOException EventService(getAllFromCalendar) ERROR: "+e.getMessage());
        }
        return accumulatedEvents;
    }

    public Event findById(String eventId) {
        List<String> calendarsId =  calendarsService.getAllCalendars().stream()
                .map(CalendarListEntry::getId)
                .collect(Collectors.toList());
        Event result = new Event();
        for (String calendarId : calendarsId) {
            try {
                result = calendarService.events().get(calendarId, eventId).execute();
            }
            catch (IOException e) {
                //do nothing, check next
            }
        }
        return result;
    }

    public Event create(String calendarId, EventDto eventDto) {
        Event newEvent = new Event();
        newEvent.setSummary(eventDto.getSummary());
        newEvent.setDescription(eventDto.getDescription());
        Event result = new Event();
        try {
            result = calendarService.events().insert(calendarId, newEvent).execute();
        }
        catch (IOException e) {
            System.out.println("IOException EventService(create) ERROR: "+e.getMessage());
        }
        return result;
    }

    public void delete(String eventId) {
        List<String> calendarsId =  calendarsService.getAllCalendars().stream()
                .map(CalendarListEntry::getId)
                .collect(Collectors.toList());
        calendarsId.forEach(calId -> {
            try {
                calendarService.events().delete(calId, eventId).execute();
            }
            catch (IOException e) {
                //do nothing, try next
            }
        });
    }

    public Event update(EventDto eventDto) {
        //WORK IN PROGRESS
        return new Event();
    }
}
