package com.kodilla.project.service.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.kodilla.project.KodillaFinalProjectApplication;
import com.kodilla.project.config.GoogleOauthConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class Authorization {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    protected static Calendar calendarService;

    @Autowired
    public Authorization(@Qualifier("appName") String appName) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Authorization.calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorizeClient(HTTP_TRANSPORT))
                .setApplicationName(appName)
                .build();
    }

    private static Credential authorizeClient(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        GoogleAuthorizationCodeFlow flow;
        LocalServerReceiver receiver;
//        StringReader reader = new StringReader(CalendarService.credentials);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        InputStream in = KodillaFinalProjectApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        InputStreamReader reader = new InputStreamReader(in);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
