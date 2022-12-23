package com.idev4.loans.ids;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

/**
 * A MwAddr.
 */

public class MwMntrngChksAdcQstnrId implements Serializable {

    private static final long serialVersionUID = 1L;

    public BigInteger mntrngChksAdcQstnrSeq;

    public Instant effStartDt;

    public BigInteger mntrngChksSeq;

}
