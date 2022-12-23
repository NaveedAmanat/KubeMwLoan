package com.idev4.loans.repository;

import com.idev4.loans.domain.MwDist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwDist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDistRepository extends JpaRepository<MwDist, Long> {

}
