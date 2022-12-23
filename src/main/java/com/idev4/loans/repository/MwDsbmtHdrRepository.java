package com.idev4.loans.repository;

import com.idev4.loans.domain.MwDsbmtHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwDsbmtHdr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDsbmtHdrRepository extends JpaRepository<MwDsbmtHdr, Long> {

}
