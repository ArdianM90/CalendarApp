package com.kodilla.project.mapper;

import com.kodilla.project.domain.LogDto;
import com.kodilla.project.domain.LogEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LogMapper {
    public List<LogDto> mapEntitiesToDtoList(List<LogEntity> entityList) {
        List<LogDto> logDtoList = new ArrayList<>();
        for (LogEntity log : entityList) {
            logDtoList.add(mapEntityToDto(log));
        }
        return logDtoList;
    }

    public LogEntity mapDtoToEntity(LogDto logDto) {
        return new LogEntity(logDto.getId(), logDto.getType(), logDto.getDescription());
    }

    public LogDto mapEntityToDto(LogEntity log) {
        return new LogDto(log.getId(), log.getType(), log.getDescription());
    }
}
