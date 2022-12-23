package com.idev4.loans.ids;

import java.io.Serializable;

/**
 * A MwBizExpDtl.
 */

public class MwBizExpDtlId implements Serializable {

    //(exp_dtl_SEQ, EXP_CTGRY_KEY, BIZ_APRSL, expTypKey)
    private static final long serialVersionUID = 1L;

    public Long expDtlSeq;
    public Long expCtgryKey;
    public Long mwBizAprsl;
    public Long expTypKey;
}
