package com.kodilla.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppDetailsConfig {
    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.app.owner.name}")
    private String appOwnerName;

    @Value("${info.app.owner.surname}")
    private String appOwnerSurname;

    @Value("${info.app.administrator.email}")
    private String appAdminEmail;

    @Bean
    @Qualifier("appName")
    public String getAppName() {
        return appName;
    }
}
