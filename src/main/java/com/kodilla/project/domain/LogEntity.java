package com.kodilla.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "logs")
public class LogEntity {
    public LogEntity(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;
}
