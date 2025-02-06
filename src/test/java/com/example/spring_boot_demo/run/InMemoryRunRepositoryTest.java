package com.example.spring_boot_demo.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryRunRepositoryTest {

	InMemoryRunRepository repository;

	@BeforeEach
	void setup() {
		repository = new InMemoryRunRepository();
		repository.create(new Run(1,
				"Monday Morning Run",
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(30),
				3,
				Location.INDOOR));

		repository.create(new Run(2,
				"Wednesday Evening Run",
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(60),
				6,
				Location.INDOOR));
	}

	@Test
	void shouldFindAllRuns() {
		assertEquals(2, repository.findAll().size(), "should've returned two runs");
	}

	@Test
	void shouldFindRunWithValidId() {
		var run = repository.findById(1).get();
		assertEquals("Monday Morning Run", run.title());
		assertEquals(3, run.miles());
	}

	@Test
	void shouldNotFindRunWithInvalidId() {
		RunNotFoundException notFoundException = assertThrows(
				RunNotFoundException.class,
				() -> repository.findById(3).get()
		);

		assertEquals("Run not found.", notFoundException.getMessage());
	}

	@Test
	void shouldCreateNewRun() {
		repository.create(new Run(3,
				"Friday Morning Run",
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(30),
				3,
				Location.INDOOR));
		List<Run> runs = repository.findAll();
		assertEquals(3, runs.size());
	}

	@Test
	void shouldUpdateRun() {
		repository.update(new Run(1,
				"Monday Morning Run",
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(30),
				5,
				Location.OUTDOOR), 1);
		var run = repository.findById(1).get();
		assertEquals("Monday Morning Run", run.title());
		assertEquals(5, run.miles());
		assertEquals(Location.OUTDOOR, run.location());
	}

	@Test
	void shouldDeleteRun() {
		repository.delete(1);
		List<Run> runs = repository.findAll();
		assertEquals(1, runs.size());
	}
}