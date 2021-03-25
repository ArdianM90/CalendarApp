package com.kodilla.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DbConfig {
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    @Qualifier("dbUsername")
    public String getUsername() {
        return username;
    }

    @Bean
    @Qualifier("dbPassword")
    public String getPassword() {
        return password;
    }

    @Bean
    @Qualifier("dbUrl")
    public String getUrl() {
        return url;
    }
}
