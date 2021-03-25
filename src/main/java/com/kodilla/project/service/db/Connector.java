package com.kodilla.project.service.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class Connector {
    private Connection conn;

    public Connector(@Qualifier("dbUsername") String username, @Qualifier("dbPassword") String password, @Qualifier("dbUrl") String url) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(url, connectionProps);
        }
        catch (SQLException e) {
            System.out.println("Creating connection to db ERROR: "+e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
