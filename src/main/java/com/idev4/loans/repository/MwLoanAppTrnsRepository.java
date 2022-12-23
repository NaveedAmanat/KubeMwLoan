package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwLoanApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppTrnsRepository extends JpaRepository<MwLoanAppTrans, Long> {

}
