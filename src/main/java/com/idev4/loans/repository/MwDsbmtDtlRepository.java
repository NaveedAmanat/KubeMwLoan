package com.idev4.loans.repository;

import com.idev4.loans.domain.MwDsbmtDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwDsbmtDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDsbmtDtlRepository extends JpaRepository<MwDsbmtDtl, Long> {

}
