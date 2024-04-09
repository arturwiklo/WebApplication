package com.example.runnerz.run;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT id,title,started_on,completed_on,kilometers,location" +
                        " FROM Run WHERE id = :id" )
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on," +
                        " kilometers, location) VALUES (?, ?, ?, ?, ?, ?)")
                .params(run.id(), run.title(), run.startedOn(), run.completedOn(), run.kilometers(), run.location().toString())
                .update();
        Assert.state(updated == 1, "Failed to insert");
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?," +
                        " kilometers = ?, location = ? WHERE id = ?")
                .params(run.title(), run.startedOn(), run.completedOn(), run.kilometers(), run.location().toString(), id)
                .update();
        Assert.state(updated == 1, "Update failed");
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM Run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}
