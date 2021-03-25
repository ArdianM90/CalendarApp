package com.kodilla.project.service.google;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.kodilla.project.config.GoogleOauthConfig;
import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.CalendarEntity;
import com.kodilla.project.mapper.CalendarMapper;
import com.kodilla.project.service.db.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("calendarsService")
public class CalendarsService extends Authorization {
    @Autowired
    CalendarMapper mapper;

    public CalendarsService(@Qualifier("appName") String appName) throws IOException, GeneralSecurityException {
        super(appName);
    }

    public List<CalendarListEntry> getAllCalendars() {
        List<CalendarListEntry> accumulatedCalendars = new ArrayList<>();
        try {
            String pageToken = null;
            do {
                CalendarList calendarList = calendarService.calendarList().list().setPageToken(pageToken).execute();
                accumulatedCalendars.addAll(calendarList.getItems());
                pageToken = calendarList.getNextPageToken();
            } while (pageToken != null);
        }
        catch (IOException e) {
            System.out.println("IOException CalendarService(getCalendars) ERROR: "+e.getMessage());
        }
        return accumulatedCalendars;
    }

    public CalendarListEntry findById(String calendarId) {
        CalendarListEntry exit = new CalendarListEntry();
        try {
            exit = calendarService.calendarList().get(calendarId).execute();
        }
        catch (IOException e) {
            System.out.println("IOException CalendarService(findCalendar) ERROR: "+e.getMessage());
        }
        return exit;
    }

    public Calendar create(CalendarDto newCalendarDto) {
        Calendar createdCalendar = new Calendar();
        try {
            createdCalendar.setSummary(newCalendarDto.getSummary());
            createdCalendar.setDescription(newCalendarDto.getDescription());
            createdCalendar = calendarService.calendars().insert(createdCalendar).execute();
        }
        catch (IOException e) {
            System.out.println("IOException CalendarService(createCalendar) ERROR: "+e.getMessage());
        }
        return createdCalendar;
    }

    public void delete(String calendarId) {
        try {
            calendarService.calendars().delete(calendarId).execute();
        }
        catch (IOException e) {
            System.out.println("IOException CalendarService(createCalendar) ERROR: "+e.getMessage());
        }
    }


    public Calendar update(CalendarDto calendarDto) {
        Calendar updatedCalendar = new Calendar();
        try {
            Calendar calendar = calendarService.calendars().get(calendarDto.getId()).execute();
            calendar.setSummary(calendarDto.getSummary());
            calendar.setDescription(calendarDto.getDescription());
            updatedCalendar = calendarService.calendars().update(calendar.getId(), calendar).execute();
        }
        catch (IOException e) {
            System.out.println("IOException CalendarService(createCalendar) ERROR: "+e.getMessage());
        }
        return updatedCalendar;
    }
}
