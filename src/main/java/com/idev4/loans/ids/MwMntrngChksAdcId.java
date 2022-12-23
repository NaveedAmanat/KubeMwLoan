package com.idev4.loans.ids;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

/**
 * A MwAddr.
 */

public class MwMntrngChksAdcId implements Serializable {

    private static final long serialVersionUID = 1L;

    public BigInteger mntrngChksSeq;

    public Instant effStartDt;

}
