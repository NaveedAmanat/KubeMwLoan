package com.idev4.loans.repository;

import com.idev4.loans.domain.MwForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MwForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwFormRepository extends JpaRepository<MwForm, Long> {

    public List<MwForm> findAllByFormSeqInAndCrntRecFlg(List<Long> ids, boolean flag);

    @Query(value = "select  frm.* from mw_form frm\r\n"
            + "join mw_prd_form_rel rel on rel.form_seq=frm.form_seq and REL.crnt_rec_flg=1\r\n"
            + "where frm.crnt_rec_flg=1 and rel.prd_seq=:prdSeq order by REL.form_sort_ordr asc", nativeQuery = true)
    public List<MwForm> findAllOrderedFormsForPrd(@Param("prdSeq") Long prdSeq);
}
