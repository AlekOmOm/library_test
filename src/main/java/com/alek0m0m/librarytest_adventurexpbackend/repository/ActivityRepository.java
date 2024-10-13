package com.alek0m0m.librarytest_adventurexpbackend.repository;

import org.example.adventurexpbackend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Override
    List<Activity> findAll();

    Activity findByName(String name);
}
