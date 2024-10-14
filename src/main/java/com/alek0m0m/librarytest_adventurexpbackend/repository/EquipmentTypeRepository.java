package com.alek0m0m.librarytest_adventurexpbackend.repository;

import com.Alek0m0m.library.spring.web.mvc.BaseRepository;
import com.alek0m0m.librarytest_adventurexpbackend.model.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentTypeRepository extends BaseRepository<EquipmentType, Long> {

}
