package com.t13.dva.LAPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t13.dva.LAPS.model.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

}
