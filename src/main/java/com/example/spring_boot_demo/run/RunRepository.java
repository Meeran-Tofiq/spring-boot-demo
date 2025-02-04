package com.example.spring_boot_demo.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final HashMap<Integer, Run> runs = new HashMap<>();

    List<Run> findAll() {
        return runs.values().stream().toList();
    }

    Optional<Run> findById(Integer id) {
        return Optional.ofNullable(runs.get(id));
    }

    void create(Run run) {
        runs.put(run.id(), run);
    }

    void update(Integer id, Run run) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.replace(id, run);
        }
    }

    void delete(Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.remove(id);
        }
    }

    @PostConstruct
    private void init() {
        Run r1 = new Run(
                1,
                "Hello",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                5,
                Location.OUTDOOR
        );
        Run r2 = new Run(
                2,
                "Hello",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                5,
                Location.INDOOR
        );
        runs.put(r1.id(), r1);
        runs.put(r2.id(), r2);
    }
}
