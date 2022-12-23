package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAnmlRgstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBizAprsl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAnmlRgstrRepository extends JpaRepository<MwAnmlRgstr, Long> {

    public List<MwAnmlRgstr> findAllByCrntRecFlg(boolean flag);

    public List<MwAnmlRgstr> findAllByLoanAppSeqAndCrntRecFlg(Long seq, boolean flag);

    public MwAnmlRgstr findOneByAnmlRgstrSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwAnmlRgstr> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                           boolean flag);

    @Query(value = " select ar.* " + "  from MW_ANML_RGSTR ar "
            + " join mw_loan_app app on app.loan_app_seq=ar.loan_app_seq and app.crnt_rec_flg=1 " + " where ar.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAnmlRgstr> findAllMwAnmlRgstrForLoanForPorts(@Param("ports") List<Long> ports,
                                                               @Param("sts") List<Long> sts);

    @Query(value = " select ar.* " + "  from MW_ANML_RGSTR ar "
            + " join mw_loan_app app on app.loan_app_seq=ar.loan_app_seq and app.crnt_rec_flg=1 " + " where ar.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and ar.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and ar.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwAnmlRgstr> findUpdatedMwAnmlRgstrForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                   @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    @Query(value = " select apr.ANML_RGSTR_SEQ , apr.EFF_START_DT, apr.LOAN_APP_SEQ, apr.RGSTR_CD, apr.TAG_NUM, apr.ANML_KND, apr.ANML_TYP, apr.ANML_CLR , apr.ANML_BRD, apr.PRCH_DT, apr.AGE_YR, apr.AGE_MNTH, apr.PRCH_AMT, apr.PIC_DT, apr.CRTD_BY, apr.CRTD_DT, apr.LAST_UPD_BY, apr.LAST_UPD_DT, apr.DEL_FLG, apr.EFF_END_DT, apr.CRNT_REC_FLG, apr.ANML_STS "
            + "  from MW_ANML_RGSTR apr " + " join mw_loan_app app on app.loan_app_seq=apr.loan_app_seq and app.crnt_rec_flg=1 "
            + " where apr.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<Object[]> findAllMwAnmlRgstrForLoanForPortsWithoutPics(@Param("ports") List<Long> ports,
                                                                       @Param("sts") List<Long> sts);

    @Query(value = " select apr.ANML_RGSTR_SEQ , apr.EFF_START_DT, apr.LOAN_APP_SEQ, apr.RGSTR_CD, apr.TAG_NUM, apr.ANML_KND, apr.ANML_TYP, apr.ANML_CLR , apr.ANML_BRD, apr.PRCH_DT, apr.AGE_YR, apr.AGE_MNTH, apr.PRCH_AMT, apr.PIC_DT, apr.CRTD_BY, apr.CRTD_DT, apr.LAST_UPD_BY, apr.LAST_UPD_DT, apr.DEL_FLG, apr.EFF_END_DT, apr.CRNT_REC_FLG, apr.ANML_STS "
            + "  from MW_ANML_RGSTR apr " + " join mw_loan_app app on app.loan_app_seq=apr.loan_app_seq and app.crnt_rec_flg=1 "
            + "  where and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and apr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and ar.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<Object[]> findUpdatedMwAnmlRgstrForLoanForPortsLastUpdatedbyNotWithoutPics(@Param("syncDate") Instant syncDate,
                                                                                           @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
