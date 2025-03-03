package com.example.spring_boot_demo.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(JdbcClientRunRepository.class)
class JdbcClientRunRepositoryTest {

	@Autowired
	JdbcClientRunRepository repository;

	@BeforeEach
	void setup() {
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
		var run = repository.findById(3);
		assertTrue(run.isEmpty());
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
		repository.update(1, new Run(1,
				"Monday Morning Run",
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(30),
				5,
				Location.OUTDOOR));
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
