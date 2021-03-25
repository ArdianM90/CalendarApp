package com.kodilla.project.mapper;

import com.kodilla.project.domain.EventDto;
import com.kodilla.project.domain.Holiday;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CalendarificMapper {
    Random rnd = new Random();

    public List<EventDto> mapHolidaysToDtoList(List<Holiday> holidays) {
        List<EventDto> eventDtoList = new ArrayList<>();
        for (Holiday entry : holidays) {
            eventDtoList.add(mapHolidayToDto(entry));
        }
        return eventDtoList;
    }

    public EventDto mapHolidayToDto(Holiday entry) {
        String id = String.valueOf((int)(rnd.nextDouble()*10000));
        while (4-id.length() > 0) {
            id = "0".concat(id);
        }
        id = "calendarific-".concat(id);
        return new EventDto(id, entry.getName(), entry.getDescription());
    }
}
