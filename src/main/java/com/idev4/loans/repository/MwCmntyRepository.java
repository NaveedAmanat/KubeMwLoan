package com.idev4.loans.repository;

import com.idev4.loans.domain.MwCmnty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwCmnty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCmntyRepository extends JpaRepository<MwCmnty, Long> {

}
