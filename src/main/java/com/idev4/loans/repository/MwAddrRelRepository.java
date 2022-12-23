package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAddrRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAddrRelRepository extends JpaRepository<MwAddrRel, Long> {

    public MwAddrRel findOneByEntySeqAndEntyType(long entySeq, String entyTyp);

    public MwAddrRel findOneByEntySeqAndEntyTypeAndCrntRecFlg(long entySeq, String entyTyp, boolean flag);

    public MwAddrRel findOneByEntySeqAndEntyTypeAndAddrRelSeqAndCrntRecFlg(long entySeq, String entyTyp, Long addrRelSeq, boolean flag);

    public MwAddrRel findOneByAddrRelSeqAndEntyTypeAndEntySeqAndCrntRecFlg(long addrRelSeq, String entyTyp, long entySeq, boolean flag);

    public List<MwAddrRel> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwAddrRel> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + " from mw_addr_rel rl" + " join mw_clnt clnt on clnt.clnt_seq=rl.enty_key and rl.enty_typ='Client' and clnt.crnt_rec_flg=1"
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + " where rl.crnt_rec_flg = 1 "
            + "  and clnt.port_key IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findAllAddressRelsForClientForPorts(@Param("ports") List<Long> ports,
                                                               @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "from mw_addr_rel rl" + " join mw_clnt clnt on clnt.clnt_seq=rl.enty_key and rl.enty_typ='Client' and clnt.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + " where rl.crnt_rec_flg = 1 "
            + "  and rl.last_upd_dt > :syncDate"
            + "  and clnt.port_key IN :ports and rl.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findUpdatedAddressRelsForClientForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                   @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_clnt_rel crl on crl.clnt_rel_seq=rl.enty_key and rl.enty_typ in ('CoBorrower','ClientRel') and crl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=crl.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1 "
            + "  and crl.rel_typ_flg in (3,4) " + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findAllAddressRelsForClientRelsForPorts(@Param("ports") List<Long> ports,
                                                                   @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_clnt_rel crl on crl.clnt_rel_seq=rl.enty_key and rl.enty_typ in ('CoBorrower','ClientRel') and crl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=crl.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1  "
            + "  and rl.last_upd_dt > :syncDate and rl.last_upd_by != :lastUpdBy  " + "  and crl.rel_typ_flg in (3,4) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findUpdatedAddressRelsForClientRelsForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                       @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + " from mw_addr_rel rl "
            + " join mw_biz_aprsl ba on ba.biz_aprsl_seq=rl.enty_key and rl.enty_typ ='Business' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1 "
            + " and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findAllAddressRelsForBusinessForPorts(@Param("ports") List<Long> ports,
                                                                 @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + " from mw_addr_rel rl "
            + " join mw_biz_aprsl ba on ba.biz_aprsl_seq=rl.enty_key and rl.enty_typ ='Business' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1  "
            + "  and rl.last_upd_dt > :syncDate and rl.last_upd_by != :lastUpdBy  "
            + " and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findUpdatedAddressRelsForBusinessForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                     @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_sch_aprsl ba on ba.sch_aprsl_seq=rl.enty_key and rl.enty_typ ='SchoolAppraisal' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1 "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findAllAddressRelsForSchoolForPorts(@Param("ports") List<Long> ports,
                                                               @Param("sts") List<Long> sts);

    @Query(value = "select rl.ADDR_REL_SEQ, rl.EFF_START_DT, rl.ADDR_SEQ, rl.ENTY_KEY, rl.ENTY_TYP, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_sch_aprsl ba on ba.sch_aprsl_seq=rl.enty_key and rl.enty_typ ='SchoolAppraisal' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + "where rl.crnt_rec_flg = 1  "
            + "  and rl.last_upd_dt > :syncDate and rl.last_upd_by != :lastUpdBy  "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddrRel> findUpdatedAddressRelsForSchoolForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                   @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

}
