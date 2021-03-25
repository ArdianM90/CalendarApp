package com.kodilla.project.mapper;

import org.springframework.stereotype.Component;

@Component
public class NullEliminator {
    public String eliminateNull(String value) {
        if (value != null) {
            return value;
        } else {
            return "EMPTY";
        }
    }
}
