package com.alek0m0m.librarytest_adventurexpbackend.repository;

import com.alek0m0m.librarytest_adventurexpbackend.model.Activity;
import com.Alek0m0m.library.spring.web.mvc.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends BaseRepository<Activity, Long> {

}
