package com.t13.dva.LAPS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t13.dva.LAPS.model.Compensation;

@Repository("compensationRepository")
public interface CompensationRepository extends JpaRepository<Compensation, Integer> {

	Page<Compensation> findByUserid(Pageable pageable, int userid);
	List<Compensation> findByUserid(int userid);
	
}
