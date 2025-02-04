package com.example.spring_boot_demo.run;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
		Integer id,
		@NotNull
		String title,
		LocalDateTime startedOn,
		LocalDateTime completedOn,
		@Positive
		Integer miles,
		Location location
) {
	public Run {
		if (!completedOn.isAfter(startedOn)) {
			throw new IllegalArgumentException("Completed time of run cannot be before the run starting.");
		}
	}
}
