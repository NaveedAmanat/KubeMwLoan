package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwAddr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAddrRepository extends JpaRepository<MwAddr, Long> {

    public List<MwAddr> findAllByCrntRecFlg(boolean flag);

    public MwAddr findOneByAddrSeqAndCrntRecFlg(long seq, boolean flag);

    public MwAddr findOneByAddrSeqAndSyncFlgAndCrntRecFlg(long seq, boolean sFlag, boolean flag);

    public List<MwAddr> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean flag);

    public List<MwAddr> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                      boolean flag);

    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + " from mw_addr_rel rl " + " join mw_clnt clnt on clnt.clnt_seq=rl.enty_key and rl.enty_typ='Client' and clnt.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + "where rl.crnt_rec_flg = 1 "
            + "  and clnt.port_key IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findAllAddrForClientForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + " from mw_addr_rel rl " + " join mw_clnt clnt on clnt.clnt_seq=rl.enty_key and rl.enty_typ='Client' and clnt.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + "where rl.crnt_rec_flg = 1 "
            + "  and addr.last_upd_dt > :syncDate"
            + "  and clnt.port_key IN :ports and addr.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findUpdatedAddrForClientForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                         @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    //// ---------------- CLNT_REL ----------------- //
    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + " from mw_addr_rel rl "
            + " join mw_clnt_rel crl on crl.clnt_rel_seq=rl.enty_key and rl.enty_typ in ('CoBorrower','ClientRel') and crl.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=crl.loan_app_seq and app.crnt_rec_flg=1 " + " where rl.crnt_rec_flg = 1"
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findAllAddrForClientRelForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + " from mw_addr_rel rl "
            + " join mw_clnt_rel crl on crl.clnt_rel_seq=rl.enty_key and rl.enty_typ in ('CoBorrower','ClientRel') and crl.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=crl.loan_app_seq and app.crnt_rec_flg=1 " + " where rl.crnt_rec_flg = 1"
            + "  and addr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and addr.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findUpdatedAddrForClientRelForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                            @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    //// ---------------- Business ----------------- //
    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_biz_aprsl ba on ba.biz_aprsl_seq=rl.enty_key and rl.enty_typ ='Business' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 where rl.crnt_rec_flg = 1"
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findAllAddrForBusinessForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_biz_aprsl ba on ba.biz_aprsl_seq=rl.enty_key and rl.enty_typ ='Business' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 where rl.crnt_rec_flg = 1"
            + "  and addr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and addr.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts ", nativeQuery = true)
    public List<MwAddr> findUpdatedAddrForBusinessForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                           @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    //// ---------------- School----------------- //
    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_sch_aprsl ba on ba.sch_aprsl_seq=rl.enty_key and rl.enty_typ ='SchoolAppraisal' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 " + " where rl.crnt_rec_flg = 1"
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAddr> findAllAddrForSchoolForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select addr.ADDR_SEQ, addr.EFF_START_DT, addr.HSE_NUM, addr.STRT, OTH_DTL, addr.CITY_SEQ, addr.ADDR_TYP_KEY, addr.CMNTY_SEQ, addr.VLG, addr.LONGITUDE, addr.LATITUDE, addr.CRTD_BY, addr.CRTD_DT, addr.LAST_UPD_BY, addr.LAST_UPD_DT, addr.DEL_FLG, addr.EFF_END_DT, addr.CRNT_REC_FLG, addr.SYNC_FLG "
            + "from mw_addr_rel rl "
            + " join mw_sch_aprsl ba on ba.sch_aprsl_seq=rl.enty_key and rl.enty_typ ='SchoolAppraisal' and ba.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " join mw_addr addr on addr.addr_seq=rl.addr_seq and addr.crnt_rec_flg=1 " + " where rl.crnt_rec_flg = 1 "
            + "  and addr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and addr.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts ", nativeQuery = true)
    public List<MwAddr> findUpdatedAddrForSchoolForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                         @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
