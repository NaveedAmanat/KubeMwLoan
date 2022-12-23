package com.idev4.loans.dto;

import com.idev4.loans.dto.tab.ExpextedLoanDto;
import com.idev4.loans.dto.tab.LoanAppMntrngChks;

import java.util.Date;
import java.util.List;

public class LoanAppDto {

    public long clientSeq;

    public long loanProd;

    public long loanProdGrp;

    public double reqAmount;

    public double recAmount;

    public long loanAppSeq;

    public long portfolioSeq;

    public Date startDate;

    public String comment;

    public double approvedAmount;

    public long prdRul;

    public long termRule;

    public long limitRule;

    public long totIncmOfErngMemb;

    public String bizDtl;

    public long pscScore;

    public boolean tblScrn;

    public long loanCyclNum;

    public Long tokenNum;

    public Date tokenDate;

    public long prntLoanAppSeq;

    public Long rejectionReasonCd;

    public Long loan_app_seq;

    public Long loan_utl_sts_seq;

    public Long loan_app_sts_seq;

    public String loan_utl_cmnt;

    public Boolean relAddrAsClntFlg;

    public Long previousAmount = 0L;

    public Long previousPscScore;

    public Double latitude;

    public Double longitude;

    public List<ExpextedLoanDto> loan_util_plan;

    public LoanAppMntrngChks loan_app_mntrng_chk;

    public Date loan_app_sts_dt;

    public long previousLoanCyclNum = 0L;

    public String loanProdDesc;

    public Long remainingInstallments = 0L;

    public Long remainingOutstandingOfPreviousLoan = 0L;
}
