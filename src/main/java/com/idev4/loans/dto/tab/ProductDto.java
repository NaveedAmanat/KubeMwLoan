package com.idev4.loans.dto.tab;

public class ProductDto {

    public long prdSeq;

    public long clntSeq;

    public long loanAppSeq;

    public long limitRule;

    public Long productSeq;

    public String productName;

    public String condition;

    public long termRule;

    public int installments;

    public long prdRul;

    @Override
    public String toString() {
        return "ProductDto [prdSeq=" + prdSeq + ", clntSeq=" + clntSeq + ", loanAppSeq=" + loanAppSeq + ", limitRule=" + limitRule
                + ", productSeq=" + productSeq + ", productName=" + productName + ", condition=" + condition + ", termRule=" + termRule
                + ", installments=" + installments + ", prdRul=" + prdRul + "]";
    }

}
