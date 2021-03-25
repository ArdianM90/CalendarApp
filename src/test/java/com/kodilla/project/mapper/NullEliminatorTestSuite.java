package com.kodilla.project.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NullEliminatorTestSuite {
    @Autowired
    NullEliminator eliminator;

    @Test
    public void shouldReturnStringEmpty() {
        //Given
        String entry = null;

        //When
        String result = eliminator.eliminateNull(entry);

        //Then
        assertNotNull(result);
    }

    @Test
    public void shouldReturnSameString() {
        //Given
        String entry = "test_entry";

        //When
        String result = eliminator.eliminateNull(entry);

        //Then
        assertEquals("test_entry", result);
    }
}