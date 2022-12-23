package com.idev4.loans.dto.tab;

import java.util.List;

public class IncomeDto {
    public BizAppIncmHdrDto income_header;
    public List<BizAprslIncmDtlDto> income_list;

    @Override
    public String toString() {
        return "IncomeDto [income_header=" + ((income_header == null) ? "null" : income_header.toString()) + ", income_list=" + ((income_list == null) ? "null" : income_list.toString()) + "]";
    }


}
