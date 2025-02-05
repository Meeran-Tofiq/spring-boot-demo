package com.example.spring_boot_demo.run;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class RunJsonDataLoader implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
	private final JdbcClientRunRepository jdbcClientRunRepository;
	private final ObjectMapper objectMapper;

	public RunJsonDataLoader(JdbcClientRunRepository jdbcClientRunRepository, ObjectMapper objectMapper) {
		this.jdbcClientRunRepository = jdbcClientRunRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		// Only load JSON data if the repository is empty
		if (jdbcClientRunRepository.count() != 0) {
			return;
		}

		try (InputStream inputStream = getClass().getResourceAsStream("/data/runs.json")) {
			if (inputStream == null) {
				throw new RuntimeException("Cannot find /data/runs.json resource");
			}
			// Deserialize JSON into a List<Run> using Jackson's TypeReference
			List<Run> allRuns = objectMapper.readValue(inputStream, new TypeReference<List<Run>>() {
			});
			log.info("Reading {} runs from JSON data into the database", allRuns.size());
			jdbcClientRunRepository.saveAll(allRuns);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load runs data", e);
		}
	}
}
