package com.kodilla.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String summary;
    private String description;
}
