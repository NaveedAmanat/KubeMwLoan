package com.idev4.loans.dto.tab;

import java.util.List;

public class BusinessAprslDto {

    public BusinessAppraisalBasicInfo basic_info;

    public AddrDto business_appraisal_address;

    public IncomeDto income_info;

    public List<ExpenseDto> expense_list;

    public List<BizAprslEstLvstkFinDto> est_lvstk_fin;

    public List<BizAprslExtngLvstkDto> extng_lvstk;

    @Override
    public String toString() {
        return "BusinessAprslDto [basic_info=" + ((basic_info == null) ? "null" : basic_info.toString())
                + ", business_appraisal_address="
                + ((business_appraisal_address == null) ? "null" : business_appraisal_address.toString()) + ", income_info="
                + ((income_info == null) ? "null" : income_info.toString()) + ", expense_list="
                + ((expense_list == null) ? "null" : expense_list.toString()) + "]";
    }

}
