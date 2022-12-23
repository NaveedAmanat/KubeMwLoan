package com.idev4.loans.dto.tab;


import com.idev4.loans.service.MwClntRelService.InsrMemDto;

import java.util.List;

public class PrevInsuranceMemDto {

    public Boolean hlthFlg;

    public Long exclCatKey;

    public String mnBrdErnr;

    public Long relBrdErnr;

    public String hlthInsrPln;

    public Long planStsKey;

    public Long anlPremAmnt;

    public Long maxPlcyAmnt;

    public List<InsrMemDto> insMem;

}
