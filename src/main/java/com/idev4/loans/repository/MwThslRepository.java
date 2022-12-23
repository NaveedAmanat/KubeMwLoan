package com.idev4.loans.repository;

import com.idev4.loans.domain.MwThsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwThsl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwThslRepository extends JpaRepository<MwThsl, Long> {

}
