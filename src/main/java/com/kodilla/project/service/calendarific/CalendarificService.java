package com.kodilla.project.service.calendarific;

import com.kodilla.project.client.CalendarificClient;
import com.kodilla.project.domain.CountryDto;
import com.kodilla.project.domain.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarificService {
    @Autowired
    CalendarificClient client;

    public List<Holiday> findByCountryAndYear(String country, String year) {
        return client.getHolidaysByCountryAndYear(country, year);
    }

    public List<Holiday> findByCountryYearAndType(String country, String year, String type) {
        return client.getHolidaysByCountryYearAndType(country, year, type);
    }

    public List<CountryDto> getListOfCountries() {
        return client.getCountriesList();
    }
}
