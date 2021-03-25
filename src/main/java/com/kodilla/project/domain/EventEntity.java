package com.kodilla.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "events")
public class EventEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;
}
