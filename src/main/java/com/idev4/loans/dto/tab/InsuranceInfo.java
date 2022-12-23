package com.idev4.loans.dto.tab;

import java.util.List;

public class InsuranceInfo {
    public InsuranceInfoHeader insurance_info_header;
    public List<InsuranceMember> insurance_members;

    @Override
    public String toString() {
        return "InsuranceInfo [insurance_info_header=" + ((insurance_info_header == null) ? "null" : insurance_info_header.toString()) + ", insurance_members="
                + ((insurance_members == null) ? "null" : insurance_members.toString()) + "]";
    }


}
