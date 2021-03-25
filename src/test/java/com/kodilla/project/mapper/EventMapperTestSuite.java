package com.kodilla.project.mapper;

import com.google.api.services.calendar.model.Event;
import com.kodilla.project.domain.EventDto;
import com.kodilla.project.domain.EventEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EventMapperTestSuite {
    @Autowired
    EventMapper eventMapper;

    @Test
    public void shouldMapEventsToDtoList() {
        //Given
        Event event1 = new Event();
        event1.setId("id1");
        event1.setSummary("test_summary1");
        event1.setDescription("test_description1");
        Event event2 = new Event();
        event2.setId("id2");
        event2.setSummary("test_summary2");
        event2.setDescription("test_description2");
        Event event3 = new Event();
        event3.setId("id3");
        event3.setSummary("test_summary3");
        event3.setDescription("test_description3");
        List<Event> eventsList = new ArrayList<>();
        eventsList.add(event1);
        eventsList.add(event2);
        eventsList.add(event3);

        //When
        List<EventDto> dtoList = eventMapper.mapGoogleObjListToDtoList(eventsList);

        //Then
        assertEquals(dtoList.size(), 3);
        assertEquals(dtoList.get(0).getId(), "id1");
        assertEquals(dtoList.get(2).getId(), "id3");
        assertEquals(dtoList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapEntitiesListToDtoList() {
        //Given
        EventEntity event1 = new EventEntity("id1", "test_summary1", "test_description1");
        EventEntity event2 = new EventEntity("id2", "test_summary2", "test_description2");
        EventEntity event3 = new EventEntity("id3", "test_summary3", "test_description3");
        List<EventEntity> entitiesList = new ArrayList<>();
        entitiesList.add(event1);
        entitiesList.add(event2);
        entitiesList.add(event3);

        //When
        List<EventDto> dtoList = eventMapper.mapEntityListToDtoList(entitiesList);

        //Then
        assertEquals(dtoList.size(), 3);
        assertEquals(dtoList.get(0).getId(), "id1");
        assertEquals(dtoList.get(2).getId(), "id3");
        assertEquals(dtoList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapDtoListToEntitiesList() {
        //Given
        EventDto event1 = new EventDto("id1", "test_summary1", "test_description1");
        EventDto event2 = new EventDto("id2", "test_summary2", "test_description2");
        EventDto event3 = new EventDto("id3", "test_summary3", "test_description3");
        List<EventDto> dtoList = new ArrayList<>();
        dtoList.add(event1);
        dtoList.add(event2);
        dtoList.add(event3);

        //When
        List<EventEntity> entitiesList = eventMapper.mapDtoListToEntityList(dtoList);

        //Then
        assertEquals(entitiesList.size(), 3);
        assertEquals(entitiesList.get(0).getId(), "id1");
        assertEquals(entitiesList.get(2).getId(), "id3");
        assertEquals(entitiesList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapEventToDto() {
        //Given
        Event event = new Event();
        event.setId("id");
        event.setSummary("test_summary");
        event.setDescription("test_description");

        //When
        EventDto dto = eventMapper.mapGoogleObjToDto(event);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getSummary(), "test_summary");
        assertEquals(dto.getDescription(), "test_description");
    }

    @Test
    public void shouldMapEntityToDto() {
        //Given
        EventEntity entity = new EventEntity("id", "test_summary", "test_description");

        //When
        EventDto dto = eventMapper.mapEntityToDto(entity);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getSummary(), "test_summary");
        assertEquals(dto.getDescription(), "test_description");
    }

    @Test
    public void shouldMapDtoToEntity() {
        //Given
        EventDto dto = new EventDto("id", "test_summary", "test_description");

        //When
        EventEntity entity = eventMapper.mapDtoToEntity(dto);

        //Then
        assertEquals(entity.getId(), "id");
        assertEquals(entity.getSummary(), "test_summary");
        assertEquals(entity.getDescription(), "test_description");
    }
}