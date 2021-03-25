package com.kodilla.project.mapper;

import com.google.api.services.calendar.model.Event;
import com.kodilla.project.domain.EventDto;
import com.kodilla.project.domain.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {
    @Autowired
    NullEliminator eliminator;

    public List<EventDto> mapGoogleObjListToDtoList(List<Event> events) {
        List<EventDto> eventDtoList = new ArrayList<>();
        for (Event entry : events) {
            eventDtoList.add(mapGoogleObjToDto(entry));
        }
        return eventDtoList;
    }

    public List<EventDto> mapEntityListToDtoList(List<EventEntity> events) {
        List<EventDto> eventDtoList = new ArrayList<>();
        for (EventEntity entry : events) {
            eventDtoList.add(mapEntityToDto(entry));
        }
        return eventDtoList;
    }

    public List<EventEntity> mapDtoListToEntityList(List<EventDto> events) {
        List<EventEntity> eventEntityList = new ArrayList<>();
        for (EventDto entry : events) {
            eventEntityList.add(mapDtoToEntity(entry));
        }
        return eventEntityList;
    }

    public EventDto mapGoogleObjToDto(Event entry) {
        return new EventDto(entry.getId(),
                eliminator.eliminateNull(entry.getSummary()),
                eliminator.eliminateNull(entry.getDescription()));
    }

    public EventDto mapEntityToDto(EventEntity entry) {
        return new EventDto(entry.getId(), entry.getSummary(), entry.getDescription());
    }

    public EventEntity mapDtoToEntity(EventDto entry) {
        return new EventEntity(entry.getId(), entry.getSummary(), entry.getDescription());
    }
}
