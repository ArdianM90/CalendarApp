package com.kodilla.project.db;

import com.kodilla.project.service.db.Connector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ConnectorTestSuite {
    @Autowired
    Connector dbConnector;

    @Test
    public void testConnect() throws SQLException {
        //Given
        //When
        Connection connection = dbConnector.getConnection();
        //Then
        assertNotNull(connection);
    }
}