package com.alek0m0m.librarytest_adventurexpbackend.repository;

import com.Alek0m0m.library.spring.web.mvc.BaseRepository;
import com.alek0m0m.librarytest_adventurexpbackend.model.Equipment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends BaseRepository<Equipment, Long> {


}
