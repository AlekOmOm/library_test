package com.alek0m0m.librarytest_adventurexpbackend.repository;

import org.example.adventurexpbackend.model.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
    EquipmentType findByName(String name);
}
