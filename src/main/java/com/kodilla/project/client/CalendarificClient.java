package com.kodilla.project.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kodilla.project.config.CalendarificConfig;
import com.kodilla.project.domain.CountryDto;
import com.kodilla.project.domain.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CalendarificClient {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CalendarificConfig config;

    private static final String FAKE_HTTP_HEADER = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    public List<Holiday> getHolidaysByCountryAndYear(String country, String year) {
        List<Holiday> holidays = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.exchange(
                getHolidaysByCountryAndYearUrl(country, year),
                HttpMethod.GET,
                getHttpEntity(),
                String.class);
        try {
            holidays = mapJsonToHolidayList(mapper.readTree(response.getBody()));
        }
        catch (Exception e) {
            System.out.println("CalendarificClient(getHolidaysByCountryAndYear) ERROR: "+e.getCause()+", "+e.getMessage());
        }
        return holidays;
    }

    public List<Holiday> getHolidaysByCountryYearAndType(String country, String year, String type) {
        List<Holiday> holidays = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.exchange(
                getHolidaysByCountryYearAndTypeUrl(country, year, type),
                HttpMethod.GET,
                getHttpEntity(),
                String.class);
        try {
            holidays = mapJsonToHolidayList(mapper.readTree(response.getBody()));
        }
        catch (Exception e) {
            System.out.println("CalendarificClient(getHolidaysByCountryAndYear) ERROR: "+e.getCause()+", "+e.getMessage());
        }
        return holidays;
    }

    public List<CountryDto> getCountriesList() {
        List<CountryDto> countries = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.exchange(
                getCountriesListUrl(),
                HttpMethod.GET,
                getHttpEntity(),
                String.class);
        try {
            countries = mapJsonToCountiresList(mapper.readTree(response.getBody()));
        }
        catch (Exception e) {
            System.out.println("CalendarificClient(getHolidaysByCountryAndYear) ERROR: "+e.getCause()+", "+e.getMessage());
        }
        return countries;
    }

    private List<Holiday> mapJsonToHolidayList(JsonNode jsonNode) {
        List<Holiday> holidays = new ArrayList<>();
        List<String> jsonStringHolidays = new ArrayList<>();
        jsonNode.get("response").get("holidays")
                .forEach(holiday -> jsonStringHolidays.add(holiday.toString()));
        try {
            for (String entry : jsonStringHolidays) {
                holidays.add(mapper.readValue(entry, Holiday.class));
            }
        }
        catch (Exception e) {
            System.out.println("CalendarificClient(mapJsonToHolidayList) ERROR: "+e.getCause()+", "+e.getMessage());
        }
        return holidays;
    }

    private List<CountryDto> mapJsonToCountiresList(JsonNode jsonNode) {
        List<CountryDto> countries = new ArrayList<>();
        System.out.println("COUNTRIES");
        jsonNode.get("response").get("countries")
                .forEach(country -> {
                    JsonObject obj = JsonParser.parseString(country.toString()).getAsJsonObject();
                    String countryName = obj.get("country_name").toString().replaceAll("\"", "");;
                    String countryIso = obj.get("iso-3166").toString().replaceAll("\"", "");;
                    countries.add(new CountryDto(countryName, countryIso));
                });
        return countries;
    }

    private URI getHolidaysByCountryAndYearUrl(String country, String year) {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getEndpoint()+"/holidays")
                .queryParam("api_key", config.getApiKey())
                .queryParam("country", country)
                .queryParam("year", year)
                .build().encode().toUri();
        return url;
    }

    private URI getHolidaysByCountryYearAndTypeUrl(String country, String year, String type) {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getEndpoint()+"/holidays")
                .queryParam("api_key", config.getApiKey())
                .queryParam("country", country)
                .queryParam("year", year)
                .queryParam("type", type)
                .build().encode().toUri();
        return url;
    }

    private URI getCountriesListUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getEndpoint()+"/countries")
                .queryParam("api_key", config.getApiKey())
                .build().encode().toUri();
        return url;
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", FAKE_HTTP_HEADER);
        return new HttpEntity(headers);
    }
}
