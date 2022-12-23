package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwLoanAppDoc;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class DocDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String doc_img;

    public Long doc_seq;

    public String eff_start_dt;

    public Long loan_app_doc_seq;

    public Long loan_app_seq;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer del_flg;

    // Added by Zohaib Asim - Dated 25-08-2021 - MFCIB CNIC
    public Long cnic_num;
    public String company_nm;
    // End by Zohaib Asim

    public Long docAprvlRjtn;

    @Override
    public String toString() {
        return "DocDto{" +
                "crnt_rec_flg=" + crnt_rec_flg +
                ", crtd_by='" + crtd_by + '\'' +
                ", doc_img='" + doc_img + '\'' +
                ", doc_seq=" + doc_seq +
                ", eff_start_dt='" + eff_start_dt + '\'' +
                ", loan_app_doc_seq=" + loan_app_doc_seq +
                ", loan_app_seq=" + loan_app_seq +
                ", crtd_dt='" + crtd_dt + '\'' +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", last_upd_dt='" + last_upd_dt + '\'' +
                ", sync_flg=" + sync_flg +
                ", del_flg=" + del_flg +
                ", cnic_num=" + cnic_num +
                ", company_nm='" + company_nm + '\'' +
                ", docAprvlRjtn='" + docAprvlRjtn + '\'' +
                '}';
    }

    public MwLoanAppDoc DtoToDomain(DateFormat formatter, MwLoanAppDoc document) {
        document.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        document.setCrtdBy(crtd_by);
        document.crtdDt(Instant.now());
        document.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        document.setDocImg(doc_img);
        document.setDocSeq(doc_seq);
        try {
            document.setEffStartDt(Instant.now());
            document.setLastUpdDt(Common.getZonedInstant(Instant.now()));
            document.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.setLoanAppDocSeq(loan_app_doc_seq);
        document.setLoanAppSeq(loan_app_seq);
        document.setLastUpdBy(last_upd_by);
        // Added by Zohaib Asim - Dated 25-08-2021 - MFCIB CNIC
        document.setCnicNum(cnic_num);
        document.setCompanyNm(company_nm);
        // End by Zohaib Asim

        document.setDocAprvlRjtn(docAprvlRjtn);
        return document;
    }

    public void DomainToDto(MwLoanAppDoc doc) {
        crnt_rec_flg = (doc.isCrntRecFlg() == null) ? 0 : (doc.isCrntRecFlg() ? 1 : 0);
        crtd_by = doc.getCrtdBy();
        doc_img = doc.getDocImg();
        doc_seq = doc.getDocSeq();
        eff_start_dt = (doc.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(doc.getEffStartDt(), false);
        loan_app_doc_seq = doc.getLoanAppDocSeq();
        loan_app_seq = doc.getLoanAppSeq();

        last_upd_by = doc.getLastUpdBy();
        last_upd_dt = (doc.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(doc.getLastUpdDt(), false);
        crtd_dt = (doc.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(doc.getCrtdDt(), false);
        sync_flg = (doc.getSyncFlg() == null) ? 0 : (doc.getSyncFlg() ? 1 : 0);
        del_flg = (doc.isDelFlg() == null) ? 0 : (doc.isDelFlg() ? 1 : 0);
        // Added by Zohaib Asim - Dated 25-08-2021 - MFCIB CNIC
        cnic_num = doc.getCnicNum();
        company_nm = doc.getCompanyNm();
        // End by Zohaib Asim

        docAprvlRjtn = doc.getDocAprvlRjtn();
    }

}
