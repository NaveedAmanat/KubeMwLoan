package com.idev4.loans.ids;

import java.io.Serializable;

/**
 * A MwLoanAppMntrngChks.
 */
public class MwLoanAppMntrngChksId implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long loanAppMntrngChksSeq;

    public Integer chkFlg;

    public MwLoanAppMntrngChksId() {
    }

    public MwLoanAppMntrngChksId(Long loanAppMntrngChksSeq, Integer chkFlg) {
        this.loanAppMntrngChksSeq = loanAppMntrngChksSeq;
        this.chkFlg = chkFlg;
    }

}
