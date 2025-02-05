package com.example.spring_boot_demo.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
	private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
	private final JdbcClient jdbcClient;

	public RunRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	List<Run> findAll() {
		return jdbcClient.sql("select * from run;")
				.query(Run.class)
				.stream().toList();
	}

	Optional<Run> findById(Integer id) {
		return jdbcClient.sql("select * from Run where id=" + id).query(Run.class).optional();
	}

	void create(Run run) {
		int affected = jdbcClient.sql("insert into Run(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?);")
				.params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString())).update();

		Assert.state(affected == 1, "Failed to create run " + run.title());
	}

	void update(Integer id, Run run) {
		int affected = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
				.params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
				.update();

		Assert.state(affected == 1, "Failed to update run " + run.title());
	}

	void delete(Integer id) {
		int affected = jdbcClient.sql("delete Run where id = ?;").param(id).update();
		Assert.state(affected == 1, "Failed to delete run " + id);
	}

	public int count() {
		return jdbcClient.sql("select * from run").query().listOfRows().size();
	}

	public void saveAll(List<Run> runs) {
		runs.stream().forEach(this::create);
	}

	public List<Run> findByLocation(String location) {
		return jdbcClient.sql("select * from run where location = :location")
				.param("location", location)
				.query(Run.class)
				.list();
	}
}