package com.kodilla.project.controller;

import com.google.api.client.util.DateTime;
import com.google.gson.Gson;
import com.kodilla.project.domain.*;
import com.kodilla.project.mapper.CalendarMapper;
import com.kodilla.project.mapper.EventMapper;
import com.kodilla.project.mapper.LogMapper;
import com.kodilla.project.service.db.DbService;
import com.kodilla.project.service.google.CalendarsService;
import com.kodilla.project.service.google.EventsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class DbControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarMapper calendarMapper;

    @MockBean
    private EventMapper eventMapper;

    @MockBean
    private LogMapper logMapper;

    @MockBean
    private CalendarsService calendarsService;

    @MockBean
    private EventsService eventsService;

    @MockBean
    private DbService dbService;

    private final Gson gson = new Gson();

    @Test
    public void shouldSaveThreeCalendars() throws Exception {
        //Given
        List<CalendarEntity> entityList = new ArrayList<>();
        entityList.add(new CalendarEntity());
        entityList.add(new CalendarEntity());
        entityList.add(new CalendarEntity());
        CalendarDto dto1 = new CalendarDto("id1", "test_summary1", "test_description1");
        CalendarDto dto2 = new CalendarDto("id2", "test_summary2", "test_description2");
        CalendarDto dto3 = new CalendarDto("id3", "test_summary3", "test_description3");
        List<CalendarDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        String jsonContent = gson.toJson(dtoList);
        when(dbService.saveAllCalendars(anyList())).thenReturn(entityList);
        when(calendarMapper.mapEntityListToDtoList(entityList)).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/db/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is("id1")))
                .andExpect(jsonPath("$[1].summary", is("test_summary2")))
                .andExpect(jsonPath("$[2].description", is("test_description3")));
        verify(calendarMapper, times(1)).mapEntityListToDtoList(entityList);
    }

    @Test
    public void shouldSaveCalendar() throws Exception {
        //Given
        CalendarDto dto = new CalendarDto("id", "test_summary", "test_description");
        CalendarEntity entity = new CalendarEntity("id", "test_summary", "test_description");
        String jsonContent = gson.toJson(dto);
        when(calendarMapper.mapDtoToEntity(any(CalendarDto.class))).thenReturn(entity);
        when(dbService.createCalendar(entity)).thenReturn(entity);
        when(calendarMapper.mapEntityToDto(entity)).thenReturn(dto);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/db/calendar")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(dbService, times(1)).createCalendar(entity);
    }

    @Test
    public void shouldDeleteCalendar() throws Exception {
        //Given
        String calendarId = "test_id";

        //When & Then
        mockMvc.perform(delete("/v1/googlecalendar/db/calendar/id="+calendarId)
                .characterEncoding("UTF-8"))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteCalendar(calendarId);
    }

    @Test
    public void shouldUpdateCalendar() throws Exception {
        //Given
        CalendarDto dto = new CalendarDto("id", "test_summary", "test_description");
        CalendarEntity entity = new CalendarEntity("id", "test_summary", "test_description");
        String jsonContent = gson.toJson(dto);
        when(calendarMapper.mapDtoToEntity(any(CalendarDto.class))).thenReturn(entity);
        when(dbService.updateCalendar(entity)).thenReturn(entity);
        when(calendarMapper.mapEntityToDto(entity)).thenReturn(dto);

        //When & Then
        mockMvc.perform(put("/v1/googlecalendar/db/calendar")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(dbService, times(1)).updateCalendar(entity);
    }

    @Test
    public void shouldSaveThreeEvents() throws Exception {
        //Given
        List<EventEntity> entityList = new ArrayList<>();
        entityList.add(new EventEntity());
        entityList.add(new EventEntity());
        entityList.add(new EventEntity());
        EventDto dto1 = new EventDto("id1", "test_summary1", "test_description1");
        EventDto dto2 = new EventDto("id2", "test_summary2", "test_description2");
        EventDto dto3 = new EventDto("id3", "test_summary3", "test_description3");
        List<EventDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        String jsonContent = gson.toJson(dtoList);
        when(dbService.saveAllEvents(anyList())).thenReturn(entityList);
        when(eventMapper.mapEntityListToDtoList(entityList)).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/db/events")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is("id1")))
                .andExpect(jsonPath("$[1].summary", is("test_summary2")))
                .andExpect(jsonPath("$[2].description", is("test_description3")));
        verify(eventMapper, times(1)).mapEntityListToDtoList(entityList);
    }

    @Test
    public void shouldSaveEvent() throws Exception {
        //Given
        EventDto dto = new EventDto("id", "test_summary", "test_description");
        EventEntity entity = new EventEntity("id", "test_summary", "test_description");
        String jsonContent = gson.toJson(dto);
        when(eventMapper.mapDtoToEntity(any(EventDto.class))).thenReturn(entity);
        when(dbService.createEvent(entity)).thenReturn(entity);
        when(eventMapper.mapEntityToDto(entity)).thenReturn(dto);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/db/event")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(dbService, times(1)).createEvent(entity);
    }

    @Test
    public void shouldDeleteEvent() throws Exception {
        //Given
        String eventId = "test_id";

        //When & Then
        mockMvc.perform(delete("/v1/googlecalendar/db/event/id="+eventId)
                .characterEncoding("UTF-8"))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteEvent(eventId);
    }

    @Test
    public void shouldUpdateEvent() throws Exception {
        //Given
        EventDto dto = new EventDto("id", "test_summary", "test_description");
        EventEntity entity = new EventEntity("id", "test_summary", "test_description");
        String jsonContent = gson.toJson(dto);
        when(eventMapper.mapDtoToEntity(any(EventDto.class))).thenReturn(entity);
        when(dbService.updateEvent(entity)).thenReturn(entity);
        when(eventMapper.mapEntityToDto(entity)).thenReturn(dto);

        //When & Then
        mockMvc.perform(put("/v1/googlecalendar/db/event")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(dbService, times(1)).updateEvent(entity);
    }

    @Test
    public void shouldGetThreeLogs() throws Exception {
        //When
        List<LogEntity> entityList = new ArrayList<>();
        entityList.add(new LogEntity());
        entityList.add(new LogEntity());
        entityList.add(new LogEntity());
        LocalDateTime date = LocalDateTime.now();
        LogDto dto1 = new LogDto("id1", "test_type1", "test_description1");
        LogDto dto2 = new LogDto("id2", "test_type2", "test_description2");
        LogDto dto3 = new LogDto("id3", "test_type3", "test_description3");
        List<LogDto> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        String jsonContent = gson.toJson(dtoList);
        when(dbService.getAllLogs()).thenReturn(entityList);
        when(logMapper.mapEntitiesToDtoList(entityList)).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/db/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is("id1")))
                .andExpect(jsonPath("$[1].type", is("test_type2")))
                .andExpect(jsonPath("$[2].description", is("test_description3")));
        verify(dbService, times(1)).getAllLogs();
    }

    @Test
    public void shouldCreateNewLog() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        LogEntity entity = new LogEntity("id", "test_type", "test_description");
        LogDto dto = new LogDto("id", "test_type", "test_description");
        String jsonContent = gson.toJson(dto);
        when(logMapper.mapDtoToEntity(any(LogDto.class))).thenReturn(entity);
        when(dbService.createLog(entity)).thenReturn(entity);
        when(logMapper.mapEntityToDto(any(LogEntity.class))).thenReturn(dto);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/db/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("test_type")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(dbService, times(1)).createLog(entity);
    }
}