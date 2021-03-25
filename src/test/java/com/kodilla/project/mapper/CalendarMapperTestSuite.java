package com.kodilla.project.mapper;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.CalendarEntity;
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
class CalendarMapperTestSuite {
    @Autowired
    CalendarMapper calendarMapper;

    @Test
    public void shouldMapListEntriesToDtoList() {
        //Given
        CalendarListEntry cal1 = new CalendarListEntry();
        cal1.setId("id1");
        cal1.setSummary("test_summary1");
        cal1.setDescription("test_description1");
        CalendarListEntry cal2 = new CalendarListEntry();
        cal2.setId("id2");
        cal2.setSummary("test_summary2");
        cal2.setDescription("test_description2");
        CalendarListEntry cal3 = new CalendarListEntry();
        cal3.setId("id3");
        cal3.setSummary("test_summary3");
        cal3.setDescription("test_description3");
        List<CalendarListEntry> calendarList = new ArrayList<>();
        calendarList.add(cal1);
        calendarList.add(cal2);
        calendarList.add(cal3);

        //When
        List<CalendarDto> dtoList = calendarMapper.mapListEntriesToDtoList(calendarList);

        //Then
        assertEquals(dtoList.size(), 3);
        assertEquals(dtoList.get(0).getId(), "id1");
        assertEquals(dtoList.get(2).getId(), "id3");
        assertEquals(dtoList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapEntitiesListToDtoList() {
        //Given
        CalendarEntity cal1 = new CalendarEntity("id1", "test_summary1", "test_description1");
        CalendarEntity cal2 = new CalendarEntity("id2", "test_summary2", "test_description2");
        CalendarEntity cal3 = new CalendarEntity("id3", "test_summary3", "test_description3");
        List<CalendarEntity> entitiesList = new ArrayList<>();
        entitiesList.add(cal1);
        entitiesList.add(cal2);
        entitiesList.add(cal3);

        //When
        List<CalendarDto> dtoList = calendarMapper.mapEntityListToDtoList(entitiesList);

        //Then
        assertEquals(dtoList.size(), 3);
        assertEquals(dtoList.get(0).getId(), "id1");
        assertEquals(dtoList.get(2).getId(), "id3");
        assertEquals(dtoList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapDtoListToEntitiesList() {
        //Given
        CalendarDto cal1 = new CalendarDto("id1", "test_summary1", "test_description1");
        CalendarDto cal2 = new CalendarDto("id2", "test_summary2", "test_description2");
        CalendarDto cal3 = new CalendarDto("id3", "test_summary3", "test_description3");
        List<CalendarDto> dtoList = new ArrayList<>();
        dtoList.add(cal1);
        dtoList.add(cal2);
        dtoList.add(cal3);

        //When
        List<CalendarEntity> entitiesList = calendarMapper.mapDtoListToEntityList(dtoList);

        //Then
        assertEquals(entitiesList.size(), 3);
        assertEquals(entitiesList.get(0).getId(), "id1");
        assertEquals(entitiesList.get(2).getId(), "id3");
        assertEquals(entitiesList.get(2).getSummary(), "test_summary3");
    }

    @Test
    public void shouldMapCalendarListEntryToCalendarDto() {
        //Given
        CalendarListEntry calendar = new CalendarListEntry();
        calendar.setId("id");
        calendar.setSummary("test_summary");
        calendar.setDescription("test_description");

        //When
        CalendarDto dto = calendarMapper.mapListEntryToCalendarDto(calendar);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getSummary(), "test_summary");
        assertEquals(dto.getDescription(), "test_description");
    }

    @Test
    public void shouldMapCalendarToCalendarDto() {
        //Given
        Calendar calendar = new Calendar();
        calendar.setId("id");
        calendar.setSummary("test_summary");
        calendar.setDescription("test_description");

        //When
        CalendarDto dto = calendarMapper.mapGoogleObjToDto(calendar);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getSummary(), "test_summary");
        assertEquals(dto.getDescription(), "test_description");
    }

    @Test
    public void shouldMapEntityToDto() {
        //Given
        CalendarEntity entity = new CalendarEntity("id", "test_summary", "test_description");

        //When
        CalendarDto dto = calendarMapper.mapEntityToDto(entity);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getSummary(), "test_summary");
        assertEquals(dto.getDescription(), "test_description");
    }

    @Test
    public void shouldMapDtoToEntity() {
        //Given
        CalendarDto dto = new CalendarDto("id", "test_summary", "test_description");

        //When
        CalendarEntity entity = calendarMapper.mapDtoToEntity(dto);

        //Then
        assertEquals(entity.getId(), "id");
        assertEquals(entity.getSummary(), "test_summary");
        assertEquals(entity.getDescription(), "test_description");
    }
}