package com.idev4.loans.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "MW_LOAN_APP_MFCIB_DATA")
public class MwLoanAppMfcibData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_DOC_SEQ")
    @SequenceGenerator(sequenceName = "LOAN_DOC_SEQ", allocationSize = 1, name = "LOAN_DOC_SEQ")
    @Column(name = "LOAN_APP_MFCIB_SEQ")
    private Long loanAppMfcibSeq;

    @Column(name = "LOAN_APP_DOC_SEQ")
    private Long loanAppDocSeq;

    @Column(name = "LEVEL1")
    private String level1;

    @Column(name = "LEVEL2")
    private String level2;

    @Column(name = "LEVEL3")
    private String level3;

    @Column(name = "LEVEL4")
    private String level4;

    @Column(name = "TAG_NM")
    private String tagNm;

    @Column(name = "TAG_VAL")
    private String tagVal;

    @Column(name = "FILE_NO")
    private String fileNo;

    @Column(name = "TRNX_NO")
    private String trnxNo;

    @Column(name = "LOAN_NO")
    private String loanNo;

    @Column(name = "SEQ_NO")
    private String seqNo;

    @Column(name = "P90")
    private String p90;

    @Column(name = "P60")
    private String p60;

    @Column(name = "P30")
    private String p30;

    @Column(name = "X")
    private String x;

    @Column(name = "P150")
    private String p150;

    @Column(name = "P120")
    private String p120;

    @Column(name = "OK")
    private String ok;

    @Column(name = "P180")
    private String p180;

    @Column(name = "LOSS")
    private String loss;

    @Column(name = "MFI_DEFAULT")
    private String mfiDefault;

    @Column(name = "LATE_PMT_DAYS")
    private String latePmtDays;

    @Column(name = "LATE_PAYMENT_1TO15")
    private String latePayment1To15;

    @Column(name = "LATE_PAYMENT_16TO20")
    private String latePayment16To20;

    @Column(name = "LATE_PAYMENT_21TO29")
    private String latePayment21To29;

    @Column(name = "LATE_PAYMENT_30")
    private String latePayment30;

    @Column(name = "COLLATERAL_AMT")
    private String collateralAmt;

    @Column(name = "ACCT_SEQ_NO")
    private String acctSeqNo;

    @Column(name = "COLL_TYP")
    private String collTyp;

    @Column(name = "CBRWR_GRNTR_CNIC")
    private String cbrwrGrntrCnic;

    @Column(name = "GRNTR_FILE_NO")
    private String grntrFileNo;

    @Column(name = "GRNTR_IN_FAVR")
    private String grntrInFavr;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "NTN")
    private String ntn;

    @Column(name = "FTHR_HSBND_M_NM")
    private String fthrHsbndMNm;

    @Column(name = "LAST_NM")
    private String lastNm;

    @Column(name = "FIRST_NM")
    private String firstNm;

    @Column(name = "DEPENDANTS")
    private String dependants;

    @Column(name = "QUALIFICATION")
    private String qualification;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "TRNX_RESULT")
    private String trnxResult;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "MIDDLE_NM")
    private String middleNm;

    @Column(name = "FTHR_HSBND_L_NM")
    private String fthrHsbndLNm;

    @Column(name = "CHECKER")
    private String checker;

    @Column(name = "REPORT_DT")
    private String reportDt;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "NATL_TYP")
    private String natlTyp;

    @Column(name = "IS_SELF")
    private String isSelf;

    @Column(name = "FTHR_HSBND_F_NM")
    private String fthrHsbndFNm;

    @Column(name = "MARITAL_STS")
    private String maritalSts;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "TRNX_DT")
    private String trnxDt;

    @Column(name = "REF_NO")
    private String refNo;

    @Column(name = "MAKER")
    private String maker;

    @Column(name = "CNIC")
    private String cnic;

    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "BRWR_TYP")
    private String brwrTyp;

    @Column(name = "EMPLOYER")
    private String employer;

    @Column(name = "REPORTED_ON")
    private String reportedOn;

    @Column(name = "SELF_EMPLOYED")
    private String selfEmployed;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "RSDNTL_ADDRS")
    private String rsdntlAddrs;

    @Column(name = "PHONE1")
    private String phone1;

    @Column(name = "PHONE2")
    private String phone2;

    @Column(name = "CUR_RSDNTAL_ADDRS_DT")
    private String curRsdntalAddrsDt;

    @Column(name = "PRMNT_ADDRS_DT")
    private String prmntAddrsDt;

    @Column(name = "PRV_RSDNTAL_ADDRS")
    private String prvRsdntalAddrs;

    @Column(name = "PRV_RSDNTAL_ADDRS_DT")
    private String prvRsdntalAddrsDt;

    @Column(name = "EMPLOYER_BUSINESS_DT")
    private String employerBusinessDt;

    @Column(name = "ODDS")
    private String odds;

    @Column(name = "SCORE")
    private String score;

    @Column(name = "PROB_OF_DEFALUT")
    private String probOfDefalut;

    @Column(name = "PERCENTILE_RISK")
    private String percentileRisk;

    @Column(name = "SBP_RISK_LEVEL")
    private String sbpRiskLevel;

    @Column(name = "PYMNT_STS")
    private String pymntSts;

    @Column(name = "STS_MNTH")
    private String stsMnth;

    @Column(name = "OVERDUE_AMT")
    private String overdueAmt;

    @Column(name = "LOAN_LESS_10K")
    private String loanLess10K;

    @Column(name = "CURRENT_30PLUS")
    private String current30Plus;

    @Column(name = "CURRENT_60PLUS")
    private String current60Plus;

    @Column(name = "CURRENT_90PLUS")
    private String current90Plus;

    @Column(name = "ENQUIRY_COUNT")
    private String enquiryCount;

    @Column(name = "CLOSE_WITHIN_MATURITY")
    private String closeWithinMaturity;

    @Column(name = "CLOSE_AFTER_MATURITY")
    private String closeAfterMaturity;

    @Column(name = "LOAN_LIMIT")
    private String loanLimit;

    @Column(name = "MONTH24_30PLUS")
    private String month2430Plus;

    @Column(name = "MONTH24_60PLUS")
    private String month2460Plus;

    @Column(name = "FILE_CREATION_DT")
    private String fileCreationDt;

    @Column(name = "CS_NAME")
    private String csName;

    @Column(name = "LOAN_COUNT")
    private String loanCount;

    @Column(name = "LOAN_OS")
    private String loanOs;

    @Column(name = "CS_CTGRY")
    private String csCtgry;

    @Column(name = "DEFAULT_COUNT")
    private String defaultCount;

    @Column(name = "MONTH24_90PLUS")
    private String month2490Plus;

    @Column(name = "LOAN_ABOVE_10K")
    private String loanAbove10K;

    @Column(name = "DEFAULT_OS")
    private String defaultOs;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "CBR_FILE_NO")
    private String cbrFileNo;

    @Column(name = "OTHR_BWR")
    private String othrBwr;

    @Column(name = "COUT_NAME")
    private String coutName;

    @Column(name = "DCLRTN_DT")
    private String dclrtnDt;

    @Column(name = "PRMNT_CITY")
    private String prmntCity;

    @Column(name = "PRMNT_ADDRS")
    private String prmntAddrs;

    @Column(name = "REVIEWS")
    private String reviews;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "REF_DT")
    private String refDt;

    @Column(name = "ASSOC_TY")
    private String assocTy;

    @Column(name = "ENQ_GROUP_ID")
    private String enqGroupId;

    @Column(name = "ACCT_TY")
    private String acctTy;

    @Column(name = "ENQ_STS")
    private String enqSts;

    @Column(name = "DISPUTE")
    private String dispute;

    @Column(name = "MEM_NM")
    private String memNm;

    @Column(name = "MAPPED_ACCT_TY")
    private String mappedAcctTy;

    @Column(name = "SEPRTE_DT")
    private String seprteDt;

    @Column(name = "SUBBRN_NAME")
    private String subbrnName;

    @Column(name = "APPLICATION_DT")
    private String applicationDt;

    @Column(name = "CO_BRWR_NM")
    private String coBrwrNm;

    @Column(name = "GRNTR_DT")
    private String grntrDt;

    @Column(name = "ASSOC")
    private String assoc;

    @Column(name = "GRNTE_AMT")
    private String grnteAmt;

    @Column(name = "INVOCATION_DT")
    private String invocationDt;

    @Column(name = "SECTION")
    private String section;

    @Column(name = "SUB_OBJ")
    private String subObj;

    @Column(name = "CCP_MSTR_LIMIT")
    private String ccpMstrLimit;

    @Column(name = "RSCHEDL_FLG")
    private String rschedlFlg;

    @Column(name = "TERM")
    private String term;

    @Column(name = "MATURITY_DT")
    private String maturityDt;

    @Column(name = "HIGH_CREDIT")
    private String highCredit;

    @Column(name = "RESCHEDULE_DT")
    private String rescheduleDt;

    @Column(name = "CLASS_CATG")
    private String classCatg;

    @Column(name = "BNC_CHQ")
    private String bncChq;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CCP_MSTR_CURRENCY")
    private String ccpMstrCurrency;

    @Column(name = "STATUS_DT")
    private String statusDt;

    @Column(name = "BALANCE")
    private String balance;

    @Column(name = "LAST_PYMT")
    private String lastPymt;

    @Column(name = "LOAN_CLASS_DESC")
    private String loanClassDesc;

    @Column(name = "ACCT_STS")
    private String acctSts;

    @Column(name = "SECURE")
    private String secure;

    @Column(name = "MIN_AMT_DUE")
    private String minAmtDue;

    @Column(name = "REPAYMENT_FREQ")
    private String repaymentFreq;

    @Column(name = "OPEN_DT")
    private String openDt;

    @Column(name = "LAST_PYMNT_DT")
    private String lastPymntDt;

    @Column(name = "CLASSIFICATION_NATURE")
    private String classificationNature;

    @Column(name = "LITIGATION_AMT")
    private String litigationAmt;

    @Column(name = "BOUNCED_REPAYMENT_CHEQUES")
    private String bouncedRepaymentCheques;

    @Column(name = "SECURITY_COLLATERAL")
    private String securityCollateral;

    @Column(name = "RESTRUCTURING_AMT")
    private String restructuringAmt;

    @Column(name = "WRITEOFF_TYP")
    private String writeoffTyp;

    @Column(name = "WRITE_OFF_AMT")
    private String writeOffAmt;

    @Column(name = "WRITEOFF_DT")
    private String writeoffDt;

    @Column(name = "REL_DT")
    private String relDt;

    @Column(name = "UPD_MAPPED_ACCT_TY")
    private String updMappedAcctTy;

    @Column(name = "UPD_CURRENCY")
    private String updCurrency;

    @Column(name = "UPD_STS")
    private String updSts;

    @Column(name = "ORG_STS_DT")
    private String orgStsDt;

    @Column(name = "ORG_STS")
    private String orgSts;

    @Column(name = "ORG_RTR")
    private String orgRtr;

    @Column(name = "UPD_AMT")
    private String updAmt;

    @Column(name = "RECOVERY_DATE")
    private String recoveryDate;

    @Column(name = "ORG_CURRENCY")
    private String orgCurrency;

    @Column(name = "ORG_MAPPED_ACCT_TY")
    private String orgMappedAcctTy;

    @Column(name = "ORG_ACCT_TY")
    private String orgAcctTy;

    @Column(name = "ORG_AMOUNT")
    private String orgAmount;

    @Column(name = "UPD_STATUS_DATE")
    private String updStatusDate;

    @Column(name = "UPD_ACCT_NO")
    private String updAcctNo;

    @Column(name = "ORG_ACCT_NO")
    private String orgAcctNo;

    @Column(name = "UPD_ACCT_TY")
    private String updAcctTy;

    @Column(name = "UPD_RTR")
    private String updRtr;

    @Column(name = "RECOVERY_AMOUNT")
    private String recoveryAmount;

    @Column(name = "DATE_OF_LAST_PAYMENT_MADE")
    private String dateOfLastPaymentMade;

    @Column(name = "COURT_NM")
    private String courtNm;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "FINANCIAL_INSTITUTION")
    private String financialInstitution;

    @Column(name = "AMOUNT_OF_FACILITY")
    private String amountOfFacility;

    @Column(name = "TOTAL_LIMIT")
    private String totalLimit;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    public Long getLoanAppMfcibSeq() {
        return this.loanAppMfcibSeq;
    }

    public void setLoanAppMfcibSeq(Long loanAppMfcibSeq) {
        this.loanAppMfcibSeq = loanAppMfcibSeq;
    }

    public Long getLoanAppDocSeq() {
        return this.loanAppDocSeq;
    }

    public void setLoanAppDocSeq(Long loanAppDocSeq) {
        this.loanAppDocSeq = loanAppDocSeq;
    }

    public String getLevel1() {
        return this.level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return this.level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel3() {
        return this.level3;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getLevel4() {
        return this.level4;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getTagNm() {
        return this.tagNm;
    }

    public void setTagNm(String tagNm) {
        this.tagNm = tagNm;
    }

    public String getTagVal() {
        return this.tagVal;
    }

    public void setTagVal(String tagVal) {
        this.tagVal = tagVal;
    }

    public String getFileNo() {
        return this.fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getTrnxNo() {
        return this.trnxNo;
    }

    public void setTrnxNo(String trnxNo) {
        this.trnxNo = trnxNo;
    }

    public String getLoanNo() {
        return this.loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getP90() {
        return this.p90;
    }

    public void setP90(String p90) {
        this.p90 = p90;
    }

    public String getP60() {
        return this.p60;
    }

    public void setP60(String p60) {
        this.p60 = p60;
    }

    public String getP30() {
        return this.p30;
    }

    public void setP30(String p30) {
        this.p30 = p30;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getP150() {
        return this.p150;
    }

    public void setP150(String p150) {
        this.p150 = p150;
    }

    public String getP120() {
        return this.p120;
    }

    public void setP120(String p120) {
        this.p120 = p120;
    }

    public String getOk() {
        return this.ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getP180() {
        return this.p180;
    }

    public void setP180(String p180) {
        this.p180 = p180;
    }

    public String getLoss() {
        return this.loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getMfiDefault() {
        return this.mfiDefault;
    }

    public void setMfiDefault(String mfiDefault) {
        this.mfiDefault = mfiDefault;
    }

    public String getLatePmtDays() {
        return this.latePmtDays;
    }

    public void setLatePmtDays(String latePmtDays) {
        this.latePmtDays = latePmtDays;
    }

    public String getLatePayment1To15() {
        return this.latePayment1To15;
    }

    public void setLatePayment1To15(String latePayment1To15) {
        this.latePayment1To15 = latePayment1To15;
    }

    public String getLatePayment16To20() {
        return this.latePayment16To20;
    }

    public void setLatePayment16To20(String latePayment16To20) {
        this.latePayment16To20 = latePayment16To20;
    }

    public String getLatePayment21To29() {
        return this.latePayment21To29;
    }

    public void setLatePayment21To29(String latePayment21To29) {
        this.latePayment21To29 = latePayment21To29;
    }

    public String getLatePayment30() {
        return this.latePayment30;
    }

    public void setLatePayment30(String latePayment30) {
        this.latePayment30 = latePayment30;
    }

    public String getCollateralAmt() {
        return this.collateralAmt;
    }

    public void setCollateralAmt(String collateralAmt) {
        this.collateralAmt = collateralAmt;
    }

    public String getAcctSeqNo() {
        return this.acctSeqNo;
    }

    public void setAcctSeqNo(String acctSeqNo) {
        this.acctSeqNo = acctSeqNo;
    }

    public String getCollTyp() {
        return this.collTyp;
    }

    public void setCollTyp(String collTyp) {
        this.collTyp = collTyp;
    }

    public String getCbrwrGrntrCnic() {
        return this.cbrwrGrntrCnic;
    }

    public void setCbrwrGrntrCnic(String cbrwrGrntrCnic) {
        this.cbrwrGrntrCnic = cbrwrGrntrCnic;
    }

    public String getGrntrFileNo() {
        return this.grntrFileNo;
    }

    public void setGrntrFileNo(String grntrFileNo) {
        this.grntrFileNo = grntrFileNo;
    }

    public String getGrntrInFavr() {
        return this.grntrInFavr;
    }

    public void setGrntrInFavr(String grntrInFavr) {
        this.grntrInFavr = grntrInFavr;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNtn() {
        return this.ntn;
    }

    public void setNtn(String ntn) {
        this.ntn = ntn;
    }

    public String getFthrHsbndMNm() {
        return this.fthrHsbndMNm;
    }

    public void setFthrHsbndMNm(String fthrHsbndMNm) {
        this.fthrHsbndMNm = fthrHsbndMNm;
    }

    public String getLastNm() {
        return this.lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public String getFirstNm() {
        return this.firstNm;
    }

    public void setFirstNm(String firstNm) {
        this.firstNm = firstNm;
    }

    public String getDependants() {
        return this.dependants;
    }

    public void setDependants(String dependants) {
        this.dependants = dependants;
    }

    public String getQualification() {
        return this.qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTrnxResult() {
        return this.trnxResult;
    }

    public void setTrnxResult(String trnxResult) {
        this.trnxResult = trnxResult;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMiddleNm() {
        return this.middleNm;
    }

    public void setMiddleNm(String middleNm) {
        this.middleNm = middleNm;
    }

    public String getFthrHsbndLNm() {
        return this.fthrHsbndLNm;
    }

    public void setFthrHsbndLNm(String fthrHsbndLNm) {
        this.fthrHsbndLNm = fthrHsbndLNm;
    }

    public String getChecker() {
        return this.checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getReportDt() {
        return this.reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getNic() {
        return this.nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNatlTyp() {
        return this.natlTyp;
    }

    public void setNatlTyp(String natlTyp) {
        this.natlTyp = natlTyp;
    }

    public String getIsSelf() {
        return this.isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public String getFthrHsbndFNm() {
        return this.fthrHsbndFNm;
    }

    public void setFthrHsbndFNm(String fthrHsbndFNm) {
        this.fthrHsbndFNm = fthrHsbndFNm;
    }

    public String getMaritalSts() {
        return this.maritalSts;
    }

    public void setMaritalSts(String maritalSts) {
        this.maritalSts = maritalSts;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTrnxDt() {
        return this.trnxDt;
    }

    public void setTrnxDt(String trnxDt) {
        this.trnxDt = trnxDt;
    }

    public String getRefNo() {
        return this.refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getCnic() {
        return this.cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPassport() {
        return this.passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBrwrTyp() {
        return this.brwrTyp;
    }

    public void setBrwrTyp(String brwrTyp) {
        this.brwrTyp = brwrTyp;
    }

    public String getEmployer() {
        return this.employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getReportedOn() {
        return this.reportedOn;
    }

    public void setReportedOn(String reportedOn) {
        this.reportedOn = reportedOn;
    }

    public String getSelfEmployed() {
        return this.selfEmployed;
    }

    public void setSelfEmployed(String selfEmployed) {
        this.selfEmployed = selfEmployed;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRsdntlAddrs() {
        return this.rsdntlAddrs;
    }

    public void setRsdntlAddrs(String rsdntlAddrs) {
        this.rsdntlAddrs = rsdntlAddrs;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getCurRsdntalAddrsDt() {
        return this.curRsdntalAddrsDt;
    }

    public void setCurRsdntalAddrsDt(String curRsdntalAddrsDt) {
        this.curRsdntalAddrsDt = curRsdntalAddrsDt;
    }

    public String getPrmntAddrsDt() {
        return this.prmntAddrsDt;
    }

    public void setPrmntAddrsDt(String prmntAddrsDt) {
        this.prmntAddrsDt = prmntAddrsDt;
    }

    public String getPrvRsdntalAddrs() {
        return this.prvRsdntalAddrs;
    }

    public void setPrvRsdntalAddrs(String prvRsdntalAddrs) {
        this.prvRsdntalAddrs = prvRsdntalAddrs;
    }

    public String getPrvRsdntalAddrsDt() {
        return this.prvRsdntalAddrsDt;
    }

    public void setPrvRsdntalAddrsDt(String prvRsdntalAddrsDt) {
        this.prvRsdntalAddrsDt = prvRsdntalAddrsDt;
    }

    public String getEmployerBusinessDt() {
        return this.employerBusinessDt;
    }

    public void setEmployerBusinessDt(String employerBusinessDt) {
        this.employerBusinessDt = employerBusinessDt;
    }

    public String getOdds() {
        return this.odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getProbOfDefalut() {
        return this.probOfDefalut;
    }

    public void setProbOfDefalut(String probOfDefalut) {
        this.probOfDefalut = probOfDefalut;
    }

    public String getPercentileRisk() {
        return this.percentileRisk;
    }

    public void setPercentileRisk(String percentileRisk) {
        this.percentileRisk = percentileRisk;
    }

    public String getSbpRiskLevel() {
        return this.sbpRiskLevel;
    }

    public void setSbpRiskLevel(String sbpRiskLevel) {
        this.sbpRiskLevel = sbpRiskLevel;
    }

    public String getPymntSts() {
        return this.pymntSts;
    }

    public void setPymntSts(String pymntSts) {
        this.pymntSts = pymntSts;
    }

    public String getStsMnth() {
        return this.stsMnth;
    }

    public void setStsMnth(String stsMnth) {
        this.stsMnth = stsMnth;
    }

    public String getOverdueAmt() {
        return this.overdueAmt;
    }

    public void setOverdueAmt(String overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public String getLoanLess10K() {
        return this.loanLess10K;
    }

    public void setLoanLess10K(String loanLess10K) {
        this.loanLess10K = loanLess10K;
    }

    public String getCurrent30Plus() {
        return this.current30Plus;
    }

    public void setCurrent30Plus(String current30Plus) {
        this.current30Plus = current30Plus;
    }

    public String getCurrent60Plus() {
        return this.current60Plus;
    }

    public void setCurrent60Plus(String current60Plus) {
        this.current60Plus = current60Plus;
    }

    public String getCurrent90Plus() {
        return this.current90Plus;
    }

    public void setCurrent90Plus(String current90Plus) {
        this.current90Plus = current90Plus;
    }

    public String getEnquiryCount() {
        return this.enquiryCount;
    }

    public void setEnquiryCount(String enquiryCount) {
        this.enquiryCount = enquiryCount;
    }

    public String getCloseWithinMaturity() {
        return this.closeWithinMaturity;
    }

    public void setCloseWithinMaturity(String closeWithinMaturity) {
        this.closeWithinMaturity = closeWithinMaturity;
    }

    public String getCloseAfterMaturity() {
        return this.closeAfterMaturity;
    }

    public void setCloseAfterMaturity(String closeAfterMaturity) {
        this.closeAfterMaturity = closeAfterMaturity;
    }

    public String getLoanLimit() {
        return this.loanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getMonth2430Plus() {
        return this.month2430Plus;
    }

    public void setMonth2430Plus(String month2430Plus) {
        this.month2430Plus = month2430Plus;
    }

    public String getMonth2460Plus() {
        return this.month2460Plus;
    }

    public void setMonth2460Plus(String month2460Plus) {
        this.month2460Plus = month2460Plus;
    }

    public String getFileCreationDt() {
        return this.fileCreationDt;
    }

    public void setFileCreationDt(String fileCreationDt) {
        this.fileCreationDt = fileCreationDt;
    }

    public String getCsName() {
        return this.csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getLoanCount() {
        return this.loanCount;
    }

    public void setLoanCount(String loanCount) {
        this.loanCount = loanCount;
    }

    public String getLoanOs() {
        return this.loanOs;
    }

    public void setLoanOs(String loanOs) {
        this.loanOs = loanOs;
    }

    public String getCsCtgry() {
        return this.csCtgry;
    }

    public void setCsCtgry(String csCtgry) {
        this.csCtgry = csCtgry;
    }

    public String getDefaultCount() {
        return this.defaultCount;
    }

    public void setDefaultCount(String defaultCount) {
        this.defaultCount = defaultCount;
    }

    public String getMonth2490Plus() {
        return this.month2490Plus;
    }

    public void setMonth2490Plus(String month2490Plus) {
        this.month2490Plus = month2490Plus;
    }

    public String getLoanAbove10K() {
        return this.loanAbove10K;
    }

    public void setLoanAbove10K(String loanAbove10K) {
        this.loanAbove10K = loanAbove10K;
    }

    public String getDefaultOs() {
        return this.defaultOs;
    }

    public void setDefaultOs(String defaultOs) {
        this.defaultOs = defaultOs;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAcctNo() {
        return this.acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCbrFileNo() {
        return this.cbrFileNo;
    }

    public void setCbrFileNo(String cbrFileNo) {
        this.cbrFileNo = cbrFileNo;
    }

    public String getOthrBwr() {
        return this.othrBwr;
    }

    public void setOthrBwr(String othrBwr) {
        this.othrBwr = othrBwr;
    }

    public String getCoutName() {
        return this.coutName;
    }

    public void setCoutName(String coutName) {
        this.coutName = coutName;
    }

    public String getDclrtnDt() {
        return this.dclrtnDt;
    }

    public void setDclrtnDt(String dclrtnDt) {
        this.dclrtnDt = dclrtnDt;
    }

    public String getPrmntCity() {
        return this.prmntCity;
    }

    public void setPrmntCity(String prmntCity) {
        this.prmntCity = prmntCity;
    }

    public String getPrmntAddrs() {
        return this.prmntAddrs;
    }

    public void setPrmntAddrs(String prmntAddrs) {
        this.prmntAddrs = prmntAddrs;
    }

    public String getReviews() {
        return this.reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRefDt() {
        return this.refDt;
    }

    public void setRefDt(String refDt) {
        this.refDt = refDt;
    }

    public String getAssocTy() {
        return this.assocTy;
    }

    public void setAssocTy(String assocTy) {
        this.assocTy = assocTy;
    }

    public String getEnqGroupId() {
        return this.enqGroupId;
    }

    public void setEnqGroupId(String enqGroupId) {
        this.enqGroupId = enqGroupId;
    }

    public String getAcctTy() {
        return this.acctTy;
    }

    public void setAcctTy(String acctTy) {
        this.acctTy = acctTy;
    }

    public String getEnqSts() {
        return this.enqSts;
    }

    public void setEnqSts(String enqSts) {
        this.enqSts = enqSts;
    }

    public String getDispute() {
        return this.dispute;
    }

    public void setDispute(String dispute) {
        this.dispute = dispute;
    }

    public String getMemNm() {
        return this.memNm;
    }

    public void setMemNm(String memNm) {
        this.memNm = memNm;
    }

    public String getMappedAcctTy() {
        return this.mappedAcctTy;
    }

    public void setMappedAcctTy(String mappedAcctTy) {
        this.mappedAcctTy = mappedAcctTy;
    }

    public String getSeprteDt() {
        return this.seprteDt;
    }

    public void setSeprteDt(String seprteDt) {
        this.seprteDt = seprteDt;
    }

    public String getSubbrnName() {
        return this.subbrnName;
    }

    public void setSubbrnName(String subbrnName) {
        this.subbrnName = subbrnName;
    }

    public String getApplicationDt() {
        return this.applicationDt;
    }

    public void setApplicationDt(String applicationDt) {
        this.applicationDt = applicationDt;
    }

    public String getCoBrwrNm() {
        return this.coBrwrNm;
    }

    public void setCoBrwrNm(String coBrwrNm) {
        this.coBrwrNm = coBrwrNm;
    }

    public String getGrntrDt() {
        return this.grntrDt;
    }

    public void setGrntrDt(String grntrDt) {
        this.grntrDt = grntrDt;
    }

    public String getAssoc() {
        return this.assoc;
    }

    public void setAssoc(String assoc) {
        this.assoc = assoc;
    }

    public String getGrnteAmt() {
        return this.grnteAmt;
    }

    public void setGrnteAmt(String grnteAmt) {
        this.grnteAmt = grnteAmt;
    }

    public String getInvocationDt() {
        return this.invocationDt;
    }

    public void setInvocationDt(String invocationDt) {
        this.invocationDt = invocationDt;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubObj() {
        return this.subObj;
    }

    public void setSubObj(String subObj) {
        this.subObj = subObj;
    }

    public String getCcpMstrLimit() {
        return this.ccpMstrLimit;
    }

    public void setCcpMstrLimit(String ccpMstrLimit) {
        this.ccpMstrLimit = ccpMstrLimit;
    }

    public String getRschedlFlg() {
        return this.rschedlFlg;
    }

    public void setRschedlFlg(String rschedlFlg) {
        this.rschedlFlg = rschedlFlg;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getMaturityDt() {
        return this.maturityDt;
    }

    public void setMaturityDt(String maturityDt) {
        this.maturityDt = maturityDt;
    }

    public String getHighCredit() {
        return this.highCredit;
    }

    public void setHighCredit(String highCredit) {
        this.highCredit = highCredit;
    }

    public String getRescheduleDt() {
        return this.rescheduleDt;
    }

    public void setRescheduleDt(String rescheduleDt) {
        this.rescheduleDt = rescheduleDt;
    }

    public String getClassCatg() {
        return this.classCatg;
    }

    public void setClassCatg(String classCatg) {
        this.classCatg = classCatg;
    }

    public String getBncChq() {
        return this.bncChq;
    }

    public void setBncChq(String bncChq) {
        this.bncChq = bncChq;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCcpMstrCurrency() {
        return this.ccpMstrCurrency;
    }

    public void setCcpMstrCurrency(String ccpMstrCurrency) {
        this.ccpMstrCurrency = ccpMstrCurrency;
    }

    public String getStatusDt() {
        return this.statusDt;
    }

    public void setStatusDt(String statusDt) {
        this.statusDt = statusDt;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getLastPymt() {
        return this.lastPymt;
    }

    public void setLastPymt(String lastPymt) {
        this.lastPymt = lastPymt;
    }

    public String getLoanClassDesc() {
        return this.loanClassDesc;
    }

    public void setLoanClassDesc(String loanClassDesc) {
        this.loanClassDesc = loanClassDesc;
    }

    public String getAcctSts() {
        return this.acctSts;
    }

    public void setAcctSts(String acctSts) {
        this.acctSts = acctSts;
    }

    public String getSecure() {
        return this.secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public String getMinAmtDue() {
        return this.minAmtDue;
    }

    public void setMinAmtDue(String minAmtDue) {
        this.minAmtDue = minAmtDue;
    }

    public String getRepaymentFreq() {
        return this.repaymentFreq;
    }

    public void setRepaymentFreq(String repaymentFreq) {
        this.repaymentFreq = repaymentFreq;
    }

    public String getOpenDt() {
        return this.openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getLastPymntDt() {
        return this.lastPymntDt;
    }

    public void setLastPymntDt(String lastPymntDt) {
        this.lastPymntDt = lastPymntDt;
    }

    public String getClassificationNature() {
        return this.classificationNature;
    }

    public void setClassificationNature(String classificationNature) {
        this.classificationNature = classificationNature;
    }

    public String getLitigationAmt() {
        return this.litigationAmt;
    }

    public void setLitigationAmt(String litigationAmt) {
        this.litigationAmt = litigationAmt;
    }

    public String getBouncedRepaymentCheques() {
        return this.bouncedRepaymentCheques;
    }

    public void setBouncedRepaymentCheques(String bouncedRepaymentCheques) {
        this.bouncedRepaymentCheques = bouncedRepaymentCheques;
    }

    public String getSecurityCollateral() {
        return this.securityCollateral;
    }

    public void setSecurityCollateral(String securityCollateral) {
        this.securityCollateral = securityCollateral;
    }

    public String getRestructuringAmt() {
        return this.restructuringAmt;
    }

    public void setRestructuringAmt(String restructuringAmt) {
        this.restructuringAmt = restructuringAmt;
    }

    public String getWriteoffTyp() {
        return this.writeoffTyp;
    }

    public void setWriteoffTyp(String writeoffTyp) {
        this.writeoffTyp = writeoffTyp;
    }

    public String getWriteOffAmt() {
        return this.writeOffAmt;
    }

    public void setWriteOffAmt(String writeOffAmt) {
        this.writeOffAmt = writeOffAmt;
    }

    public String getWriteoffDt() {
        return this.writeoffDt;
    }

    public void setWriteoffDt(String writeoffDt) {
        this.writeoffDt = writeoffDt;
    }

    public String getRelDt() {
        return this.relDt;
    }

    public void setRelDt(String relDt) {
        this.relDt = relDt;
    }

    public String getUpdMappedAcctTy() {
        return this.updMappedAcctTy;
    }

    public void setUpdMappedAcctTy(String updMappedAcctTy) {
        this.updMappedAcctTy = updMappedAcctTy;
    }

    public String getUpdCurrency() {
        return this.updCurrency;
    }

    public void setUpdCurrency(String updCurrency) {
        this.updCurrency = updCurrency;
    }

    public String getUpdSts() {
        return this.updSts;
    }

    public void setUpdSts(String updSts) {
        this.updSts = updSts;
    }

    public String getOrgStsDt() {
        return this.orgStsDt;
    }

    public void setOrgStsDt(String orgStsDt) {
        this.orgStsDt = orgStsDt;
    }

    public String getOrgSts() {
        return this.orgSts;
    }

    public void setOrgSts(String orgSts) {
        this.orgSts = orgSts;
    }

    public String getOrgRtr() {
        return this.orgRtr;
    }

    public void setOrgRtr(String orgRtr) {
        this.orgRtr = orgRtr;
    }

    public String getUpdAmt() {
        return this.updAmt;
    }

    public void setUpdAmt(String updAmt) {
        this.updAmt = updAmt;
    }

    public String getRecoveryDate() {
        return this.recoveryDate;
    }

    public void setRecoveryDate(String recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getOrgCurrency() {
        return this.orgCurrency;
    }

    public void setOrgCurrency(String orgCurrency) {
        this.orgCurrency = orgCurrency;
    }

    public String getOrgMappedAcctTy() {
        return this.orgMappedAcctTy;
    }

    public void setOrgMappedAcctTy(String orgMappedAcctTy) {
        this.orgMappedAcctTy = orgMappedAcctTy;
    }

    public String getOrgAcctTy() {
        return this.orgAcctTy;
    }

    public void setOrgAcctTy(String orgAcctTy) {
        this.orgAcctTy = orgAcctTy;
    }

    public String getOrgAmount() {
        return this.orgAmount;
    }

    public void setOrgAmount(String orgAmount) {
        this.orgAmount = orgAmount;
    }

    public String getUpdStatusDate() {
        return this.updStatusDate;
    }

    public void setUpdStatusDate(String updStatusDate) {
        this.updStatusDate = updStatusDate;
    }

    public String getUpdAcctNo() {
        return this.updAcctNo;
    }

    public void setUpdAcctNo(String updAcctNo) {
        this.updAcctNo = updAcctNo;
    }

    public String getOrgAcctNo() {
        return this.orgAcctNo;
    }

    public void setOrgAcctNo(String orgAcctNo) {
        this.orgAcctNo = orgAcctNo;
    }

    public String getUpdAcctTy() {
        return this.updAcctTy;
    }

    public void setUpdAcctTy(String updAcctTy) {
        this.updAcctTy = updAcctTy;
    }

    public String getUpdRtr() {
        return this.updRtr;
    }

    public void setUpdRtr(String updRtr) {
        this.updRtr = updRtr;
    }

    public String getRecoveryAmount() {
        return this.recoveryAmount;
    }

    public void setRecoveryAmount(String recoveryAmount) {
        this.recoveryAmount = recoveryAmount;
    }

    public String getDateOfLastPaymentMade() {
        return dateOfLastPaymentMade;
    }

    public void setDateOfLastPaymentMade(String dateOfLastPaymentMade) {
        this.dateOfLastPaymentMade = dateOfLastPaymentMade;
    }

    public String getCourtNm() {
        return courtNm;
    }

    public void setCourtNm(String courtNm) {
        this.courtNm = courtNm;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public String getAmountOfFacility() {
        return amountOfFacility;
    }

    public void setAmountOfFacility(String amountOfFacility) {
        this.amountOfFacility = amountOfFacility;
    }

    public String getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(String totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Boolean getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }
}
