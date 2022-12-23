package com.idev4.loans.repository;

import com.idev4.loans.domain.MwUc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwUc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwUcRepository extends JpaRepository<MwUc, Long> {

}
