package com.idev4.loans.repository;

import com.idev4.loans.domain.MwSancList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MwSancListRepository extends JpaRepository<MwSancList, Long> {

    public List<MwSancList> findAllByCnicNum(String cnic);

    public List<MwSancList> findAllByCnicNumAndCntryAndCrntRecFlg(String cnic, String country, Long crntRecFlg);

}
