package com.alek0m0m.librarytest_adventurexpbackend.repository;

import org.example.adventurexpbackend.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {




    // Custom query to find equipment by name
    Equipment findByName(String name);
}
