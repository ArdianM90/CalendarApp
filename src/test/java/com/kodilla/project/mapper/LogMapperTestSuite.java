package com.kodilla.project.mapper;

import com.kodilla.project.domain.EventDto;
import com.kodilla.project.domain.Holiday;
import com.kodilla.project.domain.LogDto;
import com.kodilla.project.domain.LogEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LogMapperTestSuite {
    @Autowired
    LogMapper mapper;

    @Test
    public void shouldMapEntitiesToDtoList() {
        //Given
        List<LogEntity> entities = new ArrayList<>();
        entities.add(new LogEntity("id1", "test_type1", "test_description1"));
        entities.add(new LogEntity("id2", "test_type2", "test_description2"));
        entities.add(new LogEntity("id3", "test_type3", "test_description3"));

        //When
        List<LogDto> dtos = mapper.mapEntitiesToDtoList(entities);

        //Then
        assertEquals(dtos.size(), 3);
        assertEquals(dtos.get(0).getId(), "id1");
        assertEquals(dtos.get(1).getType(), "test_type2");
        assertEquals(dtos.get(2).getDescription(), "test_description3");
    }

    @Test
    public void shouldMapDtoToEntity() {
        //Given
        LogDto dto = new LogDto("id", "test_type", "test_description");

        //When
        LogEntity entity = mapper.mapDtoToEntity(dto);

        //Then
        assertEquals(entity.getId(), "id");
        assertEquals(entity.getType(), "test_type");
        assertEquals(entity.getDescription(), "test_description");
    }

    @Test
    public void shouldMapEntityToDto() {
        //Given
        LogEntity entity = new LogEntity("id", "test_type", "test_description");

        //When
        LogDto dto = mapper.mapEntityToDto(entity);

        //Then
        assertEquals(dto.getId(), "id");
        assertEquals(dto.getType(), "test_type");
        assertEquals(dto.getDescription(), "test_description");
    }
}