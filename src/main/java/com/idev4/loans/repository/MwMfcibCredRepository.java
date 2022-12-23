package com.idev4.loans.repository;

import com.idev4.loans.domain.MwMfcibCred;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MwMfcibCredRepository extends JpaRepository<MwMfcibCred, Long> {

    public MwMfcibCred findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg(String companyNm, String credTyp, Boolean crntRecFlg);

}
