package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdGrp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MwPrdGrpRepository extends JpaRepository<MwPrdGrp, Long> {

    @Query(value = "select Distinct * from (select grp.* from mw_prd_grp grp\r\n"
            + "join mw_prd prd on prd.prd_grp_seq=grp.prd_grp_seq and prd.crnt_rec_flg=1\r\n"
            + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq and bprel.crnt_rec_flg=1 \r\n"
            + "where grp.crnt_rec_flg=1 and grp.prd_grp_sts=(select val.ref_cd_seq from mw_ref_cd_val val join mw_ref_cd_grp grp on grp.ref_cd_grp_seq=val.ref_cd_grp_key and grp.crnt_rec_flg=1 where val.crnt_rec_flg=1 and val.ref_cd='0200' and grp.ref_cd_grp='0016') \r\n"
            + "and prd.prd_sts_key=(select val.ref_cd_seq from mw_ref_cd_val val join mw_ref_cd_grp grp on grp.ref_cd_grp_seq=val.ref_cd_grp_key and grp.crnt_rec_flg=1 where val.crnt_rec_flg=1 and val.ref_cd='0200' and grp.ref_cd_grp='0016') \r\n"
            + "and bprel.brnch_seq= :brnchSeq order by prd_grp_nm)", nativeQuery = true)
    public List<MwPrdGrp> findActiveMwPrdGrpForBranch(@Param("brnchSeq") Long branchSeq);
}
