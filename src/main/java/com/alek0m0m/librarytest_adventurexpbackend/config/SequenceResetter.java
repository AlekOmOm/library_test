package com.alek0m0m.librarytest_adventurexpbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SequenceResetter {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SequenceResetter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void resetSequence(String sequenceName) {
        jdbcTemplate.execute("ALTER SEQUENCE " + sequenceName + " RESTART WITH 1");
    }

    public void resetAutoIncrement(String tableName) {
        jdbcTemplate.execute("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1");
    }

    public void resetAutoIncrement(String tableName, long startValue) {
        jdbcTemplate.execute("ALTER TABLE " + tableName + " AUTO_INCREMENT = " + startValue);
    }


    public void resetSequences(long[] startValues) {

        resetAutoIncrement("activity");
        resetAutoIncrement("equipment");
        resetAutoIncrement("equipment_type");
        resetAutoIncrement("booking");

        resetAutoIncrement("activity", startValues[0]);
        resetAutoIncrement("equipment", startValues[1]);
        resetAutoIncrement("equipment_type", startValues[2]);
        resetAutoIncrement("time_slot", startValues[3]);
        resetAutoIncrement("booking", startValues[4]);

    }


}