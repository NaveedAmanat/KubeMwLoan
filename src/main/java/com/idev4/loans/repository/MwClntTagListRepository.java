package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MwClntTagListRepository extends JpaRepository<MwClntTagList, Long> {

    MwClntTagList findOneByCnicNumAndDelFlg(long cnic, boolean del);

    MwClntTagList findOneByCnicNumAndTagsSeqAndDelFlg(long cnic, Long tagSeq, boolean del);

    MwClntTagList findOneByLoanAppSeqAndDelFlg(long loanSeq, boolean del);

    MwClntTagList findOneByLoanAppSeqAndTagsSeqAndDelFlg(long loanSeq, Long tagSeq, boolean del);

    // Added by Zohaib Asim - Dated 14-09-2021 - Production Issue - Nacta-ClntRel-CNIC Matach
    @Query(value = "SELECT CTL.*\n" +
            "  FROM MW_CLNT_TAG_LIST CTL\n" +
            " WHERE CTL.CLNT_TAG_LIST_SEQ =\n" +
            "       (SELECT MAX (CTL.CLNT_TAG_LIST_SEQ)\n" +
            "          FROM MW_CLNT_TAG_LIST CTL\n" +
            "         WHERE CTL.CNIC_NUM = :cnic AND CTL.DEL_FLG = :delFlg)", nativeQuery = true)
    MwClntTagList findDistinctByCnicNumAndDelFlg(@Param("cnic") long cnic, @Param("delFlg") long delFlg);
    // End by Zohaib Asim
}
