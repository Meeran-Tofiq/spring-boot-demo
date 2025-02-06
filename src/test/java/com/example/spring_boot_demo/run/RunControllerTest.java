package com.example.spring_boot_demo.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RunController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // Ensures constructor-based injection
@Import(RunControllerTest.Config.class)
class RunControllerTest {

	private final List<Run> runs = new ArrayList<>();
	private final RunRepository repository;
	private final MockMvc mvc;
	private final ObjectMapper objectMapper;

	@Autowired
	RunControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
		this.mvc = mvc;
		this.objectMapper = objectMapper;
		this.repository = mock(RunRepository.class); // Manually creating a mock instance
	}

	@BeforeEach
	void setUp() {
		runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 3, Location.INDOOR));
		reset(repository); // Reset mock before each test to avoid test pollution
	}

	@Test
	void findAllRuns() throws Exception {
		when(repository.findAll()).thenReturn(runs);

		mvc.perform(get("/api/runs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(runs.size()));
	}

	@Test
	void findOneRun() throws Exception {
		Run run = runs.getFirst();
		when(repository.findById(anyInt())).thenReturn(Optional.of(run));

		mvc.perform(get("/api/runs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(run.id()))
				.andExpect(jsonPath("$.title").value(run.title()))
				.andExpect(jsonPath("$.miles").value(run.miles()))
				.andExpect(jsonPath("$.location").value(run.location().toString()));
	}

	@Test
	void returnNotFoundWithInvalidId() throws Exception {
		when(repository.findById(anyInt())).thenReturn(Optional.empty());

		mvc.perform(get("/api/runs/99"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createNewRun() throws Exception {
		var run = new Run(null, "test", LocalDateTime.now(), LocalDateTime.now(), 1, Location.INDOOR);
		when(repository.save(any(Run.class))).thenReturn(run);

		mvc.perform(post("/api/runs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(run)))
				.andExpect(status().isCreated());
	}

	@Test
	void updateRun() throws Exception {
		var run = new Run(1, "updated test", LocalDateTime.now(), LocalDateTime.now(), 1, Location.INDOOR);
		when(repository.findById(1)).thenReturn(Optional.of(run));
		when(repository.save(any(Run.class))).thenReturn(run);

		mvc.perform(put("/api/runs/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(run)))
				.andExpect(status().isNoContent());
	}

	@Test
	void deleteRun() throws Exception {
		when(repository.findById(1)).thenReturn(Optional.of(runs.get(0)));
		doNothing().when(repository).deleteById(1);

		mvc.perform(delete("/api/runs/1"))
				.andExpect(status().isNoContent());
	}

	@TestConfiguration
	static class Config {
		@Bean
		RunRepository runRepository() {
			return mock(RunRepository.class); // Provide the mock
		}
	}
}
