package com.idev4.loans.ids;

import java.io.Serializable;

public class MwLoanAppDocId implements Serializable {

    //(LOAN_APP_DOC_SEQ,LOAN_APP_SEQ,DOC_SEQ,CRNT_REC_FLG);
    private static final long serialVersionUID = 1L;

    public Long loanAppDocSeq;
    public Long loanAppSeq;
    public Long docSeq;
    //public Boolean crntRecFlg;
}
