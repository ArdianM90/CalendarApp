package com.kodilla.project;

import com.kodilla.project.controller.CalendarController;
import com.kodilla.project.controller.CalendarificController;
import com.kodilla.project.controller.DbController;
import com.kodilla.project.domain.CalendarDto;
import com.kodilla.project.domain.CountryDto;
import com.kodilla.project.domain.EventDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route
public class MainView extends VerticalLayout {
    private final CalendarController calendarController;
    private final DbController dbController;
    private final CalendarificController calendarificController;
    private Div enpointDiv = new Div();

    public MainView(CalendarController calendarController, DbController dbController, CalendarificController calendarificController) {
        this.calendarController = calendarController;
        this.dbController = dbController;
        this.calendarificController = calendarificController;
        Div googleCalendarsMenuDiv = createGoogleCalendarsEndpointsMenu();
        add(googleCalendarsMenuDiv);
        Div googleEventsMenuDiv = createGoogleEventsEndpointsMenu();
        add(googleEventsMenuDiv);
        Div DatabaseMenuDiv = createDbEndpointsMenu();
        add(DatabaseMenuDiv);
        Div calendarificMenuDiv = createCalendarificEndpointsMenu();
        add(calendarificMenuDiv);
        setSizeFull();
    }

    private void getCalendarsFromGoogleCalendar() {
        Grid<CalendarDto> grid = new Grid<>(CalendarDto.class);
        List<CalendarDto> calendars = calendarController.getAllCalendars();
        grid.setItems(calendars);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(CalendarDto::getId).setHeader("ID");
        grid.addColumn(CalendarDto::getDescription).setHeader("DESCRIPTION");
        grid.addColumn(CalendarDto::getSummary).setHeader("SUMMARY");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private void getCalendarByIdFromGoogleCalendar() {
        Grid<CalendarDto> grid = new Grid<>(CalendarDto.class);
        CalendarDto calendar = calendarController.getCalendarById("addressbook#contacts@group.v.calendar.google.com");
        grid.setItems(calendar);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(CalendarDto::getId).setHeader("ID");
        grid.addColumn(CalendarDto::getDescription).setHeader("DESCRIPTION");
        grid.addColumn(CalendarDto::getSummary).setHeader("SUMMARY");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private void postCreateGoogleCalendar() {
        Grid<CalendarDto> grid = new Grid<>(CalendarDto.class);
        CalendarDto newCalendar = new CalendarDto("testing_id", "testing", "just for tests");
        CalendarDto calendar = calendarController.createCalendar(newCalendar);
        grid.setItems(calendar);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(CalendarDto::getId).setHeader("ID");
        grid.addColumn(CalendarDto::getDescription).setHeader("DESCRIPTION");
        grid.addColumn(CalendarDto::getSummary).setHeader("SUMMARY");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private void deleteGoogleCalendar() {
        calendarController.deleteCalendar("testing_id");
        getCalendarsFromGoogleCalendar();
    }

    private void saveAllCalendarsToDb() {
        dbController.saveAllCalendars(calendarController.getAllCalendars());
        //getCalendarsFromDb
    }

    private void saveAllEventsToDb() {
        dbController.saveAllEvents(calendarController.getAllEvents());
        //getEventsFromDb
    }

    private void deleteCalendarFromDb() {
        dbController.deleteCalendar("testing_id");
        //getCalendarsFromDb
    }

    private void deleteEventFromDb() {
        dbController.deleteEvent("testing_id");
        //getEventsFromDb
    }

    private void getHolidaysByCountryAndYear() {
        Grid<EventDto> grid = new Grid<>(EventDto.class);
        List<EventDto> holidays = calendarificController.getHolidaysByCountryAndYear("pl", "2020");
        grid.setItems(holidays);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(EventDto::getId).setHeader("ID");
        grid.addColumn(EventDto::getDescription).setHeader("DESCRIPTION");
        grid.addColumn(EventDto::getSummary).setHeader("SUMMARY");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private void getHolidaysByCountryYearAndType() {
        Grid<EventDto> grid = new Grid<>(EventDto.class);
        List<EventDto> holidays = calendarificController.getHolidaysByCountryYearAndType("pl", "2020", "national");
        grid.setItems(holidays);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(EventDto::getId).setHeader("ID");
        grid.addColumn(EventDto::getDescription).setHeader("DESCRIPTION");
        grid.addColumn(EventDto::getSummary).setHeader("SUMMARY");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private void getCountriesListFromCalendarific() {
        Grid<CountryDto> grid = new Grid<>(CountryDto.class);
        List<CountryDto> countries = calendarificController.getCountriesList();
        grid.setItems(countries);
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.of("[[index]]"));
        grid.addColumn(CountryDto::getCountry_name).setHeader("COUNTRY");
        grid.addColumn(CountryDto::getIso).setHeader("ISO CODE");
        enpointDiv.removeAll();
        enpointDiv.add(grid);
        this.add(enpointDiv);
        enpointDiv.setSizeFull();
    }

    private Div createGoogleCalendarsEndpointsMenu() {
        Div menu = new Div();
        menu.setText("Google Calendars Endpoints: ");
        Button button1 = new Button("GET all calendars", e -> getCalendarsFromGoogleCalendar());
        button1.getStyle().set("background-color", "#BAB86C");
        Button button2 = new Button("GET calendar by ID", e -> getCalendarByIdFromGoogleCalendar());
        button2.getStyle().set("background-color", "#FFA500");
        Button button3 = new Button("Create (POST) calendar", e -> postCreateGoogleCalendar());
        button3.getStyle().set("background-color", "#FFA500");
        Button button4 = new Button("DELETE calendar", e -> deleteGoogleCalendar());
        button4.getStyle().set("background-color", "#FFA500");
        Button button5 = new Button("Update (PUT) calendar", e -> postCreateGoogleCalendar());
        button5.getStyle().set("background-color", "#FFA500");
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        menu.add(button4);
        menu.add(button5);
        return menu;
    }

    private Div createGoogleEventsEndpointsMenu() {
        Div menu = new Div();
        menu.setText("Google Events Endpoints: ");
        Button button1 = new Button("GET all events");
        button1.getStyle().set("background-color", "#790604");
        Button button2 = new Button("GET all events from calendar");
        button2.getStyle().set("background-color", "#790604");
        Button button3 = new Button("GET event by ID");
        button3.getStyle().set("background-color", "#790604");
        Button button4 = new Button("Create (POST) event");
        button4.getStyle().set("background-color", "#790604");
        Button button5 = new Button("DELETE event");
        button5.getStyle().set("background-color", "#790604");
        Button button6 = new Button("Update (PUT) event");
        button6.getStyle().set("background-color", "#790604");
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        menu.add(button4);
        menu.add(button5);
        menu.add(button6);
        return menu;
    }

    private Div createDbEndpointsMenu() {
        Div menu = new Div();
        menu.setText("Database Endpoints: ");
        Button button1 = new Button("Save all calendars from google (POST)", e -> saveAllCalendarsToDb());
        button1.getStyle().set("background-color", "#BAB86C");
        Button button2 = new Button("Save all events from google (POST)", e -> saveAllEventsToDb());
        button2.getStyle().set("background-color", "#BAB86C");
        Button button3 = new Button("Create calendar (POST)");
        button3.getStyle().set("background-color", "#790604");
        Button button4 = new Button("Delete calendar (DELETE)", e -> deleteCalendarFromDb());
        button4.getStyle().set("background-color", "#FFA500");
        Button button5 = new Button("Update calendar (PUT)");
        button5.getStyle().set("background-color", "#790604");
        Button button6 = new Button("Create event (POST)");
        button6.getStyle().set("background-color", "#790604");
        Button button7 = new Button("Delete event (DELETE)", e -> deleteEventFromDb());
        button7.getStyle().set("background-color", "#FFA500");
        Button button8 = new Button("Update event (PUT)");
        button8.getStyle().set("background-color", "#790604");
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        menu.add(button4);
        menu.add(button5);
        menu.add(button6);
        menu.add(button7);
        menu.add(button8);
        return menu;
    }

    private Div createCalendarificEndpointsMenu() {
        Div menu = new Div();
        menu.setText("Calendarific Endpoints: ");
        Button button1 = new Button("GET holidays by country and year", e -> getHolidaysByCountryAndYear());
        button1.getStyle().set("background-color", "#FFA500");
        Button button2 = new Button("GET holidays by country, year and type", e -> getHolidaysByCountryYearAndType());
        button2.getStyle().set("background-color", "#FFA500");
        Button button3 = new Button("GET all countries", e -> getCountriesListFromCalendarific());
        button3.getStyle().set("background-color", "#BAB86C");
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        return menu;
    }
}
