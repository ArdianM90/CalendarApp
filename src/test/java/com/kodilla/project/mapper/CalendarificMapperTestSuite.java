package com.kodilla.project.mapper;

import com.kodilla.project.domain.EventDto;
import com.kodilla.project.domain.Holiday;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CalendarificMapperTestSuite {
    @Autowired
    CalendarificMapper mapper;

    @Test
    public void shouldMapHolidaysToDtoList() {
        //Given
        List<Holiday> holidaysList = new ArrayList<>();
        holidaysList.add(new Holiday("test_name1", "test_description1"));
        holidaysList.add(new Holiday("test_name2", "test_description2"));
        holidaysList.add(new Holiday("test_name3", "test_description3"));

        //When
        List<EventDto> dtoList = mapper.mapHolidaysToDtoList(holidaysList);

        //Then
        assertEquals(dtoList.size(), 3);
        assertNotNull(dtoList.get(0).getId());
        assertNotNull(dtoList.get(1).getId());
        assertNotNull(dtoList.get(2).getId());
        assertEquals(dtoList.get(1).getSummary(), "test_name2");
        assertEquals(dtoList.get(2).getDescription(), "test_description3");
    }

    @Test
    public void shouldMapHolidayToDto() {
        //Given
        Holiday holiday = new Holiday("test_name", "test_description");

        //When
        EventDto dto = mapper.mapHolidayToDto(holiday);

        //Then
        assertNotNull(dto.getId());
        assertEquals(dto.getSummary(), "test_name");
        assertEquals(dto.getDescription(), "test_description");
    }
}