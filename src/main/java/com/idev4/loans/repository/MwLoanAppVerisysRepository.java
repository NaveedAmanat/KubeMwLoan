package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppVerisys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author, Muhammad Bassam
 * @date, 3-May-2021
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppVerisysRepository extends JpaRepository<MwLoanAppVerisys, Long> {

    public List<MwLoanAppVerisys> findOneByLoanAppSeqAndCnicCatOrderByVerSeqDesc(Long seq, String cat);

}
