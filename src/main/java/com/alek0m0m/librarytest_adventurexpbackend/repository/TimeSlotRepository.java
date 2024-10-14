package com.alek0m0m.librarytest_adventurexpbackend.repository;

import com.Alek0m0m.library.spring.web.mvc.BaseRepository;
import com.alek0m0m.librarytest_adventurexpbackend.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends BaseRepository<TimeSlot, Long> {
}
