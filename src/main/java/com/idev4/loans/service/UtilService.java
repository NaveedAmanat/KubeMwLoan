package com.idev4.loans.service;

import com.idev4.loans.dto.DueSheetDTO;
import com.idev4.loans.dto.ODClientsDTO;
import com.idev4.loans.web.rest.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UtilService {

    private final Logger log = LoggerFactory.getLogger(UtilService.class);

    private final EntityManager em;

    public UtilService(EntityManager em) {
        this.em = em;
    }

    public List<DueSheetDTO> getDueSheet(long pb, long seq) throws IOException {
        String query = "";
        if (pb == 1) {
            query = "brnch_seq = " + seq;
        } else if (pb == 2) {
            query = "port_seq = " + seq;
        }
        String qr = Common.readFile(Charset.defaultCharset(), "dueRecovery.txt");
        qr = qr.replace("%REPLACE%", query);
        Query q = em.createNativeQuery(qr);
        List<Object[]> results = q.getResultList();
        List<DueSheetDTO> resp = new ArrayList<>();

        if (results != null && results.size() != 0) {
            results.forEach(obj -> {
                DueSheetDTO dto = new DueSheetDTO();
                dto.rpt_flg = obj[0] != null ? obj[0].toString() : "";
                dto.clnt_id = obj[1] != null ? obj[1].toString() : "";
                dto.clnt_nm = obj[2] != null ? obj[2].toString() : "";
                dto.fs_nm = obj[3] != null ? obj[3].toString() : "";
                dto.ph_nm = obj[4] != null ? obj[4].toString() : "";
                dto.addr = obj[5] != null ? obj[5].toString() : "";
                dto.dsbmt_dt = obj[6] != null ? obj[6].toString() : "";
                dto.prd_nm = obj[7] != null ? obj[7].toString() : "";
                dto.ost_num_of_inst = obj[8] != null ? obj[8].toString() : "";
                dto.ost_ppal_amnt = obj[9] != null ? obj[9].toString() : "";
                dto.od_amt = obj[10] != null ? obj[10].toString() : "";
                dto.bdo_nm = obj[11] != null ? obj[11].toString() : "";
                resp.add(dto);
            });
        }
        return resp;
    }

    public List<ODClientsDTO> getODClients(long pb, long seq) throws IOException {
        String query = "";
        if (pb == 1) {
            query = "brnch_seq = " + seq;
        } else if (pb == 2) {
            query = "port_seq = " + seq;
        }

        String q;
        q = Common.readFile(Charset.defaultCharset(), "OD_CRDT_RPT_DASHBRD_TBL_INSRT.txt");
        q = q.replace("%REPLACE%", query);
        Query rs5 = em.createNativeQuery(q);

        // Query q = em.createNativeQuery( com.idev4.rds.util.Queries.OVER_DUE_LOANS ).setParameter( "brnch", obj[ 4 ].toString() )
        // .setParameter( "prdSeq", prdSeq ).setParameter( "aDt", asDt );
        int insertStatement = rs5.executeUpdate();

        String selectQueryStr = Common.readFile(Charset.defaultCharset(), "OD_CRDT_RPT_DASHBRD_TBL_SLCT.txt");
        Query selectQuery = em.createNativeQuery(selectQueryStr);
        List<Object[]> results = selectQuery.getResultList();

        // String qr = Common.readFile( Charset.defaultCharset(), "ODClients.txt" );
        // qr = qr.replace( "%REPLACE%", query );
        // Query q = em.createNativeQuery( qr );
        // List< Object[] > results = q.getResultList();
        List<ODClientsDTO> resp = new ArrayList<>();

        if (results != null && results.size() != 0) {
            results.forEach(obj -> {
                ODClientsDTO dto = new ODClientsDTO();
                dto.rpt_flg = obj[0] != null ? obj[0].toString() : "";
                dto.clnt_id = obj[1] != null ? obj[1].toString() : "";
                dto.clnt_nm = obj[2] != null ? obj[2].toString() : "";
                dto.fs_nm = obj[3] != null ? obj[3].toString() : "";
                dto.ph_nm = obj[4] != null ? obj[4].toString() : "";
                dto.addr = obj[5] != null ? obj[5].toString() : "";
                dto.dsbmt_dt = obj[6] != null ? obj[6].toString() : "";
                dto.dsbmt_amt = obj[7] != null ? obj[7].toString() : "";
                dto.prd_nm = obj[8] != null ? obj[8].toString() : "";
                dto.ost_num_of_inst = obj[9] != null ? obj[9].toString() : "";
                dto.ost_ppal_amnt = obj[10] != null ? obj[10].toString() : "";
                dto.ost_srvc_chrgs = obj[11] != null ? obj[11].toString() : "";
                dto.od_num_of_inst = obj[12] != null ? obj[12].toString() : "";
                dto.od_amt = obj[13] != null ? obj[13].toString() : "";
                dto.od_days = obj[14] != null ? obj[14].toString() : "";
                dto.loan_app_seq = obj[15] != null ? obj[15].toString() : "";
                dto.cmpl_dt = obj[16] != null ? obj[16].toString() : "";
                dto.bdo_nm = obj[17] != null ? obj[17].toString() : "";
                dto.cnic_num = obj[18] != null ? obj[18].toString() : "";
                resp.add(dto);
            });
        }
        return resp;
    }
}
