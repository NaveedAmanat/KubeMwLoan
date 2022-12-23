package com.idev4.loans.ids;

import java.io.Serializable;

public class MwClntPscId implements Serializable {

    //(PSC_SEQ,qst_seq, ANSWR_SEQ,loan_app_Seq);
    private static final long serialVersionUID = 1L;

    private long pscSeq;
    private long qstSeq;
    private long answrSeq;
    private long loanAppSeq;

}
