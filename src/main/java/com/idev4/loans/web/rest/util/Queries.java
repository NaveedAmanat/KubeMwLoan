package com.idev4.loans.web.rest.util;

public class Queries {

    public static String statusSeqOld = "select grp.ref_cd_grp_nm, val.ref_cd_dscr, val.ref_cd_seq from mw_ref_cd_grp grp,"
            + " mw_ref_cd_val val where val.ref_cd_grp_key = grp.ref_cd_grp_seq AND grp.ref_cd_grp_nm = 'APPLICATION_STATUS' and grp.crnt_rec_flg=1 and val.crnt_rec_flg=1";

    public static String statusSeq = "select grp.ref_cd_grp_nm, val.ref_cd_dscr, val.ref_cd_seq, val.ref_cd from mw_ref_cd_val val"
            + " join mw_ref_cd_grp grp on val.REF_CD_GRP_KEY = grp.REF_CD_GRP_SEQ and grp.REF_CD_GRP = '0106' and grp.crnt_rec_flg=1"
            + " where val.crnt_rec_flg=1";

    public static String addressCombs = "select st.st_cd as cd ,st.st_nm as nm, st.st_cd,st.st_nm,dst.dist_cd,dst.dist_nm, thsl.thsl_cd, thsl.thsl_nm,uc.uc_cd,uc.uc_nm,uc.uc_commnets,rel.city_uc_rel_seq,cty.city_nm from mw_city_uc_rel rel, mw_city cty, mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st where cty.city_seq = rel.city_seq and rel.uc_seq = uc.uc_seq and uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and rel.del_flg = 0 and cty.del_flg = 0 and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and rel.crnt_rec_flg = 1 and cty.crnt_rec_flg = 1 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1";

    public static String addressCombsForPort = "SELECT st.st_cd as cd, st.st_nm as nm, st.st_cd, st.st_nm, dst.dist_cd, dst.dist_nm, thsl.thsl_cd, thsl.thsl_nm, uc.uc_cd, uc.uc_nm, uc.uc_commnets, rel.city_uc_rel_seq, cty.city_nm from mw_city_uc_rel rel, mw_city cty, mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st, mw_port_location_rel prt where cty.city_seq = rel.city_seq and rel.uc_seq = uc.uc_seq and uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and prt.city_seq = rel.city_uc_rel_seq and rel.del_flg = 0 and cty.del_flg = 0 and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and rel.crnt_rec_flg = 1 and cty.crnt_rec_flg = 1 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1 and prt.crnt_rec_flg=1 and prt.port_seq =";

    public static String entityAddress = "SELECT cntry.cntry_cd, cntry.cntry_nm, st.st_cd, st.st_nm, dist.dist_cd, dist.dist_nm, thsl.thsl_cd, thsl.thsl_nm, uc.uc_cd, uc.uc_nm, uc.uc_commnets, rel.city_uc_rel_seq, city.city_nm FROM mw_cntry cntry, mw_st st, mw_dist dist, mw_thsl thsl, mw_uc uc, mw_city city, mw_city_uc_rel rel WHERE rel.uc_SEQ =uc.UC_SEQ AND uc.thsl_SEQ =thsl.thsl_SEQ AND thsl.dist_SEQ =dist.dist_SEQ AND dist.st_SEQ =st.st_SEQ AND st.cntry_SEQ =cntry.cntry_SEQ AND city.city_seq = rel.city_seq and rel.del_flg = 0 and city.del_flg = 0 and uc.del_flg = 0 and thsl.del_flg = 0 and dist.del_flg = 0 and st.del_flg = 0 and rel.crnt_rec_flg = 1 and city.crnt_rec_flg = 1 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dist.crnt_rec_flg = 1 and st.crnt_rec_flg = 1 and cntry.crnt_rec_flg = 1 AND rel.city_uc_rel_seq =";

    public static String getOrgFromCityUcRelSeq = "SELECT REL.city_uc_rel_seq, rel.city_seq, city.city_nm, rel.uc_seq, uc.uc_nm, thsl.thsl_seq, thsl.thsl_nm, dist.dist_seq, dist.dist_nm, st.st_seq, st.st_nm, cntry.cntry_seq, cntry.cntry_nm FROM mw_city_uc_rel rel JOIN mw_city city ON rel.city_seq = city.city_seq AND city.crnt_rec_flg = 1 JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 WHERE REL.city_uc_rel_seq=";

    public static String clntHistory = "select clnt.frst_nm, clnt.last_nm, clnt.clnt_id, addr.hse_num, addr.strt,"
            + "addrRel.addr_seq, loan.loan_App_sts, val.ref_cd_dscr from mw_clnt clnt, mw_addr addr, mw_addr_rel addrRel, "
            + "mw_loan_app loan, mw_ref_cd_val val where loan.clnt_seq = clnt.clnt_seq AND addr.addr_seq = addrRel.addr_seq "
            + "AND addrRel.enty_key = clnt.clnt_seq AND loan.loan_App_sts = val.ref_cd_seq AND val.ref_cd_dscr = 'Active' AND "
            + "clnt.cnic_num =";

    /*public static String clientsListing = "select clnt.frst_nm, clnt.last_nm, clnt.clnt_id, addr.hse_num, addr.strt,"+
     "addrRel.addr_seq, uc.uc_nm, city.city_nm, loan.loan_App_sts, val.ref_cd_dscr FROM mw_clnt clnt, mw_addr addr,"+
     "mw_addr_rel addrRel, mw_uc uc, mw_city city, mw_loan_app loan, mw_ref_cd_val val WHERE addrRel.enty_key ="+
     "clnt.clnt_seq AND addr.addr_seq = addrRel.addr_seq AND city.city_seq = addr.city_seq AND uc.UC_SEQ ="+ 
     "city.uc_seq AND loan.clnt_seq = clnt.clnt_seq AND val.ref_cd_seq = loan.loan_App_sts";*/

    public static String clntHlthInsr = "select clnt.clnt_seq, loan.loan_App_seq,loan.loan_App_sts, val.ref_cd_dscr,"
            + "val.ref_cd_seq, insr.CLNT_HLTH_INSR_SEQ, insr.HLTH_INSR_PLAN_SEQ, insr.EXCL_CTGRY_KEY, insr.HLTH_INSR_FLG FROM "
            + "mw_clnt clnt, mw_loan_app loan, mw_ref_cd_val val, mw_clnt_hlth_insr insr WHERE insr.loan_App_seq=loan.loan_App_seq "
            + "AND loan.clnt_seq=clnt.clnt_seq AND loan.loan_App_sts = val.ref_cd_seq AND val.ref_cd_dscr = 'Active' "
            + "AND clnt.clnt_seq = ";

    /*public static String clientsListing = "select loan_id,valu.ref_cd_dscr as loan_App_status,frst_nm,last_nm,client_id,cnic_num,gender, marital_status,"+
    "house_num,city,uc,tehsil,dist,state,country from (select loan_id,loan_App_sts,frst_nm,last_nm,clnt_id as client_id,cnic_num,gender, val.ref_cd_dscr "+
    "as marital_status,hse_num as house_num,city_nm as city,uc_nm as uc,thsl_nm as tehsil,dist_nm as dist,st_nm as state,cntry_nm as country "+
    "from (select app.loan_id,app.loan_App_sts,c.frst_nm,c.last_nm,c.clnt_id,c.cnic_num,v.ref_cd_dscr as gender ,mrtl_sts_key,ad.hse_num,cit.city_nm,"+
    "uc_nm,thsl_nm,dist_nm,st_nm,cntry_nm from mw_clnt c join mw_ref_cd_val v on c.gndr_key=v.ref_cd_seq join mw_addr_rel ar on ar.enty_Key=c.clnt_SEQ "+
    "join mw_addr ad on ad.addr_seq=ar.addr_seq join mw_city cit on cit.city_seq=ad.city_seq join mw_uc u on u.UC_SEQ=cit.uc_seq join mw_thsl ths "+
    "on ths.thsl_seq=u.thsl_seq join mw_dist dis on dis.dist_seq=ths.dist_seq join mw_st state on state.st_seq=dis.st_seq join mw_cntry cntry on "+
    "cntry.cntry_seq=state.st_seq join mw_loan_app app on app.clnt_seq=c.clnt_seq)t join mw_ref_cd_val val on t.mrtl_sts_key=val.ref_cd_seq)t1 "+
    "join mw_ref_cd_val valu on t1.loan_App_sts=valu.ref_cd_seq";*/

    public static String clientsListing = "select app.loan_id, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=app.loan_app_sts and crnt_rec_flg=1) "
            + "as loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, "
            + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.gndr_key and crnt_rec_flg=1) as gender_key, "
            + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.mrtl_sts_key and crnt_rec_flg=1) as marital_sts, "
            + "ad.hse_num, cit.city_nm,uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, "
            + "port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm " + "from mw_loan_app app "
            + "join mw_clnt clnt on app.clnt_seq=clnt.clnt_seq and clnt.crnt_rec_flg=1 "
            + "join mw_addr_rel ar on ar.enty_Key=clnt.clnt_SEQ and ar.crnt_rec_flg=1 and ar.enty_typ='Client' "
            + "join mw_addr ad on ad.addr_seq=ar.addr_seq and ad.crnt_rec_flg=1 "
            + "join mw_city cit on cit.city_seq=ad.city_seq and cit.crnt_rec_flg=1 "
            + "join mw_uc uc on uc.UC_SEQ=cit.uc_seq and uc.crnt_rec_flg=1 "
            + "join mw_thsl thsl on thsl.thsl_seq=uc.thsl_seq and thsl.crnt_rec_flg=1 "
            + "join mw_dist dist on dist.dist_seq=thsl.dist_seq and dist.crnt_rec_flg=1 "
            + "join mw_st st on st.st_seq=dist.st_seq and st.crnt_rec_flg=1 "
            + "join mw_cntry cntry on cntry.cntry_seq=st.st_seq and cntry.crnt_rec_flg=1 "
            + "join mw_port port on port.port_seq=app.port_seq and port.crnt_rec_flg=1 "
            + "join mw_brnch brnch on brnch.brnch_seq=port.brnch_seq and brnch.crnt_rec_flg=1 "
            + "join mw_area area on area.area_seq=brnch.area_seq and area.crnt_rec_flg=1 "
            + "join mw_reg reg on reg.reg_seq=area.reg_seq and reg.crnt_rec_flg=1 "
            + "where app.crnt_rec_flg=1 ORDER BY app.LOAN_APP_SEQ DESC";

    // public static String clientsListing(String userId) {
    // return "SELECT app.loan_id, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1 ) AS
    // loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq =
    // clnt.gndr_key AND crnt_rec_flg = 1 ) AS gender_key, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.mrtl_sts_key AND
    // crnt_rec_flg = 1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm,
    // port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm, app.loan_app_sts_dt, app.rcmnd_loan_amt, app.aprvd_loan_amt, prd.prd_nm,
    // cmnty.cmnty_nm, ad.strt FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id = '"+userId+"' JOIN
    // mw_clnt clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND
    // ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 JOIN
    // mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1 JOIN
    // mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1
    // JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 JOIN mw_st st ON st.st_seq = dist.st_seq AND
    // st.crnt_rec_flg = 1 JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 JOIN mw_port port ON port.port_seq
    // = app.port_seq AND port.crnt_rec_flg = 1 JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 JOIN
    // mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1 JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND
    // reg.crnt_rec_flg = 1 left outer JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty
    // ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 WHERE app.crnt_rec_flg = 1 ORDER BY app.loan_app_seq DESC";
    // }
    public static String clientsListingFilter = "select app.loan_id, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=app.loan_app_sts and crnt_rec_flg=1) "
            + "as loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, "
            + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.gndr_key and crnt_rec_flg=1) as gender_key, "
            + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.mrtl_sts_key and crnt_rec_flg=1) as marital_sts, "
            + "ad.hse_num, cit.city_nm,uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, "
            + "port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm " + "from mw_loan_app app "
            + "join mw_clnt clnt on app.clnt_seq=clnt.clnt_seq and clnt.crnt_rec_flg=1 "
            + "join mw_addr_rel ar on ar.enty_Key=clnt.clnt_SEQ and ar.crnt_rec_flg=1 and ar.enty_typ='Client' "
            + "join mw_addr ad on ad.addr_seq=ar.addr_seq and ad.crnt_rec_flg=1 "
            + "join mw_city cit on cit.city_seq=ad.city_seq and cit.crnt_rec_flg=1 "
            + "join mw_uc uc on uc.UC_SEQ=cit.uc_seq and uc.crnt_rec_flg=1 "
            + "join mw_thsl thsl on thsl.thsl_seq=uc.thsl_seq and thsl.crnt_rec_flg=1 "
            + "join mw_dist dist on dist.dist_seq=thsl.dist_seq and dist.crnt_rec_flg=1 "
            + "join mw_st st on st.st_seq=dist.st_seq and st.crnt_rec_flg=1 "
            + "join mw_cntry cntry on cntry.cntry_seq=st.st_seq and cntry.crnt_rec_flg=1 "
            + "join mw_port port on port.port_seq=app.port_seq and port.crnt_rec_flg=1 "
            + "join mw_brnch brnch on brnch.brnch_seq=port.brnch_seq and brnch.crnt_rec_flg=1 "
            + "join mw_area area on area.area_seq=brnch.area_seq and area.crnt_rec_flg=1 "
            + "join mw_reg reg on reg.reg_seq=area.reg_seq and reg.crnt_rec_flg=1 " + "where app.crnt_rec_flg=1";

//    public static String clientsListingForPagination( String userId, String statusList ) {
//        return " FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id = '" + userId
//                + "' JOIN mw_clnt clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1 LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 LEFT OUTER JOIN mw_port port ON port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1 LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1 LEFT OUTER JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 "
//                + " LEFT OUTER JOIN mw_port_emp_rel perel on perel.port_seq=port.port_seq and perel.crnt_rec_flg=1"
//                + " LEFT OUTER JOIN mw_emp emp on emp.emp_seq=perel.emp_seq" + " WHERE app.crnt_rec_flg = 1 and app.loan_app_sts in ( "
//                + statusList + " ) ";
//    }
    public static String tagList = "select list.TAG_FROM_DT, list.TAG_TO_DT, tag.tags_seq, tag.TAG_NM, tag.TAG_DSCR, tag.SVRTY_FLG_KEY from MW_CLNT_TAG_LIST list join MW_TAGS tag on list.TAGS_SEQ=tag.TAGS_SEQ and tag.crnt_rec_flg=1 where tag.tag_id != '0006' and list.cnic_num=:cnicNum";
    public static String verisysStatus = "select get_Verisys_Status(:pLoanAppSeq, :p_function) from dual";
    public static String verisysBmApprovalFunc = "select fn_verisys_bm_approval (:P_LOAN_APP_SEQ) from dual";
    public static String NADI_BY_LOAN_APP_SEQ = "select ndi from vw_loan_app where loan_app_seq=:loanAppSeq";
    public static String IS_APPRAISAL_BY_LOAN_APP_SEQ = "SELECT REL.FORM_SEQ FROM MW_LOAN_APP APP\n" +
            "    JOIN MW_PRD_FORM_REL REL ON REL.PRD_SEQ = APP.PRD_SEQ AND REL.CRNT_REC_FLG =1 AND REL.FORM_SEQ IN (7,14,15) \n" +
            "    WHERE APP.LOAN_APP_SEQ = :loanAppSeq AND APP.CRNT_REC_FLG = 1";
    public static String geographyQuery = "select cntry.cntry_cd,cntry.cntry_nm, st.st_cd,st.st_nm,dist.dist_cd,dist.dist_nm, thsl.thsl_cd, thsl.thsl_nm,uc.uc_cd,"
            + "uc.uc_nm,uc.uc_commnets,city.city_seq,city.city_nm from mw_cntry cntry,mw_st st, mw_dist dist,mw_thsl thsl, mw_uc uc, mw_city city where "
            + "city.uc_SEQ=uc.UC_SEQ and uc.thsl_SEQ=thsl.thsl_SEQ and thsl.dist_SEQ=dist.dist_SEQ and dist.st_SEQ=st.st_SEQ and "
            + "st.cntry_SEQ=cntry.cntry_SEQ";
    public static String orgnizationQuery = "select reg.reg_seq,reg_nm, area.area_seq, area.area_nm, branch.brnch_seq, branch.brnch_nm, port.port_seq,"
            + "port_nm, cmnty.cmnty_seq, cmnty.cmnty_nm from mw_reg reg, mw_area area, mw_brnch branch, mw_port port, mw_cmnty cmnty where area.reg_seq = reg.reg_seq "
            + "and branch.area_seq = area.area_seq and port.brnch_seq = branch.brnch_seq and cmnty.port_seq = port.port_seq";
    public static String otherFiltersQuery = "select grp.ref_cd_grp_seq, grp.ref_cd_grp, grp.ref_cd_grp_nm, val.ref_cd_seq, val.ref_cd_grp_key, "
            + "val.ref_cd_dscr from mw_ref_cd_grp grp, mw_ref_cd_val val where val.ref_cd_grp_key = grp.ref_cd_grp_seq AND (grp.REF_CD_GRP_NM='GENDER' OR "
            + "grp.REF_CD_GRP_NM = 'MARITAL_STATUS')";
    // Has BM BDO inline select
    // public static String clientHistory( String cnic ) {
    // return " SELECT app.loan_app_seq, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1 )
    // AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, clnt.fthr_frst_nm, clnt.fthr_last_nm, ( SELECT ref_cd_dscr
    // FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1 ) AS gender_key, ( SELECT ref_cd_dscr FROM mw_ref_cd_val
    // WHERE ref_cd_seq = clnt.mrtl_sts_key AND crnt_rec_flg = 1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm,
    // dist.dist_nm, st.st_nm, cntry.cntry_nm, ( SELECT port_nm FROM mw_port port WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg =
    // 1 ) AS port_nm, ( SELECT brnch_nm FROM mw_brnch brnch WHERE brnch.brnch_seq = ( SELECT port.brnch_seq FROM mw_port port WHERE
    // port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 ) AND brnch.crnt_rec_flg = 1 ) AS brnch_nm, ( SELECT area_nm FROM mw_area area
    // WHERE area.area_seq = ( SELECT brnch.area_seq FROM mw_brnch brnch WHERE brnch.brnch_seq = ( SELECT port.brnch_seq FROM mw_port port
    // WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 ) AND brnch.crnt_rec_flg = 1 ) AND area.crnt_rec_flg = 1 ) AS area_nm, (
    // SELECT reg.reg_nm FROM mw_reg reg WHERE reg.reg_seq = ( SELECT area.reg_seq FROM mw_area area WHERE area.area_seq = ( SELECT
    // brnch.area_seq FROM mw_brnch brnch WHERE brnch.brnch_seq = ( SELECT port.brnch_seq FROM mw_port port WHERE port.port_seq =
    // app.port_seq AND port.crnt_rec_flg = 1 ) AND brnch.crnt_rec_flg = 1 ) AND area.crnt_rec_flg = 1 ) AND reg.crnt_rec_flg = 1 ) AS
    // reg_nm, prd.prd_seq, prd.prd_nm, prd.mlt_loan_flg, app.aprvd_loan_amt, app.rcmnd_loan_amt, app.rqstd_loan_amt, clnt.clnt_seq,
    // clnt.clnt_sts_key, clnt.cnic_expry_dt, clnt.co_bwr_san_flg, clnt.crnt_addr_perm_flg, clnt.dis_flg, clnt.dob, clnt.edu_lvl_key,
    // clnt.erng_memb, clnt.gndr_key, clnt.hse_hld_memb, clnt.mnths_res, clnt.mrtl_sts_key, clnt.natr_of_dis_key, clnt.nick_nm,
    // clnt.nom_dtl_available_flg, clnt.num_of_chldrn, clnt.num_of_dpnd, clnt.num_of_erng_memb, clnt.occ_key, clnt.ph_num, clnt.port_key,
    // clnt.res_typ_key, clnt.slf_pdc_flg, clnt.spz_frst_nm, clnt.spz_last_nm, clnt.tot_incm_of_erng_memb, clnt.yrs_res, ar.addr_rel_seq,
    // ad.addr_seq, ad.cmnty_seq, ad.oth_dtl, ad.strt, ad.vlg, rel.city_uc_rel_seq, rel.city_seq, uc.uc_seq, thsl.thsl_seq, dist.dist_seq,
    // st.st_seq, cntry.cntry_seq, app.loan_cycl_num, clnt.mthr_madn_nm, ( SELECT emp.emp_nm FROM mw_emp emp JOIN mw_brnch brnch ON
    // brnch.brnch_seq = ( SELECT port.brnch_seq FROM mw_port port WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 ) AND
    // brnch.crnt_rec_flg = 1 JOIN mw_brnch_emp_rel rel ON rel.brnch_seq = brnch.brnch_seq AND rel.crnt_rec_flg = 1 AND rel.del_flg = 0
    // WHERE emp.emp_seq = rel.emp_seq ) AS bm, ( SELECT emp.emp_nm FROM mw_emp emp JOIN mw_port_emp_rel rel ON rel.port_seq = app.port_seq
    // AND rel.crnt_rec_flg = 1 WHERE emp.emp_seq = rel.emp_seq ) AS bdo, ( SELECT port.brnch_seq FROM mw_port port WHERE port_seq =
    // app.port_seq AND crnt_rec_flg = 1 ) AS brnchseq, app.co_bwr_addr_as_clnt_flg, app.crtd_dt lst_app_dt, dsbmt_dt, (select sum(pymt_amt)
    // from mw_pymt_sched_hdr psh join mw_pymt_sched_dtl psd on psd.pymt_sched_hdr_seq=psh.pymt_sched_hdr_seq and psd.crnt_rec_flg=1 join
    // mw_rcvry_dtl rdl on rdl.pymt_sched_dtl_seq=psd.pymt_sched_dtl_seq and rdl.crnt_rec_flg=1 where psh.crnt_rec_flg=1 and
    // psh.loan_app_seq=app.loan_app_seq) paid_amt, (select ref_cd_dscr from mw_ref_cd_val where crnt_rec_flg=1 and
    // ref_cd_seq=app.loan_utl_sts_seq) utl, app.loan_utl_cmnt, get_pd_od_inst(app.loan_app_seq) od_inst FROM mw_loan_app app JOIN mw_clnt
    // clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 AND clnt.cnic_num = "
    // + cnic
    // + " LEFT OUTER JOIN mw_dsbmt_vchr_hdr hdr ON hdr.loan_app_seq = app.loan_app_seq AND hdr.crnt_rec_flg = 1 LEFT OUTER JOIN mw_prd prd
    // ON app.prd_seq = prd.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND
    // ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 LEFT
    // OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND
    // cit.crnt_rec_flg = 1 LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 LEFT OUTER JOIN mw_thsl thsl ON
    // thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND
    // dist.crnt_rec_flg = 1 LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cntry cntry ON
    // cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 LEFT OUTER JOIN mw_ref_cd_val val ON val.ref_cd_seq = app.loan_app_sts AND
    // val.crnt_rec_flg = 1 WHERE app.crnt_rec_flg = 1 AND val.ref_cd != '0007' AND val.ref_cd != '0008' AND val.ref_cd != '1285' AND
    // prd.prd_typ_key != '1165' ORDER BY app.loan_cycl_num DESC ";
    // }
    public static String loanApplocation = "select p.PORT_NM, b.BRNCH_CD,b.BRNCH_NM, a.AREA_NM, r.REG_NM from MW_BRNCH b "
            + "join mw_port p on p.BRNCH_SEQ= b.BRNCH_SEQ " + "join mw_area a on a.area_seq= b.area_seq "
            + "join mw_reg r on r.REG_SEQ=a.REG_SEQ " + "where b.CRNT_REC_FLG=1 " + "and p.CRNT_REC_FLG=1 " + "and a.CRNT_REC_FLG=1 "
            + "and r.CRNT_REC_FLG=1 " + "and p.port_seq=";
    public static String advRule = "Select 1 from vw_loan_app where loan_seq=";
    public static String CLNT_LOAN_GTT = "insert into CLNT_LOAN_GTT (SELECT c.clnt_seq,    c.clnt_id, \r\n"
            + "              c.cnic_num,    c.cnic_expry_dt,    c.frst_nm,    c.last_nm,    c.nick_nm,    c.mthr_madn_nm, \r\n"
            + "              c.fthr_frst_nm,    c.fthr_last_nm,    c.spz_frst_nm,    c.spz_last_nm,    c.dob,    c.num_of_dpnd, \r\n"
            + "              c.erng_memb,    c.hse_hld_memb,    c.num_of_chldrn,    c.num_of_erng_memb,    c.yrs_res, \r\n"
            + "              c.mnths_res,    c.port_key,    c.gndr_key,    c.mrtl_sts_key,    c.edu_lvl_key,    c.occ_key, \r\n"
            + "              c.natr_of_dis_key,    c.clnt_sts_key,    c.res_typ_key,    c.dis_flg,    c.slf_pdc_flg, \r\n"
            + "              c.crnt_addr_perm_flg,    c.ph_num,    l.loan_app_seq,    l.loan_id,    l.loan_cycl_num, \r\n"
            + "              l.rqstd_loan_amt,    l.aprvd_loan_amt,    l.prd_seq,    l.rcmnd_loan_amt,    l.loan_app_sts, \r\n"
            + "              l.cmnt,    l.REJECTION_REASON_CD,    l.port_seq  FROM mw_clnt c  JOIN mw_loan_app l \r\n"
            + "            ON l.clnt_seq=c.clnt_seq  WHERE l.crnt_rec_flg=1 AND c.crnt_rec_flg=1 AND c.clnt_seq =:clntSeq and l.loan_app_seq=:loanAppSeq)";
    public static String checkRule = "select 1 from CLNT_LOAN_GTT where ";
    /*
    Modified by Yousaf on 03-Mar-2022:
    Add "rcvd in from brnch for portfolio transfer"
     */
    public static String ALLONERECOVERYBYLOANAPP = "select psd.pymt_sched_dtl_seq,psd.inst_num inst_num, \n" +
            "psd.ppal_amt_due,psd.tot_chrg_due,sum(psc.amt) amt,psd.due_dt,sts.ref_cd_dscr sts, \n" +
            "pymt.rcvry_trx_seq, pymt.pymt_dt,pymt.instr_num, \n" +
            "pymt.typ_str||max(( \n" +
            "select ' rcvd in '||bb.brnch_dscr from RPTB_PORT_TRF_DETAIL trf\n" +
            "join mw_brnch bb on bb.brnch_Seq = trf.from_branch\n" +
            "where trf.loan_app_seq = la.prnt_loan_app_seq \n" +
            "and trf.REMARKS = 'LOAN' and trf.LOAN_APP_SEQ = :loanSeq                       \n" +
            "and trf.TRF_DT = (select max(tt.TRF_DT) from RPTB_PORT_TRF_DETAIL tt where tt.LOAN_APP_SEQ = trf.loan_app_seq)\n" +
            "group by bb.brnch_dscr                                                \n" +
            "having max(trf.TRF_DT) >= pymt.EFF_START_DT\n" +
            ")) typ_str,\n" +
            "pymt.pymt_amt,pymt.post_flg,pymt.trx_pymt,pymt.rcvry_typ_seq,prd.PRD_CMNT\n" +
            "from mw_pymt_sched_dtl psd \n" +
            "left outer join (select pymt_sched_dtl_seq, \n" +
            "listagg( rcvry_trx_seq,',') within group (order by rcvry_trx_seq) rcvry_trx_seq, \n" +
            "listagg( pymt_dt,',') within group (order by rcvry_trx_seq) pymt_dt, \n" +
            "listagg( typ_str,',') within group (order by rcvry_trx_seq) typ_str, \n" +
            "listagg( instr_num,',') within group (order by rcvry_trx_seq) instr_num, \n" +
            "listagg( pymt_amt,',') within group (order by rcvry_trx_seq) pymt_amt, \n" +
            "listagg( post_flg,',') within group (order by rcvry_trx_seq) post_flg, \n" +
            "listagg( trx_pymt,',') within group (order by rcvry_trx_seq) trx_pymt, \n" +
            "listagg( rcvry_typ_seq,',') within group (order by rcvry_trx_seq) rcvry_typ_seq,\n" +
            "EFF_START_DT \n" +
            "from( select dtl.pymt_sched_dtl_seq,trx.rcvry_trx_seq,nvl(trx.post_flg,0)post_flg,trx.pymt_dt,typ.typ_str,trx.instr_num,sum(dtl.pymt_amt) pymt_amt,trx.pymt_amt trx_pymt,trx.rcvry_typ_seq,\n" +
            "max(trx.EFF_START_DT) EFF_START_DT \n" +
            "from mw_rcvry_dtl dtl  join mw_rcvry_trx trx on trx.rcvry_trx_seq = dtl.rcvry_trx_seq \n" +
            "join mw_typs typ on typ.typ_seq = trx.rcvry_typ_seq where dtl.crnt_rec_flg = 1 \n" +
            "and trx.crnt_rec_flg = 1 and typ.crnt_rec_flg = 1 AND trx.pymt_ref = to_char(:clntSeq)  \n" +
            "group by dtl.pymt_sched_dtl_seq,trx.rcvry_trx_seq,trx.post_flg,trx.pymt_dt,typ.typ_str,trx.instr_num,trx.pymt_amt,trx.rcvry_typ_seq \n" +
            ")group by pymt_sched_dtl_seq,EFF_START_DT ) pymt on pymt.pymt_sched_dtl_seq = psd.pymt_sched_dtl_seq \n" +
            "join mw_pymt_sched_hdr psh on psd.pymt_sched_hdr_seq=psh.pymt_sched_hdr_seq and psh.crnt_rec_flg=1 \n" +
            "left outer join mw_pymt_sched_chrg psc on psd.pymt_sched_dtl_seq = psc.pymt_sched_dtl_seq and psc.crnt_rec_flg=1  \n" +
            "join mw_loan_app la on la.loan_app_seq=psh.loan_app_seq and la.crnt_rec_flg=1 \n" +
            "join mw_prd prd on prd.PRD_SEQ=la.PRD_SEQ and prd.CRNT_REC_FLG = 1 \n" +
            "join mw_clnt c on c.clnt_seq=la.clnt_seq and c.crnt_rec_flg=1 \n" +
            "left outer join mw_ref_cd_val sts on psd.pymt_sts_key=sts.ref_cd_seq and sts.crnt_rec_flg=1  \n" +
            "where psd.crnt_rec_flg=1 and la.prnt_loan_app_seq =:loanSeq  and la.clnt_seq=:clntSeq\n" +
            "group by psd.pymt_sched_dtl_seq,c.clnt_id,c.frst_nm,c.last_nm,la.prnt_loan_app_seq,la.loan_id, \n" +
            "psd.inst_num,psd.ppal_amt_due,psd.tot_chrg_due,psd.due_dt,sts.ref_cd_dscr, \n" +
            "pymt.rcvry_trx_seq,pymt.pymt_dt,pymt.instr_num,pymt.typ_str,pymt.pymt_amt,pymt.post_flg,pymt.trx_pymt,pymt.rcvry_typ_seq,prd.PRD_CMNT,\n" +
            "pymt.EFF_START_DT \n" +
            "order by psd.due_dt,la.prnt_loan_app_seq desc";
    public static String dashBoardQuery = "select sum(nvl(trgt_clnt,0)) trgt_clnt,\r\n"
            + "sum(nvl(new_clnt,0)+nvl(rpt_clnt,0)) achvd_clnts,\r\n" + "sum(nvl(tot_amt_due,0)) tot_amt_due,\r\n"
            + "sum(nvl(rcvry_amt,0)) tot_rcvd_amt,\r\n" + "sum(nvl(tb_cmpltd_clnts,0)) tb_cmpltd,\r\n"
            + "case when sum((nvl(new_clnt,0)+nvl(rpt_clnt,0))) <> 0 then sum(rpt_clnt) / sum((nvl(new_clnt,0)+nvl(rpt_clnt,0))) else 0 end rtnd_clnt,\r\n"
            + "case when sum(rpt_clnt) <> 0 then sum(rpt15_clnt) / sum(rpt_clnt) else 0 end tat,\r\n"
            + "case when sum(new_clnt) <> 0 then sum(new_clnt_amt) / sum(new_clnt) else 0 end new_loan_size,\r\n"
            + "case when sum(rpt_clnt) <> 0 then sum(rpt_clnt_amt) / sum(rpt_clnt) else 0 end rpt_loan_size,\r\n"
            + "case when sum(port_clnt_cnt) <> 0 then sum(wmn_part) / sum(port_clnt_cnt) else 0 end female_part,nvl(sum(ip_clnt),0) ip_clnt,\r\n"
            + "nvl(sum(ip_amt),0) ip_amt\r\n" + "from (\r\n" + "select ap.port_seq,ap.prd_seq,\r\n"
            + "count(case when ap.loan_cycl_num=1 then ap.clnt_seq else null end) new_clnt,\r\n"
            + "sum(case when ap.loan_cycl_num=1 then dtl.amt else null end) new_clnt_amt,\r\n"
            + "count(case when ap.loan_cycl_num>1 then ap.clnt_seq else null end) rpt_clnt,\r\n"
            + "sum(case when ap.loan_cycl_num>1 then dtl.amt else null end) rpt_clnt_amt,\r\n"
            + "count(distinct ap.port_seq) over (partition by 1) port_cnt,\r\n"
            + "count(case when ap.loan_cycl_num>1 and dsbmt_dt-cmp_dt >=15 then ap.clnt_seq else null end) rpt15_clnt,\r\n"
            + "trgt.trgt_clients / (select count(port_seq) from mw_port prt where prt.crnt_rec_flg=1 and prt.brnch_seq=98) trgt_clnt,\r\n"
            + "trgt.trgt_amt / count(distinct ap.port_seq) over (partition by 1) trgt_amt,\r\n" + "par.par_amt par_amt,\r\n"
            + "par.od_clnts od_clnts,\r\n" + "rcv.rcvry_amt rcvry_amt,\r\n" + "tot_amt_due tot_amt_due,\r\n"
            + "tot_clnt_due tot_clnt_due,\r\n" + "tb_cmpltd_clnts tb_cmpltd_clnts,\r\n" + "port_clnt_cnt,\r\n" + "wmn_part,\r\n"
            + "ip_clnt,\r\n" + "ip_amt\r\n" + "from mw_clnt clnt\r\n"
            + "join mw_loan_app ap on ap.clnt_seq=clnt.clnt_seq and ap.crnt_rec_flg=1\r\n"
            + "join mw_prd prd on prd.prd_seq=ap.prd_seq and prd.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val sts on sts.ref_cd_seq=ap.loan_app_sts and sts.crnt_rec_flg=1 and sts.ref_cd='0005'\r\n"
            + "join mw_ref_cd_val gndr on gndr.ref_cd_seq=clnt.gndr_key and gndr.crnt_rec_flg=1\r\n"
            + "join mw_dsbmt_vchr_hdr hdr on hdr.loan_app_seq=ap.loan_app_seq and hdr.crnt_rec_flg=1\r\n"
            + "join mw_dsbmt_vchr_dtl dtl on dtl.dsbmt_hdr_seq=hdr.dsbmt_hdr_seq and dtl.crnt_rec_flg=1\r\n" + "join (\r\n"
            + "-- Total Due in the Period\r\n"
            + "select ap.port_seq,ap.prd_seq,sum(psd.ppal_amt_due+nvl(tot_chrg_due,0)+nvl(oth.oth_chrg,0)) tot_amt_due,count(distinct ap.clnt_seq) tot_clnt_due\r\n"
            + "from mw_loan_app ap\r\n" + "join mw_pymt_sched_hdr psh on psh.loan_app_seq = ap.loan_app_seq and psh.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_dtl psd on psd.pymt_sched_hdr_seq=psh.pymt_sched_hdr_seq and psd.crnt_rec_flg=1\r\n"
            + "left outer join (select pymt_sched_dtl_seq,sum(amt) oth_chrg from mw_pymt_sched_chrg where crnt_rec_flg=1 group by pymt_sched_dtl_seq) oth\r\n"
            + "on oth.pymt_sched_dtl_seq=psd.pymt_sched_dtl_seq\r\n" + "where ap.crnt_rec_flg=1\r\n"
            + "and psd.due_dt between ':startDate' and ':endDate'\r\n"
            + "group by ap.port_seq,ap.prd_seq) td on td.port_seq=ap.port_seq and td.prd_seq=ap.prd_seq\r\n" + "-- End of Total Due\r\n"
            + "\r\n"
            + "left outer join (select brnch_seq,prd_seq,trgt_clients,trgt_amt from mw_brnch_trgt trgt where brnch_seq=98 and trgt_perd = to_char(to_date(':endDate'),'YYYYMM')) trgt on\r\n"
            + "trgt.prd_seq=prd.prd_grp_seq\r\n" + "-- start par\r\n" + "left outer join\r\n" + "(\r\n"
            + "select ap.port_seq,ap.prd_seq, sum(dtl.ppal_amt_due+tot_chrg_due+oth.oth_chrg) par_amt,count(distinct ap.clnt_seq) od_clnts\r\n"
            + "from mw_loan_app ap\r\n"
            + "join mw_ref_cd_val sts on sts.ref_cd_seq=ap.loan_app_sts and sts.crnt_rec_flg=1 and sts.ref_cd='0005'\r\n"
            + "join mw_pymt_sched_hdr hdr on hdr.loan_app_seq=ap.loan_app_seq and hdr.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_dtl dtl on dtl.pymt_sched_hdr_seq=hdr.pymt_sched_hdr_seq and dtl.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val psts on psts.ref_cd_seq=dtl.pymt_sts_key and psts.crnt_rec_flg=1\r\n"
            + "left outer join (select pymt_sched_dtl_seq,sum(amt) oth_chrg from mw_pymt_sched_chrg where crnt_rec_flg=1 group by pymt_sched_dtl_seq) oth\r\n"
            + "on oth.pymt_sched_dtl_seq=dtl.pymt_sched_dtl_seq\r\n" + "where ap.crnt_rec_flg=1\r\n" + "and exists (select null\r\n"
            + "from mw_clnt cl\r\n" + "join mw_loan_app ap on ap.clnt_seq=cl.clnt_seq and ap.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_hdr hdr on hdr.loan_app_seq=ap.loan_app_seq and hdr.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_dtl dtl on dtl.pymt_sched_hdr_seq=hdr.pymt_sched_hdr_seq and dtl.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val psts on psts.ref_cd_seq=dtl.pymt_sts_key and psts.crnt_rec_flg=1\r\n"
            + "where sysdate>due_dt and psts.ref_cd='0945' and cl.clnt_seq=ap.clnt_seq)\r\n" + "\r\n" + "and psts.ref_cd='0945'\r\n"
            + "--and sysdate>due_dt\r\n" + "group by ap.port_seq,ap.prd_seq\r\n"
            + ") par on par.port_seq=ap.port_seq and par.prd_seq=ap.prd_seq\r\n" + "-- End Par\r\n" + "-- start prev Completion Date\r\n"
            + "left outer join\r\n" + "(\r\n" + "select cl.clnt_seq,max(loan_app_sts_dt) cmp_dt\r\n" + "from mw_loan_app cl\r\n"
            + "join mw_ref_cd_val sts on sts.ref_cd_seq=cl.loan_app_sts and sts.crnt_rec_flg=1\r\n"
            + "where sts.ref_cd='0006' and cl.crnt_rec_flg=1\r\n" + "group by cl.clnt_seq\r\n" + ") pcd on pcd.clnt_seq=ap.clnt_seq\r\n"
            + "-- End of Previous Completion Date\r\n" + "left outer join (\r\n" + "-- start recovery Amount\r\n"
            + "select ap.port_seq,ap.prd_seq,sum(rd.pymt_amt) rcvry_amt\r\n" + "from mw_rcvry_dtl rd\r\n"
            + "join mw_rcvry_trx trx on trx.rcvry_trx_seq=rd.rcvry_trx_seq and trx.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_dtl psd on psd.pymt_sched_dtl_seq=rd.pymt_sched_dtl_seq and psd.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_hdr psh on psh.pymt_sched_hdr_seq = psd.pymt_sched_hdr_seq and psh.crnt_rec_flg=1\r\n"
            + "join mw_loan_app ap on ap.loan_app_seq=psh.loan_app_seq and ap.crnt_rec_flg=1\r\n" + "where rd.crnt_rec_flg=1\r\n"
            + "and pymt_dt between ':startDate' and ':endDate'\r\n" + "group by ap.port_seq,ap.prd_seq\r\n"
            + ") rcv on rcv.port_seq=ap.port_seq and rcv.prd_seq=ap.prd_seq\r\n" + "-- End of Recovery Amount\r\n" + "left outer join (\r\n"
            + "-- To Be Completed Clients\r\n" + "select ap.port_seq,ap.prd_seq,count(distinct ap.clnt_seq) tb_cmpltd_clnts\r\n"
            + "from mw_loan_app ap\r\n" + "join mw_pymt_sched_hdr psh on psh.loan_app_seq=ap.loan_app_seq and psh.crnt_rec_flg=1\r\n"
            + "join mw_pymt_sched_dtl psd on psd.pymt_sched_hdr_seq=psh.pymt_sched_hdr_seq and psd.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val sts on sts.ref_cd_seq=ap.loan_app_sts and sts.crnt_rec_flg=1 and sts.ref_cd='0005'\r\n"
            + "where ap.crnt_rec_flg=1\r\n"
            + "and psd.inst_num = (select max(inst_num) from mw_pymt_sched_hdr h join mw_pymt_sched_dtl d on\r\n"
            + "d.pymt_sched_hdr_seq=h.pymt_sched_hdr_seq and d.crnt_rec_flg=1 where h.crnt_rec_flg=1 and h.loan_app_seq=ap.loan_app_seq)\r\n"
            + "and due_dt between ':startDate' and ':endDate'\r\n"
            + "group by ap.port_seq,ap.prd_seq) cmpltd on cmpltd.port_seq=ap.port_seq and cmpltd.prd_seq=ap.prd_seq\r\n"
            + "-- End of to Be Completed\r\n" + "left outer join (\r\n" + "-- Women Participation\r\n"
            + "select ap.port_seq,ap.prd_seq,\r\n"
            + "count(case when ((bo.ref_cd='0191' and gndr.ref_cd='0019') or (bo.ref_cd in ('0190','1055','1057','1056','1058','1059'))) then ap.clnt_seq else null end) wmn_part,\r\n"
            + "count(distinct clnt.clnt_seq) port_clnt_cnt\r\n" + "from mw_clnt clnt\r\n"
            + "join mw_ref_cd_val gndr on gndr.ref_cd_seq=clnt.gndr_key and gndr.crnt_rec_flg=1\r\n"
            + "join mw_loan_app ap on ap.clnt_seq=clnt.clnt_seq and ap.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val lst on lst.ref_cd_seq=ap.loan_app_sts and lst.crnt_rec_flg=1 and lst.ref_cd='0005'\r\n"
            + "join mw_biz_aprsl biz on biz.loan_app_seq=ap.loan_app_seq and biz.crnt_rec_flg=1\r\n"
            + "join mw_ref_cd_val bo on bo.ref_cd_seq=biz.prsn_run_the_biz and bo.crnt_rec_flg=1\r\n"
            + "where ap.crnt_rec_flg=1 --and ap.loan_app_seq=22175\r\n" + "group by ap.port_seq,ap.prd_seq\r\n"
            + ") wp on wp.port_seq=ap.port_seq and wp.prd_seq=ap.prd_seq\r\n" + "-- End of Women Participation\r\n"
            + "Left outer join (\r\n" + "-- In Process Applications\r\n"
            + "select ap.port_seq,ap.prd_seq,count(distinct ap.clnt_seq) ip_clnt,sum(ap.aprvd_loan_amt) ip_amt\r\n"
            + "from mw_loan_app ap\r\n"
            + "join mw_ref_cd_val ast on ast.ref_cd_seq=ap.loan_app_sts and ap.crnt_rec_flg=1 and ast.ref_cd in ('0002','0004')\r\n"
            + "where ap.crnt_rec_flg=1\r\n"
            + "group by ap.port_seq,ap.prd_seq ) ip on ip.port_seq=ap.port_seq and ip.prd_seq=ap.prd_seq\r\n"
            + "-- end of in Process Application\r\n" + "where clnt.crnt_rec_flg=1\r\n"
            + "and hdr.dsbmt_dt between ':startDate' and ':endDate'\r\n" + "and ap.port_seq in :ports \r\n"
            + "and prd.prd_grp_seq in :grps \r\n"
            + "group by ap.port_seq,ap.prd_seq,trgt.trgt_clients,trgt.trgt_amt,par.par_amt,par.od_clnts,rcv.rcvry_amt,tot_amt_due,tot_clnt_due,tb_cmpltd_clnts,port_clnt_cnt,wmn_part,ip_clnt,ip_amt)";
    public static String krkAuthenticationQuery = "0";
    public static String krkRecommendedAmntQuery = "select  get_KRK_amt_eligibity(:cnic_num, :rcmnd_amt) from dual";
    // Added by Zohaib Asim - Dated 10-03-2022 - Get Sequence for Any Table
    public static String getTableSeq = "SELECT FN_GET_TBL_SEQ(:tblNm, :userId) FROM DUAL";
    public static String prevPSCScoreValue = "select nvl(GET_PSC_SCORE(:loanSeq),0) from dual";

    public static String clientsListing(String userId) {
        return "SELECT app.loan_app_seq, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1 ) AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_seq, clnt.cnic_num, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1 ) AS gender_key, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.mrtl_sts_key AND crnt_rec_flg = 1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm, app.loan_app_sts_dt, app.rcmnd_loan_amt, app.aprvd_loan_amt, prd.prd_nm, cmnty.cmnty_nm, ad.strt, ad.OTH_DTL, ad.vlg, prd.prd_seq, app.loan_id FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id = '"
                + userId
                + "' JOIN mw_clnt clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1 LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 LEFT OUTER JOIN mw_port port ON port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1 LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1 LEFT OUTER JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 WHERE app.crnt_rec_flg = 1 ORDER BY app.loan_app_sts_dt DESC";
    }

    // Added by Areeba - Query Optimization - 12-12-2022
    public static String clientsListingForPagination(String userId) {
        return " SELECT *\n" +
                "    FROM (SELECT CL.FRST_NM,\n" +
                "                 CL.LAST_NM,\n" +
                "                 CL.CLNT_ID,\n" +
                "                 LA.LOAN_APP_ID,\n" +
                "                 CL.FRST_NM || ' ' || CL.LAST_NM\n" +
                "                     AS CLNT_NM,\n" +
                "                 CL.CLNT_SEQ,\n" +
                "                 LA.LOAN_APP_SEQ,\n" +
                "                 RCV.ref_cd_dscr,\n" +
                "                 LA.rcmnd_loan_amt,\n" +
                "                 LA.APRVD_LOAN_AMT,\n" +
                "                 PR.PRD_SEQ, PR.PRD_NM,\n" +
                "                 EM.EMP_NM\n" +
                "                     AS BDO_NM,\n" +
                "                 LA.LOAN_APP_STS_DT,\n" +
                "                 INST_NUM.MAX_INST_NUM, " +
                "                 TRF.CNT AS TRF_LA_SEQ, " +
                "                 fn_verisys_bm_approval (LA.loan_app_seq)\n" +
                "                     verisys,\n" +
                "                 get_Verisys_Status (LA.loan_app_seq, 'W')\n" +
                "                     verisys_desc,\n" +
                "                 asoc.cnt as\n" +
                "                     assoc_prd\n" +
                "            FROM MW_CLNT        CL,\n" +
                "                 MW_LOAN_APP    LA,\n" +
                "                 MW_PRD         PR,\n" +
                "                 MW_REF_CD_VAL  RCV,\n" +
                "                 MW_PORT_EMP_REL PER,\n" +
                "                 MW_EMP         EM,\n" +
                "                   (SELECT PSH.LOAN_APP_SEQ, COUNT(PSD.INST_NUM) AS MAX_INST_NUM  \n" +
                "                 FROM MW_PYMT_SCHED_HDR PSH,\n" +
                "                      MW_PYMT_SCHED_DTL PSD\n" +
                "                      WHERE PSH.PYMT_SCHED_HDR_SEQ = PSD.PYMT_SCHED_HDR_SEQ\n" +
                "                      AND PSH.CRNT_REC_FLG = 1\n" +
                "                      AND PSD.CRNT_REC_FLG = 1\n" +
                " AND PSD.PYMT_STS_KEY <> 945 " +
                "                      GROUP BY PSH.LOAN_APP_SEQ) INST_NUM,\n" +
                "                (SELECT PTD.LOAN_APP_SEQ, COUNT(1) AS CNT\n" +
                "                        FROM RPTB_PORT_TRF_DETAIL PTD\n" +
                "                        GROUP BY PTD.LOAN_APP_SEQ) TRF," +
                "                 (SELECT apr.prd_seq, COUNT (1) as cnt\n" +
                "                    FROM mw_asoc_prd_rel apr\n" +
                "                   WHERE apr.crnt_rec_flg = 1\n" +
                "                   group by apr.prd_seq) asoc\n" +
                "           WHERE     CL.CLNT_SEQ = LA.CLNT_SEQ\n" +
                "                 AND LA.PRD_SEQ = PR.PRD_SEQ\n" +
                "                 AND RCV.ref_cd_seq = LA.loan_app_sts\n" +
                "                 AND LA.PORT_SEQ = PER.PORT_SEQ\n" +
                "                 AND PER.EMP_SEQ = EM.EMP_SEQ\n" +
                " AND LA.LOAN_APP_SEQ = INST_NUM.LOAN_APP_SEQ(+) \n" +
                "                    AND LA.LOAN_APP_SEQ = TRF.LOAN_APP_SEQ(+)" +
                "                 AND LA.PRD_SEQ = ASOC.PRD_SEQ(+)\n" +
                "                 AND EXISTS\n" +
                "                         (SELECT 1\n" +
                "                            FROM MW_ACL AC\n" +
                "                           WHERE     AC.PORT_sEQ = LA.PORT_SEQ\n" +
                "                                 AND AC.user_id = '" + userId + "') ";
    }

    public static String clientsListingForPaginationP2(String userId, String statusList) {
        return " AND LA.LOAN_APP_STS IN (" + statusList + " ) " +
                " AND CL.CRNT_REC_FLG = 1\n" +
                "                 AND LA.CRNT_REC_FLG = 1\n" +
                "                 AND PR.CRNT_REC_FLG = 1\n" +
                "                 AND RCV.CRNT_REC_FLG = 1\n" +
                "                 AND PER.CRNT_REC_FLG = 1\n" +
                "          UNION ALL\n" +
                "          SELECT CL.FRST_NM,\n" +
                "                 CL.LAST_NM,\n" +
                "                 CL.CLNT_ID,\n" +
                "                 LA.LOAN_APP_ID,\n" +
                "                 CL.FRST_NM || ' ' || CL.LAST_NM\n" +
                "                     AS CLNT_NM,\n" +
                "                 CL.CLNT_SEQ,\n" +
                "                 LA.LOAN_APP_SEQ,\n" +
                "                 RCV.ref_cd_dscr,\n" +
                "                 LA.rcmnd_loan_amt,\n" +
                "                 LA.APRVD_LOAN_AMT,\n" +
                "                 PR.PRD_SEQ, PR.PRD_NM,\n" +
                "                 EM.EMP_NM\n" +
                "                     AS BDO_NM,\n" +
                "                 LA.LOAN_APP_STS_DT,\n" +
                "                 INST_NUM.MAX_INST_NUM, " +
                "                 TRF.CNT AS TRF_LA_SEQ, " +
                "                 fn_verisys_bm_approval (LA.loan_app_seq)\n" +
                "                     verisys,\n" +
                "                 get_Verisys_Status (LA.loan_app_seq, 'W')\n" +
                "                     verisys_desc,\n" +
                "                 asoc.cnt as\n" +
                "                     assoc_prd\n" +
                "            FROM MW_CLNT        CL,\n" +
                "                 MW_LOAN_APP    LA,\n" +
                "                 MW_PRD         PR,\n" +
                "                 MW_REF_CD_VAL  RCV,\n" +
                "                 MW_PORT_EMP_REL PER,\n" +
                "                 MW_EMP         EM,\n" +
                "       (SELECT PSH.LOAN_APP_SEQ, COUNT(PSD.INST_NUM) AS MAX_INST_NUM\n" +
                "                 FROM MW_PYMT_SCHED_HDR PSH,\n" +
                "                      MW_PYMT_SCHED_DTL PSD\n" +
                "                      WHERE PSH.PYMT_SCHED_HDR_SEQ = PSD.PYMT_SCHED_HDR_SEQ\n" +
                "                      AND PSH.CRNT_REC_FLG = 1\n" +
                "                      AND PSD.CRNT_REC_FLG = 1\n" +
                " AND PSD.PYMT_STS_KEY <> 945 " +
                "                      GROUP BY PSH.LOAN_APP_SEQ) INST_NUM,\n" +
                "                      (SELECT PTD.LOAN_APP_SEQ, COUNT(1) AS CNT\n" +
                "                        FROM RPTB_PORT_TRF_DETAIL PTD\n" +
                "                        GROUP BY PTD.LOAN_APP_SEQ) TRF," +
                "                 (SELECT apr.prd_seq, COUNT (1) as cnt\n" +
                "                    FROM mw_asoc_prd_rel apr\n" +
                "                   WHERE apr.crnt_rec_flg = 1\n" +
                "                   group by apr.prd_seq) asoc\n" +
                "           WHERE     CL.CLNT_SEQ = LA.CLNT_SEQ\n" +
                "                 AND LA.PRD_SEQ = PR.PRD_SEQ\n" +
                "                 AND RCV.ref_cd_seq = LA.loan_app_sts\n" +
                "                 AND LA.PORT_SEQ = PER.PORT_SEQ\n" +
                "                 AND PER.EMP_SEQ = EM.EMP_SEQ\n" +
                "   AND LA.LOAN_APP_SEQ = INST_NUM.LOAN_APP_SEQ(+)\n" +
                "           AND LA.LOAN_APP_SEQ = TRF.LOAN_APP_SEQ(+) " +
                "                 AND LA.PRD_SEQ = ASOC.PRD_SEQ(+)\n" +
                "                 AND EXISTS\n" +
                "                         (SELECT 1\n" +
                "                            FROM MW_ACL AC\n" +
                "                           WHERE     AC.PORT_sEQ = LA.PORT_SEQ\n" +
                "                                 AND AC.user_id = '" + userId + "') ";
    }

    public static String clientsListingForPaginationP3() {
        return "                 AND LA.LOAN_APP_STS = 704\n" +
                "                 AND TRUNC (LA.LOAN_APP_STS_DT) >= TRUNC (SYSDATE - 360)\n" +
                "                 AND CL.CRNT_REC_FLG = 1\n" +
                "                 AND LA.CRNT_REC_FLG = 1\n" +
                "                 AND PR.CRNT_REC_FLG = 1\n" +
                "                 AND RCV.CRNT_REC_FLG = 1\n" +
                "                 AND PER.CRNT_REC_FLG = 1) ";
    }

    public static String countClientsListingForPagination(String userId) {
        return "SELECT count(app.loan_app_seq) FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id = '"
                + userId
                + "' JOIN mw_clnt clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1 LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 LEFT OUTER JOIN mw_port port ON port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1 LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1 LEFT OUTER JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 WHERE app.crnt_rec_flg = 1 ";
    }

    public static String clientsListingCount(String userId) {
        return "SELECT count( app.loan_id ) FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq = app.port_seq AND acl.user_id = '"
                + userId
                + "' JOIN mw_clnt clnt ON app.clnt_seq = clnt.clnt_seq AND clnt.crnt_rec_flg = 1 LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1 LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1 LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 LEFT OUTER JOIN mw_port port ON port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1 LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1 LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1 LEFT OUTER JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 WHERE app.crnt_rec_flg = 1 ORDER BY app.loan_app_sts_dt DESC";
    }

    public static String clientsListingForFilter(String userId, String filterString) {
        return "SELECT app.loan_id, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=app.loan_app_sts AND crnt_rec_flg=1 ) AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=clnt.gndr_key AND crnt_rec_flg=1 ) AS gender_key, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=clnt.mrtl_sts_key AND crnt_rec_flg=1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq =app.port_seq AND acl.user_id = '"
                + userId
                + "' JOIN mw_clnt clnt ON app.clnt_seq =clnt.clnt_seq AND clnt.crnt_rec_flg=1 JOIN mw_addr_rel ar ON ar.enty_Key =clnt.clnt_SEQ AND ar.crnt_rec_flg=1 AND ar.enty_typ ='Client' JOIN mw_addr ad ON ad.addr_seq =ar.addr_seq AND ad.crnt_rec_flg=1 JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq=ad.city_seq Join mw_city cit on cit.city_seq=rel.city_seq AND cit.crnt_rec_flg=1 JOIN mw_uc uc ON uc.UC_SEQ =rel.uc_seq AND uc.crnt_rec_flg=1 JOIN mw_thsl thsl ON thsl.thsl_seq =uc.thsl_seq AND thsl.crnt_rec_flg=1 JOIN mw_dist dist ON dist.dist_seq =thsl.dist_seq AND dist.crnt_rec_flg=1 JOIN mw_st st ON st.st_seq =dist.st_seq AND st.crnt_rec_flg=1 JOIN mw_cntry cntry ON cntry.cntry_seq =st.st_seq AND cntry.crnt_rec_flg=1 JOIN mw_port port ON port.port_seq =app.port_seq AND port.crnt_rec_flg=1 JOIN mw_brnch brnch ON brnch.brnch_seq =port.brnch_seq AND brnch.crnt_rec_flg=1 JOIN mw_area area ON area.area_seq =brnch.area_seq AND area.crnt_rec_flg=1 JOIN mw_reg reg ON reg.reg_seq =area.reg_seq AND reg.crnt_rec_flg =1 "
                + filterString + " WHERE app.crnt_rec_flg=1 ORDER BY app.LOAN_APP_SEQ DESC";
    }

    public static String clientHistoryJoinedOrg(String cnic) {
        return "SELECT app.LOAN_APP_SEQ, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=app.loan_app_sts AND crnt_rec_flg=1 ) AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, clnt.FTHR_FRST_NM, clnt.FTHR_LAST_NM, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=clnt.gndr_key AND crnt_rec_flg=1 ) AS gender_key, (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq=clnt.mrtl_sts_key AND crnt_rec_flg=1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm , prd.prd_seq, prd.prd_nm, prd.mlt_loan_flg , app.aprvd_loan_amt , app.rcmnd_loan_amt, app.rqstd_loan_amt, clnt.clnt_seq, clnt.clnt_sts_key, clnt.cnic_expry_dt, clnt.co_bwr_san_flg, clnt.crnt_addr_perm_flg, clnt.dis_flg, clnt.dob, clnt.edu_lvl_key, clnt.erng_memb, clnt.gndr_key, clnt.hse_hld_memb, clnt.mnths_res, clnt.mrtl_sts_key, clnt.natr_of_dis_key, clnt.nick_nm, clnt.nom_dtl_available_flg, clnt.num_of_chldrn, clnt.num_of_dpnd, clnt.num_of_erng_memb, clnt.occ_key, clnt.ph_num, clnt.port_key, clnt.res_typ_key, clnt.slf_pdc_flg, clnt.spz_frst_nm, clnt.spz_last_nm, clnt.tot_incm_of_erng_memb, clnt.yrs_res, ar.addr_rel_seq, ad.addr_seq, ad.cmnty_seq, ad.oth_dtl, ad.strt, ad.vlg, REL.city_uc_rel_seq, REL.city_seq, uc.uc_seq, thsl.thsl_seq, dist.dist_seq, st.st_seq, cntry.cntry_seq, APP.loan_cycl_num,  clnt.mthr_madn_nm FROM mw_loan_app app JOIN mw_clnt clnt ON app.clnt_seq =clnt.clnt_seq AND clnt.crnt_rec_flg=1 AND clnt.cnic_num ="
                + cnic
                + " LEFT OUTER JOIN mw_prd prd ON app.prd_seq=prd.prd_seq AND PRD.crnt_rec_flg=1 JOIN mw_addr_rel ar ON ar.enty_Key =clnt.clnt_SEQ AND ar.crnt_rec_flg=1 AND ar.enty_typ ='Client' JOIN mw_addr ad ON ad.addr_seq =ar.addr_seq AND ad.crnt_rec_flg=1 JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq=ad.city_seq JOIN mw_city cit ON cit.city_seq =rel.city_seq AND cit.crnt_rec_flg=1 JOIN mw_uc uc ON uc.UC_SEQ =rel.uc_seq AND uc.crnt_rec_flg=1 JOIN mw_thsl thsl ON thsl.thsl_seq =uc.thsl_seq AND thsl.crnt_rec_flg=1 JOIN mw_dist dist ON dist.dist_seq =thsl.dist_seq AND dist.crnt_rec_flg=1 JOIN mw_st st ON st.st_seq =dist.st_seq AND st.crnt_rec_flg=1 JOIN mw_cntry cntry ON cntry.cntry_seq =st.st_seq AND cntry.crnt_rec_flg=1 JOIN mw_port port ON port.port_seq =app.port_seq AND port.crnt_rec_flg=1 JOIN mw_brnch brnch ON brnch.brnch_seq =port.brnch_seq AND brnch.crnt_rec_flg=1 JOIN mw_area area ON area.area_seq =brnch.area_seq AND area.crnt_rec_flg=1 JOIN mw_reg reg ON reg.reg_seq =area.reg_seq AND reg.crnt_rec_flg =1 WHERE app.crnt_rec_flg=1 order by app.LOAN_APP_STS_DT desc";
    }

    /*
     * @modifier, Muhammad Bassam
     * @date, 19-Apr-2021
     * @description, multiple loan records(inactive rows) fixed
     * Modified by Yousaf Ali - May - 2022
     * */
    public static String clientHistory() {
        return "                                            SELECT app.loan_app_seq,\n" +
                "                                                         (SELECT ref_cd_dscr\n" +
                "                                                            FROM mw_ref_cd_val\n" +
                "                                                           WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1)\n" +
                "                                                             AS loan_app_sts_str,\n" +
                "                                                         clnt.frst_nm,\n" +
                "                                                         clnt.last_nm,\n" +
                "                                                         clnt.clnt_id,\n" +
                "                                                         clnt.cnic_num,\n" +
                "                                                         clnt.fthr_frst_nm,\n" +
                "                                                         clnt.fthr_last_nm,\n" +
                "                                                         (SELECT ref_cd_dscr\n" +
                "                                                            FROM mw_ref_cd_val\n" +
                "                                                           WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1)\n" +
                "                                                             AS gender_key,\n" +
                "                                                         (SELECT ref_cd_dscr\n" +
                "                                                            FROM mw_ref_cd_val\n" +
                "                                                           WHERE ref_cd_seq = clnt.mrtl_sts_key AND crnt_rec_flg = 1)\n" +
                "                                                             AS marital_sts,\n" +
                "                                                         ad.hse_num,\n" +
                "                                                         cit.city_nm,\n" +
                "                                                         uc.uc_nm,\n" +
                "                                                         thsl.thsl_nm,\n" +
                "                                                         dist.dist_nm,\n" +
                "                                                         st.st_nm,\n" +
                "                                                         cntry.cntry_nm,\n" +
                "                                                         (SELECT port_nm\n" +
                "                                                            FROM mw_port port\n" +
                "                                                           WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg = 1)\n" +
                "                                                             AS port_nm,\n" +
                "                                                         (SELECT brnch_nm\n" +
                "                                                            FROM mw_brnch brnch\n" +
                "                                                           WHERE     brnch.brnch_seq =\n" +
                "                                                                     (SELECT port.brnch_seq\n" +
                "                                                                        FROM mw_port port\n" +
                "                                                                       WHERE     port.port_seq = app.port_seq\n" +
                "                                                                             AND port.crnt_rec_flg = 1)\n" +
                "                                                                 AND brnch.crnt_rec_flg = 1)\n" +
                "                                                             AS brnch_nm,\n" +
                "                                                         (SELECT area_nm\n" +
                "                                                            FROM mw_area area\n" +
                "                                                           WHERE     area.area_seq =\n" +
                "                                                                     (SELECT brnch.area_seq\n" +
                "                                                                        FROM mw_brnch brnch\n" +
                "                                                                       WHERE     brnch.brnch_seq =\n" +
                "                                                                                 (SELECT port.brnch_seq\n" +
                "                                                                                    FROM mw_port port\n" +
                "                                                                                   WHERE     port.port_seq = app.port_seq\n" +
                "                                                                                         AND port.crnt_rec_flg = 1)\n" +
                "                                                                             AND brnch.crnt_rec_flg = 1)\n" +
                "                                                                 AND area.crnt_rec_flg = 1)\n" +
                "                                                             AS area_nm,\n" +
                "                                                         (SELECT reg.reg_nm\n" +
                "                                                            FROM mw_reg reg\n" +
                "                                                           WHERE     reg.reg_seq =\n" +
                "                                                                     (SELECT area.reg_seq\n" +
                "                                                                        FROM mw_area area\n" +
                "                                                                       WHERE     area.area_seq =\n" +
                "                                                                                 (SELECT brnch.area_seq\n" +
                "                                                                                    FROM mw_brnch brnch\n" +
                "                                                                                   WHERE     brnch.brnch_seq =\n" +
                "                                                                                             (SELECT port.brnch_seq\n" +
                "                                                                                                FROM mw_port port\n" +
                "                                                                                               WHERE     port.port_seq =\n" +
                "                                                                                                         app.port_seq\n" +
                "                                                                                                     AND port.crnt_rec_flg = 1)\n" +
                "                                                                                         AND brnch.crnt_rec_flg = 1)\n" +
                "                                                                             AND area.crnt_rec_flg = 1)\n" +
                "                                                                 AND reg.crnt_rec_flg = 1)\n" +
                "                                                             AS reg_nm,\n" +
                "                                                         prd.prd_seq,\n" +
                "                                                         prd.prd_nm,\n" +
                "                                                         prd.mlt_loan_flg,\n" +
                "                                                         app.aprvd_loan_amt,\n" +
                "                                                         app.rcmnd_loan_amt,\n" +
                "                                                         app.rqstd_loan_amt,\n" +
                "                                                         clnt.clnt_seq,\n" +
                "                                                         app.loan_app_sts,\n" +
                "                                                         clnt.cnic_expry_dt,\n" +
                "                                                         clnt.co_bwr_san_flg,\n" +
                "                                                         clnt.crnt_addr_perm_flg,\n" +
                "                                                         clnt.dis_flg,\n" +
                "                                                         clnt.dob,\n" +
                "                                                         clnt.edu_lvl_key,\n" +
                "                                                         clnt.erng_memb,\n" +
                "                                                         clnt.gndr_key,\n" +
                "                                                         clnt.hse_hld_memb,\n" +
                "                                                         clnt.mnths_res,\n" +
                "                                                         clnt.mrtl_sts_key,\n" +
                "                                                         clnt.natr_of_dis_key,\n" +
                "                                                         clnt.nick_nm,\n" +
                "                                                         clnt.nom_dtl_available_flg,\n" +
                "                                                         clnt.num_of_chldrn,\n" +
                "                                                         clnt.num_of_dpnd,\n" +
                "                                                         clnt.num_of_erng_memb,\n" +
                "                                                         clnt.occ_key,\n" +
                "                                                         clnt.ph_num,\n" +
                "                                                         clnt.port_key,\n" +
                "                                                         clnt.res_typ_key,\n" +
                "                                                         clnt.slf_pdc_flg,\n" +
                "                                                         clnt.spz_frst_nm,\n" +
                "                                                         clnt.spz_last_nm,\n" +
                "                                                         clnt.tot_incm_of_erng_memb,\n" +
                "                                                         clnt.yrs_res,\n" +
                "                                                         ar.addr_rel_seq,\n" +
                "                                                         ad.addr_seq,\n" +
                "                                                         ad.cmnty_seq,\n" +
                "                                                         ad.oth_dtl,\n" +
                "                                                         ad.strt,\n" +
                "                                                         ad.vlg,\n" +
                "                                                         rel.city_uc_rel_seq,\n" +
                "                                                         rel.city_seq,\n" +
                "                                                         uc.uc_seq,\n" +
                "                                                         thsl.thsl_seq,\n" +
                "                                                         dist.dist_seq,\n" +
                "                                                         st.st_seq,\n" +
                "                                                         cntry.cntry_seq,\n" +
                "                                                         app.loan_cycl_num,\n" +
                "                                                         clnt.mthr_madn_nm,\n" +
                "                                                         'N/A'\n" +
                "                                                             AS bm,\n" +
                "                                                         (SELECT emp.emp_nm\n" +
                "                                                            FROM mw_emp emp\n" +
                "                                                                 JOIN mw_port_emp_rel rel\n" +
                "                                                                     ON rel.port_seq = app.port_seq AND rel.crnt_rec_flg = 1\n" +
                "                                                           WHERE emp.emp_seq = rel.emp_seq)\n" +
                "                                                             AS bdo,\n" +
                "                                                         (SELECT port.brnch_seq\n" +
                "                                                            FROM mw_port port\n" +
                "                                                           WHERE port_seq = app.port_seq AND crnt_rec_flg = 1)\n" +
                "                                                             AS brnchseq,\n" +
                "                                                         app.co_bwr_addr_as_clnt_flg,\n" +
                "                                                         app.crtd_dt\n" +
                "                                                             lst_app_dt,\n" +
                "                                                         dsbmt_dt,\n" +
                "                                                         (SELECT SUM (pymt_amt)\n" +
                "                                                            FROM mw_pymt_sched_hdr psh\n" +
                "                                                                 JOIN mw_pymt_sched_dtl psd\n" +
                "                                                                     ON     psd.pymt_sched_hdr_seq = psh.pymt_sched_hdr_seq\n" +
                "                                                                        AND psd.crnt_rec_flg = 1\n" +
                "                                                                 JOIN mw_rcvry_dtl rdl\n" +
                "                                                                     ON     rdl.pymt_sched_dtl_seq = psd.pymt_sched_dtl_seq\n" +
                "                                                                        AND rdl.crnt_rec_flg = 1\n" +
                "                                                           WHERE psh.crnt_rec_flg = 1 AND psh.loan_app_seq = app.loan_app_seq)\n" +
                "                                                             paid_amt,\n" +
                "                                                         (SELECT ref_cd_dscr\n" +
                "                                                            FROM mw_ref_cd_val\n" +
                "                                                           WHERE crnt_rec_flg = 1 AND ref_cd_seq = app.loan_utl_sts_seq)\n" +
                "                                                             utl,\n" +
                "                                                         app.loan_utl_cmnt,\n" +
                "                                                         get_pd_od_inst (app.loan_app_seq)\n" +
                "                                                             od_inst,\n" +
                "                                                         LOAN_APP_OST(app.loan_app_seq, SYSDATE, 'psc') ost,\n" +
                "                                                         (SELECT COUNT (1)\n" +
                "                                                              FROM MW_PYMT_SCHED_HDR  psh\n" +
                "                                                                   JOIN MW_PYMT_SCHED_DTL psd\n" +
                "                                                                       ON     psd.PYMT_SCHED_HDR_SEQ = psh.PYMT_SCHED_HDR_SEQ\n" +
                "                                                                          AND psd.CRNT_REC_FLG = 1\n" +
                "                                                             WHERE     psh.CRNT_REC_FLG = 1\n" +
                "                                                                   AND psd.PYMT_STS_KEY IN (945, 1145)\n" +
                "                                                                   AND psh.LOAN_APP_SEQ = app.loan_app_seq) inst_count,\n" +
                "                                                         grp.PRD_GRP_NM,\n" +
                "                                                         (SELECT max(app1.APRVD_LOAN_AMT)\n" +
                "                                                          FROM mw_loan_app  app1      \n" +
                "                                                         WHERE     app1.crnt_rec_flg = 1\n" +
                "                                                               AND app1.loan_cycl_num =\n" +
                "                                                                   NVL (app.loan_cycl_num,app.loan_cycl_num - 1)\n" +
                "                                                               AND app1.prd_seq NOT IN (29)\n" +
                "                                                               AND app1.clnt_seq = app.clnt_seq) previousAmount,\n" +
                "                                                         clnt.MEMBRSHP_DT MEMBRSHP_DT,\n" +
                "                                                         clnt.REF_CD_LEAD_TYP_SEQ REF_CD_LEAD_TYP_SEQ, GET_PSC_SCORE(app.loan_app_Seq) LOAN_APP_PVRTY_SCR, ad.LATITUDE , ad.LONGITUDE, app.APRVD_LOAN_AMT - LOAN_APP_OST(app.loan_app_seq, SYSDATE, 'p') ost_KTK\n" +
                "                                                    FROM mw_clnt clnt\n" +
                "                                                         LEFT OUTER JOIN mw_loan_app app\n" +
                "                                                             ON     app.clnt_seq = clnt.clnt_seq\n" +
                "                                                                AND app.crnt_rec_flg = 1\n" +
                "                                                                AND app.loan_app_sts NOT IN\n" +
                "                                                                        (SELECT ref_cd_seq\n" +
                "                                                                           FROM mw_ref_cd_val\n" +
                "                                                                          WHERE ref_cd IN ('0007', '0008', '1285'))\n" +
                "                                                                AND app.loan_app_sts IN (704, 703)\n" +
                "                                                                AND app.loan_cycl_num != -1\n" +
                "                                                                AND app.prd_seq NOT IN (2,\n" +
                "                                                                                        3,\n" +
                "                                                                                        5,\n" +
                "                                                                                        13,\n" +
                "                                                                                        14,\n" +
                "                                                                                        29)\n" +
                "                                                         LEFT OUTER JOIN mw_dsbmt_vchr_hdr hdr\n" +
                "                                                             ON hdr.loan_app_seq = app.loan_app_seq AND hdr.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_prd prd\n" +
                "                                                             ON     app.prd_seq = prd.prd_seq\n" +
                "                                                                AND prd.prd_typ_key != '1165'\n" +
                "                                                                AND prd.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_prd_grp grp\n" +
                "                                                            ON prd.PRD_GRP_SEQ = grp.PRD_GRP_SEQ\n" +
                "                                                                and grp.CRNT_REC_FLG = 1\n" +
                "                                                         LEFT OUTER JOIN mw_addr_rel ar\n" +
                "                                                             ON     ar.enty_key = clnt.clnt_seq\n" +
                "                                                                AND ar.crnt_rec_flg = 1\n" +
                "                                                                AND ar.enty_typ = 'Client'\n" +
                "                                                         LEFT OUTER JOIN mw_addr ad\n" +
                "                                                             ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_city_uc_rel rel\n" +
                "                                                             ON rel.city_uc_rel_seq = ad.city_seq\n" +
                "                                                         LEFT OUTER JOIN mw_city cit\n" +
                "                                                             ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_uc uc\n" +
                "                                                             ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_thsl thsl\n" +
                "                                                             ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_dist dist\n" +
                "                                                             ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_st st\n" +
                "                                                             ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1\n" +
                "                                                         LEFT OUTER JOIN mw_cntry cntry\n" +
                "                                                             ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1\n" +
                "                                                   WHERE     clnt.crnt_rec_flg = 1\n" +
                "                                                         AND clnt.cnic_num = :cnic\n" +
                "                                                         AND clnt.frst_nm != 'dumy'\n" +
                "                                                ORDER BY app.loan_cycl_num DESC\n" +
                "                                                FETCH FIRST 1 ROWS ONLY";
    }

    public static String getClientRelHistory(String cnic) {
        return "select app.LOAN_APP_SEQ, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=app.loan_app_sts and crnt_rec_flg=1) \r\n"
                + "                	as loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.cnic_num, clnt.FTHR_FRST_NM, clnt.FTHR_LAST_NM, \r\n"
                + "                	(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.gndr_key and crnt_rec_flg=1) as gender_key, \r\n"
                + "                	(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.mrtl_sts_key and crnt_rec_flg=1) as marital_sts, \r\n"
                + "                  prd.prd_seq, prd.prd_nm, prd.MLT_LOAN_FLG, clnt.rel_wth_clnt_key , clnt.REL_TYP_FLG, ( SELECT Distinct brnch_nm FROM mw_brnch brnch WHERE brnch.brnch_seq = ( SELECT Distinct port.brnch_seq FROM mw_port port WHERE port.port_seq = app.port_seq AND port.crnt_rec_flg = 1 ) AND brnch.crnt_rec_flg = 1 ) AS brnch_nm, 'N/A' AS bm, 'N/A' AS bdo \r\n"
                + "                  , (select distinct 'active' from mw_clnt_rel rel join mw_loan_app app on app.loan_app_seq=rel.loan_app_seq and app.crnt_rec_flg=1 and app.loan_app_sts=703 where rel.crnt_rec_flg=1 and rel.rel_typ_flg not in (2,4) and rel.cnic_num=" + cnic + ") \r\n"
                + "                	from mw_loan_app app \r\n"
                + "                	join mw_clnt_rel clnt on app.LOAN_APP_SEQ=clnt.LOAN_APP_SEQ and clnt.crnt_rec_flg=1 and clnt.cnic_num=" + cnic + "\r\n"
                + "                	join mw_prd prd on app.prd_seq=prd.prd_seq JOIN mw_ref_cd_val val on val.ref_cd_seq = app.loan_app_sts  AND val.crnt_rec_flg = 1 AND val.ref_cd!='0008' \r\n"
                + "                	where app.crnt_rec_flg=1 order by app.last_upd_dt desc";
    }

    public static String getNomineeHistory(String cnic) {
        return "select app.loan_id, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=app.loan_app_sts and crnt_rec_flg=1) "
                + "as loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, clnt.FTHR_FRST_NM, clnt.FTHR_LAST_NM, "
                + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.gndr_key and crnt_rec_flg=1) as gender_key, "
                + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.mrtl_sts_key and crnt_rec_flg=1) as marital_sts,  "
                + "ad.hse_num, cit.city_nm,uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, "
                + "port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm , prd.prd_seq, prd.prd_nm, prd.MLT_LOAN_FLG, nom.NOM_SEQ, nom.FRST_NM as Nom_FNM, nom.LAST_NM as NOM_LNM, nom.PH_NUM, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=nom.REL_WTH_CLNT_KEY)  "
                + "from mw_loan_app app " + "join mw_nom nom on app.loan_app_seq=nom.loan_app_seq and nom.crnt_rec_flg=1 and nom.cnic_num="
                + cnic + " " + "join mw_clnt clnt on clnt.CLNT_SEQ=app.clnt_seq " + "join mw_prd prd on app.prd_seq=prd.prd_seq "
                + "join mw_addr_rel ar on ar.enty_Key=clnt.clnt_SEQ and ar.crnt_rec_flg=1 and ar.enty_typ='Client' "
                + "join mw_addr ad on ad.addr_seq=ar.addr_seq and ad.crnt_rec_flg=1 "
                + "join mw_city cit on cit.city_seq=ad.city_seq and cit.crnt_rec_flg=1 "
                + "join mw_uc uc on uc.UC_SEQ=cit.uc_seq and uc.crnt_rec_flg=1 "
                + "join mw_thsl thsl on thsl.thsl_seq=uc.thsl_seq and thsl.crnt_rec_flg=1 "
                + "join mw_dist dist on dist.dist_seq=thsl.dist_seq and dist.crnt_rec_flg=1 "
                + "join mw_st st on st.st_seq=dist.st_seq and st.crnt_rec_flg=1 "
                + "join mw_cntry cntry on cntry.cntry_seq=st.st_seq and cntry.crnt_rec_flg=1 "
                + "join mw_port port on port.port_seq=app.port_seq and port.crnt_rec_flg=1 "
                + "join mw_brnch brnch on brnch.brnch_seq=port.brnch_seq and brnch.crnt_rec_flg=1 "
                + "join mw_area area on area.area_seq=brnch.area_seq and area.crnt_rec_flg=1 "
                + "join mw_reg reg on reg.reg_seq=area.reg_seq and reg.crnt_rec_flg=1 " + "where app.crnt_rec_flg=1";
    }
    // End

    public static String getCobHistory(String cnic) {
        return "select app.loan_id, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=app.loan_app_sts and crnt_rec_flg=1) "
                + "as loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, clnt.FTHR_FRST_NM, clnt.FTHR_LAST_NM, "
                + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.gndr_key and crnt_rec_flg=1) as gender_key, "
                + "(select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=clnt.mrtl_sts_key and crnt_rec_flg=1) as marital_sts,  "
                + "ad.hse_num, cit.city_nm,uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, "
                + "port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm , prd.prd_seq, prd.prd_nm, prd.MLT_LOAN_FLG, nom.CBWR_SEQ, nom.FRST_NM as C_FNM, nom.LAST_NM as c_lNM, nom.PH_NUM, (select ref_cd_dscr from mw_ref_cd_val where ref_cd_seq=nom.REL_WTH_CLNT_KEY)  "
                + "from mw_loan_app app " + "join mw_cbwr nom on app.loan_app_seq=nom.loan_app_seq and nom.crnt_rec_flg=1 and nom.cnic_num="
                + cnic + " " + "join mw_clnt clnt on clnt.CLNT_SEQ=app.clnt_seq " + "join mw_prd prd on app.prd_seq=prd.prd_seq "
                + "join mw_addr_rel ar on ar.enty_Key=clnt.clnt_SEQ and ar.crnt_rec_flg=1 and ar.enty_typ='Client' "
                + "join mw_addr ad on ad.addr_seq=ar.addr_seq and ad.crnt_rec_flg=1 "
                + "join mw_city cit on cit.city_seq=ad.city_seq and cit.crnt_rec_flg=1 "
                + "join mw_uc uc on uc.UC_SEQ=cit.uc_seq and uc.crnt_rec_flg=1 "
                + "join mw_thsl thsl on thsl.thsl_seq=uc.thsl_seq and thsl.crnt_rec_flg=1 "
                + "join mw_dist dist on dist.dist_seq=thsl.dist_seq and dist.crnt_rec_flg=1 "
                + "join mw_st st on st.st_seq=dist.st_seq and st.crnt_rec_flg=1 "
                + "join mw_cntry cntry on cntry.cntry_seq=st.st_seq and cntry.crnt_rec_flg=1 "
                + "join mw_port port on port.port_seq=app.port_seq and port.crnt_rec_flg=1 "
                + "join mw_brnch brnch on brnch.brnch_seq=port.brnch_seq and brnch.crnt_rec_flg=1 "
                + "join mw_area area on area.area_seq=brnch.area_seq and area.crnt_rec_flg=1 "
                + "join mw_reg reg on reg.reg_seq=area.reg_seq and reg.crnt_rec_flg=1 " + "where app.crnt_rec_flg=1";
    }
}
