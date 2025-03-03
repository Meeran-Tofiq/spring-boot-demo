package com.example.spring_boot_demo.run;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

	private final RunRepository runRepository;

	public RunController(RunRepository runRepository) {
		this.runRepository = runRepository;
	}

	@GetMapping("")
	List<Run> getRuns() {
		return runRepository.findAll();
	}

	@GetMapping("/{id}")
	Run getRunById(@PathVariable("id") Integer id) {
		Optional<Run> run = runRepository.findById(id);
		if (run.isEmpty()) {
			throw new RunNotFoundException();
		}
		return run.get();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	void createRun(@Valid @RequestBody Run run) {
		runRepository.save(run);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	void updateRunById(@RequestBody Run run) {
		runRepository.save(run);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void deleteRunById(@PathVariable("id") Integer id) {
		runRepository.deleteById(id);
	}

	@GetMapping("/location/{location}")
	List<Run> getAllByLocation(@PathVariable Location location) {
		return runRepository.findAllByLocation(location);
	}
}
