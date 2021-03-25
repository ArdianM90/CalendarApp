package com.kodilla.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CalendarificConfig {
    @Value("${calendarific.endpoint.prod}")
    private String endpoint;

    @Value("${calendarific.api.key}")
    private String apiKey;
}
