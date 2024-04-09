package com.example.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record Run(
        Integer id,
        @NotEmpty
        String title,
        Instant startedOn,
        Instant completedOn,
        @Positive
        Integer kilometers,
        Location location
) {

    public Run {
        if(!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Started on isAfter startedOn");
        }
    }
}