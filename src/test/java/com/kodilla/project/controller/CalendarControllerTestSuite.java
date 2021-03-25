package com.kodilla.project.controller;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.gson.Gson;
import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.EventDto;
import com.kodilla.project.mapper.CalendarMapper;
import com.kodilla.project.mapper.EventMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CalendarControllerTestSuite {
    private final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarMapper calendarMapper;

    @MockBean
    private EventMapper eventMapper;

    @MockBean
    private CalendarsService calendarsService;

    @MockBean
    private EventsService eventsService;

    @Test
    public void shouldGetListOfAllCalendars() throws Exception {
        //Given
        List<CalendarDto> dtoList = new ArrayList<>();
        dtoList.add(new CalendarDto("1", "test", "test"));
        dtoList.add(new CalendarDto("2", "test", "test"));
        dtoList.add(new CalendarDto("3", "test", "test"));
        when(calendarMapper.mapListEntriesToDtoList(any())).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/calendars")
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(calendarsService, times(1)).getAllCalendars();
    }

    @Test
    public void shouldFindCalendarById() throws Exception {
        //Given
        CalendarDto calendarDto = new CalendarDto("id1", "test_summary", "test_description");
        when(calendarsService.findById(calendarDto.getId())).thenReturn(new CalendarListEntry());
        when(calendarMapper.mapListEntryToCalendarDto(any(CalendarListEntry.class))).thenReturn(calendarDto);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/calendars/id="+calendarDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id1")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(calendarsService, times(1)).findById(calendarDto.getId());
    }

    @Test
    public void shouldCreateNewCalendar() throws Exception {
        //Given
        CalendarDto calendarDto = new CalendarDto("id", "test_summary", "test_description");
        Calendar calendar = new Calendar();
        String jsonContent = gson.toJson(calendarDto);
        when(calendarsService.create(any(CalendarDto.class))).thenReturn(calendar);
        when(calendarMapper.mapGoogleObjToDto(calendar)).thenReturn(calendarDto);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(calendarsService, times(1)).create(any(CalendarDto.class));
    }

    @Test
    public void shouldDeleteCalendar() throws Exception {
        //Given
        String calendarId = "id1";

        //When & Then
        mockMvc.perform(delete("/v1/googlecalendar/calendars/id="+calendarId))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(calendarsService, times(1)).delete(calendarId);
    }

    @Test
    public void shouldUpdateExistingCalendar() throws Exception {
        //Given
        CalendarDto entryDto = new CalendarDto("id1", "test_summary", "test_description");
        CalendarDto exitDto = new CalendarDto("id1", "updated_summary", "updated_description");
        String jsonContent = gson.toJson(entryDto);
        when(calendarsService.update(any(CalendarDto.class))).thenReturn(new Calendar());
        when(calendarMapper.mapGoogleObjToDto(any(Calendar.class))).thenReturn(exitDto);

        //When & Then
        mockMvc.perform(put("/v1/googlecalendar/calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id1")))
                .andExpect(jsonPath("$.summary", is("updated_summary")))
                .andExpect(jsonPath("$.description", is("updated_description")));
        verify(calendarsService, times(1)).update(any(CalendarDto.class));
    }

    @Test
    public void shouldGetListOfAllEvents() throws Exception {
        //Given
        List<EventDto> dtoList = new ArrayList<>();
        dtoList.add(new EventDto("1", "test", "test"));
        dtoList.add(new EventDto("2", "test", "test"));
        dtoList.add(new EventDto("3", "test", "test"));
        when(eventMapper.mapGoogleObjListToDtoList(any())).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/events")
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(eventsService, times(1)).getAllEvents();
    }

    @Test
    public void shouldGetListOfEventsFromSpecifiedCalendar() throws Exception {
        //Given
        String calendarId = "calId";
        List<EventDto> dtoList = new ArrayList<>();
        dtoList.add(new EventDto("1", "test", "test"));
        dtoList.add(new EventDto("2", "test", "test"));
        dtoList.add(new EventDto("3", "test", "test"));
        when(eventsService.getAllFromCalendar(calendarId)).thenReturn(new ArrayList<>());
        when(eventMapper.mapGoogleObjListToDtoList(any())).thenReturn(dtoList);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/events/calendarid="+calendarId)
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(eventsService, times(1)).getAllFromCalendar(calendarId);
    }

    @Test
    public void shouldFindEventById() throws Exception {
        //Given
        EventDto eventDto = new EventDto("id1", "test_summary", "test_description");
        when(eventsService.findById(eventDto.getId())).thenReturn(new Event());
        when(eventMapper.mapGoogleObjToDto(any(Event.class))).thenReturn(eventDto);

        //When & Then
        mockMvc.perform(get("/v1/googlecalendar/events/id="+eventDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(eventsService, times(1)).findById(eventDto.getId());
    }

    @Test
    public void shouldCreateEvent() throws Exception {
        //Given
        String calendarId = "calId";
        EventDto eventDto = new EventDto("id1", "test_summary", "test_description");
        Event event = new Event();
        String jsonContent = gson.toJson(eventDto);
        when(eventsService.create(any(String.class), any(EventDto.class))).thenReturn(event);
        when(eventMapper.mapGoogleObjToDto(event)).thenReturn(eventDto);

        //When & Then
        mockMvc.perform(post("/v1/googlecalendar/events/calendarid="+calendarId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id1")))
                .andExpect(jsonPath("$.summary", is("test_summary")))
                .andExpect(jsonPath("$.description", is("test_description")));
        verify(eventsService, times(1)).create(any(String.class), any(EventDto.class));
    }

    @Test
    public void shouldDeleteEvent() throws Exception {
        //Given
        String eventId = "id1";

        //When & Then
        mockMvc.perform(delete("/v1/googlecalendar/events/id="+eventId))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(eventsService, times(1)).delete(eventId);
    }

    @Test
    public void shouldUpdateExistingEvent() throws Exception {
        //Given
        EventDto eventDto = new EventDto("id1", "test_summary", "test_description");
        String jsonContent = gson.toJson(eventDto);

        //When & Then
        mockMvc.perform(put("/v1/googlecalendar/events")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(eventsService, times(1)).update(any());
    }
}