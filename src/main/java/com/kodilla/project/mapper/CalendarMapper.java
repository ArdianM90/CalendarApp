package com.kodilla.project.mapper;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.CalendarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarMapper {
    @Autowired
    NullEliminator eliminator;

    public List<CalendarDto> mapListEntriesToDtoList(List<CalendarListEntry> calendars) {
        List<CalendarDto> calendarDtoList = new ArrayList<>();
        for (CalendarListEntry entry : calendars) {
            calendarDtoList.add(mapListEntryToCalendarDto(entry));
        }
        return calendarDtoList;
    }

    public List<CalendarDto> mapEntityListToDtoList(List<CalendarEntity> calendars) {
        List<CalendarDto> calendarDtoList = new ArrayList<>();
        for (CalendarEntity entry : calendars) {
            calendarDtoList.add(mapEntityToDto(entry));
        }
        return calendarDtoList;
    }

    public List<CalendarEntity> mapDtoListToEntityList(List<CalendarDto> calendars) {
        List<CalendarEntity> calendarEntityList = new ArrayList<>();
        for (CalendarDto entry : calendars) {
            calendarEntityList.add(mapDtoToEntity(entry));
        }
        return calendarEntityList;
    }

    public CalendarDto mapListEntryToCalendarDto(CalendarListEntry entry) {
        return new CalendarDto(entry.getId(),
                eliminator.eliminateNull(entry.getSummary()),
                eliminator.eliminateNull(entry.getDescription()));
    }

    public CalendarDto mapGoogleObjToDto(Calendar entry) {
        return new CalendarDto(entry.getId(), entry.getSummary(), entry.getDescription());
    }

    public CalendarDto mapEntityToDto(CalendarEntity entry) {
        return new CalendarDto(entry.getId(), entry.getSummary(), entry.getDescription());
    }

    public CalendarEntity mapDtoToEntity(CalendarDto entry) {
        return new CalendarEntity(entry.getId(), entry.getSummary(), entry.getDescription());
    }
}
