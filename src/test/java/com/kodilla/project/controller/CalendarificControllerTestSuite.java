package com.kodilla.project.controller;

import com.google.gson.Gson;
import com.kodilla.project.service.calendarific.CalendarificService;
import com.kodilla.project.service.google.CalendarsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CalendarificControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarificService calendarificService;

    private String jsonThreeHolidaysResponse = "{\"meta\":{\"code\":200},\"response\":{\"holidays\":[{\"name\":\"Valentine's Day\",\"description\":\"Valentine\\u2019s Day (Walentynki) is one of the most romantic days of the year celebrated in many countries including Poland on February 14.\",\"country\":{\"id\":\"pl\",\"name\":\"Poland\"},\"date\":{\"iso\":\"2020-02-14\",\"datetime\":{\"year\":2020,\"month\":2,\"day\":14}},\"type\":[\"Observance\"],\"locations\":\"All\",\"states\":\"All\"},{\"name\":\"March Equinox\",\"description\":\"March Equinox in Poland (Warsaw)\",\"country\":{\"id\":\"pl\",\"name\":\"Poland\"},\"date\":{\"iso\":\"2020-03-20T04:49:36+01:00\",\"datetime\":{\"year\":2020,\"month\":3,\"day\":20,\"hour\":4,\"minute\":49,\"second\":36},\"timezone\":{\"offset\":\"+01:00\",\"zoneabb\":\"CET\",\"zoneoffset\":3600,\"zonedst\":0,\"zonetotaloffset\":3600}},\"type\":[\"Season\"],\"locations\":\"All\",\"states\":\"All\"},{\"name\":\"Good Friday\",\"description\":\"Good Friday is observed in many churches in Poland to remember Jesus Christ\\u2019s death on the cross.\",\"country\":{\"id\":\"pl\",\"name\":\"Poland\"},\"date\":{\"iso\":\"2020-04-10\",\"datetime\":{\"year\":2020,\"month\":4,\"day\":10}},\"type\":[\"Observance\"],\"locations\":\"All\",\"states\":\"All\"}]}}";

    @Test
    public void shouldRequestListOfHolidaysByCountryAndYear() throws Exception {
        //Given
        String country = "pl";
        String year = "2020";

        //When & Then
        mockMvc.perform(get("/v1/calendarific/holidays/country_year/country="+country+"&year="+year))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(calendarificService, times(1)).findByCountryAndYear(country, year);
    }

    @Test
    public void shouldRequestListOfHolidaysByCountryYearAndType() throws Exception {
        String country = "pl";
        String year = "2020";
        String type = "religious";

        //When & Then
        mockMvc.perform(get("/v1/calendarific/holidays/country_year_type/country="+country+"&year="+year+"&type="+type))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(calendarificService, times(1)).findByCountryYearAndType(country, year, type);

    }

    @Test
    public void shouldRequestCountriesList() throws Exception {
        mockMvc.perform(get("/v1/calendarific/countries"))
//                .andDo(print())
                .andExpect(status().isOk());
        verify(calendarificService, times(1)).getListOfCountries();
    }
}