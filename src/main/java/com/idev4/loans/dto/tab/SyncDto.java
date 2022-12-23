package com.idev4.loans.dto.tab;

import java.io.Serializable;
import java.util.List;

public class SyncDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<ClntBasicInfoDto> mw_clnt;

    public List<ClientCnicToken> mw_cnic_tkn;

    public List<AddressDto> mw_addr;

    public List<AddrRelDto> mw_addr_rel;

    public List<ClientPermAddressDto> mw_clnt_perm_addr;

    public List<LoanAppDto> mw_loan_app;

    public List<LoanAppCrdtScrDto> mw_loan_app_crdt_scr;

    public List<OtherLoanDto> mw_mfcib_oth_outsd_loan;

    public List<InsuranceInfoHeader> mw_clnt_hlth_insr;

    public List<InsuranceMember> mw_hlth_insr_memb;

    public List<ClntRelDto> mw_clnt_rel;

    public List<ExpextedLoanDto> mw_loan_utl_plan;

    public List<BusinessAppraisalBasicInfo> mw_biz_aprsl;

    public List<BizAppIncmHdrDto> mw_biz_aprsl_incm_hdr;

    public List<BizAprslIncmDtlDto> mw_biz_aprsl_incm_dtl;

    public List<ExpenseDto> mw_biz_exp_dtl;

    public List<PSC> mw_clnt_psc;

    public List<SchoolAppraisalBasicInfoDto> mw_sch_aprsl;

    public List<SchoolGradesDto> mw_sch_grd;

    public List<SchoolAttendanceDto> mw_sch_atnd;

    public List<SchoolInfrastructureDto> mw_sch_asts;

    public List<SchoolQuestionDto> mw_sch_qlty_chk;

    public List<DocDto> mw_loan_app_doc;

    public List<AnmlRgstrDto> mw_anml_rgstr;

    public List<BizAprslEstLvstkFinDto> mw_biz_aprsl_est_lvstk_fin;

    public List<BizAprslExtngLvstkDto> mw_biz_aprsl_extng_lvstk;

    public List<LoanAppMntrngChks> mw_loan_app_mntrng_chks;

    public List<MntrngChksAdcDto> mw_mntrng_chks_adc;

    public List<MntrngChksAdcQstnrDto> mw_mntrng_chks_adc_qstnr;

    public List<CnicUpdDTO> mw_cnic_upd;

    public String srvrInst;

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
