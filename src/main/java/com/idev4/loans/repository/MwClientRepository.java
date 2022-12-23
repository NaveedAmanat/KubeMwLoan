package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MwClientRepository extends JpaRepository<MwClient, Long> {

    public MwClient findOneByCnicNum(Long cnicNum);

    public MwClient findOneByCnicNumAndCrntRecFlg(Long cnicNum, boolean flag);

    public List<MwClient> findAllByCrntRecFlg(boolean flag);

    public MwClient findOneByClntSeqAndCrntRecFlg(long seq, boolean flag);

    public MwClient findOneByClntIdAndCrntRecFlg(String seq, boolean flag);

    public List<MwClient> findAllByPortKeyAndSyncFlgAndCrntRecFlg(long seq, boolean sflag, boolean flag);

    public List<MwClient> findAllByPortKeyAndCrntRecFlg(long seq, boolean flag);

    public List<MwClient> findAllByLastUpdDtAfterAndPortKeyAndLastUpdByNotAndCrntRecFlg(Instant lastUpdDt, long portKey, String lastUpdBy,
                                                                                        boolean flag);

    public List<MwClient> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                        boolean flag);

    public List<MwClient> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortKeyInOrderByEffStartDt(Instant lastUpdDt,
                                                                                                                     String lastUpdBy, boolean flag, List<Long> portKey);

    public List<MwClient> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseAndCrntRecFlgAndPortKeyIn(Instant lastUpdDt, String lastUpdBy,
                                                                                                 boolean flag, List<Long> portKey);

    public List<MwClient> findAllByCrntRecFlgAndPortKeyIn(boolean flag, List<Long> portKey);

    @Query(value = "select distinct clnt.* from mw_clnt clnt  \r\n" + "join mw_loan_app app on app.clnt_seq=clnt.clnt_seq \r\n"
            + "join mw_port port on port.port_seq=app.port_seq \r\n" + "where port.brnch_seq=:brnchSeq", nativeQuery = true)
    public List<MwClient> findAllClntsForBrnch(@Param("brnchSeq") Long brnchSeq);

    @Query(value = "select clnt.* from mw_clnt clnt join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1"
            + " where clnt.crnt_rec_flg=1 and app.loan_app_sts = :sts and clnt.clnt_id= :clntId", nativeQuery = true)
    public List<MwClient> findIfActiveLoan(@Param("clntId") String clntId, @Param("sts") Long sts);

    @Query(value = "select clnt.* from mw_clnt clnt join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1"
            + " where clnt.crnt_rec_flg=1 and clnt.port_key in :portKey" + " and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClient> findAllByPortKeyInAndStsIn(@Param("portKey") List<Long> portKey, @Param("sts") List<Long> sts);

    public MwClient findOneByClntSeqAndCnicNumAndCrntRecFlg(long clntSeq, long cnicNum, boolean flag);
}
