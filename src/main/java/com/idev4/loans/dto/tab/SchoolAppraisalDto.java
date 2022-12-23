package com.idev4.loans.dto.tab;

import java.util.List;

public class SchoolAppraisalDto {

    public SchoolAppraisalBasicInfoDto basic_info;

    public List<SchoolGradesDto> grades;

    public SchoolAttendanceDto attendance;

    public List<BizAprslIncmDtlDto> income_list;

    public List<ExpenseDto> expense_list;

    public AddrDto school_appraisal_address;
}
