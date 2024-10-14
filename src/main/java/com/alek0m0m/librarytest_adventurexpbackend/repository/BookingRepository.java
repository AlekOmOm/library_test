package com.alek0m0m.librarytest_adventurexpbackend.repository;

import com.Alek0m0m.library.spring.web.mvc.BaseRepository;
import com.alek0m0m.librarytest_adventurexpbackend.model.Activity;
import com.alek0m0m.librarytest_adventurexpbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends BaseRepository<Booking, Long> {

    List<Booking> findByActivity(Activity activity);

    List<Booking> findByDate(LocalDate date);

    @Query("SELECT b FROM Booking b JOIN FETCH b.activity a LEFT JOIN FETCH a.equipmentList")
    List<Booking> findAllWithActivities();
}