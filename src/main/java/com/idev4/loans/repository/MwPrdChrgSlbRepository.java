package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdChrgSlb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdChrgSlbRepository extends JpaRepository<MwPrdChrgSlb, Long> {

    public MwPrdChrgSlb findOneByPrdChrgSlbSeqAndCrntRecFlg(Long prdChrgSeq, boolean flag);

    public List<MwPrdChrgSlb> findAllByPrdChrgSeqAndCrntRecFlg(Long prdChrgSeq, boolean flag);

    @Query(value = "select slb.* from MW_PRD_CHRG_SLB slb  "
            + "join mw_prd_chrg chrg on chrg.PRD_CHRG_SEQ = slb.PRD_CHRG_SEQ and chrg.crnt_rec_flg=1 "
            + "join mw_prd prd on prd.prd_seq = chrg.prd_seq and prd.crnt_rec_flg=1 "
            + "join mw_brnch_prd_rel rel on REL.prd_seq =  prd.prd_seq and rel.crnt_rec_flg=1 "
            + "where (slb.crnt_rec_flg=1 or slb.del_flg=1) and rel.brnch_seq= :brnchSeq"
            + "  and slb.last_upd_dt > :syncDate", nativeQuery = true)
    public List<MwPrdChrgSlb> findUpdatedMwPrdChrgSlbForBranch(@Param("brnchSeq") Long branchSeq,
                                                               @Param("syncDate") Instant syncDate);
}
