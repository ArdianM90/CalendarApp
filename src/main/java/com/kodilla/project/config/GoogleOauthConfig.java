package com.kodilla.project.config;

import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Component
@Qualifier("googleConfig")
public class GoogleOauthConfig {
    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.project.id}")
    private String projectId;

    @Value("${google.auth.uri}")
    private String authUri;

    @Value("${google.token.uri}")
    private String tokenUri;

    @Value("${google.auth.provider}")
    private String authProviderUrl;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uris}")
    private String redirectUris;

    @Value("${google.api.key}")
    private String apiKey;
}
