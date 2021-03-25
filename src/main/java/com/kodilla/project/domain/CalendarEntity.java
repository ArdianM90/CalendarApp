package com.kodilla.project.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "calendars")
public class CalendarEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;
}
