package com.idev4.loans.repository;

import com.idev4.loans.domain.MwTblIndx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MwTblIndxRepository extends JpaRepository<MwTblIndx, Long> {

    public MwTblIndx findOneByTblNm(String tblNm);

}
