package com.kodilla.project.controller;

import com.kodilla.project.domain.CountryDto;
import com.kodilla.project.domain.EventDto;
import com.kodilla.project.mapper.CalendarificMapper;
import com.kodilla.project.service.calendarific.CalendarificService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/calendarific")
public class CalendarificController {
    private final CalendarificService calendarificService;
    private final CalendarificMapper calendarificMapper;

    public CalendarificController(CalendarificService calendarificService, CalendarificMapper calendarificMapper) {
        this.calendarificService = calendarificService;
        this.calendarificMapper = calendarificMapper;
    }

    @GetMapping(value = "/holidays/country_year/country={country}&year={year}")
    public List<EventDto> getHolidaysByCountryAndYear(@PathVariable String country, @PathVariable String year) {
        return calendarificMapper.mapHolidaysToDtoList(calendarificService.findByCountryAndYear(country, year));
    }

    @GetMapping(value = "/holidays/country_year_type/country={country}&year={year}&type={type}")
    public List<EventDto> getHolidaysByCountryYearAndType(@PathVariable String country,
                                                          @PathVariable String year,
                                                          @PathVariable String type) {
        return calendarificMapper.mapHolidaysToDtoList(calendarificService.findByCountryYearAndType(country, year, type));
    }

    @GetMapping(value = "/countries")
    public List<CountryDto> getCountriesList() {
        return calendarificService.getListOfCountries();
    }
}
