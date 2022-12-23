package com.idev4.loans.repository;

import com.idev4.loans.domain.MwQst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwQstRepository extends JpaRepository<MwQst, Long> {

    public MwQst findOneByQstSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQst> findAllByQstSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQst> findAllByQstSeq(Long seq);

    public List<MwQst> findAllByDelFlgAndCrntRecFlg(boolean recflag, boolean delflag);

    public List<MwQst> findAllByQstnrSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwQst> findAllByDelFlgAndCrntRecFlgAndQstTypKey(boolean recflag, boolean delflag, long type);

    public List<MwQst> findAllByDelFlgAndCrntRecFlgAndQstnrSeqOrderByQstSortOrdr(boolean recflag, boolean delflag, long type);

}

