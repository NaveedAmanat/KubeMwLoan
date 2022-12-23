package com.idev4.loans.service;

import com.idev4.loans.domain.MWTags;
import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.PersonalInfoDto;
import com.idev4.loans.dto.TagDto;
import com.idev4.loans.dto.ValidationDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MWTags.
 */
@Service
@Transactional
public class MWTagsService {

    private final Logger log = LoggerFactory.getLogger(MWTagsService.class);

    private final MWTagsRepository mWTagsRepository;

    private final EntityManager em;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwWrtOfClntRepository mwWrtOfClntRepository;

    private final MwBrnchRepository mwBrnchRepository;

    private final MwClntRepository mwClntRepository;

    // private final MwNactaListRepository mwNactaListRepository;

    // private final MwAmlMtchdClntRepository mwAmlMtchdClntRepository;

    private final MwRefCdValRepository mwRefCdValRepository;

    public MWTagsService(MWTagsRepository mWTagsRepository, EntityManager em, MwLoanAppRepository mwLoanAppRepository,
                         MwWrtOfClntRepository mwWrtOfClntRepository, MwBrnchRepository mwBrnchRepository, MwClntRepository mwClntRepository,
                         // MwNactaListRepository mwNactaListRepository,
                         // MwAmlMtchdClntRepository mwAmlMtchdClntRepository,
                         MwRefCdValRepository mwRefCdValRepository) {
        this.mWTagsRepository = mWTagsRepository;
        this.em = em;
        this.mwWrtOfClntRepository = mwWrtOfClntRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwClntRepository = mwClntRepository;
        // this.mwNactaListRepository = mwNactaListRepository;
        // this.mwAmlMtchdClntRepository = mwAmlMtchdClntRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
    }

    /**
     * Save a mWTags.
     *
     * @param mWTags the entity to save
     * @return the persisted entity
     */
    public MWTags save(MWTags mWTags) {
        log.debug("Request to save MWTags : {}", mWTags);
        return mWTagsRepository.save(mWTags);
    }

    /**
     * Get all the mWTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MWTags> findAll(Pageable pageable) {
        log.debug("Request to get all MWTags");
        return mWTagsRepository.findAll(pageable);
    }

    /**
     * Get one mWTags by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MWTags findOne(Long id) {
        log.debug("Request to get MWTags : {}", id);
        return mWTagsRepository.findOne(id);
    }

    public Map<String, Object> getUserTagsByCnic(String cnic) {

        List<MWTags> tags = new ArrayList<MWTags>();

        MWTags tag = mWTagsRepository.findOneByTagIdAndCrntRecFlg(cnic, true);
        String tagQuery = Queries.tagList;
        log.debug("Final tagQuery is: {} ", tagQuery);
        Query qt = em.createNativeQuery(tagQuery).setParameter("cnicNum", cnic);
        ;
        List<Object[]> tagsResult = qt.getResultList();
        log.debug("tagsResult", tagsResult);
        // if(tag != null){
        // if(tag.getSvrtyFlgKey() != null){
        // if(tag.getSvrtyFlgKey() == 0){
        // String query = Queries.clntHistory + cnic;
        // log.debug("Final Query is: {} ",query);
        // Query q = em.createNativeQuery(query);
        // List<Object[]> result = q.getResultList();
        //
        // Map<String,Object> resp = new HashMap<String, Object>();
        // resp.put("history", result);
        // resp.put("tag", tag);
        // }else {
        // String
        // }
        // }
        // }
        //

        String query = Queries.clientHistory();
        log.debug("Final Query is: {} ", query);
        Query q = em.createNativeQuery(query).setParameter("cnic", cnic);
        List<Object[]> result = q.getResultList();
        log.debug("history", result);

        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("history", result);
        resp.put("tags", tagsResult);

        return resp;
    }

    @Transactional(timeout = 10)
    public Map<String, Object> getUserValidation(String cnicNum, boolean isClient, boolean isBm,
                                                 String eventName) {

        Map<String, Object> resp = new HashMap<String, Object>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        if (cnicNum.length() != 13) {
            resp.put("canProceed", false);
            resp.put("reason", "CNIC Number is Invalid");
            return resp;
        }

        resp.put("canProceed", true);
        resp.put("isKrkAuthentic", "0");
        resp.put("isHilAuthentic", "0");
        resp.put("isKTKAuthentic", "0");


        log.info("P_CNIC : " + cnicNum + ", P_EVENT : " + eventName + ", P_USERID : " + user);

        try {
            String parmPrcOutMsg = "", parmPrcOutPrdElgbl = "";
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("prc_cnic_validation");
            storedProcedure.registerStoredProcedureParameter("P_CNIC", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_EVENT", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_USERID", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_RETURN_MSG", String.class, ParameterMode.OUT);
            storedProcedure.registerStoredProcedureParameter("P_PRD_ELG_CODE", String.class, ParameterMode.OUT);
            storedProcedure.setParameter("P_CNIC", Long.valueOf(cnicNum));
            storedProcedure.setParameter("P_EVENT", eventName);
            storedProcedure.setParameter("P_USERID", user);
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

        String query = Queries.clientHistory();
        Query q = em.createNativeQuery(query).setParameter("cnic", cnicNum);
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
                Query qr = em.createNativeQuery(valquery);
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
                    Query qry = em.createNativeQuery(prevPSCScoreValue).setParameter("loanSeq", dto.loanAppSeq);
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
        Query qrel = em.createNativeQuery(clntRelQuery);
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
            MwClnt clnt = mwClntRepository.findOneByCnicNumAndCrntRecFlg(Long.valueOf(cnicNum), true);
            if (clnt == null) {
                // Zohaib Asim - Dated 23-05-2022 - Changed to Database Sequence
                Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_CLNT")
                        .setParameter("userId", user);
                Object tblSeqRes = qry.getSingleResult();

                if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                    clntSeq = Long.parseLong(tblSeqRes.toString());
                }
                // clntSeq = Common.GenerateClientSequence( cnicNum );
                clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clntSeq, true);
                if (clnt == null) {
                    resp.put("clientSeq", clntSeq);
                } else {
                    for (int x = 1; x < 10; x++) {
                        Long nSeq = Long.valueOf(x + ("" + clntSeq));
                        clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(nSeq, true);
                        if (clnt == null) {
                            resp.put("clientSeq", nSeq);
                            clntSeq = nSeq;
                            break;
                        }
                    }
                }
                MwClnt nClnt = new MwClnt();
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

                mwClntRepository.save(nClnt);
            } else {
                resp.put("clientSeq", clnt.getClntSeq());
                clntSeq = clnt.getClntSeq();
            }
        }

        // Added by Zohaib Asim - Dated 24-03-2022 - Production Issue (Sequence)
        if (isClient) {
            Long seq = 0L;
            Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "SUBMIT_APP")
                    .setParameter("userId", user);
            Object tblSeqRes = qry.getSingleResult();

            if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                seq = Long.parseLong(tblSeqRes.toString());
            }

            resp.put("commonSeq", seq);
        }
        // End

        return resp;
    }

    public Map<String, Object> getUserTagsByCnicforValidation(ValidationDto dto) {

        Map<String, Object> resp = new HashMap<String, Object>();

        String tagQuery = Queries.tagList;
        log.debug("Final tagQuery is: {} ", tagQuery);
        Query qt = em.createNativeQuery(tagQuery).setParameter("cnicNum", dto.cnicNum);
        ;
        List<Object[]> tagsResult = qt.getResultList();
        log.debug("tagsResult", tagsResult);
        String query = Queries.clientHistory();
        log.debug("Final Query is: {} ", query);
        Query q = em.createNativeQuery(query).setParameter("cnic", dto.cnicNum);
        List<Object[]> result = q.getResultList();
        log.debug("history", result);

        if (tagsResult != null && tagsResult.size() > 0) {
            Object[] res = tagsResult.get(0);
            TagDto tagdto = new TagDto();
            if (res != null) {

                tagdto.tagFromDt = res[0].toString();
                tagdto.tagToDt = res[1].toString();
                tagdto.tagSeq = res[2].toString();
                tagdto.tagName = res[3].toString();
                tagdto.tagDscr = res[4].toString();
                tagdto.SvrtyFlagKey = res[5].toString();
                resp.put("tag", tagdto);
            }
        }

        String clntRelQuery = Queries.getClientRelHistory(dto.cnicNum);
        Query qrel = em.createNativeQuery(clntRelQuery);
        List<Object[]> clntRel = qrel.getResultList();

        if (clntRel != null && clntRel.size() > 0) {
            Object[] res = clntRel.get(0);
            Map<String, String> clntrel = new HashMap<>();
            if (res != null) {
                clntrel.put("loanAppseq", res[0].toString());
                clntrel.put("status", res[1].toString());
                clntrel.put("firstName", res[2].toString());
                clntrel.put("lastName", res[3].toString());
                clntrel.put("cnic", res[4].toString());
                clntrel.put("fatherFirstName", res[5].toString());
                clntrel.put("fatherLastName", res[6].toString());

                clntrel.put("genderKey", res[7].toString());
                clntrel.put("maritalStsKey", res[8].toString());
                clntrel.put("prdSeq", res[9].toString());
                clntrel.put("prdName", res[10].toString());
                clntrel.put("multiLoan", (res[11] == null) ? "" : res[11].toString());
                resp.put("clientRel", clntrel);
            }
        }

        resp.put("history", result);
        resp.put("tags", tagsResult);
        resp.put("clientRelHistory", null);

        if (dto.nomCnic != "") {
            String tagQueryn = Queries.tagList;
            log.debug("Final tagQuery is: {} ", tagQueryn);
            Query qtn = em.createNativeQuery(tagQueryn).setParameter("cnicNum", dto.nomCnic);
            ;
            List<Object[]> tagsResultn = qtn.getResultList();
            log.debug("tagsResult", tagsResultn);

            String querynom = Queries.getNomineeHistory(dto.nomCnic);
            log.debug("Final Query is: {} ", querynom);
            Query nomq = em.createNativeQuery(querynom);
            List<Object[]> nomresult = nomq.getResultList();
            log.debug("history", nomresult);

            if (tagsResultn != null && tagsResultn.size() > 0) {
                Object[] res = tagsResultn.get(0);
                TagDto tagdto = new TagDto();
                if (res != null) {

                    tagdto.tagFromDt = res[0].toString();
                    tagdto.tagToDt = res[1].toString();
                    tagdto.tagSeq = res[2].toString();
                    tagdto.tagName = res[3].toString();
                    tagdto.tagDscr = res[4].toString();
                    tagdto.SvrtyFlagKey = res[5].toString();
                    resp.put("nomtag", tagdto);
                }
            }

            String clntRelQueryn = Queries.getClientRelHistory(dto.nomCnic);
            Query qreln = em.createNativeQuery(clntRelQueryn);
            List<Object[]> clntReln = qrel.getResultList();

            if (clntReln != null && clntReln.size() > 0) {
                Object[] res = clntReln.get(0);
                Map<String, String> clntrel = new HashMap<>();
                if (res != null && clntRel.size() > 0) {
                    clntrel.put("loanAppseq", res[0].toString());
                    clntrel.put("status", res[1].toString());
                    clntrel.put("firstName", res[2].toString());
                    clntrel.put("lastName", res[3].toString());
                    clntrel.put("cnic", res[4].toString());
                    clntrel.put("fatherFirstName", res[5].toString());
                    clntrel.put("fatherLastName", res[6].toString());

                    clntrel.put("genderKey", res[7].toString());
                    clntrel.put("maritalStsKey", res[8].toString());
                    clntrel.put("prdSeq", res[9].toString());
                    clntrel.put("prdName", res[10].toString());
                    clntrel.put("multiLoan", (res[11] == null) ? "" : res[11].toString());
                    resp.put("nomClientRel", clntrel);
                }
            }

            resp.put("nomHistory", nomresult);
            resp.put("nomTags", tagsResultn);

        }

        if (dto.cobCnic != "") {
            String tagQueryc = Queries.tagList;
            log.debug("Final tagQuery is: {} ", tagQueryc);
            Query qtc = em.createNativeQuery(tagQueryc).setParameter("cnicNum", dto.cobCnic);
            ;
            List<Object[]> tagsResultc = qtc.getResultList();
            log.debug("tagsResult", tagsResultc);

            if (tagsResultc != null && tagsResultc.size() > 0) {
                Object[] res = tagsResultc.get(0);
                TagDto tagdto = new TagDto();
                if (res != null) {

                    tagdto.tagFromDt = res[0].toString();
                    tagdto.tagToDt = res[1].toString();
                    tagdto.tagSeq = res[2].toString();
                    tagdto.tagName = res[3].toString();
                    tagdto.tagDscr = res[4].toString();
                    tagdto.SvrtyFlagKey = res[5].toString();
                    resp.put("nomtag", tagdto);
                }
            }

            String querycob = Queries.getCobHistory(dto.cobCnic);
            log.debug("Final Query is: {} ", querycob);
            Query cobq = em.createNativeQuery(querycob);
            List<Object[]> cobresult = cobq.getResultList();
            log.debug("history", cobresult);
            String clntRelQueryc = Queries.getClientRelHistory(dto.cobCnic);
            Query qrelc = em.createNativeQuery(clntRelQueryc);
            List<Object[]> clntRelc = qrelc.getResultList();

            if (clntRelc != null && clntRelc.size() > 0) {
                Object[] res = clntRelc.get(0);
                Map<String, String> clntrel = new HashMap<>();
                if (res != null) {
                    clntrel.put("loanAppseq", res[0].toString());
                    clntrel.put("status", res[1].toString());
                    clntrel.put("firstName", res[2].toString());
                    clntrel.put("lastName", res[3].toString());
                    clntrel.put("cnic", res[4].toString());
                    clntrel.put("fatherFirstName", res[5].toString());
                    clntrel.put("fatherLastName", res[6].toString());

                    clntrel.put("genderKey", res[7].toString());
                    clntrel.put("maritalStsKey", res[8].toString());
                    clntrel.put("prdSeq", res[9].toString());
                    clntrel.put("prdName", res[10].toString());
                    clntrel.put("multiLoan", (res[11] == null) ? "" : res[11].toString());
                    resp.put("cobClientRel", clntrel);
                }
            }
            resp.put("cobHistory", cobresult);
            resp.put("cobTags", tagsResultc);

        }

        return resp;
    }

    public Map<String, Object> getUserValidation(String cnicNum) {
        Map<String, Object> resp = new HashMap<String, Object>();
        String tagQuery = Queries.tagList;
        resp.put("canProceed", true);
        Query qt = em.createNativeQuery(tagQuery).setParameter("cnicNum", cnicNum);
        ;
        List<Object[]> tagsResult = qt.getResultList();
        if (tagsResult != null && tagsResult.size() > 0) {
            Object[] res = tagsResult.get(0);
            TagDto tagdto = new TagDto();
            if (res != null) {
                try {
                    if ((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res[0].toString()).toInstant()
                            .compareTo(Instant.now()) < 0)
                            && ((res[1] == null) ? true
                            : (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res[1].toString()).toInstant()
                            .compareTo(Instant.now()) > 0))) {
                        tagdto.tagFromDt = (res[0] == null) ? "" : res[0].toString();
                        tagdto.tagToDt = (res[1] == null) ? "" : res[1].toString();
                        tagdto.tagSeq = (res[2] == null) ? "" : res[2].toString();
                        tagdto.tagName = (res[3] == null) ? "" : res[3].toString();
                        tagdto.tagDscr = (res[4] == null) ? "" : res[4].toString();
                        tagdto.SvrtyFlagKey = (res[5] == null) ? "" : res[5].toString();
                        if (tagdto.SvrtyFlagKey.trim().toLowerCase().equals("0")) {
                            resp.put("canProceed", true);
                            resp.put("reason", "Warn Client");
                        } else {
                            resp.put("canProceed", false);
                            resp.put("reason", tagdto.tagDscr);
                        }
                        resp.put("tag", tagdto);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // List< MwNactaList > nactaList = mwNactaListRepository.findAllByCnicNum( cnicNum );
        // if ( nactaList.size() > 0 ) {
        // MwNactaList nacta = nactaList.get( 0 );
        // TagDto tagdto = new TagDto();
        // tagdto.tagSeq = "5";
        // tagdto.tagName = "AML User tagged by NACTA";
        // tagdto.tagDscr = "User tagged by NACTA";
        // tagdto.SvrtyFlagKey = "1";
        // resp.put( "canProceed", false );
        // resp.put( "reason", tagdto.tagDscr );
        // resp.put( "tag", tagdto );
        //
        // Long portSeq = new BigDecimal( em.createNativeQuery( "select port_seq from mw_emp e\r\n"
        // + " join mw_port_emp_rel erel on erel.emp_seq=e.emp_seq and erel.crnt_rec_flg=1\r\n" + " where e.emp_lan_id=:userNm" )
        // .setParameter( "userNm", SecurityContextHolder.getContext().getAuthentication().getName() ).getSingleResult()
        // .toString() ).longValue();
        // MwRefCdVal val = mwRefCdValRepository.findRefCdByGrpAndVal( "2785", "1642" );
        // MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
        // long amlMtchdClntSeq = SequenceFinder.findNextVal( "AML_MTCHD_CLNT_SEQ" );
        // mtchdClnt.setAmlMtchdClntSeq( amlMtchdClntSeq );
        // mtchdClnt.setAmlSrcAgy( val.getRefCdSeq() );
        // mtchdClnt.setCnicNum( Long.parseLong( cnicNum ) );
        // mtchdClnt.setCrtdBy( SecurityContextHolder.getContext().getAuthentication().getName() );
        // mtchdClnt.setCrtdDt( Instant.now() );
        // mtchdClnt.setPortSeq( portSeq );
        // mwAmlMtchdClntRepository.save( mtchdClnt );
        // }

        String query = Queries.clientHistory();
        Query q = em.createNativeQuery(query).setParameter("cnic", cnicNum);
        List<Object[]> result = q.getResultList();
        if (result != null && result.size() > 0) {
            Object[] loan = result.get(0);
            if (loan != null) {
                PersonalInfoDto dto = new PersonalInfoDto();
                dto.loanAppSeq = (loan[0] == null) ? "" : loan[0].toString();
                dto.status = (loan[1] == null) ? "" : loan[1].toString();
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
                dto.portKey = (loan[21] == null) ? 0L : new BigDecimal(loan[21].toString()).longValue();
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
                    dto.clntDob = (loan[29] == null) ? null : tabDateFormat.format(simpleDateFormat.parse(loan[33].toString()));
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
                dto.addressRelSeq = (loan[55] == null) ? 0 : new BigDecimal(loan[55].toString()).longValue();
                dto.addresSeq = (loan[56] == null) ? 0 : new BigDecimal(loan[56].toString()).longValue();

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
                dto.motherMaidenName = (loan[69] == null) ? "" : loan[69].toString();

                dto.isSANt = (loan[30] == null) ? 0 : Integer.parseInt(loan[30].toString());
                dto.isNomDetailAvailablet = (loan[42] == null) ? 0 : Integer.parseInt(loan[42].toString());
                dto.isPermAddresst = (loan[31] == null) ? 0 : Integer.parseInt(loan[31].toString());
                dto.selfPDCt = (loan[50] == null) ? 0 : Integer.parseInt(loan[50].toString());
                dto.disableFlagt = (loan[32] == null) ? 0 : Integer.parseInt(loan[32].toString());

                dto.bm = (loan[70] == null) ? "" : loan[70].toString();
                dto.bdo = (loan[71] == null) ? "" : loan[71].toString();

                // dto.brnchSeq = ( loan[ 72 ] == null ) ? 0 : new BigDecimal( loan[ 72 ].toString() ).longValue();

                if (resp.get("reason") != null) {
                    if (resp.get("reason").toString().length() <= 0) {
                        if (!dto.status.toString().trim().toLowerCase().equals("completed")
                                && !dto.status.toString().trim().toLowerCase().equals("rejected")
                                && !dto.status.toString().trim().toLowerCase().equals("discarded")
                                && !dto.status.toString().trim().toLowerCase().equals("deferred")) {
                            resp.put("canProceed", false);
                            // resp.put( "reason",
                            // "Active as Client with Loan [" + dto.loanAppSeq + "] Client ID [" + dto.clientId + "] Client Name ["
                            // + dto.firstName + " " + dto.lastName + "] Status[" + dto.status + "] in [" + dto.branchName
                            // + "] Branch." );
                            resp.put("reason", "This CNIC is in " + dto.status + " mode in branch " + dto.branchName + " for BDO "
                                    + dto.bdo + " as client.");
                        }
                    }
                } else {
                    if (!dto.status.toString().trim().toLowerCase().equals("completed")
                            && !dto.status.toString().trim().toLowerCase().equals("rejected")
                            && !dto.status.toString().trim().toLowerCase().equals("discarded")
                            && !dto.status.toString().trim().toLowerCase().equals("deferred")) {
                        resp.put("canProceed", false);
                        // resp.put( "reason",
                        // "Active as Client with Loan [" + dto.loanAppSeq + "] Client ID [" + dto.clientId + "] Client Name ["
                        // + dto.firstName + " " + dto.lastName + "] Status[" + dto.status + "] in [" + dto.branchName
                        // + "] Branch." );

                        resp.put("reason", "This CNIC is in " + dto.status + " mode in branch " + dto.branchName + " for BDO " + dto.bdo
                                + " as client.");
                    }
                }

                String valquery = Queries.statusSeq;
                Query qr = em.createNativeQuery(valquery);
                List<Object[]> resultSet = qr.getResultList();

                long draftSeq = 0L;
                long completedStatus = 0L;
                for (Object[] str : resultSet) {
                    if (str[1].toString().toLowerCase().equals("draft"))
                        draftSeq = Long.valueOf(str[2].toString());
                    if (str[1].toString().toLowerCase().equals("completed"))
                        completedStatus = Long.valueOf(str[2].toString());
                }

                List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(dto.clientSeq,
                        completedStatus);
                if (loans.size() > 0) {
                    if (loans.get(0) != null) {
//                        dto.previousAmount = loans.get( 0 ).getAprvdLoanAmt().longValue();
                        // dto.loanCyclNum = loans.get( 0 ).getLoanCyclNum() + 1L;
                        dto.previousPscScore = loans.get(0).getPscScore();
                    }
                }

                resp.put("client", dto);
            }
        }

        String clntRelQuery = Queries.getClientRelHistory(cnicNum);
        Query qrel = em.createNativeQuery(clntRelQuery);
        List<Object[]> clntRel = qrel.getResultList();
        if (clntRel != null && clntRel.size() > 0) {
            Object[] res = clntRel.get(0);
            Map<String, String> clntrel = new HashMap<>();
            if (res != null) {
                clntrel.put("loanAppSeq", res[0].toString());
                clntrel.put("status", res[1].toString());
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
                clntrel.put("type", (res[12] == null) ? "" : res[12].toString());
                clntrel.put("clientId", (res[13] == null) ? "" : res[13].toString());
                clntrel.put("brnchName", (res[14] == null) ? "" : res[14].toString());
                clntrel.put("bm", (res[15] == null) ? "" : res[15].toString());
                clntrel.put("bdo", (res[16] == null) ? "" : res[16].toString());
                String type = "Client Relative";
                if (res[12] != null) {
                    if (res[12].toString().equals("1")) {
                        type = "Nominee";
                    } else if (res[12].toString().equals("3")) {
                        type = "Co-Borrower";
                    } else if (res[12].toString().equals("2")) {
                        type = "Next Of Kin";
                    }
                }

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
                        resp.put("reason", "This CNIC is in " + clntrel.get("status") + " mode in branch " + clntrel.get("brnchName")
                                + " for BDO " + clntrel.get("bdo") + " as " + type + ".");
                    }
                }
                resp.put("clientRel", clntrel);
            }
        }
        return resp;
    }
}
