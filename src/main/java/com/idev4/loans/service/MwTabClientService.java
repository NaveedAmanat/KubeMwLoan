package com.idev4.loans.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idev4.loans.domain.*;
import com.idev4.loans.dto.CreditBureauDto;
import com.idev4.loans.dto.PersonalInfoDto;
import com.idev4.loans.dto.tab.AddrDto;
import com.idev4.loans.dto.tab.LoanAppDto;
import com.idev4.loans.dto.tab.ProductDto;
import com.idev4.loans.dto.tab.TabClientAppDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class MwTabClientService {

    private static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private static DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    private final Logger log = LoggerFactory.getLogger(MwTabClientService.class);
    @Autowired
    private MwRefCdValRepository mwRefCdValRepository;
    @Autowired
    private MwLoanAppRepository mwLoanAppRepository;
    @Autowired
    private MwClientRepository mwClientRepository;
    @Autowired
    private MwClntRelRepository mwClntRelRepository;
    @Autowired
    private MwLoanAppDocRepository mwLoanAppDocRepository;
    @Autowired
    private MwPrdRepository mwPrdRepository;
    @Autowired
    private MwMfcibAlwdPrdRepository mwMfcibAlwdPrdRepository;
    @Autowired
    private MwPrdGrpRepository mwPrdGrpRepository;
    @Autowired
    private MwWrtOfClntRepository mwWrtOfClntRepository;
    @Autowired
    private MwBrnchRepository mwBrnchRepository;
    @Autowired
    private MwCityRepository mwCityRepository;
    @Autowired
    private MwPrdLoanTrmRepository mwPrdLoanTrmRepository;
    @Autowired
    private MwLoanAppPpalStngsRepository mwLoanAppPpalStngsRepository;
    @Autowired
    private MwPrdPpalLmtRepository mwPrdPpalLmtRepository;
    @Autowired
    private MwPrdSgrtInstRepository mwPrdSgrtInstRepository;
    @Autowired
    private MwPrdChrgRepository mwPrdChrgRepository;
    @Autowired
    private MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository;
    @Autowired
    private MwPrdFormRelRepository mwPrdFormRelRepository;
    @Autowired
    private MwClntCnicTknRepository mwClntCnicTknRepository;
    @Autowired
    private MwLoanAppCrdtScrRepository mwLoanAppCrdtScrRepository;
    @Autowired
    private MwMfcibOthOutsdLoanRepository mwMfcibOthOutsdLoanRepository;
    @Autowired
    private MwLoanUtlPlanRepository mwLoanUtlPlanRepository;
    @Autowired
    private MwSancListRepository mwSancListRepository;
    @Autowired
    private MwClntTagListRepository mwClntTagListRepository;
    @Autowired
    private MwAddrRepository mwAddrRepository;
    @Autowired
    private MwAddrRelRepository mwAddrRelRepository;
    @Autowired
    private MwClntPermAddrRepository mwClntPermAddrRepository;
    @Autowired
    private MwPortRepository mwPortRepository;
    @Autowired
    private CreditBureau creditBureau;
    @Autowired
    private MwTabService mwTabService;
    @Autowired
    private EntityManager entityManager;

    public Map<String, Object> generateMfcib(TabClientAppDto dto, String curUser) throws Exception {
        String address = "";
        String city = "LAHORE";
        if (dto.client.basic_info != null) {
            MwClient exClnt = mwClientRepository.findOneByClntSeqAndCrntRecFlg(dto.client.basic_info.clnt_seq, true);
            if (exClnt == null) {
                exClnt = new MwClient();
            }

            if (exClnt.getMembrshpDt() != null && !exClnt.getMembrshpDt().toString().isEmpty()) {
                exClnt.setMembrshpDt(exClnt.getMembrshpDt());
            }

            exClnt = dto.client.basic_info.DtoToDomain(formatter, formatterDate, exClnt);
            mwClientRepository.save(exClnt);

            if (dto.client.client_address != null) {
                mwTabService.saveAddress(dto.client.client_address, curUser, dto.loan_info);
                address = dto.client.client_address.address.hse_num + ", " + dto.client.client_address.address.strt + ", " + dto.client.client_address.address.vlg + ", " + dto.client.client_address.address.oth_dtl;
                String mwCity = (String) mwCityRepository.findByCitySeqAndCrntRecFlg(dto.client.client_address.address.city_seq);
                if (mwCity != null) {
                    city = mwCity;
                }
            }
        }

        if (dto.loan_info != null) {
            MwLoanApp exLoan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loan_info.loan_app_seq, true);
            if (exLoan == null) {
                exLoan = new MwLoanApp();
            }
            exLoan = dto.loan_info.DtoToDomain(formatter, exLoan, dto.loan_info);
            mwLoanAppRepository.save(exLoan);
        }

        if (dto.nominee != null) {
            dto.nominee.gndr_key = -1L;
            dto.nominee.occ_key = -1L;
            dto.nominee.loan_user = -1;
            dto.nominee.mrtl_sts_key = -1L;
            dto.nominee.ph_num = "-1";
            dto.nominee.fthr_frst_nm = "-1";
            dto.nominee.fthr_last_nm = "-1";

            mwTabService.saveClientRel(dto.nominee, curUser);
        }

        CreditBureauDto creditBureauDto = new CreditBureauDto();

        creditBureauDto.gender = mwRefCdValRepository.findRefCdGrpAndRefCdSeq("0006", String.valueOf(dto.client.basic_info.gndr_key)).getRefCdDscr().substring(0, 1);
        creditBureauDto.address = address;
        creditBureauDto.accountType = "IN";
        creditBureauDto.amount = String.valueOf(dto.loan_info.rqstd_loan_amt);
        creditBureauDto.applicationId = String.valueOf(dto.loan_info.loan_app_seq);
        creditBureauDto.cellNo = "";
        creditBureauDto.cityOrDistrict = "LAHORE"; //city;
        creditBureauDto.cnicNo = dto.client.basic_info.cnic_num + "";
        creditBureauDto.dateOfBirth = dto.client.basic_info.dob.replaceAll("/", "-");
        creditBureauDto.emailAddress = "";
        creditBureauDto.employerAddress = "";
        creditBureauDto.employerCellNo = "";
        creditBureauDto.employerCityOrDistrict = "";
        creditBureauDto.employerCompanyName = "";
        creditBureauDto.employerEmailAddress = "";
        creditBureauDto.employerOwnershipStatus = "";
        creditBureauDto.employerPhoneNo = "";
        creditBureauDto.fatherOrHusbandFirstName = dto.client.basic_info.fthr_frst_nm;
        creditBureauDto.fatherOrHusbandLastName = dto.client.basic_info.fthr_last_nm;
        creditBureauDto.fatherOrHusbandMiddleName = "";
        creditBureauDto.firstName = dto.client.basic_info.frst_nm;
        creditBureauDto.groupId = "";
        creditBureauDto.lastName = dto.client.basic_info.last_nm;
        creditBureauDto.middleName = "";
        creditBureauDto.nicNoOrPassportNo = "";
        creditBureauDto.phoneNo = dto.client.basic_info.ph_num + "";
        creditBureauDto.profession = "";
        creditBureauDto.subBranchCode = "0001"; //mwBrnchRepository.findByBrnchByPortSeq(dto.client.basic_info.port_key).getBrnchCd();
        creditBureauDto.reqFor = "Client";
        creditBureauDto.associationType = "PRN";


        MwRefCdVal mwRefCdVal = mwRefCdValRepository.findRefCdGrpAndRefCdSeq("0399", String.valueOf(dto.client.basic_info.ref_cd_loan_usr));

        if (!mwRefCdVal.getRefCdDscr().equalsIgnoreCase("Self")) {
            creditBureauDto.associationType = "SID";
            creditBureauDto.co_cnic_1 = String.valueOf(dto.nominee.cnic_num);
            creditBureauDto.co_first_name_1 = dto.nominee.frst_nm;
            creditBureauDto.co_mid_name_1 = "";
            creditBureauDto.co_last_name_1 = dto.nominee.last_nm;
            creditBureauDto.co_association_1 = dto.nominee.cnic_num != null ? "S" : "";
        }
        return creditBureauReport2(creditBureauDto);
    }

    public Map<String, Object> creditBureauReport2(@RequestBody CreditBureauDto dto) throws Exception {
        Map<String, Object> response = new HashMap<>();

        dto.newApplicationId = dto.applicationId;
        String resp = creditBureau.getBureauCreditReport(dto);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapRespObj = new HashMap<>();

        if (resp.contains("MESSAGE") && resp.contains("DCL-")) {
            return (Map<String, Object>) mapper.readValue(resp, Object.class);
        }

        return response;
    }

    public Map<String, Object> getloanDetail(String loanAppSeq) {

        MwLoanApp mwLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(loanAppSeq), true);

        MwClient mwClient = mwClientRepository.findOneByClntSeqAndCrntRecFlg(mwLoanApp.getClntSeq(), true);

        MwRefCdVal mwRefCdVal = mwRefCdValRepository.findRefCdGrpAndRefCdSeq("0399", String.valueOf(mwClient.getRefCdLoanUsr()));

        int self = mwRefCdVal.getRefCd().equals("0001") ? 1 : 0;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();
        Query q = entityManager.createNativeQuery(
                        "SELECT FN_MFI_GNRT (:loanAppSeq, :self) FROM DUAL")
                .setParameter("loanAppSeq", loanAppSeq).setParameter("self", self);

        try {
            Object fnResp = q.getSingleResult();

            if (fnResp != null) {
                Clob clob = (Clob) fnResp;
                response = (Map<String, Object>) mapper.readValue(clob.getSubString(1, (int) clob.length()), Object.class);
            }

        } catch (Exception e) {

        }
//        return  response;
        List<String> lists = new ArrayList<>();

        Map<String, Object> res = new HashMap<>();

        if (response.containsKey("loansDetail")) {
            if (isList(response.get("loansDetail"))) {
                List<Map<String, Object>> lmap = new ArrayList<>();
                ((List<Map<String, Object>>) response.get("loansDetail")).forEach(summary -> {
                    Map<String, Object> newMap = new HashMap<>();
                    for (Map.Entry<String, Object> entry : summary.entrySet()) {
                        newMap.put(entry.getKey(), entry.getValue());
                        if (entry.getKey().equals("doc_seq")) {
                            MwLoanAppDoc mwLoanAppDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(Long.parseLong(loanAppSeq), Long.parseLong(String.valueOf(entry.getValue())), true);
                            if (mwLoanAppDoc != null) {
                                newMap.put("doc_img", mwLoanAppDoc.getDocImg());
                            }
                        }
                    }
                    lmap.add(newMap);
                });
                response.put("loansDetail", lmap);
            }
        }

        if (response.containsKey("nomineeSummary")) {
            if (isList(response.get("nomineeSummary"))) {
                List<Map<String, Object>> lmap = new ArrayList<>();
                ((List<Map<String, Object>>) response.get("nomineeSummary")).forEach(summary -> {
                    Map<String, Object> newMap = new HashMap<>();
                    for (Map.Entry<String, Object> entry : summary.entrySet()) {
                        newMap.put(entry.getKey(), entry.getValue());
                        if (entry.getKey().equals("doc_seq")) {
                            MwLoanAppDoc mwLoanAppDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(Long.parseLong(loanAppSeq), Long.parseLong(String.valueOf(entry.getValue())), true);
                            if (mwLoanAppDoc != null) {
                                newMap.put("doc_img", mwLoanAppDoc.getDocImg());
                            }
                        }
                    }
                    lmap.add(newMap);
                });
                response.put("nomineeSummary", lmap);
            }
        }

        return response;
    }

    private boolean isList(Object object) {
        return object instanceof ArrayList;
    }

    @Transactional
    public String validatePolicy(TabClientAppDto dto, long isActiveLoan, String user) {
        Map<String, Object> response = new HashMap<>();

        String loanAppSeq = String.valueOf(dto.loan_info.loan_app_seq);

        if (dto.documents != null && dto.documents.size() > 0) {
            dto.documents.forEach(doc -> {
                log.info("LoanAppDocSeq:" + doc.loan_app_doc_seq + "LoanAppSeq:" + doc.loan_app_seq
                        + ", DocSeq:" + doc.doc_seq);
                if (doc.doc_seq != null) {
                    MwLoanAppDoc exDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndLoanAppDocSeqAndCrntRecFlg(doc.loan_app_seq, doc.doc_seq,
                            doc.loan_app_doc_seq, true);
                    if (exDoc == null) {
                        exDoc = new MwLoanAppDoc();
                    }
                    exDoc = doc.DtoToDomain(formatter, exDoc);
                    mwLoanAppDocRepository.save(exDoc);
                }
            });

        }
//        else if(isActiveLoan > 0){
//            return "{\"loanComments\": \"Please Shown Evidence of Active Loan\",\"mfcibAlwdPrd\": []}";
//        }

        long count = isActiveLoan - (dto.documents != null && dto.documents.size() > 0 ? dto.documents.size() : 0);

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PRC_MFCIB_VALIDATE_POLICY");
        storedProcedure.registerStoredProcedureParameter("P_LOAN_APP_SEQ", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_ACTIVE_LN", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_USER_ID", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("V_RESULT", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("P_LOAN_APP_SEQ", loanAppSeq);
        storedProcedure.setParameter("P_ACTIVE_LN", count > 0 ? count : 0);
        storedProcedure.setParameter("P_USER_ID", user);
        storedProcedure.execute();

        String parmOutputProcedure = storedProcedure.getOutputParameterValue("V_RESULT").toString();
        log.info("PRC_MFCIB_VALIDATE_POLICY: Procedure executed with status " + parmOutputProcedure);

        return parmOutputProcedure;


        //  response.put("loanComments", "Successfully Validated");
        //  return response;
    }

    @Transactional(timeout = 10)
    public Map<String, Object> getUserValidationDetail(String cnicNum, boolean isClient, boolean isBm,
                                                       String eventName) {

        Map<String, Object> resp = new HashMap<String, Object>();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        if (cnicNum.length() != 13) {
            resp.put("canProceed", false);
            resp.put("reason", "CNIC Number is Invalid");
            return resp;
        }

        resp.put("canProceed", true);
        resp.put("isKrkAuthentic", "0");
        resp.put("isHilAuthentic", "0");
        resp.put("isKTKAuthentic", "0");


        log.info("P_CNIC : " + cnicNum + ", P_EVENT : " + eventName + ", P_USERID : " + userId);

        try {
            String parmPrcOutMsg = "", parmPrcOutPrdElgbl = "";
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("prc_cnic_validation");
            storedProcedure.registerStoredProcedureParameter("P_CNIC", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_EVENT", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_USERID", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_RETURN_MSG", String.class, ParameterMode.OUT);
            storedProcedure.registerStoredProcedureParameter("P_PRD_ELG_CODE", String.class, ParameterMode.OUT);
            storedProcedure.setParameter("P_CNIC", Long.valueOf(cnicNum));
            storedProcedure.setParameter("P_EVENT", eventName);
            storedProcedure.setParameter("P_USERID", userId);
            storedProcedure.execute();
            parmPrcOutMsg = storedProcedure.getOutputParameterValue("P_RETURN_MSG").toString();
            parmPrcOutPrdElgbl = storedProcedure.getOutputParameterValue("P_PRD_ELG_CODE").toString();


            //
            log.info("CNIC Validation Procedure Response1: " + parmPrcOutMsg);
            log.info("CNIC Validation Procedure Response2: " + parmPrcOutPrdElgbl);
            //
            char[] prdElgblty = parmPrcOutPrdElgbl.toCharArray();
            resp.put("isKrkAuthentic", prdElgblty[0]);
            resp.put("isHilAuthentic", prdElgblty[1]);
            resp.put("isKTKAuthentic", prdElgblty[2]);

            //
            if (parmPrcOutMsg.equals("Y")) {
                resp.put("canProceed", true);
                resp.put("reason", "OK");
            } else {
                resp = new HashMap<String, Object>();
                resp.put("canProceed", false);
                resp.put("reason", parmPrcOutMsg);
                System.out.println("reason : " + parmPrcOutMsg);
                //return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.put("canProceed", false);
            resp.put("reason", "Validation failed");
            //return resp;
        }

        //   String query = Queries.clientHistory();

        Query q = entityManager.createNativeQuery(clientHistoryDetail()).setParameter("cnic", cnicNum);
        List<Object[]> result = q.getResultList();
        if (result != null && result.size() > 0) {
            Object[] loan = result.get(0);
            if (loan != null) {
                PersonalInfoDto dto = new PersonalInfoDto();
                dto.loanAppSeq = (loan[0] == null) ? "" : loan[0].toString();
                dto.status = (loan[1] == null) ? "" : loan[1].toString();
                resp.put("clientStatus", dto.status);
                dto.firstName = (loan[2] == null) ? "" : loan[2].toString();
                dto.lastName = (loan[3] == null) ? "" : loan[3].toString();
                dto.clientId = (loan[4] == null) ? "" : loan[4].toString();
                dto.cnicNum = (loan[5] == null) ? 0L : new BigDecimal(loan[5].toString()).longValue();
                dto.fathrFirstName = (loan[6] == null) ? "" : loan[6].toString();
                dto.fathrLastName = (loan[7] == null) ? "" : loan[7].toString();
                dto.gender = (loan[8] == null) ? "" : loan[8].toString();
                dto.maritalStatus = (loan[9] == null) ? "" : loan[9].toString();
                dto.houseNum = (loan[10] == null) ? "" : loan[10].toString();
                dto.cityName = (loan[11] == null) ? "" : loan[11].toString();
                dto.ucName = (loan[12] == null) ? "" : loan[12].toString();
                dto.tehsilName = (loan[13] == null) ? "" : loan[13].toString();
                dto.districtName = (loan[14] == null) ? "" : loan[14].toString();
                dto.state = (loan[15] == null) ? "" : loan[15].toString();
                dto.countryName = (loan[16] == null) ? "" : loan[16].toString();
                dto.portfolio = (loan[17] == null) ? "" : loan[17].toString();
                dto.branchName = (loan[18] == null) ? "" : loan[18].toString();
                dto.area = (loan[19] == null) ? "" : loan[19].toString();
                dto.region = (loan[20] == null) ? "" : loan[20].toString();
                dto.prdSeq = (loan[21] == null) ? 0L : new BigDecimal(loan[21].toString()).longValue();
                dto.prdName = (loan[22] == null) ? "" : loan[22].toString();
                dto.multiLoan = (loan[23] == null) ? "" : loan[23].toString();
                dto.approvedAmount = (loan[24] == null) ? "" : loan[24].toString();
                dto.recAmount = (loan[25] == null) ? "" : loan[25].toString();
                dto.reqAmount = (loan[26] == null) ? "" : loan[26].toString();

                dto.clientSeq = (loan[27] == null) ? 0L : new BigDecimal(loan[27].toString()).longValue();
                dto.clientStatus = (loan[28] == null) ? 0L : new BigDecimal(loan[28].toString()).longValue();

                String pattern = "yyyy-MM-dd HH:mm:ss.S";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                SimpleDateFormat tabDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    dto.expiryDate = (loan[29] == null) ? null : tabDateFormat.parse(loan[29].toString());
                    dto.expryDate = (loan[29] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[29].toString()));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dto.isSAN = (loan[30] == null) ? false : loan[30].toString().equalsIgnoreCase("1");
                dto.isPermAddress = (loan[31] == null) ? false : loan[31].toString().equalsIgnoreCase("1");

                dto.disableFlag = (loan[32] == null) ? false : loan[32].toString().equalsIgnoreCase("1");
                try {
                    dto.dob = (loan[33] == null) ? null : tabDateFormat.parse(loan[33].toString());
                    dto.clntDob = (loan[33] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[33].toString()));
                    dto.dsbmtDt = (loan[75] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[75].toString()));
                    dto.lstAppDt = (loan[74] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[74].toString()));

                    // Added by Yousaf Ali - Dated May-2022
                    dto.membrshpDt = (loan[84] == null) ? null : new SimpleDateFormat("dd-MM-yyyy").parse(
                            tabDateFormat.format(simpleDateFormat.parse(loan[84].toString()))
                    ).toInstant();

                    dto.cnicIssueDate = (loan[94] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[94].toString())); //(loan[94] == null) ? null :tabDateFormat.parse(loan[94].toString());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dto.eduLvlKey = (loan[34] == null) ? 0 : new BigDecimal(loan[34].toString()).longValue();
                dto.earningMembers = (loan[35] == null) ? 0 : new BigDecimal(loan[35].toString()).longValue();
                dto.genderKey = (loan[36] == null) ? 0 : new BigDecimal(loan[36].toString()).longValue();

                dto.houseHoldMember = (loan[37] == null) ? 0 : new BigDecimal(loan[37].toString()).longValue();
                dto.mnthsOfResidence = (loan[38] == null) ? 0 : new BigDecimal(loan[38].toString()).longValue();
                dto.maritalStatusKey = (loan[39] == null) ? 0 : new BigDecimal(loan[39].toString()).longValue();
                dto.natureDisabilityKey = (loan[40] == null) ? 0 : new BigDecimal(loan[40].toString()).longValue();
                dto.nickName = (loan[41] == null) ? "" : loan[41].toString();

                dto.isNomDetailAvailable = (loan[42] == null) ? false : loan[42].toString().equalsIgnoreCase("1");
                dto.numOfChidren = (loan[43] == null) ? 0 : new BigDecimal(loan[43].toString()).longValue();
                dto.numOfDependts = (loan[44] == null) ? 0 : new BigDecimal(loan[44].toString()).longValue();
                dto.numOfErngMemb = (loan[45] == null) ? 0 : new BigDecimal(loan[45].toString()).longValue();
                dto.occupationKey = (loan[46] == null) ? 0 : new BigDecimal(loan[46].toString()).longValue();

                dto.phone = (loan[47] == null) ? "" : loan[47].toString();
                dto.portKey = (loan[48] == null) ? 0 : new BigDecimal(loan[48].toString()).longValue();
                dto.residenceTypeKey = (loan[49] == null) ? 0 : new BigDecimal(loan[49].toString()).longValue();
                dto.selfPDC = (loan[50] == null) ? false : loan[50].toString().equalsIgnoreCase("1");
                dto.spzFirstName = (loan[51] == null) ? "" : loan[51].toString();
                dto.spzLastName = (loan[52] == null) ? "" : loan[52].toString();

                dto.totIncmOfErngMemb = (loan[53] == null) ? 0 : new BigDecimal(loan[53].toString()).longValue();
                dto.yearsOfResidence = (loan[54] == null) ? 0 : new BigDecimal(loan[54].toString()).longValue();
                //55 - 56 below loan_cycl_num

                dto.community = (loan[57] == null) ? 0 : new BigDecimal(loan[57].toString()).longValue();
                dto.otherDetails = (loan[58] == null) ? "" : loan[58].toString();
                dto.street = (loan[59] == null) ? "" : loan[59].toString();
                dto.sreet_area = (loan[59] == null) ? "" : loan[59].toString();
                dto.village = (loan[60] == null) ? "" : loan[60].toString();

                dto.cityUcRelSeq = (loan[61] == null) ? 0 : new BigDecimal(loan[61].toString()).longValue();
                dto.city = (loan[62] == null) ? 0 : new BigDecimal(loan[62].toString()).longValue();
                dto.uc = (loan[63] == null) ? 0 : new BigDecimal(loan[63].toString()).longValue();

                dto.tehsil = (loan[64] == null) ? 0 : new BigDecimal(loan[64].toString()).longValue();
                dto.district = (loan[65] == null) ? 0 : new BigDecimal(loan[65].toString()).longValue();
                dto.stateSeq = (loan[66] == null) ? 0 : new BigDecimal(loan[66].toString()).longValue();
                dto.country = (loan[67] == null) ? 0 : new BigDecimal(loan[67].toString()).longValue();
                dto.loanCyclNum = (loan[68] == null) ? 0 : new BigDecimal(loan[68].toString()).longValue();
                long nextLoanSeq = dto.loanCyclNum + 1;
                dto.addressRelSeq = (loan[55] == null) ? Common.GenerateLoanAppSequenceWithClntSeq(cnicNum, String.valueOf(nextLoanSeq), TableNames.MW_ADDR_REL) : new BigDecimal(loan[55].toString()).longValue();
                dto.addresSeq = (loan[56] == null) ? Common.GenerateLoanAppSequenceWithClntSeq(cnicNum, String.valueOf(nextLoanSeq), TableNames.MW_ADDR) : new BigDecimal(loan[56].toString()).longValue();

                dto.motherMaidenName = (loan[69] == null) ? "" : loan[69].toString();

                dto.isSANt = (loan[30] == null) ? 0 : Integer.parseInt(loan[30].toString());
                dto.isNomDetailAvailablet = (loan[42] == null) ? 0 : Integer.parseInt(loan[42].toString());
                dto.isPermAddresst = (loan[31] == null) ? 0 : Integer.parseInt(loan[31].toString());
                dto.selfPDCt = (loan[50] == null) ? 0 : Integer.parseInt(loan[50].toString());
                dto.disableFlagt = (loan[32] == null) ? 0 : Integer.parseInt(loan[32].toString());

                dto.bm = (loan[70] == null) ? "" : loan[70].toString();
                dto.bdo = (loan[71] == null) ? "" : loan[71].toString();

                dto.brnchSeq = (loan[72] == null) ? 0 : new BigDecimal(loan[72].toString()).longValue();
                dto.coBwrAddrAsClntFlg = (loan[73] == null) ? 0 : Integer.parseInt(loan[73].toString());
                dto.paidAmt = (loan[76] == null) ? null : loan[76].toString();
                dto.utl = (loan[77] == null) ? null : loan[77].toString();
                dto.loanUtlCmnt = (loan[78] == null) ? null : loan[78].toString();
                dto.odInst = (loan[79] == null) ? null : loan[79].toString();
                // Added by Yousaf Ali - Dated May-2022
                dto.prevLoanOst = (loan[80] == null) ? null : loan[80].toString();
                dto.prevLoanRemainInst = (loan[81] == null) ? null : loan[81].toString();
                dto.prevLoanProd = (loan[82] == null) ? null : loan[82].toString();
                dto.previousAmount = (loan[83] == null) ? 0 : new BigDecimal(loan[83].toString()).longValue();
                // Added by Yousaf Ali - Dated May-2022
                dto.refCdLeadTypSeq = (loan[85] == null) ? 0 : new BigDecimal(loan[85].toString()).longValue();
                dto.lat = (loan[87] == null) ? 0 : new BigDecimal(loan[87].toString()).doubleValue();
                dto.lon = (loan[88] == null) ? 0 : new BigDecimal(loan[88].toString()).doubleValue();
                // Added by Yousaf Ali - Dated 30-Sep-2022
                dto.prevLoanOstPrForKTK = (loan[89] == null) ? null : new BigDecimal(loan[89].toString()).longValue();
                // Ended

                dto.avdCount = (loan[90] == null) ? "" : loan[90].toString();
                dto.sameDayCount = (loan[91] == null) ? "" : loan[91].toString();
                dto.ovedueCount = (loan[92] == null) ? "" : loan[92].toString();

                if (resp.get("reason") != null) {
                    if (resp.get("reason").toString().length() <= 0) {
                        if (!dto.status.toString().trim().toLowerCase().equals("completed")
                                && !dto.status.toString().trim().toLowerCase().equals("rejected")
                                && !dto.status.toString().trim().toLowerCase().equals("discarded")
                                && !dto.status.toString().trim().toLowerCase().equals("deferred")
                                && !dto.status.isEmpty()) {
                            resp.put("canProceed", false);
                            // resp.put( "reason",
                            // "Active as Client with Loan [" + dto.loanAppSeq + "] Client ID [" + dto.clientId + "] Client Name ["
                            // + dto.firstName + " " + dto.lastName + "] Status[" + dto.status + "] in [" + dto.branchName
                            // + "] Branch." );
                            resp.put("reason", "This CNIC is in " + dto.status + " mode in branch " + dto.branchName + " for BDO "
                                    + dto.bdo + " as client (" + dto.clientId + " - " + dto.firstName + " " + dto.lastName + ").");
                        }
                    }
                } else {
                    if (!dto.status.toString().trim().toLowerCase().equals("completed")
                            && !dto.status.toString().trim().toLowerCase().equals("rejected")
                            && !dto.status.toString().trim().toLowerCase().equals("discarded")
                            && !dto.status.toString().trim().toLowerCase().equals("deferred")
                            && !dto.status.isEmpty()) {
                        resp.put("canProceed", false);
                        // resp.put( "reason",
                        // "Active as Client with Loan [" + dto.loanAppSeq + "] Client ID [" + dto.clientId + "] Client Name ["
                        // + dto.firstName + " " + dto.lastName + "] Status[" + dto.status + "] in [" + dto.branchName
                        // + "] Branch." );

                        resp.put("reason", "This CNIC is in " + dto.status + " mode in branch " + dto.branchName + " for BDO " + dto.bdo
                                + " as client(" + dto.clientId + " - " + dto.firstName + " " + dto.lastName + ").");
                    }
                }

                String valquery = Queries.statusSeq;
                Query qr = entityManager.createNativeQuery(valquery);
                List<Object[]> resultSet = qr.getResultList();

                long draftSeq = 0L;
                long completedStatus = 0L;
                for (Object[] str : resultSet) {
                    if (str[1].toString().toLowerCase().equals("draft"))
                        draftSeq = Long.valueOf(str[2].toString());
                    if (str[1].toString().toLowerCase().equals("completed"))
                        completedStatus = Long.valueOf(str[2].toString());
                }


                if (isClient && dto.loanCyclNum > 1) {

                    String prevPSCScoreValue = Queries.prevPSCScoreValue;
                    Query qry = entityManager.createNativeQuery(prevPSCScoreValue).setParameter("loanSeq", dto.loanAppSeq);
                    if (qry.getSingleResult().toString() != null && !qry.getSingleResult().toString().equals("null")) {
                        dto.previousPscScore = Long.valueOf(qry.getSingleResult().toString());
                    } else {
                        dto.previousPscScore = 0L;
                    }
                } else {
                    dto.previousPscScore = 0L;
                }

                resp.put("client", dto);
            }
        }

        String clntRelQuery = Queries.getClientRelHistory(cnicNum);
        Query qrel = entityManager.createNativeQuery(clntRelQuery);
        List<Object[]> clntRel = qrel.getResultList();
        if (clntRel != null && clntRel.size() > 0) {
            Object[] res = clntRel.get(0);
            Map<String, String> clntrel = new HashMap<>();
            if (res != null) {
                clntrel.put("loanAppSeq", res[0].toString());
                clntrel.put("status", res[1].toString());
                resp.put("relStatus", (res[17] == null) ? "" : res[17].toString());
                resp.put("relTypFlg", (res[13] == null) ? "0" : res[13].toString());
                clntrel.put("firstName", (res[2] == null) ? "" : res[2].toString());
                clntrel.put("lastName", (res[3] == null) ? "" : res[3].toString());
                clntrel.put("cnicNum", (res[4] == null) ? "" : res[4].toString());
                clntrel.put("fatherFirstName", (res[5] == null) ? "" : res[5].toString());
                clntrel.put("fatherLastName", (res[6] == null) ? "" : res[6].toString());

                clntrel.put("gender", (res[7] == null) ? "" : res[7].toString());
                clntrel.put("maritalSts", (res[8] == null) ? "" : res[8].toString());
                clntrel.put("prdSeq", (res[9] == null) ? "" : res[9].toString());
                clntrel.put("prdName", (res[10] == null) ? "" : res[10].toString());
                clntrel.put("multiLoan", (res[11] == null) ? "" : res[11].toString());
                clntrel.put("relWithClient", (res[12] == null) ? "" : res[12].toString());
                clntrel.put("type", (res[13] == null) ? "" : res[13].toString());
                clntrel.put("brnchName", (res[14] == null) ? "" : res[14].toString());
                clntrel.put("bm", (res[15] == null) ? "" : res[15].toString());
                clntrel.put("bdo", (res[16] == null) ? "" : res[16].toString());
                clntrel.put("clientId", "");
                String type = "Client Relative";
                if (res[13] != null) {
                    if (res[13].toString().equals("1")) {
                        type = "Nominee";
                    } else if (res[13].toString().equals("3")) {
                        type = "Co-Borrower";
                    } else if (res[13].toString().equals("2")) {
                        type = "Next Of Kin";
                    }
                }
                if (res[13] != null) {
                    if (!res[13].toString().equals("4") && !res[13].toString().equals("2")) {
                        if (resp.get("reason") != null) {
                            if (resp.get("reason").toString().length() <= 0) {
                                if (res[1].toString().trim().toLowerCase().equals("active")
                                        || res[1].toString().trim().toLowerCase().equals("approved")) {
                                    resp.put("canProceed", false);
                                    // resp.put( "reason",
                                    // "Active as " + type + " with Loan [" + ( ( res[ 0 ] == null ) ? "" : res[ 0 ].toString() )
                                    // + "] Client Name [" + ( ( res[ 2 ] == null ) ? "" : res[ 2 ].toString() ) + " "
                                    // + ( ( res[ 3 ] == null ) ? "" : res[ 3 ].toString() ) + "] Status["
                                    // + ( ( res[ 1 ] == null ) ? "" : res[ 1 ].toString() ) + "]." );
                                    resp.put("reason", "This CNIC is in " + clntrel.get("status") + " mode in branch "
                                            + clntrel.get("brnchName") + " for BDO " + clntrel.get("bdo") + " as " + type + ".");
                                }
                            }
                        } else {
                            if (res[1].toString().trim().toLowerCase().equals("active")
                                    || res[1].toString().trim().toLowerCase().equals("approved")) {
                                resp.put("canProceed", false);
                                // resp.put( "reason",
                                // "Active as " + type + " with Loan [" + ( ( res[ 0 ] == null ) ? "" : res[ 0 ].toString() )
                                // + "] Client Name [" + ( ( res[ 2 ] == null ) ? "" : res[ 2 ].toString() ) + " "
                                // + ( ( res[ 3 ] == null ) ? "" : res[ 3 ].toString() ) + "] Status["
                                // + ( ( res[ 1 ] == null ) ? "" : res[ 1 ].toString() ) + "]." );
                                resp.put("reason", "This CNIC is in " + clntrel.get("status") + " mode in branch "
                                        + clntrel.get("brnchName") + " for BDO " + clntrel.get("bdo") + " as " + type + ".");
                            }
                        }
                    }
                }
                resp.put("clientRel", clntrel);
            }
        }

        Long clntSeq = 0L;
        if (isClient) {
            MwClient clnt = mwClientRepository.findOneByCnicNumAndCrntRecFlg(Long.valueOf(cnicNum), true);
            if (clnt == null) {
                // Zohaib Asim - Dated 23-05-2022 - Changed to Database Sequence
                Query qry = entityManager.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_CLNT").setParameter("userId", userId);
                Object tblSeqRes = qry.getSingleResult();

                if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                    clntSeq = Long.parseLong(tblSeqRes.toString());
                }
                // clntSeq = Common.GenerateClientSequence( cnicNum );
                clnt = mwClientRepository.findOneByClntSeqAndCrntRecFlg(clntSeq, true);
                if (clnt == null) {
                    resp.put("clientSeq", clntSeq);
                } else {
                    for (int x = 1; x < 10; x++) {
                        Long nSeq = Long.valueOf(x + ("" + clntSeq));
                        clnt = mwClientRepository.findOneByClntSeqAndCrntRecFlg(nSeq, true);
                        if (clnt == null) {
                            resp.put("clientSeq", nSeq);
                            clntSeq = nSeq;
                            break;
                        }
                    }
                }
                MwClient nClnt = new MwClient();
                nClnt.setClntSeq(clntSeq);
                nClnt.setEffStartDt(Instant.now());
                nClnt.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                nClnt.setClntStsDt(Instant.parse("1901-01-01T00:00:00.00Z"));
                nClnt.setSmHsldFlg(false);
                nClnt.setClntId(String.format("%012d", clntSeq));
                nClnt.setCnicNum(Long.parseLong(cnicNum));
                nClnt.setCnicExpryDt(Instant.parse("1901-01-01T00:00:00.00Z"));
                nClnt.setCnicIssueDt(Instant.parse("1901-01-01T00:00:00.00Z"));
                nClnt.setFrstNm("dumy");
                nClnt.setDob(Instant.parse("1901-01-01T00:00:00.00Z"));
                nClnt.setNumOfDpnd(-1l);
                nClnt.setErngMemb(-1l);
                nClnt.setNumOfChldrn(-1l);
                nClnt.setNumOfErngMemb(-1l);
                nClnt.setYrsRes(-1l);
                nClnt.setMnthsRes(-1l);
                nClnt.setPortKey(-1l);
                nClnt.setMrtlStsKey(-1l);
                nClnt.setOccKey(-1l);
                nClnt.setClntStsDt(Instant.parse("1901-01-01T00:00:00.00Z"));
                nClnt.setResTypKey(-1l);
                nClnt.setDisFlg(false);
                nClnt.setNomDtlAvailableFlg(false);
                nClnt.setSlfPdcFlg(false);
                nClnt.setCrntAddrPermFlg(false);
                nClnt.setCoBwrSanFlg(false);
                nClnt.setClntStsKey(-1l);
                nClnt.setTotIncmOfErngMemb(-1l);
                nClnt.setPhNum("dumy");
                nClnt.setLastNm("dumy");
                nClnt.setPftFlg(false);
                nClnt.setDelFlg(false);
                nClnt.setBizDtl("dumy");
                nClnt.setHseHldMemb(-1l);
                nClnt.setGndrKey(-1l);
                nClnt.setEduLvlKey(-1l);
                nClnt.setBizDtl("dumy");
                nClnt.setSyncFlg(false);
                nClnt.setLastUpdDt(Instant.now());
                nClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                nClnt.setCrtdDt(Instant.now());
                nClnt.setCrntRecFlg(true);

                // Added by Yousaf Ali - Dated May-2022
                nClnt.setMembrshpDt(Instant.now());
                nClnt.setRefCdLeadTypSeq(-1L);
                //

                mwClientRepository.save(nClnt);
            } else {
                resp.put("clientSeq", clnt.getClntSeq());
                clntSeq = clnt.getClntSeq();
            }
        }

        // Added by Zohaib Asim - Dated 24-03-2022 - Production Issue (Sequence)
        if (isClient) {
            Long seq = 0L;
            Query qry = entityManager.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "SUBMIT_APP").setParameter("userId", userId);
            Object tblSeqRes = qry.getSingleResult();

            if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                seq = Long.parseLong(tblSeqRes.toString());
            }

            resp.put("commonSeq", seq);
        }
        // End

        return resp;
    }

    private String clientHistoryDetail() {
        return "     SELECT app.loan_app_seq,\n" +
                "            (SELECT ref_cd_dscr\n" +
                "               FROM mw_ref_cd_val\n" +
                "              WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1)\n" +
                "                AS loan_app_sts_str,\n" +
                "            clnt.frst_nm,\n" +
                "            clnt.last_nm,\n" +
                "            clnt.clnt_id,\n" +
                "            clnt.cnic_num,\n" +
                "            clnt.fthr_frst_nm,\n" +
                "            clnt.fthr_last_nm,\n" +
                "            (SELECT ref_cd_dscr\n" +
                "               FROM mw_ref_cd_val\n" +
                "              WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1)\n" +
                "                AS gender_key,\n" +
                "            (SELECT ref_cd_dscr\n" +
                "               FROM mw_ref_cd_val\n" +
                "              WHERE ref_cd_seq = clnt.mrtl_sts_key AND crnt_rec_flg = 1)\n" +
                "                AS marital_sts,\n" +
                "            ad.hse_num,\n" +
                "            cit.city_nm,\n" +
                "            uc.uc_nm,\n" +
                "            thsl.thsl_nm,\n" +
                "            dist.dist_nm,\n" +
                "            st.st_nm,\n" +
                "            cntry.cntry_nm,\n" +
                "            (SELECT port_nm\n" +
                "               FROM mw_port port\n" +
                "              WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg = 1)\n" +
                "                AS port_nm,\n" +
                "            (SELECT brnch_nm\n" +
                "               FROM mw_brnch brnch\n" +
                "              WHERE     brnch.brnch_seq =\n" +
                "                        (SELECT port.brnch_seq\n" +
                "                           FROM mw_port port\n" +
                "                          WHERE     port.port_seq = app.port_seq\n" +
                "                                AND port.crnt_rec_flg = 1)\n" +
                "                    AND brnch.crnt_rec_flg = 1)\n" +
                "                AS brnch_nm,\n" +
                "            (SELECT area_nm\n" +
                "               FROM mw_area area\n" +
                "              WHERE     area.area_seq =\n" +
                "                        (SELECT brnch.area_seq\n" +
                "                           FROM mw_brnch brnch\n" +
                "                          WHERE     brnch.brnch_seq =\n" +
                "                                    (SELECT port.brnch_seq\n" +
                "                                       FROM mw_port port\n" +
                "                                      WHERE     port.port_seq = app.port_seq\n" +
                "                                            AND port.crnt_rec_flg = 1)\n" +
                "                                AND brnch.crnt_rec_flg = 1)\n" +
                "                    AND area.crnt_rec_flg = 1)\n" +
                "                AS area_nm,\n" +
                "            (SELECT reg.reg_nm\n" +
                "               FROM mw_reg reg\n" +
                "              WHERE     reg.reg_seq =\n" +
                "                        (SELECT area.reg_seq\n" +
                "                           FROM mw_area area\n" +
                "                          WHERE     area.area_seq =\n" +
                "                                    (SELECT brnch.area_seq\n" +
                "                                       FROM mw_brnch brnch\n" +
                "                                      WHERE     brnch.brnch_seq =\n" +
                "                                                (SELECT port.brnch_seq\n" +
                "                                                   FROM mw_port port\n" +
                "                                                  WHERE     port.port_seq =\n" +
                "                                                            app.port_seq\n" +
                "                                                        AND port.crnt_rec_flg = 1)\n" +
                "                                            AND brnch.crnt_rec_flg = 1)\n" +
                "                                AND area.crnt_rec_flg = 1)\n" +
                "                    AND reg.crnt_rec_flg = 1)\n" +
                "                AS reg_nm,\n" +
                "            prd.prd_seq,\n" +
                "            prd.prd_nm,\n" +
                "            prd.mlt_loan_flg,\n" +
                "            app.aprvd_loan_amt,\n" +
                "            app.rcmnd_loan_amt,\n" +
                "            app.rqstd_loan_amt,\n" +
                "            clnt.clnt_seq,\n" +
                "            app.loan_app_sts,\n" +
                "            clnt.cnic_expry_dt,\n" +
                "            clnt.co_bwr_san_flg,\n" +
                "            clnt.crnt_addr_perm_flg,\n" +
                "            clnt.dis_flg,\n" +
                "            clnt.dob,\n" +
                "            clnt.edu_lvl_key,\n" +
                "            clnt.erng_memb,\n" +
                "            clnt.gndr_key,\n" +
                "            clnt.hse_hld_memb,\n" +
                "            clnt.mnths_res,\n" +
                "            clnt.mrtl_sts_key,\n" +
                "            clnt.natr_of_dis_key,\n" +
                "            clnt.nick_nm,\n" +
                "            clnt.nom_dtl_available_flg,\n" +
                "            clnt.num_of_chldrn,\n" +
                "            clnt.num_of_dpnd,\n" +
                "            clnt.num_of_erng_memb,\n" +
                "            clnt.occ_key,\n" +
                "            clnt.ph_num,\n" +
                "            clnt.port_key,\n" +
                "            clnt.res_typ_key,\n" +
                "            clnt.slf_pdc_flg,\n" +
                "            clnt.spz_frst_nm,\n" +
                "            clnt.spz_last_nm,\n" +
                "            clnt.tot_incm_of_erng_memb,\n" +
                "            clnt.yrs_res,\n" +
                "            ar.addr_rel_seq,\n" +
                "            ad.addr_seq,\n" +
                "            ad.cmnty_seq,\n" +
                "            ad.oth_dtl,\n" +
                "            ad.strt,\n" +
                "            ad.vlg,\n" +
                "            rel.city_uc_rel_seq,\n" +
                "            rel.city_seq,\n" +
                "            uc.uc_seq,\n" +
                "            thsl.thsl_seq,\n" +
                "            dist.dist_seq,\n" +
                "            st.st_seq,\n" +
                "            cntry.cntry_seq,\n" +
                "            app.loan_cycl_num,\n" +
                "            clnt.mthr_madn_nm,\n" +
                "            'N/A'\n" +
                "                AS bm,\n" +
                "            (SELECT emp.emp_nm\n" +
                "               FROM mw_emp emp\n" +
                "                    JOIN mw_port_emp_rel rel\n" +
                "                        ON rel.port_seq = app.port_seq AND rel.crnt_rec_flg = 1\n" +
                "              WHERE emp.emp_seq = rel.emp_seq)\n" +
                "                AS bdo,\n" +
                "            (SELECT port.brnch_seq\n" +
                "               FROM mw_port port\n" +
                "              WHERE port_seq = app.port_seq AND crnt_rec_flg = 1)\n" +
                "                AS brnchseq,\n" +
                "            app.co_bwr_addr_as_clnt_flg,\n" +
                "            app.crtd_dt\n" +
                "                lst_app_dt,\n" +
                "            dsbmt_dt,\n" +
                "            (SELECT SUM (pymt_amt)\n" +
                "               FROM mw_pymt_sched_hdr psh\n" +
                "                    JOIN mw_pymt_sched_dtl psd\n" +
                "                        ON     psd.pymt_sched_hdr_seq = psh.pymt_sched_hdr_seq\n" +
                "                           AND psd.crnt_rec_flg = 1\n" +
                "                    JOIN mw_rcvry_dtl rdl\n" +
                "                        ON     rdl.pymt_sched_dtl_seq = psd.pymt_sched_dtl_seq\n" +
                "                           AND rdl.crnt_rec_flg = 1\n" +
                "              WHERE psh.crnt_rec_flg = 1 AND psh.loan_app_seq = app.loan_app_seq)\n" +
                "                paid_amt,\n" +
                "            (SELECT ref_cd_dscr\n" +
                "               FROM mw_ref_cd_val\n" +
                "              WHERE crnt_rec_flg = 1 AND ref_cd_seq = app.loan_utl_sts_seq)\n" +
                "                utl,\n" +
                "            app.loan_utl_cmnt,\n" +
                "            get_pd_od_inst (app.loan_app_seq)\n" +
                "                od_inst,\n" +
                "            LOAN_APP_OST (app.loan_app_seq, SYSDATE, 'psc')\n" +
                "                ost,\n" +
                "            (SELECT COUNT (1)\n" +
                "               FROM MW_PYMT_SCHED_HDR psh\n" +
                "                    JOIN MW_PYMT_SCHED_DTL psd\n" +
                "                        ON     psd.PYMT_SCHED_HDR_SEQ = psh.PYMT_SCHED_HDR_SEQ\n" +
                "                           AND psd.CRNT_REC_FLG = 1\n" +
                "              WHERE     psh.CRNT_REC_FLG = 1\n" +
                "                    AND psd.PYMT_STS_KEY IN (945, 1145)\n" +
                "                    AND psh.LOAN_APP_SEQ = app.loan_app_seq)\n" +
                "                inst_count,\n" +
                "            grp.PRD_GRP_NM,\n" +
                "            (SELECT MAX (app1.APRVD_LOAN_AMT)\n" +
                "               FROM mw_loan_app app1\n" +
                "              WHERE     app1.crnt_rec_flg = 1\n" +
                "                    AND app1.loan_cycl_num =\n" +
                "                        NVL (app.loan_cycl_num, app.loan_cycl_num - 1)\n" +
                "                    AND app1.prd_seq NOT IN (29)\n" +
                "                    AND app1.clnt_seq = app.clnt_seq)\n" +
                "                previousAmount,\n" +
                "            clnt.MEMBRSHP_DT\n" +
                "                MEMBRSHP_DT,\n" +
                "            clnt.REF_CD_LEAD_TYP_SEQ\n" +
                "                REF_CD_LEAD_TYP_SEQ,\n" +
                "            GET_PSC_SCORE (app.loan_app_Seq)\n" +
                "                LOAN_APP_PVRTY_SCR,\n" +
                "            ad.LATITUDE,\n" +
                "            ad.LONGITUDE,\n" +
                "            app.APRVD_LOAN_AMT - LOAN_APP_OST (app.loan_app_seq, SYSDATE, 'p')\n" +
                "                ost_KTK,\n" +
                "            (SELECT COUNT (1)\n" +
                "               FROM MW_PYMT_SCHED_HDR psh\n" +
                "                    JOIN MW_PYMT_SCHED_DTL psd\n" +
                "                        ON     psd.PYMT_SCHED_HDR_SEQ = psh.PYMT_SCHED_HDR_SEQ\n" +
                "                           AND psd.CRNT_REC_FLG = 1\n" +
                "              WHERE     psh.CRNT_REC_FLG = 1\n" +
                "                    AND psd.PYMT_STS_KEY = 947\n" +
                "                    AND psh.LOAN_APP_SEQ = app.loan_app_seq)\n" +
                "                ADV_COUNT,\n" +
                "            (SELECT COUNT (1)\n" +
                "               FROM MW_PYMT_SCHED_HDR psh\n" +
                "                    JOIN MW_PYMT_SCHED_DTL psd\n" +
                "                        ON     psd.PYMT_SCHED_HDR_SEQ = psh.PYMT_SCHED_HDR_SEQ\n" +
                "                           AND psd.CRNT_REC_FLG = 1\n" +
                "              WHERE     psh.CRNT_REC_FLG = 1\n" +
                "                    AND psd.PYMT_STS_KEY = 946\n" +
                "                    AND psh.LOAN_APP_SEQ = app.loan_app_seq)\n" +
                "                SAMEDAY_COUNT,\n" +
                "            get_od_info (app.loan_app_seq, SYSDATE, 'i')\n" +
                "                OD_COUNT,\n" +
                "            TO_CHAR (Loan_mturty_dt (app.loan_app_seq), 'dd-MON-yyyy')\n" +
                "                loan_mturty_dt,\n" +
                "                clnt.cnic_issue_dt\n" +
                "       FROM mw_clnt clnt\n" +
                "            LEFT OUTER JOIN mw_loan_app app\n" +
                "                ON     app.clnt_seq = clnt.clnt_seq\n" +
                "                   AND app.crnt_rec_flg = 1\n" +
                "                   AND app.loan_app_sts NOT IN\n" +
                "                           (SELECT ref_cd_seq\n" +
                "                              FROM mw_ref_cd_val\n" +
                "                             WHERE ref_cd IN ('0007', '0008', '1285'))\n" +
                "                   AND app.loan_app_sts IN (704, 703)\n" +
                "                   AND app.loan_cycl_num != -1\n" +
                "                   AND app.prd_seq NOT IN (2,\n" +
                "                                           3,\n" +
                "                                           5,\n" +
                "                                           13,\n" +
                "                                           14,\n" +
                "                                           29)\n" +
                "            LEFT OUTER JOIN mw_dsbmt_vchr_hdr hdr\n" +
                "                ON hdr.loan_app_seq = app.loan_app_seq AND hdr.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_prd prd\n" +
                "                ON     app.prd_seq = prd.prd_seq\n" +
                "                   AND prd.prd_typ_key != '1165'\n" +
                "                   AND prd.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_prd_grp grp\n" +
                "                ON prd.PRD_GRP_SEQ = grp.PRD_GRP_SEQ AND grp.CRNT_REC_FLG = 1\n" +
                "            LEFT OUTER JOIN mw_addr_rel ar\n" +
                "                ON     ar.enty_key = clnt.clnt_seq\n" +
                "                   AND ar.crnt_rec_flg = 1\n" +
                "                   AND ar.enty_typ = 'Client'\n" +
                "            LEFT OUTER JOIN mw_addr ad\n" +
                "                ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_city_uc_rel rel\n" +
                "                ON rel.city_uc_rel_seq = ad.city_seq\n" +
                "            LEFT OUTER JOIN mw_city cit\n" +
                "                ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_uc uc\n" +
                "                ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_thsl thsl\n" +
                "                ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_dist dist\n" +
                "                ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_st st\n" +
                "                ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1\n" +
                "            LEFT OUTER JOIN mw_cntry cntry\n" +
                "                ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1\n" +
                "      WHERE     clnt.crnt_rec_flg = 1\n" +
                "            AND clnt.cnic_num = :cnic\n" +
                "            AND clnt.frst_nm != 'dumy'\n" +
                "   ORDER BY app.loan_cycl_num DESC\n" +
                "FETCH FIRST 1 ROWS ONLY ";
    }

    public ResponseEntity<Map> submitCompletedApplication(TabClientAppDto dto, String user) throws ParseException {

        Map<String, String> resp = new HashMap<String, String>();

        String formValidateKbkResp = verifyRecommendedAmountForKrkSubmit(dto);
        if (!formValidateKbkResp.equals("clear")) {
            resp.put("status", "5");
            resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            resp.put("message", formValidateKbkResp);
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }

        String formValidateResp = formValidationCheck(dto);
        if (!formValidateResp.equals("0")) {
            resp.put("status", "5");
            resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            resp.put("message", "Client/Nominee CNIC Issue date is Invalid");
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }

        String verisys = verifyNadraVerisys(dto);
        if (!verisys.equals("0")) {
            resp.put("status", "5");
            resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            resp.put("message", "NADRA verification is pending");
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }

        String cbwrCnicNactaCheck = cbrwrNactaCheck(dto);
        if (cbwrCnicNactaCheck.length() > 0) {
            resp.put("status", "5");
            resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            resp.put("message", cbwrCnicNactaCheck);
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }

        ResponseEntity<Map> result = submitApplication(dto, user);

        saveClientVerisys(result, dto, SecurityContextHolder.getContext().getAuthentication().getName());

        boolean nactaCnicTagged = dto.loan_info == null ? false : mwTabService.nactaVerification(dto.loan_info.loan_app_seq);
        if (nactaCnicTagged) {
            resp.put("status", "3");
            MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
            resp.put("loan_app_sts", "" + nactaRefCdVal.getRefCdSeq());
            resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
            resp.put("canProceed", "true");
            return ResponseEntity.ok().body(resp);
        }
        String nactaNameCheck = dto.loan_info == null ? "" : mwTabService.nactaNameMatch(dto.loan_info.loan_app_seq);
        if (nactaNameCheck.length() > 0) {
            resp.put("status", "4");
            resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            resp.put("message", "Name match found with NACTA/UN list. Please complete the verification process before disbursement. "
                    + nactaNameCheck);
            resp.put("canProceed", "true");
            return ResponseEntity.ok().body(resp);
        }
        return result;
    }

    @Transactional
    public ResponseEntity<Map> submitApplication(TabClientAppDto dto, String curUser) throws ParseException {
        Map<String, String> resp = new HashMap<String, String>();
        List<MwRefCdVal> vals = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> onSubmissionSts = new ArrayList<>();
        List<Long> onApprSts = new ArrayList<>();
        long appSeq = 702l;
        Long loanAppDiscardSts = 1107L;
        Boolean discardAppFlg = false;

        for (MwRefCdVal val : vals) {
            if (val.getRefCd().equals("0009") || val.getRefCd().equals("0004") || val.getRefCd().equals("0005")
                    || val.getRefCd().equals("0002") || val.getRefCd().equals("1305")) {
                onSubmissionSts.add(val.getRefCdSeq());
            }
            if (val.getRefCd().equals("1305") || val.getRefCd().equals("0009") || val.getRefCd().equals("0005")
                    || val.getRefCd().equals("0004")) {
                onApprSts.add(val.getRefCdSeq());
            }
            if (val.getRefCd().equals("0004"))
                appSeq = val.getRefCdSeq();
        }

        if (dto.loan_info != null) {
            MwRefCdVal sts = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
            if (sts != null && sts.getRefCd().equals("0002")) {
                List<MwLoanApp> exLoans = mwLoanAppRepository.findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(dto.loan_info.loan_app_seq,
                        onSubmissionSts, true);
                if (exLoans != null && exLoans.size() > 0) {
                    resp.put("status", "1");
                    resp.put("canProceed", "false");
                    resp.put("message",
                            "This Loan is already in Disbursed/Approved/Active/Need More Clarification/Submitted/Advance Status");
                    log.info("SUBMIT APPLICATION ",
                            "This Loan is already in Disbursed/Approved/Active/Need More Clarification/Submitted/Advance Status");
                    return ResponseEntity.ok().body(resp);

                    // return null;
                }
            }
            if (sts != null && (sts.getRefCd().equals("0004") || sts.getRefCd().equals("0007") || sts.getRefCd().equals("0003")
                    || sts.getRefCd().equals("0008"))) {
                List<MwLoanApp> exLoans = mwLoanAppRepository.findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(dto.loan_info.loan_app_seq,
                        onApprSts, true);
                if (exLoans != null && exLoans.size() > 0) {
                    resp.put("status", "2");
                    resp.put("canProceed", "false");
                    resp.put("message", "This Loan is already in Approved/Active/Advance Status");
                    log.info("SUBMIT APPLICATION ", "This Loan is already in Approved/Active/Advance Status");
                    return ResponseEntity.badRequest().body(resp);
                }
            }
        }

        if (dto.client.basic_info != null) {
            MwClient exClnt = mwClientRepository.findOneByClntSeqAndCrntRecFlg(dto.client.basic_info.clnt_seq, true);
            if (exClnt == null) {
                exClnt = new MwClient();
            }

            // Added by Yousaf Ali - Dated May-2022
            if (exClnt.getMembrshpDt() != null && !exClnt.getMembrshpDt().toString().isEmpty()) {
                exClnt.setMembrshpDt(exClnt.getMembrshpDt());
            }
            //

            exClnt = dto.client.basic_info.DtoToDomain(formatter, formatterDate, exClnt);
            mwClientRepository.save(exClnt);

            if (dto.client.client_address != null) {
                //Overloaded by Rizwan Mahfooz on 22 AUGUST 2022
                saveAddress(dto.client.client_address, curUser, dto.loan_info);
                //End
            }
        }
        if (dto.loan_info != null) {
            MwLoanApp exLoan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loan_info.loan_app_seq, true);
            if (exLoan == null) {
                exLoan = new MwLoanApp();
            }

            // Added by Zohaib Asim - Dated 25-11-2021 - System Controls -> Discard Application if older than 30Days
            exLoan = dto.loan_info.DtoToDomain(formatter, exLoan, dto.loan_info);
            // Date
            Date effStartDt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dto.loan_info.eff_start_dt);
            Instant dateForComparison = Instant.now().minus(30, ChronoUnit.DAYS);
            log.info("App effStartDt: " + effStartDt.toInstant());
            log.info("Current-30:" + dateForComparison);
            //
            if (effStartDt.toInstant().compareTo(dateForComparison) < 0) {
                exLoan.setLoanAppSts(loanAppDiscardSts);
                exLoan.setLoanAppStsDt(Instant.now());

                discardAppFlg = true;
                resp.put("loan_app_sts", "" + loanAppDiscardSts);
            } else {
                discardAppFlg = false;
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
            }

            log.info("Loan App Status: " + resp.get("loan_app_sts"));
            // End

            mwLoanAppRepository.save(exLoan);

            // ---------- SELF-PDC && ISAN Check ------------ //
            if (dto.client != null) {
                if (dto.client.basic_info != null) {
                    if (((dto.client.basic_info.co_bwr_san_flg == null) ? false
                            : (dto.client.basic_info.co_bwr_san_flg == 1) ? true : false)
                            || ((dto.client.basic_info.slf_pdc_flg == null) ? false
                            : (dto.client.basic_info.slf_pdc_flg == 1) ? true : false)) {
                        MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loan_info.loan_app_seq,
                                Common.Coborrower, true);
                        if (rel != null) {
                            rel.setCrntRecFlg(false);
                            rel.setDelFlg(true);
                            rel.setEffEndDt(Instant.now());
                            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                            rel.setLastUpdDt(Instant.now());
                            mwClntRelRepository.save(rel);
                        }
                    }
                }
            }
            if (dto.client != null) {
                if (dto.client.basic_info != null) {
                    if (((dto.client.basic_info.nom_dtl_available_flg == null) ? false
                            : (dto.client.basic_info.nom_dtl_available_flg == 1) ? true : false)) {
                        MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loan_info.loan_app_seq,
                                Common.NextToKin, true);
                        if (rel != null) {
                            rel.setCrntRecFlg(false);
                            rel.setDelFlg(true);
                            rel.setEffEndDt(Instant.now());
                            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                            rel.setLastUpdDt(Instant.now());
                            mwClntRelRepository.save(rel);
                        }
                    } else {
                        MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loan_info.loan_app_seq,
                                Common.Nominee, true);
                        if (rel != null) {
                            rel.setCrntRecFlg(false);
                            rel.setDelFlg(true);
                            rel.setEffEndDt(Instant.now());
                            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                            rel.setLastUpdDt(Instant.now());
                            mwClntRelRepository.save(rel);
                        }
                    }
                }
            }

            ////////
            if (dto.loan_info.prd_seq != null) {
                MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(dto.loan_info.prd_seq, true);

                ProductDto pdto = new ProductDto();
                pdto.clntSeq = dto.loan_info.clnt_seq;
                pdto.loanAppSeq = dto.loan_info.loan_app_seq;
                pdto.prdSeq = dto.loan_info.prd_seq;

                List<ProductDto> pdtos = mwTabService.getProductsListingForClient(pdto);
                ProductDto rdto = null;

                if (pdtos.size() > 0) {
                    rdto = pdtos.get(0);
                    log.debug("SUBMIT APPLICATION", rdto.toString());
                }
                if (rdto != null && rdto.termRule > 0) {
                    MwPrdLoanTrm trm = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(dto.loan_info.prd_seq, rdto.termRule,
                            true);
                    if (trm != null) {
                        List<MwLoanAppPpalStngs> exPplStng = mwLoanAppPpalStngsRepository
                                .findAllByLoanAppSeq(dto.loan_info.loan_app_seq);
                        mwLoanAppPpalStngsRepository.delete(exPplStng);

                        MwPrdPpalLmt lmt = mwPrdPpalLmtRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(), rdto.limitRule,
                                true);
                        List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository.findAllBySgrtEntySeqAndCrntRecFlg(lmt.getPrdPpalLmtSeq(),
                                true);
                        String stngInsts = "";
                        for (int i = 0; i < sgrtInst.size(); i++) {
                            if (i == 0) {
                                stngInsts = stngInsts + sgrtInst.get(i).getInstNum();
                            } else {
                                stngInsts = stngInsts + "," + sgrtInst.get(i).getInstNum();
                            }
                        }
                        MwLoanAppPpalStngs stng = new MwLoanAppPpalStngs();
                        stng.setLoanAppPpalStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_PPAL_STNGS_SEQ));
                        stng.setIrrFlg(prd.getIrrFlg());
                        stng.setIrrRate(prd.getIrrVal());
                        stng.setLoanAppSeq(dto.loan_info.loan_app_seq);
                        MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(trm.getTrmKey(), true);
                        stng.setNumOfInst((val == null) ? 0 : Long.parseLong(val.getRefCdDscr()));
                        stng.setNumOfInstSgrt(sgrtInst.size() + 0L);
                        stng.setPrdSeq(prd.getPrdSeq());
                        MwRefCdVal freq = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(trm.getPymtFreqKey(), true);
                        stng.setPymtFreq((freq == null) ? 0 : Long.parseLong(freq.getRefCdDscr()));
                        stng.setRndngAdjInst(prd.getRndngAdj().doubleValue());
                        stng.setRndngScl(prd.getRndngScl());
                        stng.setSgrtInst(stngInsts);

                        mwLoanAppPpalStngsRepository.save(stng);
                    }
                }

                List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(dto.loan_info.prd_seq, true, false);

                List<MwLoanAppChrgStngs> exChrgStngs = mwLoanAppChrgStngsRepository.findAllByLoanAppSeq(dto.loan_info.loan_app_seq);
                mwLoanAppChrgStngsRepository.delete(exChrgStngs);
                charges.forEach(charge -> {
                    List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository.findAllBySgrtEntySeqAndCrntRecFlg(charge.getPrdChrgSeq(),
                            true);
                    String stngInsts = "";
                    for (int i = 0; i < sgrtInst.size(); i++) {
                        if (i == 0) {
                            stngInsts = stngInsts + sgrtInst.get(i).getInstNum();
                        } else {
                            stngInsts = stngInsts + "," + sgrtInst.get(i).getInstNum();
                        }
                    }
                    MwLoanAppChrgStngs stng = new MwLoanAppChrgStngs();
                    stng.setLoanAppChrgStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_CHRG_STNGS_SEQ));
                    stng.setLoanAppSeq(dto.loan_info.loan_app_seq);
                    stng.setNumOfInstSgrt(charge.getSgrtInstNum());
                    stng.setPrdChrgSeq(charge.getPrdChrgSeq());
                    stng.setPrdSeq(prd.getPrdSeq());
                    stng.setRndngFlg(charge.getAdjustRoundingFlg());
                    stng.setSgrtInst(stngInsts);
                    stng.setUpfrontFlg(charge.getUpfrontFlg());
                    stng.setTypSeq(charge.getChrgTypSeq());
                    mwLoanAppChrgStngsRepository.save(stng);
                });
                /////////
                List<MwPrdFormRel> prdForms = mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg(dto.loan_info.prd_seq, true);
                prdForms.forEach(rel -> {
                    Common.updateFormComplFlag(rel.getFormSeq(), dto.loan_info.loan_app_seq, curUser);
                });
            }
        }

        if (dto.client.cnic_token != null) {
            MwCnicTkn exTkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.client.cnic_token.loan_app_seq, true);
            if (exTkn == null) {
                exTkn = new MwCnicTkn();
                // mwClntCnicTknRepository.delete( exTkn );
            }
            exTkn = dto.client.cnic_token.DtoToDomain(formatter, formatterDate, exTkn);
            mwClntCnicTknRepository.save(exTkn);
        }


        if (dto.credit_scoring != null) {
            MwLoanAppCrdtScr exScr = mwLoanAppCrdtScrRepository
                    .findOneByLoanAppCrdtScrSeqAndCrntRecFlg(dto.credit_scoring.loan_app_crdt_scr_seq, true);
            if (exScr == null) {
                exScr = new MwLoanAppCrdtScr();
            }
            exScr = dto.credit_scoring.DtoToDomain(formatter, exScr);
            mwLoanAppCrdtScrRepository.save(exScr);
        }
        if (dto.insurance_info != null) {
            mwTabService.saveInsuranceInfo(dto.insurance_info, curUser);
        }

        if (dto.other_loans != null && dto.other_loans.size() > 0) {
            dto.other_loans.forEach(otherLoan -> {
                MwMfcibOthOutsdLoan exLoan = mwMfcibOthOutsdLoanRepository
                        .findOneByOthOutsdLoanSeqAndLoanAppSeqAndCrntRecFlg(otherLoan.oth_outsd_loan_seq, otherLoan.loan_app_seq, true);
                if (exLoan == null) {
                    exLoan = new MwMfcibOthOutsdLoan();
                }
                exLoan = otherLoan.DtoToDomain(formatter, formatterDate, curUser, exLoan);
                mwMfcibOthOutsdLoanRepository.save(exLoan);
            });
        }

        if (dto.nominee != null) {
            mwTabService.saveClientRel(dto.nominee, curUser);
        }

        if (dto.next_of_kin != null) {
            mwTabService.saveClientRel(dto.next_of_kin, curUser);
        }

        if (dto.coborrower != null) {
            if (dto.coborrower.basic_info != null) {
                mwTabService.saveClientRel(dto.coborrower.basic_info, curUser);
            }
            if (dto.coborrower.coborrower_address != null) {
                mwTabService.saveAddress(dto.coborrower.coborrower_address, curUser);
            }
        }

        if (dto.client_relative != null) {
            if (dto.client_relative.basic_info != null) {
                mwTabService.saveClientRel(dto.client_relative.basic_info, curUser);
            }
            if (dto.client_relative.client_relative_address != null) {
                mwTabService.saveAddress(dto.client_relative.client_relative_address, curUser);
            }
        }

        if (dto.business_appraisal != null) {
            mwTabService.saveBusinessAppraisal(dto.business_appraisal, curUser);
        }

        if (dto.home_imp_aprsl != null) {
            mwTabService.saveHomeAprsl(dto.home_imp_aprsl, curUser);
        }

        if (dto.loan_info != null) {
            mwTabService.savePsc(dto.psc, curUser);
        }

        if (dto.expected_loan_utilization != null) {
            dto.expected_loan_utilization.forEach(utl -> {
                MwLoanUtlPlan exUtil = mwLoanUtlPlanRepository.findOneByLoanUtlPlanSeqAndCrntRecFlg(utl.loan_utl_plan_seq, true);
                if (exUtil == null) {
                    exUtil = new MwLoanUtlPlan();
                }
                exUtil = utl.DtoToDomain(formatter, exUtil);
                mwLoanUtlPlanRepository.save(exUtil);
            });
        }

        if (dto.documents != null) {
            dto.documents.forEach(doc -> {
                log.info("LoanAppDocSeq:" + doc.loan_app_doc_seq + "LoanAppSeq:" + doc.loan_app_seq
                        + ", DocSeq:" + doc.doc_seq);
                if (doc.doc_seq != null && doc.doc_seq != 0 && doc.doc_seq != -1 && doc.doc_seq != -2) {
                    MwLoanAppDoc exDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndLoanAppDocSeqAndCrntRecFlg(doc.loan_app_seq, doc.doc_seq,
                            doc.loan_app_doc_seq, true);
                    if (exDoc == null) {
                        exDoc = new MwLoanAppDoc();
                    }
                    exDoc = doc.DtoToDomain(formatter, exDoc);
                    mwLoanAppDocRepository.save(exDoc);
                }
            });
        }

        if (dto.school_appraisal != null) {
            mwTabService.saveSchoolAppraisal(dto.school_appraisal, curUser);
        }

        if (dto.school_information != null) {
            mwTabService.saveSchoolInformation(dto.school_information);
        }

        if (dto.anml_rgstr != null) {
            mwTabService.saveAnmlRgstr(dto.anml_rgstr, curUser);
        }

        if (dto.loan_info != null) {
            MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
            if (val.getRefCd().equals("0007") || val.getRefCd().equals("0008")) {
                mwTabService.deleteApplication(dto.loan_info.loan_app_seq, curUser, val.getRefCdSeq(), dto.loan_info.cmnt);
            }
        }

        String msg = "Application Updated";
        MwRefCdVal sts = null;

        // Added by Zohaib Asim - Dated 25-11-2021 - System Controls
        if (discardAppFlg) {
            resp.put("status", "7");
            resp.put("canProceed", "true");

            sts = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(loanAppDiscardSts, true);
        } else {
            resp.put("status", "0");
            resp.put("canProceed", "true");

            sts = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
        }
        if (sts != null) {
            if (discardAppFlg) {
                msg = "Dear User, As per the 30-day loan application policy, this application has expired and will be discarded. Please generate new application for the client";
            } else {
                msg = "Application Status Updated As: " + sts.getRefCdDscr();
            }
        }
        resp.put("message", msg);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Success", msg)).body(resp);
    }

    public String verifyRecommendedAmountForKrkSubmit(TabClientAppDto dto) {
        try {
            if (dto.loan_info.prd_seq == 33 || dto.loan_info.prd_seq == 34) {
                Object res = entityManager.createNativeQuery(Queries.krkRecommendedAmntQuery).setParameter("cnic_num", dto.client.basic_info.cnic_num)
                        .setParameter("rcmnd_amt", dto.loan_info.rcmnd_loan_amt).getSingleResult();
                return res.toString();
            } else {
                return "clear";
            }
        } catch (NoResultException e) {
            log.debug("NoResultException in  verifyRecommendedAmountForKrkSubmit:", e.getMessage());
            return "Client is not eligible for this product";
        } catch (Exception e) {
            log.debug("Exception in  verifyRecommendedAmountForKrkSubmit:", e.getMessage());
            return "Some error occurred";
        }
    }

    @Transactional
    public String verifyNadraVerisys(TabClientAppDto dto) {
        String resp = "0";
        if (dto.loan_info.loan_app_sts == 702 && dto.user_role != null && dto.user_role.equals("BM")) {
            Query qry = entityManager.createNativeQuery(Queries.verisysBmApprovalFunc).setParameter("P_LOAN_APP_SEQ", dto.loan_info.loan_app_seq);
            String versts = qry.getSingleResult().toString();
            if (versts != null && versts.equals("0")) {
                resp = "2";
            }
        }
        return resp;
    }

    @Transactional
    public String formValidationCheck(TabClientAppDto dto) {
        String resp = "0";
        int bizSelfPrsn = 0;
        try {
            if (dto.business_appraisal != null && dto.business_appraisal.basic_info.prsn_run_the_biz == 191)
                bizSelfPrsn = 1;
        } catch (Exception e) {
        }

        if (dto.loan_info.loan_app_sts == 700) {
            if (dto.client.basic_info.cnic_issue_dt == null || dto.client.basic_info.cnic_issue_dt.equals("")
                    || dto.client.basic_info.cnic_issue_dt.equals("01-01-1950")) {
                resp = "2";
            }
            if (dto.nominee != null && dto.client.basic_info.nom_dtl_available_flg == 1 && bizSelfPrsn == 0) {
                if (dto.nominee.cnic_issue_dt == null || dto.nominee.cnic_issue_dt.equals("")
                        || dto.nominee.cnic_issue_dt.equals("01-01-1950")) {
                    resp = "2";
                }
            }
        }
        return resp;
    }

    public String cbrwrNactaCheck(TabClientAppDto dto) {
        String resp = "";
        if (dto.loan_info != null) {
            MwRefCdVal stsVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
            if (stsVal.getRefCd().equals("0002") || stsVal.getRefCd().equals("0004")) {
                if (dto.coborrower != null && dto.coborrower.basic_info != null) {
                    if (dto.coborrower.basic_info.rel_typ_flg.longValue() == 3) {
                        //
//                        MwRefCdVal relWithClnt = mwRefCdValRepository
//                                .findOneByRefCdSeqAndCrntRecFlg( dto.coborrower.basic_info.rel_wth_clnt_key, true );
                        MwRefCdVal relWithClnt = mwRefCdValRepository
                                .findOneByRefCdSeqAndCrntRecFlg(548L, true);

                        //

                        if (relWithClnt.getRefCd().equals("0032")) {
                            List<MwSancList> nactaRelList = mwSancListRepository
                                    .findAllByCnicNumAndCntryAndCrntRecFlg(dto.coborrower.basic_info.cnic_num + "",
                                            "Pakistan", 1L);
                            MwClntTagList nactaRelTag = mwClntTagListRepository
                                    .findOneByCnicNumAndTagsSeqAndDelFlg(dto.coborrower.basic_info.cnic_num, 6l, false);
                            if (nactaRelList.size() > 0 || nactaRelTag != null) {
                                return "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.";
                            }
                        }
                    }
                } else {
                    MwClntRel cob = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loan_info.loan_app_seq, 3l,
                            true);
                    if (cob != null) {
                        MwRefCdVal relWithClnt = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(cob.getRelWthClntKey(), true);
                        if (relWithClnt.getRefCd().equals("0032")) {
                            List<MwSancList> nactaRelList = mwSancListRepository.findAllByCnicNumAndCntryAndCrntRecFlg(cob.getCnicNum() + "",
                                    "Pakistan", 1L);
                            MwClntTagList nactaRelTag = mwClntTagListRepository.findOneByCnicNumAndTagsSeqAndDelFlg(cob.getCnicNum(), 6l,
                                    false);
                            if (nactaRelList.size() > 0 || nactaRelTag != null) {
                                return "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.";
                            }
                        }
                    }
                }
            }
        }
        return resp;
    }

    @Transactional
    public void saveClientVerisys(ResponseEntity<Map> result, TabClientAppDto dto, String curUser) {
        String parmOutputProcedure = "";
        try {

            // call verisys proc for submitted app status only
            if (result.getBody().get("status").equals("0") && result.getBody().get("canProceed").equals("true") && dto.loan_info.loan_app_sts == 700) {
                StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PRC_CNIC_VERISYS");
                storedProcedure.registerStoredProcedureParameter("P_LOAN_APP_SEQ", Long.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter("P_RTN_MSG", String.class, ParameterMode.OUT);
                storedProcedure.setParameter("P_LOAN_APP_SEQ", dto.loan_info.loan_app_seq);
                storedProcedure.execute();
                parmOutputProcedure = storedProcedure.getOutputParameterValue("P_RTN_MSG").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveAddress(AddrDto dto, String curUser) throws ParseException {
        if (dto.address_rel != null && dto.address != null) {

            MwAddrRel exRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.address_rel.enty_key,
                    dto.address_rel.enty_typ, true);
            if (exRel == null) {
                exRel = new MwAddrRel();
                exRel.setAddrRelSeq(dto.address_rel.addr_rel_seq);
                exRel.setAddrSeq(dto.address_rel.addr_seq);
            }
            exRel = dto.address_rel.DtoToDomain(formatter, exRel);

            MwAddr exAdr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(exRel.getAddrSeq(), true);
            if (exAdr == null) {
                exAdr = new MwAddr();
                exAdr.setAddrSeq(exRel.getAddrSeq());
            }
            exAdr = dto.address.DtoToDomain(formatter, curUser, exAdr);
            mwAddrRepository.save(exAdr);
            mwAddrRelRepository.save(exRel);
        }

        if (dto.address_perm_rel != null) {
            MwClntPermAddr exAddr = mwClntPermAddrRepository.findOneByClntSeqAndCrntRecFlg(dto.address_perm_rel.clnt_seq, true);
            if (exAddr == null) {
                exAddr = new MwClntPermAddr();
            }
            exAddr = dto.address_perm_rel.DtoToDomain(formatter, exAddr);
            mwClntPermAddrRepository.save(exAddr);
        }
    }

    @Transactional
    public void saveAddress(AddrDto dto, String curUser, LoanAppDto loanAppDto) throws ParseException {
        if (dto.address_rel != null && dto.address != null) {

            MwAddrRel exRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.address_rel.enty_key,
                    dto.address_rel.enty_typ, true);
            if (exRel == null) {
                exRel = new MwAddrRel();
                exRel.setAddrRelSeq(dto.address_rel.addr_rel_seq);
                exRel.setAddrSeq(dto.address_rel.addr_seq);
            }
            exRel = dto.address_rel.DtoToDomain(formatter, exRel);

            MwAddr exAdr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(exRel.getAddrSeq(), true);
            if (exAdr == null) {
                exAdr = new MwAddr();
                exAdr.setAddrSeq(exRel.getAddrSeq());
            }
            exAdr = dto.address.DtoToDomain(formatter, curUser, exAdr);
            mwAddrRepository.save(exAdr);
            mwAddrRelRepository.save(exRel);
        }

        if (dto.address_perm_rel != null) {
            MwClntPermAddr currAddr = null;
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(loanAppDto.port_seq, true);
            if (port != null) {
                MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);
                if (brnch != null) {
                    currAddr = mwClntPermAddrRepository.findbyClntSeqAndBrnchSeq(dto.address_perm_rel.clnt_seq, brnch.getBrnchSeq());
                }
            }

            MwClntPermAddr prevAddr = mwClntPermAddrRepository.findOneByClntSeqAndCrntRecFlg(dto.address_perm_rel.clnt_seq, true);
            if (prevAddr != null) {
                prevAddr.setCrntRecFlg(false);
                prevAddr.setDelFlg(true);
                mwClntPermAddrRepository.save(prevAddr);
            }

            if (currAddr == null) {
                currAddr = new MwClntPermAddr();
            }

            currAddr = dto.address_perm_rel.DtoToDomain(formatter, currAddr);
            mwClntPermAddrRepository.save(currAddr);
        }
    }


    public Map<String, String> loanAppAprvlRjtcn(TabClientAppDto dto, String user) {
        Map<String, String> resp = new HashMap<String, String>();
        if (dto.documents != null) {
            dto.documents.forEach(doc -> {
                log.info("LoanAppDocSeq:" + doc.loan_app_doc_seq + "LoanAppSeq:" + doc.loan_app_seq
                        + ", DocSeq:" + doc.doc_seq);
                MwLoanAppDoc exDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(doc.loan_app_seq, doc.doc_seq, true);

                if (exDoc == null) {
                    exDoc = new MwLoanAppDoc();
                }
                exDoc = doc.DtoToDomain(formatter, exDoc);
                mwLoanAppDocRepository.save(exDoc);
            });
        }
        resp.put("DocAprvlRjtn", "true");
        return resp;
    }
}
