package com.idev4.loans.web.rest.util;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanFormCmplFlg;
import com.idev4.loans.domain.MwTblIndx;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanFormCmplFlgRepository;
import com.idev4.loans.repository.MwTblIndxRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Service
@Transactional
public class Common {

    public static int Client = 0;
    public static int Nominee = 1;
    public static int Coborrower = 3;
    public static int NextToKin = 2;
    public static int Relatives = 4;
    public static int BusinessAppraisal = 5;
    public static int SchoolAppraisal = 6;
    public static String cobAddress = "CoBorrower";
    public static String businessAddress = "Business";
    public static String relAddress = "ClientRel";
    public static String schApAddress = "SchoolAppraisal";
    public static String clntAddress = "Client";
    public static String DeferredStatus = "1285";
    public static String AdvanceStatus = "1305";
    public static String AmlMatchStatus = "1682";
    public static String DraftStatus = "0001";
    public static String SubmittedStatus = "0002";
    public static String NeedMoreClarificationStatus = "0003";
    public static String ApprovedStatus = "0004";
    public static String ActiveStatus = "0005";
    public static String RejectedStatus = "0007";
    public static String WOStatus = "1245";
    public static String BlackListStatus = "1246";
    public static String DiscardedStatus = "0008";
    public static String CompletedStatus = "0006";
    public static String DisbursedStatus = "0009";
    private static MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository;
    private static MwTblIndxRepository mwTblIndxRepository;
    private static MwClntRepository mwClntRepository;
    private static EntityManager em;
    private static String QUERY_FILE_PATH = File.separator + "opt" + File.separator + "tomcat" + File.separator + "webapps" + File.separator
            + "loanservice" + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "queries" + File.separator;

    public Common(MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository, MwTblIndxRepository mwTblIndxRepository, EntityManager em,
                  MwClntRepository mwClntRepository) {
        Common.mwLoanFormCmplFlgRepository = mwLoanFormCmplFlgRepository;
        Common.mwTblIndxRepository = mwTblIndxRepository;
        Common.em = em;
        Common.mwClntRepository = mwClntRepository;
    }

    public static long updateFormComplFlag(long formSeq, long loanAppSeq, String currUser) {
        MwLoanFormCmplFlg flag = mwLoanFormCmplFlgRepository.findOneByFormSeqAndLoanAppSeqAndCrntRecFlg(formSeq, loanAppSeq, true);

        Instant currIns = Instant.now();
        if (flag == null) {
            long seq = SequenceFinder.findNextVal(Sequences.LOAN_FORM_COMPL_SEQ);
            flag = new MwLoanFormCmplFlg();
            flag.setLoan_form_cmpl_flg_SEQ(seq);
        }
        flag.setCrntRecFlg(true);
        flag.setCrtdBy(currUser);
        flag.setCrtdDt(currIns);
        flag.setDelFlg(false);
        flag.setEffStartDt(currIns);
        flag.setFormSeq(formSeq);
        flag.setLastUpdBy(currUser);
        flag.setLastUpdDt(currIns);
        flag.setLoanAppSeq(loanAppSeq);
        flag.setPlanStsKey(1L);
        return mwLoanFormCmplFlgRepository.save(flag).getLoan_form_cmpl_flg_SEQ();

    }

    public static void removeComplFlag(long formSeq, long loanAppSeq, String currUser) {
        MwLoanFormCmplFlg flag = mwLoanFormCmplFlgRepository.findOneByFormSeqAndLoanAppSeqAndCrntRecFlg(formSeq, loanAppSeq, true);
        if (flag != null)
            mwLoanFormCmplFlgRepository.delete(flag);
    }

    public static String findTableNumber(String tblNm) {
        MwTblIndx tblIndx = mwTblIndxRepository.findOneByTblNm(tblNm);
        return (tblIndx == null) ? "" : ((tblIndx.getIndx() == null) ? "" : tblIndx.getIndx().toString());
    }

    public static Long GenerateLoanAppSequence(String cnic, String cycle_no, String tblNm) {
        char[] hexDigits = {'a', 'b', 'c', 'd', 'e', 'f'};
        String res = Long.toHexString(Long.parseLong(cnic));
        String f_res = "";
        for (int i = 0; i < res.length(); i++) {
            if (Character.isLetter(res.charAt(i)))
                f_res += new String(hexDigits).indexOf(res.charAt(i));
            else
                f_res += res.charAt(i);
        }
        return Long.parseLong(f_res + findTableNumber(tblNm) + cycle_no);
    }

    public static Long GenerateLoanAppSequenceWithClntSeq(String cnic, String cycle_no, String tblNm) {
        MwClnt clnt = mwClntRepository.findOneByCnicNumAndCrntRecFlg(Long.parseLong(cnic), true);
        if (clnt != null)
            return Long.parseLong(clnt.getClntSeq() + findTableNumber(tblNm) + cycle_no);
        else
            return GenerateLoanAppSequence(cnic, cycle_no, tblNm);
    }

    public static Long GenerateClientSequence(String cnic) {
        return GenerateLoanAppSequence(cnic, "", "");
    }

    public static Long GenerateTableSequence(String cnic, String tblNm, Long cycle_no) {
        return GenerateLoanAppSequenceWithClntSeq(cnic, cycle_no + "", tblNm);
    }
    //Ended by Areeba

    //Added by Areeba
    public static Long GenerateTableSequenceWithLoanAppSeq(String loanAppSeq, String tblNm) {
        return Long.parseLong(loanAppSeq) + Long.parseLong(findTableNumber(tblNm));
    }

    public static String GetFormattedDateForTab(Instant ins, boolean simpleFlag) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (simpleFlag)
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = Date.from(ins);
        return sdf.format(date);
    }

    /*public static String getCreditScoringJSON( Long clientSeq, Long brnchSeq ) {
        // Modified by Rizwan Mahfooz - Dated 27-05-2022 - Credit Scoring JSON
*//*        System.out.println( "select CR_CREDIT_SCORING(" + clientSeq + ", " + brnchSeq + ") from dual" );
        Query q = em.createNativeQuery( "select CR_CREDIT_SCORING(" + clientSeq + ", " + brnchSeq + ") from dual" );
        String response = q.getSingleResult().toString();
        System.out.println( "CR JSON found is: " + response );
        return response;*//*
        Query qry = em.createNativeQuery( "select CR_CREDIT_SCORING(" + clientSeq + ", " + brnchSeq + ") from dual" );
        Object fnResp = qry.getSingleResult();
        String response = "";

        if ( fnResp != null ){
            Clob clob = (Clob)fnResp;
            try {
                response = clob.getSubString(1, (int)clob.length());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }*/

    public static Instant getZonedInstant(Instant ins) {
        return ins.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("+05:30"))).toInstant();
    }

    public static String getCreditScoringJSON(Long clientSeq, Long brnchSeq, Long loanCylcNum, String occupation,
                                              Long businessDuration, String businessOwnership, String current_residency_yrs,
                                              String current_residency_mnths, String ndi, String travelling_expense,
                                              String comitte_expense, String food_expense, String earning_members,
                                              String mobile_expense, String rqstdLoanAmount, String consumerRatio,
                                              String earnerRatio, String marriageExpense, String povertyScore,
                                              String areaName, String productName, Long totalExpense,
                                              Long kszbInstallment, String dependants) {
        Query qry = em.createNativeQuery("select CR_CREDIT_SCORING( " + clientSeq + ", " + brnchSeq + ", " + loanCylcNum + ",'" + occupation +
                "', " + businessDuration + ",'" + businessOwnership + "'," + current_residency_yrs + ", " + current_residency_mnths + ", " + ndi + ", " + travelling_expense + ", " + comitte_expense + ", " + food_expense +
                ", " + earning_members + ", " + mobile_expense + ", " + rqstdLoanAmount + ", " + consumerRatio +
                ", " + earnerRatio + ", " + marriageExpense + ", " + povertyScore + ",'" + areaName +
                "','" + productName + "'," + totalExpense + "," + kszbInstallment + "," + dependants + "  ) from dual");

        Object fnResp = qry.getSingleResult();
        String response = "";

        if (fnResp != null) {
            Clob clob = (Clob) fnResp;
            try {
                response = clob.getSubString(1, (int) clob.length());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static String readFile(Charset encoding, String fileName) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(QUERY_FILE_PATH + fileName));
        return new String(encoded, encoding);
    }
}
