package com.idev4.loans.service;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idev4.loans.domain.*;
import com.idev4.loans.dto.krkRecAmntDto;
import com.idev4.loans.dto.tab.*;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.*;
import org.apache.commons.lang.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.servlet.ServletContext;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MwTabService {

    private static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private static DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    private final Logger log = LoggerFactory.getLogger(MwTabService.class);
    private final MwClntRepository mwClntRepository;
    private final MwAddrRepository mwAddrRepository;
    private final MwAddrRelRepository mwAddrRelRepository;
    private final MwLoanAppRepository mwLoanAppRepository;
    private final MwMfcibOthOutsdLoanRepository mwMfcibOthOutsdLoanRepository;
    private final MwClntRelRepository mwClntRelRepository;
    private final MwBizAprslRepository mwBizAprslRepository;
    private final MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository;
    private final MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository;
    private final MwBizExpDtlRepository mwBizExpDtlRepository;
    private final MwLoanUtlPlanRepository mwLoanUtlPlanRepository;
    private final MwClntPscRepository mwClntPscRepository;
    private final MwPrdRepository mwPrdRepository;
    private final MwRulRepository mwRulRepository;
    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;
    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;
    private final MwPrdChrgRepository mwPrdChrgRepository;
    private final MwRefCdValRepository mwRefCdValRepository;
    private final MwPrdSgrtInstRepository mwPrdSgrtInstRepository;
    private final MwLoanAppPpalStngsRepository mwLoanAppPpalStngsRepository;
    private final MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository;
    private final EntityManager em;
    private final MwLoanAppDocRepository mwLoanAppDocRepository;
    private final MwClntHlthInsrRepository mwClntHlthInsrRepository;
    private final MwHlthInsrMembRepository mwHlthInsrMembRepository;
    private final MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository;
    private final MwPrdFormRelRepository mwPrdFormRelRepository;
    private final MwSchAprslRepository mwSchAprslRepository;
    private final MwSchAtndRepository mwSchAtndRepository;
    private final MwSchAstsRepository mwSchAstsRepository;
    private final MwSchGrdRepository mwSchGrdRepository;
    private final MwSchQltyChkRepository mwSchQltyChkRepository;
    private final MwDvcRgstryRepository mwDvcRgstryRepository;
    private final MwPortRepository mwPortRepository;
    private final MwClntPermAddrRepository mwClntPermAddrRepository;
    private final MwClntCnicTknRepository mwClntCnicTknRepository;
    private final MwEmpRepository mwEmpRepository;
    private final MwBrnchEmpRelRepository mwBrnchEmpRelRepository;
    private final MwPortEmpRelRepository mwPortEmpRelRepository;
    private final MwAnmlRgstrRepository mwAnmlRgstrRepository;
    private final MwBizAprslEstLvstkFinRepository mwBizAprslEstLvstkFinRepository;
    private final MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository;
    private final MwLoanAppMntrngChksRepository mwLoanAppMntrngChksRepository;
    private final MwBrnchRepository mwBrnchRepository;
    private final MwLoanAppCrdtScrRepository mwLoanAppCrdtScrRepository;
    private final MwMntrngChksAdcRepository mwMntrngChksAdcRepository;
    private final MwMntrngChksAdcQstnrRepository mwMntrngChksAdcQstnrRepository;
    private final MwAmlMtchdClntRepository mwAmlMtchdClntRepository;
    private final MwSancListRepository mwSancListRepository;
    private final MwClntTagListRepository mwClntTagListRepository;
    @Autowired
    ServletContext context;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MwCnicUpdRepository mwCnicUpdRepository;
    @Autowired
    MwLoanAppVerisysRepository mwLoanAppVerisysRepository;
    @Autowired
    MwHmAprslRepository mwHmAprslRepository;
    @Autowired
    MwLoanAppCrdtSumryRepository mwLoanAppCrdtSumryRepository;
    @Autowired
    MwLoanAppMfcibDataRepository mwLoanAppMfcibDataRepository;
    // Zohaib Asim - Dated 12/11/2021 - Tasdeeq Token Issue
    @Autowired
    MwMfcibCredRepository mfcibCredRepository;
    Long discardedSts = 0L;

    /*
     * public SyncDto getDataForTab(Long portSeq) { final SyncDto dto = new
     * SyncDto();
     *
     * List<MwClnt> clnts = mwClntRepository.findAllByPortKey(portSeq,
     * true);
     *
     * if(clnts.size()>0) { dto.mw_clnt = new ArrayList<>(); dto.mw_addr_rel = new
     * ArrayList<>(); dto.mw_addr = new ArrayList<>(); clnts.forEach(clnt->{
     * if(clnt.getSyncFlg()) { ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
     * clntDto.DomainToDto(clnt); dto.mw_clnt.add(clntDto); }
     *
     * // clnt.setSyncFlg(false); // mwClntRepository.save(clnt);
     *
     * MwAddrRel addrRel =
     * mwAddrRelRepository.findOneByEntySeq(clnt.getClntSeq(), true);
     *
     * if(addrRel != null) { if(addrRel.getSyncFlg()) { AddrRelDto addressRelDto =
     * new AddrRelDto(); addressRelDto.DomainToDto(addrRel);
     * dto.mw_addr_rel.add(addressRelDto); }
     *
     * MwAddr addr =
     * mwAddrRepository.findOneByAddrSeqAndSyncFlg(addrRel.getAddrSeq()
     * , true, true); if(addr != null) { AddressDto addressDto = new AddressDto();
     * addressDto.DomainToDto(addr); dto.mw_addr.add(addressDto); } }
     *
     *
     * // MwLoanApp loanApp =
     * mwLoanAppRepository.findOneByClntSeqAndSyncFlg(clientSeq, flag)
     * }); } return dto; }
     */
    Long defferedSts = 0L;
    Long rejectedSts = 0L;
    Long complSts = 704L;

    public MwTabService(MwClntRepository mwClntRepository, MwAddrRepository mwAddrRepository, MwAddrRelRepository mwAddrRelRepository,
                        MwLoanAppRepository mwLoanAppRepository, MwMfcibOthOutsdLoanRepository mwMfcibOthOutsdLoanRepository,
                        MwClntRelRepository mwClntRelRepository, MwBizAprslRepository mwBizAprslRepository,
                        MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository, MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository,
                        MwBizExpDtlRepository mwBizExpDtlRepository, MwLoanUtlPlanRepository mwLoanUtlPlanRepository,
                        MwClntPscRepository mwClntPscRepository, MwPrdRepository mwPrdRepository, MwRulRepository mwRulRepository,
                        MwPrdLoanTrmRepository mwPrdLoanTrmRepository, MwPrdPpalLmtRepository mwPrdPpalLmtRepository,
                        MwPrdChrgRepository mwPrdChrgRepository, MwRefCdValRepository mwRefCdValRepository,
                        MwPrdSgrtInstRepository mwPrdSgrtInstRepository, MwLoanAppPpalStngsRepository mwLoanAppPpalStngsRepository,
                        MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository, EntityManager em, MwLoanAppDocRepository mwLoanAppDocRepository,
                        MwClntHlthInsrRepository mwClntHlthInsrRepository, MwHlthInsrMembRepository mwHlthInsrMembRepository,
                        MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository, MwPrdFormRelRepository mwPrdFormRelRepository,
                        MwSchAprslRepository mwSchAprslRepository, MwSchAtndRepository mwSchAtndRepository, MwSchAstsRepository mwSchAstsRepository,
                        MwSchGrdRepository mwSchGrdRepository, MwSchQltyChkRepository mwSchQltyChkRepository,
                        MwDvcRgstryRepository mwDvcRgstryRepository, MwClntPermAddrRepository mwClntPermAddrRepository,
                        MwClntCnicTknRepository mwClntCnicTknRepository, MwPortRepository mwPortRepository, MwEmpRepository mwEmpRepository,
                        MwBrnchEmpRelRepository mwBrnchEmpRelRepository, MwPortEmpRelRepository mwPortEmpRelRepository,
                        MwAnmlRgstrRepository mwAnmlRgstrRepository, MwBizAprslEstLvstkFinRepository mwBizAprslEstLvstkFinRepository,
                        MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository, MwLoanAppMntrngChksRepository mwLoanAppMntrngChksRepository,
                        MwBrnchRepository mwBrnchRepository, MwLoanAppCrdtScrRepository mwLoanAppCrdtScrRepository,
                        MwMntrngChksAdcRepository mwMntrngChksAdcRepository, MwMntrngChksAdcQstnrRepository mwMntrngChksAdcQstnrRepository,
                        MwAmlMtchdClntRepository mwAmlMtchdClntRepository, MwSancListRepository mwSancListRepository,
                        MwClntTagListRepository mwClntTagListRepository, MwHmAprslRepository mwHmAprslRepository) {
        this.mwClntRepository = mwClntRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwMfcibOthOutsdLoanRepository = mwMfcibOthOutsdLoanRepository;
        this.mwClntRelRepository = mwClntRelRepository;
        this.mwBizAprslRepository = mwBizAprslRepository;
        this.mwBizAprslIncmHdrRepository = mwBizAprslIncmHdrRepository;
        this.mwBizAprslIncmDtlRepository = mwBizAprslIncmDtlRepository;
        this.mwBizExpDtlRepository = mwBizExpDtlRepository;
        this.mwLoanUtlPlanRepository = mwLoanUtlPlanRepository;
        this.mwClntPscRepository = mwClntPscRepository;
        this.mwPrdRepository = mwPrdRepository;
        this.em = em;
        this.mwRulRepository = mwRulRepository;
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
        this.mwPrdChrgRepository = mwPrdChrgRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwPrdSgrtInstRepository = mwPrdSgrtInstRepository;
        this.mwLoanAppPpalStngsRepository = mwLoanAppPpalStngsRepository;
        this.mwLoanAppChrgStngsRepository = mwLoanAppChrgStngsRepository;
        this.mwLoanAppDocRepository = mwLoanAppDocRepository;
        this.mwClntHlthInsrRepository = mwClntHlthInsrRepository;
        this.mwHlthInsrMembRepository = mwHlthInsrMembRepository;
        this.mwLoanFormCmplFlgRepository = mwLoanFormCmplFlgRepository;
        this.mwPrdFormRelRepository = mwPrdFormRelRepository;
        this.mwSchAprslRepository = mwSchAprslRepository;
        this.mwSchAtndRepository = mwSchAtndRepository;
        this.mwSchAstsRepository = mwSchAstsRepository;
        this.mwSchGrdRepository = mwSchGrdRepository;
        this.mwSchQltyChkRepository = mwSchQltyChkRepository;
        this.mwDvcRgstryRepository = mwDvcRgstryRepository;
        this.mwClntPermAddrRepository = mwClntPermAddrRepository;
        this.mwClntCnicTknRepository = mwClntCnicTknRepository;
        this.mwPortRepository = mwPortRepository;
        this.mwEmpRepository = mwEmpRepository;
        this.mwBrnchEmpRelRepository = mwBrnchEmpRelRepository;
        this.mwPortEmpRelRepository = mwPortEmpRelRepository;
        this.mwAnmlRgstrRepository = mwAnmlRgstrRepository;
        this.mwBizAprslEstLvstkFinRepository = mwBizAprslEstLvstkFinRepository;
        this.mwBizAprslExtngLvstkRepository = mwBizAprslExtngLvstkRepository;
        this.mwLoanAppMntrngChksRepository = mwLoanAppMntrngChksRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwLoanAppCrdtScrRepository = mwLoanAppCrdtScrRepository;
        this.mwMntrngChksAdcRepository = mwMntrngChksAdcRepository;
        this.mwMntrngChksAdcQstnrRepository = mwMntrngChksAdcQstnrRepository;
        this.mwAmlMtchdClntRepository = mwAmlMtchdClntRepository;
        this.mwSancListRepository = mwSancListRepository;
        this.mwClntTagListRepository = mwClntTagListRepository;
        this.mwHmAprslRepository = mwHmAprslRepository;
    }

    public SyncDto markSyncFlag(SyncDto dto) {

        if (dto.mw_clnt != null) {
            dto.mw_clnt.forEach(item -> {
                if (item.clnt_seq != null) {
                    MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(item.clnt_seq, true);
                    if (clnt != null) {
                        clnt.setSyncFlg(false);
                        mwClntRepository.save(clnt);
                    }
                }
            });
        }

        if (dto.mw_addr != null) {
            dto.mw_addr.forEach(item -> {
                if (item.addr_seq != null) {
                    MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(item.addr_seq, true);
                    if (addr != null) {
                        addr.setSyncFlg(false);
                        mwAddrRepository.save(addr);
                    }
                }
            });
        }

        if (dto.mw_addr_rel != null) {
            dto.mw_addr_rel.forEach(item -> {
                if (item.addr_rel_seq != null) {
                    MwAddrRel adrRel = mwAddrRelRepository.findOneByAddrRelSeqAndEntyTypeAndEntySeqAndCrntRecFlg(item.addr_rel_seq, item.enty_typ,
                            item.enty_key, true);
                    if (adrRel != null) {
                        adrRel.setSyncFlg(false);
                        mwAddrRelRepository.save(adrRel);
                    }
                }
            });
        }

        if (dto.mw_loan_app != null) {
            dto.mw_loan_app.forEach(item -> {
                if (item.loan_app_seq != null) {
                    MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(item.loan_app_seq, true);
                    if (app != null) {
                        app.setSyncFlg(false);
                        mwLoanAppRepository.save(app);
                    }
                }
            });
        }

        if (dto.mw_mfcib_oth_outsd_loan != null) {
            dto.mw_mfcib_oth_outsd_loan.forEach(item -> {
                if (item.oth_outsd_loan_seq != null) {
                    MwMfcibOthOutsdLoan loan = mwMfcibOthOutsdLoanRepository.findOneByOthOutsdLoanSeqAndCrntRecFlg(item.oth_outsd_loan_seq,
                            true);
                    if (loan != null) {
                        loan.setSyncFlg(false);
                        mwMfcibOthOutsdLoanRepository.save(loan);
                    }
                }
            });
        }

        if (dto.mw_clnt_hlth_insr != null) {
            dto.mw_clnt_hlth_insr.forEach(item -> {
                if (item.clnt_hlth_insr_seq != null) {
                    MwClntHlthInsr insr = mwClntHlthInsrRepository.findOneByClntHlthInsrSeqAndCrntRecFlg(item.clnt_hlth_insr_seq, true);
                    if (insr != null) {
                        insr.setSyncFlg(false);
                        mwClntHlthInsrRepository.save(insr);
                    }
                }
            });
        }

        if (dto.mw_hlth_insr_memb != null) {
            dto.mw_hlth_insr_memb.forEach(item -> {
                if (item.hlth_insr_memb_seq != null) {
                    MwHlthInsrMemb mem = mwHlthInsrMembRepository.findOneByHlthInsrMembSeqAndCrntRecFlg(item.hlth_insr_memb_seq, true);
                    if (mem != null) {
                        mem.setSyncFlg(false);
                        mwHlthInsrMembRepository.save(mem);
                    }
                }
            });
        }

        if (dto.mw_clnt_rel != null) {
            dto.mw_clnt_rel.forEach(item -> {
                if (item.clnt_rel_seq != null) {
                    MwClntRel clntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlgAndCrntRecFlg(item.clnt_rel_seq,
                            item.rel_typ_flg, true);
                    if (clntRel != null) {
                        clntRel.setSyncFlg(false);
                        mwClntRelRepository.save(clntRel);
                    }
                }
            });
        }

        if (dto.mw_loan_utl_plan != null) {
            dto.mw_loan_utl_plan.forEach(item -> {
                if (item.loan_utl_plan_seq != null) {
                    MwLoanUtlPlan utl = mwLoanUtlPlanRepository.findOneByLoanUtlPlanSeqAndCrntRecFlg(item.loan_utl_plan_seq, true);
                    if (utl != null) {
                        utl.setSyncFlg(false);
                        mwLoanUtlPlanRepository.save(utl);
                    }
                }
            });
        }

        if (dto.mw_biz_aprsl != null) {
            dto.mw_biz_aprsl.forEach(item -> {
                if (item.biz_aprsl_seq != null) {
                    MwBizAprsl aprsl = mwBizAprslRepository.findOneByBizAprslSeqAndCrntRecFlg(item.biz_aprsl_seq, true);
                    if (aprsl != null) {
                        aprsl.setSyncFlg(false);
                        mwBizAprslRepository.save(aprsl);
                    }
                }
            });
        }
        if (dto.mw_biz_aprsl_incm_hdr != null) {
            dto.mw_biz_aprsl_incm_hdr.forEach(item -> {
                if (item.incm_hdr_seq != null) {
                    MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByIncmHdrSeqAndCrntRecFlg(item.incm_hdr_seq, true);
                    if (hdr != null) {
                        hdr.setSyncFlg(false);
                        mwBizAprslIncmHdrRepository.save(hdr);
                    }
                }
            });
        }

        if (dto.mw_biz_aprsl_incm_dtl != null) {
            dto.mw_biz_aprsl_incm_dtl.forEach(item -> {
                if (item.incm_dtl_seq != null) {
                    MwBizAprslIncmDtl dtl = mwBizAprslIncmDtlRepository.findOneByIncmDtlSeqAndIncmCtgryKeyAndCrntRecFlg(item.incm_dtl_seq,
                            item.incm_ctgry_key, true);
                    if (dtl != null) {
                        dtl.setSyncFlg(false);
                        mwBizAprslIncmDtlRepository.save(dtl);
                    }
                }
            });
        }

        if (dto.mw_biz_exp_dtl != null) {
            dto.mw_biz_exp_dtl.forEach(item -> {
                if (item.exp_dtl_seq != null) {
                    MwBizExpDtl exp = mwBizExpDtlRepository.findOneByExpDtlSeqAndExpCtgryKeyAndCrntRecFlg(item.exp_dtl_seq,
                            item.exp_ctgry_key, true);
                    if (exp != null) {
                        exp.setSyncFlg(false);
                        mwBizExpDtlRepository.save(exp);
                    }
                }
            });
        }

        if (dto.mw_clnt_psc != null) {
            dto.mw_clnt_psc.forEach(item -> {
                if (item.psc_seq != null) {
                    MwClntPsc psc = mwClntPscRepository.findOneByPscSeqAndQstSeqAndCrntRecFlg(item.psc_seq, item.qst_seq, true);
                    if (psc != null) {
                        psc.setSyncFlg(false);
                        mwClntPscRepository.save(psc);
                    }
                }
            });
        }

        if (dto.mw_sch_aprsl != null) {
            dto.mw_sch_aprsl.forEach(item -> {
                if (item.sch_aprsl_seq != null) {
                    MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(item.sch_aprsl_seq, true);
                    if (aprsl != null) {
                        aprsl.setSyncFlg(false);
                        mwSchAprslRepository.save(aprsl);
                    }
                }
            });
        }

        if (dto.mw_sch_grd != null) {
            dto.mw_sch_grd.forEach(item -> {
                if (item.sch_grd_seq != null) {
                    MwSchGrd grd = mwSchGrdRepository.findOneBySchGrdSeqAndCrntRecFlg(item.sch_grd_seq, true);
                    if (grd != null) {
                        grd.setSyncFlg(false);
                        mwSchGrdRepository.save(grd);
                    }
                }
            });
        }

        if (dto.mw_sch_asts != null) {
            dto.mw_sch_asts.forEach(item -> {
                if (item.sch_asts_seq != null) {
                    MwSchAsts asts = mwSchAstsRepository.findOneBySchAstsSeqAndCrntRecFlg(item.sch_asts_seq, true);
                    if (asts != null) {
                        asts.setSyncFlg(false);
                        mwSchAstsRepository.save(asts);
                    }
                }
            });
        }

        if (dto.mw_sch_qlty_chk != null) {
            dto.mw_sch_qlty_chk.forEach(item -> {
                if (item.sch_qlty_chk_seq != null) {
                    MwSchQltyChk chk = mwSchQltyChkRepository.findOneBySchQltyChkSeqAndQstSeqAndCrntRecFlg(item.sch_qlty_chk_seq,
                            item.qst_seq, true);
                    if (chk != null) {
                        chk.setSyncFlg(false);
                        mwSchQltyChkRepository.save(chk);
                    }
                }
            });
        }

        return dto;
    }

    public String markSyncDate(String mac) {
        MwDvcRgstr dvc = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(mac, true);
        if (dvc == null)
            return null;
        dvc.setSyncDt(dvc.getSyncTmpDt());
        mwDvcRgstryRepository.save(dvc);
        return "Date Updated";
    }

    public SyncDto getDataForDeviceRegistered(String mac, Boolean completeDataRequest) throws ParseException {
        MwDvcRgstr dvc = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(mac, true);
        if (dvc == null)
            return null;

        List<MwRefCdVal> vals = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> sts = new ArrayList<>();
        vals.forEach(val -> {
            if (val.getRefCd().equals(Common.SubmittedStatus) || val.getRefCd().equals(Common.NeedMoreClarificationStatus)
                    || val.getRefCd().equals(Common.ApprovedStatus) || val.getRefCd().equals(Common.ActiveStatus)
                    || val.getRefCd().equals(Common.AdvanceStatus) || val.getRefCd().equals(Common.AmlMatchStatus)
                    || val.getRefCd().equals(Common.DeferredStatus) || val.getRefCd().equals(Common.RejectedStatus)
                    || val.getRefCd().equals(Common.DiscardedStatus)) {
                sts.add(val.getRefCdSeq());
            }
            if (!completeDataRequest && (val.getRefCd().equals(Common.DeferredStatus) || val.getRefCd().equals(Common.RejectedStatus)
                    || val.getRefCd().equals(Common.DiscardedStatus))) {
                sts.add(val.getRefCdSeq());
            }
            if (val.getRefCd().equals(Common.DiscardedStatus)) {
                discardedSts = val.getRefCdSeq();
            }
            if (val.getRefCd().equals(Common.DeferredStatus))
                defferedSts = val.getRefCdSeq();
            if (val.getRefCd().equals(Common.RejectedStatus))
                rejectedSts = val.getRefCdSeq();
            if (val.getRefCd().equals(Common.CompletedStatus))
                complSts = val.getRefCdSeq();
        });

        if (dvc.getEntyTypFlg() == 1) {

            MwPortEmpRel rel = mwPortEmpRelRepository.findOneByPortSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (rel == null)
                return null;
            MwEmp emp = mwEmpRepository.findOneByEmpSeq(rel.getEmpSeq());
            if (emp == null)
                return null;

            List<Long> ports = new ArrayList<>();
            ports.add(dvc.getEntyTypSeq());
            SyncDto dto = (dvc.getSyncDt() == null || completeDataRequest) ? getCompleteDataForTab(ports, sts)
                    : traverseDataForTab(ports, dvc.getSyncDt(), emp.getEmpLanId(), sts);

            dvc.setSyncTmpDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);
            return dto;
        } else if (dvc.getEntyTypFlg() == 2) {

            MwBrnchEmpRel rel = mwBrnchEmpRelRepository.findOneByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (rel == null)
                return null;
            MwEmp emp = mwEmpRepository.findOneByEmpSeq(rel.getEmpSeq());
            if (emp == null)
                return null;
            List<MwPort> prts = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            List<Long> ports = new ArrayList<>();
            prts.forEach(port -> {
                ports.add(port.getPortSeq());
            });
            SyncDto dto = (dvc.getSyncDt() == null || completeDataRequest) ? getCompleteDataForTab(ports, sts)
                    : traverseDataForTab(ports, dvc.getSyncDt(), emp.getEmpLanId(), sts);

            dto = getCompleteMntrngChksForBranch(dto, dvc.getEntyTypSeq());
            dvc.setSyncTmpDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);
            return dto;
        }
        return new SyncDto();
    }

    @Async
    public void saveFile(SyncDto dto, String user) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            FileOutputStream f = new FileOutputStream(new File(context.getRealPath("") + "WEB-INF" + File.separator + "classes"
                    + File.separator + "sync" + File.separator + user + "-" + date + "-LO-SYNC.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(dto);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SyncDto getDataForTab(List<Long> ports, Instant syncDate, String user) {
        final SyncDto dto = new SyncDto();
        // ------------ MW_CLNT ------------//
        List<MwClnt> clnts = mwClntRepository
                .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortKeyInOrderByEffStartDt(syncDate, user, true, ports);

        if (clnts != null && clnts.size() > 0) {
            dto.mw_clnt = new ArrayList<>();
            clnts.forEach(clnt -> {
                if (ports.contains(clnt.getPortKey())) {
                    ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
                    clntDto.DomainToDto(clnt);
                    dto.mw_clnt.add(clntDto);
                }
            });
        }

        // return dto;
        // /*
        // mwClntRepository.save(clnts);

        // -------------- MW_ADDR_REL ----------//
        List<MwAddrRel> addrRels = mwAddrRelRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(syncDate, user,
                true);
        // if ( addrRels != null && addrRels.size() > 0 ) {
        // addrRels.forEach( rel -> {
        // if ( rel.getEntyType() != null ) {
        // if ( rel.getEntyType().toLowerCase().equals( "client" ) ) {
        // Long portKey = getPortKeyUsingClientSeq( rel.getEntySeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddrRelDto addressRelDto = new AddrRelDto();
        // addressRelDto.DomainToDto( rel );
        // if ( dto.mw_addr_rel == null )
        // dto.mw_addr_rel = new ArrayList<>();
        // dto.mw_addr_rel.add( addressRelDto );
        // // rel.setSyncFlg(false);
        // }
        // }
        // } else if ( rel.getEntyType().toLowerCase().equals( "nominee" )
        // || rel.getEntyType().toLowerCase().equals( Common.cobAddress.toLowerCase() )
        // || rel.getEntyType().toLowerCase().equals( "next of kin" )
        // || rel.getEntyType().toLowerCase().equals( Common.relAddress.toLowerCase() ) ) {
        // MwClntRel clntRel = null;
        // if ( rel.getEntyType().toLowerCase().equals( Common.cobAddress.toLowerCase() ) )
        // clntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlg( rel.getEntySeq(), Common.Coborrower,
        // true );
        // if ( rel.getEntyType().toLowerCase().equals( Common.relAddress.toLowerCase() ) )
        // clntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlg( rel.getEntySeq(), Common.Relatives,
        // true );
        // if ( clntRel != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( clntRel.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddrRelDto addressRelDto = new AddrRelDto();
        // addressRelDto.DomainToDto( rel );
        // if ( dto.mw_addr_rel == null )
        // dto.mw_addr_rel = new ArrayList<>();
        // dto.mw_addr_rel.add( addressRelDto );
        // // rel.setSyncFlg(false);
        // }
        // }
        // }
        // } else if ( rel.getEntyType().toLowerCase().equals( Common.businessAddress.toLowerCase() ) ) {
        // MwBizAprsl bizAprsl = mwBizAprslRepository.findOneByBizAprslSeq( rel.getEntySeq(), true );
        // if ( bizAprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( bizAprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddrRelDto addressRelDto = new AddrRelDto();
        // addressRelDto.DomainToDto( rel );
        // if ( dto.mw_addr_rel == null )
        // dto.mw_addr_rel = new ArrayList<>();
        // dto.mw_addr_rel.add( addressRelDto );
        // // rel.setSyncFlg(false);
        // }
        // }
        // }
        // } else if ( rel.getEntyType().toLowerCase().equals( Common.schApAddress.toLowerCase() ) ) {
        // MwSchAprsl schAprsl = mwSchAprslRepository.findOneBySchAprslSeq( rel.getEntySeq(), true );
        // if ( schAprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( schAprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddrRelDto addressRelDto = new AddrRelDto();
        // addressRelDto.DomainToDto( rel );
        // if ( dto.mw_addr_rel == null )
        // dto.mw_addr_rel = new ArrayList<>();
        // dto.mw_addr_rel.add( addressRelDto );
        // // rel.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // }
        // } );
        // }
        // // mwAddrRelRepository.save(addrRels);
        //
        // // ------------ MW_CNIC_TKN --------------//
        // List< MwCnicTkn > tkns = mwClntCnicTknRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user,
        // true );
        // if ( tkns != null && tkns.size() > 0 ) {
        // tkns.forEach( tkn -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( tkn.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ClientCnicToken tdto = new ClientCnicToken();
        // tdto.DomainToDto( tkn );
        // if ( dto.mw_cnic_tkn == null )
        // dto.mw_cnic_tkn = new ArrayList<>();
        // dto.mw_cnic_tkn.add( tdto );
        // }
        // }
        // } );
        // }
        //
        // // ------------ MW_CLNT_PERM_ADDR --------------//
        // List< MwClntPermAddr > perms = mwClntPermAddrRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate,
        // user, true );
        // if ( perms != null && perms.size() > 0 ) {
        // perms.forEach( perm -> {
        // Long portKey = getPortKeyUsingClientSeq( perm.getClntSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ClientPermAddressDto pdto = new ClientPermAddressDto();
        // pdto.DomainToDto( perm );
        // if ( dto.mw_clnt_perm_addr == null )
        // dto.mw_clnt_perm_addr = new ArrayList<>();
        // dto.mw_clnt_perm_addr.add( pdto );
        // }
        // }
        // } );
        // }
        //
        // // ------------ MW_ADDR --------------//
        // List< MwAddr > addrs = mwAddrRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true );
        //
        // if ( addrs != null && addrs.size() > 0 ) {
        // addrs.forEach( addr -> {
        // MwAddrRel rel = mwAddrRelRepository.findOneByAddrSeq( addr.getAddrSeq(), true );
        // if ( rel != null ) {
        // if ( rel.getEntyType() != null ) {
        // if ( rel.getEntyType().toLowerCase().equals( "client" ) ) {
        // Long portKey = getPortKeyUsingClientSeq( rel.getEntySeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddressDto addressDto = new AddressDto();
        // addressDto.DomainToDto( addr );
        // if ( dto.mw_addr == null )
        // dto.mw_addr = new ArrayList<>();
        // dto.mw_addr.add( addressDto );
        // // addr.setSyncFlg(false);
        // }
        // }
        // } else if (
        // // rel.getEntyType().toLowerCase().equals( Common.Nominee ) ||
        // rel.getEntyType().toLowerCase().equals( Common.cobAddress.toLowerCase() )
        // || rel.getEntyType().toLowerCase().equals( "nextofkin" )
        // || rel.getEntyType().toLowerCase().equals( Common.relAddress.toLowerCase() ) ) {
        // MwClntRel clntRel = null;
        // if ( rel.getEntyType().toLowerCase().equals( Common.cobAddress.toLowerCase() ) )
        // clntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlg( rel.getEntySeq(),
        // Common.Coborrower, true );
        // if ( rel.getEntyType().toLowerCase().equals( Common.relAddress.toLowerCase() ) )
        // clntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlg( rel.getEntySeq(),
        // Common.Relatives, true );
        // if ( clntRel != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( clntRel.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddressDto addressDto = new AddressDto();
        // addressDto.DomainToDto( addr );
        // if ( dto.mw_addr == null )
        // dto.mw_addr = new ArrayList<>();
        // dto.mw_addr.add( addressDto );
        // // addr.setSyncFlg(false);
        // }
        // }
        // }
        // } else if ( rel.getEntyType().toLowerCase().equals( Common.businessAddress.toLowerCase() ) ) {
        // MwBizAprsl bizAprsl = mwBizAprslRepository.findOneByBizAprslSeq( rel.getEntySeq(), true );
        // if ( bizAprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( bizAprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddressDto addressDto = new AddressDto();
        // addressDto.DomainToDto( addr );
        // if ( dto.mw_addr == null )
        // dto.mw_addr = new ArrayList<>();
        // dto.mw_addr.add( addressDto );
        // // addr.setSyncFlg(false);
        // }
        // }
        // }
        // } else if ( rel.getEntyType().toLowerCase().equals( Common.schApAddress.toLowerCase() ) ) {
        // MwSchAprsl schAprsl = mwSchAprslRepository.findOneBySchAprslSeq( rel.getEntySeq(), true );
        // if ( schAprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( schAprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // AddressDto addressDto = new AddressDto();
        // addressDto.DomainToDto( addr );
        // if ( dto.mw_addr == null )
        // dto.mw_addr = new ArrayList<>();
        // dto.mw_addr.add( addressDto );
        // // addr.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // }
        // }
        // } );
        // }
        //
        // // mwAddrRepository.save(addrs);
        //
        // // ---------- MW_LOAN_APP ----------- //
        // List< MwLoanApp > loans = mwLoanAppRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true
        // );
        //
        // if ( loans != null && loans.size() > 0 ) {
        // loans.forEach( loan -> {
        // if ( loan.getPortSeq() != null && loan.getPrdSeq() != null ) {
        // MwPrd prd = mwPrdRepository.findOneByPrdSeq( loan.getPrdSeq(), true );
        // if ( prd != null ) {
        // if ( prd.getPrdTypKey() != 1165 ) {
        // if ( ports.contains( loan.getPortSeq() ) ) {
        // LoanAppDto loanDto = new LoanAppDto();
        // loanDto.DomainToDto( loan );
        // if ( dto.mw_loan_app == null )
        // dto.mw_loan_app = new ArrayList<>();
        // dto.mw_loan_app.add( loanDto );
        // // loan.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // } );
        // }
        //
        // // mwLoanAppRepository.save(loans);
        //
        // // ---------- MW_MFCIB_OTH_OUTSD_LOAN --------- //
        // List< MwMfcibOthOutsdLoan > otherLoans = mwMfcibOthOutsdLoanRepository
        // .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true );
        // if ( otherLoans != null && otherLoans.size() > 0 ) {
        // otherLoans.forEach( loan -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( loan.getLoanAppSeq() );
        // if ( ports.contains( portKey ) ) {
        // OtherLoanDto oDto = new OtherLoanDto();
        // oDto.DomainToDto( loan );
        // if ( dto.mw_mfcib_oth_outsd_loan == null )
        // dto.mw_mfcib_oth_outsd_loan = new ArrayList<>();
        // dto.mw_mfcib_oth_outsd_loan.add( oDto );
        // // loan.setSyncFlg(false);
        // }
        // } );
        // }
        // // mwMfcibOthOutsdLoanRepository.save(otherLoans);
        //
        // // ---------- MW_CLNT_HLTH_INSR ---------- //
        // List< MwClntHlthInsr > insrs = mwClntHlthInsrRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate,
        // user, true );
        // if ( insrs != null && insrs.size() > 0 ) {
        // insrs.forEach( insr -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( insr.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // InsuranceInfoHeader hdrDto = new InsuranceInfoHeader();
        // hdrDto.DomainToDto( insr );
        // if ( dto.mw_clnt_hlth_insr == null )
        // dto.mw_clnt_hlth_insr = new ArrayList<>();
        // dto.mw_clnt_hlth_insr.add( hdrDto );
        // // insr.setSyncFlg(false);
        // }
        // }
        // } );
        // }
        // // mwClntHlthInsrRepository.save(insrs);
        //
        // // ----------- MW_HLTH_INSR_MEMB -------- //
        // List< MwHlthInsrMemb > members = mwHlthInsrMembRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot(
        // syncDate,
        // user, true );
        // if ( members != null && members.size() > 0 ) {
        // members.forEach( member -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( member.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // InsuranceMember memDto = new InsuranceMember();
        // memDto.DomainToDto( member );
        // if ( dto.mw_hlth_insr_memb == null )
        // dto.mw_hlth_insr_memb = new ArrayList<>();
        // dto.mw_hlth_insr_memb.add( memDto );
        // // member.setSyncFlg(false);
        // }
        // }
        // } );
        // }
        // // mwHlthInsrMembRepository.save(members);
        //
        // // --------- MW_CLNT_REL --------- //
        // List< MwClntRel > rels = mwClntRelRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true
        // );
        // if ( rels != null && rels.size() > 0 ) {
        // rels.forEach( rel -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( rel.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ClntRelDto rDto = new ClntRelDto();
        // rDto.DomainToDto( rel );
        // if ( dto.mw_clnt_rel == null )
        // dto.mw_clnt_rel = new ArrayList<>();
        // dto.mw_clnt_rel.add( rDto );
        // // rel.setSyncFlg(false);
        // }
        // }
        // } );
        // }
        // // mwClntRelRepository.save(rels);
        //
        // // ---------- MW_LOAN_UTL_PLAN ---------- //
        // List< MwLoanUtlPlan > plans = mwLoanUtlPlanRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate,
        // user,
        // true );
        // if ( plans != null && plans.size() > 0 ) {
        // plans.forEach( plan -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( plan.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ExpextedLoanDto eDto = new ExpextedLoanDto();
        // eDto.DomainToDto( plan );
        // if ( dto.mw_loan_utl_plan == null )
        // dto.mw_loan_utl_plan = new ArrayList<>();
        // dto.mw_loan_utl_plan.add( eDto );
        // // plan.setSyncFlg(false);
        // }
        // }
        // } );
        // }
        // // mwLoanUtlPlanRepository.save(plans);
        //
        // // --------- MW_BIZ_APRSL -------- //
        // List< MwBizAprsl > bizAprsls = mwBizAprslRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate,
        // user,
        // true );
        // if ( bizAprsls != null && bizAprsls.size() > 0 ) {
        // bizAprsls.forEach( aprsl -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // BusinessAppraisalBasicInfo info = new BusinessAppraisalBasicInfo();
        // info.DomainToDto( aprsl );
        // if ( dto.mw_biz_aprsl == null )
        // dto.mw_biz_aprsl = new ArrayList<>();
        // dto.mw_biz_aprsl.add( info );
        // // aprsl.setSyncFlg(false);
        // }
        // }
        // } );
        // // mwBizAprslRepository.save(bizAprsls);
        // }
        //
        // // ----------- MW_BIZ_APRSL_INCM_HDR ----------- //
        // List< MwBizAprslIncmHdr > hdrs = mwBizAprslIncmHdrRepository
        // .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true );
        // if ( hdrs != null && hdrs.size() > 0 ) {
        // hdrs.forEach( hdr -> {
        // MwBizAprsl aprsl = mwBizAprslRepository.findOneByBizAprslSeq( hdr.getMwBizAprsl(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // BizAppIncmHdrDto hDto = new BizAppIncmHdrDto();
        // hDto.DomainToDto( hdr );
        // if ( dto.mw_biz_aprsl_incm_hdr == null )
        // dto.mw_biz_aprsl_incm_hdr = new ArrayList<>();
        // dto.mw_biz_aprsl_incm_hdr.add( hDto );
        // // hdr.setSyncFlg(false);
        // }
        // }
        // }
        // } );
        // // mwBizAprslIncmHdrRepository.save(hdrs);
        // }
        //
        // // ---------- MW_BIZ_APRSL_INCM_DTL ---------- //
        // List< MwBizAprslIncmDtl > dtls = mwBizAprslIncmDtlRepository
        // .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true );
        // if ( dtls != null && dtls.size() > 0 ) {
        // dtls.forEach( dtl -> {
        // if ( dtl.getEntyTypFlg() == 1 ) {
        // MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByIncmHdrSeq( dtl.getMwBizAprslIncmHdr(),
        // true );
        // if ( hdr != null ) {
        // MwBizAprsl aprsl = mwBizAprslRepository.findOneByBizAprslSeq( hdr.getMwBizAprsl(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
        // dDto.DomainToDto( dtl );
        // if ( dto.mw_biz_aprsl_incm_dtl == null )
        // dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
        // dto.mw_biz_aprsl_incm_dtl.add( dDto );
        // // dtl.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // } else if ( dtl.getEntyTypFlg() == 2 ) {
        // MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeq( dtl.getMwBizAprslIncmHdr(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
        // dDto.DomainToDto( dtl );
        // if ( dto.mw_biz_aprsl_incm_dtl == null )
        // dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
        // dto.mw_biz_aprsl_incm_dtl.add( dDto );
        // // dtl.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // } );
        // // mwBizAprslIncmDtlRepository.save(dtls);
        // }
        //
        // // --------- MW_BIZ_EXP_DTL ---------- //
        // List< MwBizExpDtl > exps = mwBizExpDtlRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user,
        // true );
        // if ( exps != null && exps.size() > 0 ) {
        // exps.forEach( ex -> {
        // if ( ex.getEntyTypFlg() == 1 ) {
        // MwBizAprsl aprsl = mwBizAprslRepository.findOneByBizAprslSeq( ex.getMwBizAprsl(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getMwLoanApp() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ExpenseDto eDto = new ExpenseDto();
        // eDto.DomainToDto( ex );
        // if ( dto.mw_biz_exp_dtl == null )
        // dto.mw_biz_exp_dtl = new ArrayList<>();
        // dto.mw_biz_exp_dtl.add( eDto );
        // // ex.setSyncFlg(false);
        // }
        // }
        // }
        // } else if ( ex.getEntyTypFlg() == 2 ) {
        // MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeq( ex.getMwBizAprsl(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // ExpenseDto eDto = new ExpenseDto();
        // eDto.DomainToDto( ex );
        // if ( dto.mw_biz_exp_dtl == null )
        // dto.mw_biz_exp_dtl = new ArrayList<>();
        // dto.mw_biz_exp_dtl.add( eDto );
        // // ex.setSyncFlg(false);
        // }
        // }
        // }
        // }
        // } );
        // // mwBizExpDtlRepository.save(exps);
        // }
        //
        // // --------- MW_CLNT_PSC -------- //
        // List< MwClntPsc > pscs = mwClntPscRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true
        // );
        // if ( pscs != null && pscs.size() > 0 ) {
        // pscs.forEach( psc -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( psc.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // PSC pDto = new PSC();
        // pDto.DomainToDto( psc );
        // if ( dto.mw_clnt_psc == null )
        // dto.mw_clnt_psc = new ArrayList<>();
        // dto.mw_clnt_psc.add( pDto );
        // // psc.setSyncFlg(false);
        // }
        // }
        // } );
        // // mwClntPscRepository.save(pscs);
        // }
        //
        // // --------- MW_SCH_APRSL ---------//
        // List< MwSchAprsl > schAprsls = mwSchAprslRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate,
        // user,
        // true );
        // if ( schAprsls != null && schAprsls.size() > 0 ) {
        // schAprsls.forEach( aprsl -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // SchoolAppraisalBasicInfoDto sDto = new SchoolAppraisalBasicInfoDto();
        // sDto.DomainToDto( aprsl );
        // if ( dto.mw_sch_aprsl == null )
        // dto.mw_sch_aprsl = new ArrayList<>();
        // dto.mw_sch_aprsl.add( sDto );
        // // aprsl.setSyncFlg(false);
        // }
        // }
        // } );
        // // mwSchAprslRepository.save(schAprsls);
        // }
        //
        // // ---------- MW_SCH_GRD -------- //
        // List< MwSchGrd > grds = mwSchGrdRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true );
        // if ( grds != null && grds.size() > 0 ) {
        // grds.forEach( grd -> {
        // MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeq( grd.getSchAprslSeq(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // SchoolGradesDto gDto = new SchoolGradesDto();
        // gDto.DomainToDto( grd );
        // if ( dto.mw_sch_grd == null )
        // dto.mw_sch_grd = new ArrayList<>();
        // dto.mw_sch_grd.add( gDto );
        // // grd.setSyncFlg(false);
        // }
        // }
        // }
        // } );
        // // mwSchGrdRepository.save(grds);
        // }
        //
        // // --------- mw_sch_atnd --------- //
        // List< MwSchAtnd > atnds = mwSchAtndRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true
        // );
        // if ( atnds != null && atnds.size() > 0 ) {
        // atnds.forEach( atnd -> {
        // MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeq( atnd.getSchAprslSeq(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // SchoolAttendanceDto gDto = new SchoolAttendanceDto();
        // gDto.DomainToDto( atnd );
        // if ( dto.mw_sch_atnd == null )
        // dto.mw_sch_atnd = new ArrayList<>();
        // dto.mw_sch_atnd.add( gDto );
        // }
        // }
        // }
        // } );
        // }
        //
        // // ---------- MW_SCH_ASTS -------- //
        // List< MwSchAsts > asts = mwSchAstsRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user, true
        // );
        // if ( asts != null && asts.size() > 0 ) {
        // asts.forEach( ast -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( ast.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // SchoolInfrastructureDto aDto = new SchoolInfrastructureDto();
        // aDto.DomainToDto( ast );
        // if ( dto.mw_sch_asts == null )
        // dto.mw_sch_asts = new ArrayList<>();
        // dto.mw_sch_asts.add( aDto );
        // }
        // }
        // } );
        // // mwSchAstsRepository.save(asts);
        // }
        //
        // // ---------- MW_SCH_QLTY_CHK -------- //
        // List< MwSchQltyChk > chks = mwSchQltyChkRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user,
        // true );
        // if ( chks != null && chks.size() > 0 ) {
        // chks.forEach( chk -> {
        // MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeq( chk.getSchAprslSeq(), true );
        // if ( aprsl != null ) {
        // Long portKey = getPortKeyUsingLoanAppSeq( aprsl.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // SchoolQuestionDto sDto = new SchoolQuestionDto();
        // sDto.DomainToDto( chk );
        // if ( dto.mw_sch_qlty_chk == null )
        // dto.mw_sch_qlty_chk = new ArrayList<>();
        // dto.mw_sch_qlty_chk.add( sDto );
        // // chk.setSyncFlg(false);
        // }
        // }
        // }
        // } );
        // // mwSchQltyChkRepository.save(chks);
        // }
        //
        // List< MwLoanAppDoc > docs = mwLoanAppDocRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNot( syncDate, user,
        // true );
        // if ( docs != null && docs.size() > 0 ) {
        // docs.forEach( doc -> {
        // Long portKey = getPortKeyUsingLoanAppSeq( doc.getLoanAppSeq() );
        // if ( portKey != null ) {
        // if ( ports.contains( portKey ) ) {
        // if ( ports.contains( portKey ) ) {
        // DocDto dDto = new DocDto();
        // dDto.DomainToDto( doc );
        // if ( dto.mw_loan_app_doc == null )
        // dto.mw_loan_app_doc = new ArrayList<>();
        // dto.mw_loan_app_doc.add( dDto );
        // }
        // }
        // }
        // } );
        // }

        return dto;

        // */
    }

    public Long getPortKeyUsingClientSeq(Long clientSeq) {
        if (clientSeq != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
            if (clnt != null) {
                return clnt.getPortKey();
            }
            return null;
        }
        return null;
    }

    public Long getPortKeyUsingLoanAppSeq(Long loanAppSeq) {
        if (loanAppSeq != null) {
            MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
            if (loan != null)
                return loan.getPortSeq();
            return null;
        }
        return null;
    }

    @Transactional
    @Timed
    public boolean nactaVerification(long loanAppSeq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (loan == null)
            return false;

        MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
        MwRefCdVal apprSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0004");
        MwRefCdVal submittedSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0002");
        if (loan.getLoanAppSts().longValue() == apprSts.getRefCdSeq().longValue()
                || loan.getLoanAppSts().longValue() == submittedSts.getRefCdSeq().longValue()) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
            if (clnt != null) {
                List<MwSancList> nactaList = mwSancListRepository.findAllByCnicNumAndCntryAndCrntRecFlg(clnt.getCnicNum().toString(),
                        "Pakistan", 1L);
                MwClntTagList nactaTag = mwClntTagListRepository.findOneByCnicNumAndTagsSeqAndDelFlg(clnt.getCnicNum(), 6l, false);
                if (nactaList.size() > 0 || nactaTag != null) {
                    MwRefCdVal amlMtchRefCd = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "1682");
                    MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
                    long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
                    mtchdClnt.setAmlMtchdClntSeq(amlMtchdClntSeq);
                    mtchdClnt.setPortSeq(loan.getPortSeq());
                    mtchdClnt.setAmlSrcAgy(nactaRefCdVal.getRefCdSeq());
                    mtchdClnt.setCnicNum(clnt.getCnicNum());
                    mtchdClnt.setCrtdBy(currUser);
                    mtchdClnt.setCrtdDt(Instant.now());
                    mtchdClnt.setStpFlg(true);
                    mtchdClnt.setClntSrcFlg(0);
                    mtchdClnt.setLoanAppSeq(loan.getLoanAppSeq());
                    mwAmlMtchdClntRepository.save(mtchdClnt);
                    loan.setLoanAppSts(amlMtchRefCd.getRefCdSeq());
                    mwLoanAppRepository.save(loan);

                    // Zohaib Asim - Dated 1/11/2022 - Same Funcationality Implemented in Backend Procedure
                    /*tagClnt( clnt, loan.getLoanAppSeq() );
                    tagClntRels( loan.getLoanAppSeq() );
                    return true;*/
                    String prcOutput = prcTagClnt(loanAppSeq, clnt.getCnicNum(), currUser);
                    if (prcOutput.contains("SUCCESS")) {
                        return true;
                    } else {
                        return false;
                    }
                }
                List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                for (MwClntRel rel : rels) {
                    List<MwSancList> nactaRelList = mwSancListRepository.findAllByCnicNumAndCntryAndCrntRecFlg(rel.getCnicNum().toString(),
                            "Pakistan", 1L);
                    MwClntTagList nactaRelTag = mwClntTagListRepository.findOneByCnicNumAndTagsSeqAndDelFlg(rel.getCnicNum(), 6l, false);
                    if (nactaRelList.size() > 0 || nactaRelTag != null) {
                        /*if ( rel.getRelTypFlg().longValue() == 3 ) {
                            MwRefCdVal relWithClnt = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg( rel.getRelWthClntKey(), true );
                            if ( relWithClnt.getRefCd().equals( "0032" ) )
                                continue;
                        }*/
                        MwRefCdVal amlMtchRefCd = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "1682");
                        MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
                        long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
                        mtchdClnt.setAmlMtchdClntSeq(amlMtchdClntSeq);
                        mtchdClnt.setPortSeq(loan.getPortSeq());
                        mtchdClnt.setAmlSrcAgy(nactaRefCdVal.getRefCdSeq());
                        mtchdClnt.setCnicNum(clnt.getCnicNum());
                        mtchdClnt.setCrtdBy(currUser);
                        mtchdClnt.setCrtdDt(Instant.now());
                        mtchdClnt.setStpFlg(true);
                        mtchdClnt.setClntSrcFlg(Integer.parseInt(rel.getRelTypFlg().toString()));
                        mtchdClnt.setLoanAppSeq(loan.getLoanAppSeq());
                        mwAmlMtchdClntRepository.save(mtchdClnt);

                        loan.setLoanAppSts(amlMtchRefCd.getRefCdSeq());
                        mwLoanAppRepository.save(loan);

                        // Zohaib Asim - Dated 1/11/2022 - Same Funcationality Implemented in Backend Procedure
                        /*tagClnt( clnt, loan.getLoanAppSeq() );
                        tagClntRels( loan.getLoanAppSeq() );
                        return true;*/
                        String prcOutput = prcTagClnt(loanAppSeq, clnt.getCnicNum(), currUser);
                        if (prcOutput.contains("SUCCESS")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                // nactaAmlCheck( clnt.getCnicNum(), dto.loan_info.port_seq, dto.loan_info.loan_app_seq );
            }
        }
        return false;
    }

    @Transactional
    @Timed
    public String prcTagClnt(long loanAppSeq, Long clntCnic, String currUser) {

        log.info("PRC_TAG_CLNTS: Procedure execution started against Loan# " + loanAppSeq + ", CNIC: " + clntCnic);

        String parmOutputProcedure = "";

        if (clntCnic > 0 && loanAppSeq > 0) {
            // Precedure Call
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("PRC_TAG_CLNTS");
            storedProcedure.registerStoredProcedureParameter("P_TAG_DTL", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_LOAN_APP_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_CNIC", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_TAGS_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_RMKS", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_USER_ID", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("OP_PRC_RESP", String.class, ParameterMode.OUT);

            storedProcedure.setParameter("P_TAG_DTL", "TAG");
            storedProcedure.setParameter("P_LOAN_APP_SEQ", loanAppSeq);
            storedProcedure.setParameter("P_CNIC", clntCnic);
            storedProcedure.setParameter("P_TAGS_SEQ", 6L);
            storedProcedure.setParameter("P_RMKS", "AML TAGGED ON APPLICATION SUBMIT/APPROVAL");
            storedProcedure.setParameter("P_USER_ID", currUser);
            storedProcedure.execute();

            parmOutputProcedure = storedProcedure.getOutputParameterValue("OP_PRC_RESP").toString();
            log.info("PRC_TAG_CLNTS: Procedure executed with status " + parmOutputProcedure + " against Loan# " + loanAppSeq + ", CNIC: " + clntCnic);
        }

        return parmOutputProcedure;
    }

    public void tagClnt(MwClnt clnt, long loanAppSeq) {
        if (clnt != null) {
            MwClntTagList mwClntTagList = mwClntTagListRepository.findDistinctByCnicNumAndDelFlg(clnt.getCnicNum(), 0L);
            if (mwClntTagList == null) {
                mwClntTagList = new MwClntTagList();
                long mwClntTagListSeq = SequenceFinder.findNextVal("CLNT_TAG_LIST_SEQ");
                mwClntTagList.setClntTagListSeq(mwClntTagListSeq);
                mwClntTagList.setCnicNum(clnt.getCnicNum());
                mwClntTagList.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwClntTagList.setCrtdDt(Instant.now());
                mwClntTagList.setDelFlg(false);
                mwClntTagList.setEffStartDt(Instant.now());
                mwClntTagList.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwClntTagList.setLastUpdDt(Instant.now());
                mwClntTagList.setLoanAppSeq(loanAppSeq);
                mwClntTagList.setTagFromDt(Instant.now());
                mwClntTagList.setTagsSeq(6l);
                // Modified by Zohaib Asim - Dated 14-09-2021 - Production Issue - Nacta-ClntRel-CNIC
                mwClntTagList.setCrntRecFlg(true);
                // End by Zohaib Asim
                mwClntTagListRepository.save(mwClntTagList);
            }
        }
    }

    public void tagClntRels(long loanAppSeq) {
        List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        for (MwClntRel rel : rels) {
            /*if (rel.getRelTypFlg().longValue() == 3) {
                MwRefCdVal relWithClnt = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(rel.getRelWthClntKey(), true);
                if (relWithClnt.getRefCd().equals("0032"))
                    continue;

            }*/
            MwClntTagList mwClntTagList = mwClntTagListRepository.findDistinctByCnicNumAndDelFlg(rel.getCnicNum(), 0L);
            if (mwClntTagList == null) {
                mwClntTagList = new MwClntTagList();
                long mwClntTagListSeq = SequenceFinder.findNextVal("CLNT_TAG_LIST_SEQ");
                mwClntTagList.setClntTagListSeq(mwClntTagListSeq);
                mwClntTagList.setCnicNum(rel.getCnicNum());
                mwClntTagList.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwClntTagList.setCrtdDt(Instant.now());
                mwClntTagList.setDelFlg(false);
                mwClntTagList.setEffStartDt(Instant.now());
                mwClntTagList.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwClntTagList.setLastUpdDt(Instant.now());
                mwClntTagList.setLoanAppSeq(loanAppSeq);
                mwClntTagList.setTagFromDt(Instant.now());
                mwClntTagList.setTagsSeq(6l);
                // Modified by Zohaib Asim - Dated 14-09-2021 - Production Issue - Nacta-ClntRel-CNIC
                mwClntTagList.setCrntRecFlg(true);
                // End by Zohaib Asim
                mwClntTagListRepository.save(mwClntTagList);

            }
        }
    }

    @Transactional
    public String verifyNadraVerisys(TabAppDto dto) {
        String resp = "0";
        if (dto.loan_info.loan_app_sts == 702 && dto.user_role != null && dto.user_role.equals("BM")) {
            Query qry = em.createNativeQuery(Queries.verisysBmApprovalFunc).setParameter("P_LOAN_APP_SEQ", dto.loan_info.loan_app_seq);
            String versts = qry.getSingleResult().toString();
            if (versts != null && versts.equals("0")) {
                resp = "2";
            }
        }
        return resp;
    }

    @Transactional
    public String formValidationCheck(TabAppDto dto) {
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

    public String cbrwrNactaCheck(TabAppDto dto) {
        String resp = "";
        if (dto.loan_info != null) {
            MwRefCdVal stsVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
            if (stsVal.getRefCd().equals("0002") || stsVal.getRefCd().equals("0004")) {
                if (dto.coborrower != null && dto.coborrower.basic_info != null) {
                    if (dto.coborrower.basic_info.rel_typ_flg.longValue() == 3) {
                        MwRefCdVal relWithClnt = mwRefCdValRepository
                                .findOneByRefCdSeqAndCrntRecFlg(dto.coborrower.basic_info.rel_wth_clnt_key, true);
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

    public String nactaNameMatch(long loanAppSeq) {
        String resp = "";
        MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (loan == null)
            return resp;

        MwRefCdVal stsVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(loan.getLoanAppSts(), true);
        if (!stsVal.getRefCd().equals("0004"))
            return resp;

        List<MwAmlMtchdClnt> exList = mwAmlMtchdClntRepository.findAllByLoanAppSeqAndStpFlg(loanAppSeq, false);
        List<Long> nactaDelSeqList = exList.stream().map(MwAmlMtchdClnt::getAmlMtchdClntSeq).collect(Collectors.toList());
        mwAmlMtchdClntRepository.delete(exList);

        List<MwAmlMtchdClnt> amlMtchdList = new ArrayList<>();

        MwRefCdVal nacta = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
        MwRefCdVal unsc = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1641");
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
        if (clnt != null) {
            // Query modified by Zohaib Asim - Dated 5-08-2021 CR:Sanction List
            String query = "select mc.frst_nm||' '||mc.last_nm as clnt_nm, mc.fthr_frst_nm||' '||mc.fthr_last_nm as fthr_nm, md.dist_nm, ms.st_nm\r\n"
                    + "from mw_clnt mc\r\n"
                    + "JOIN MW_LOAN_APP LA ON LA.CLNT_SEQ = mc.CLNT_SEQ AND LA.CRNT_REC_FLG = mc.CRNT_REC_FLG\r\n"
                    + "join mw_addr_rel adrel on adrel.enty_key=mc.clnt_seq and adrel.enty_typ='Client' and adrel.crnt_rec_flg=1\r\n"
                    + "join mw_addr madd on madd.addr_seq=adrel.addr_seq and madd.crnt_rec_flg=1\r\n"
                    + "join mw_city_uc_rel mcu on mcu.city_uc_rel_seq=madd.city_seq and mcu.crnt_rec_flg=1\r\n"
                    + "join mw_uc mu on mu.uc_seq=mcu.uc_seq and mu.crnt_rec_flg=1\r\n"
                    + "join mw_thsl mt on mt.thsl_seq=mu.thsl_seq and mt.crnt_rec_flg=1\r\n"
                    + "join mw_dist md on md.dist_seq=mt.dist_seq and md.crnt_rec_flg=1\r\n"
                    + "join mw_st ms on ms.st_seq=md.st_seq and ms.crnt_rec_flg=1\r\n"
                    + "join mw_sanc_list mnl on LOWER (mnl.cntry) = 'pakistan' AND "
                    + "((LOWER(mnl.sanc_type) = 'nacta' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM)=lower(mc.frst_nm||' '||mc.last_nm)) \r\n"
                    + " OR (LOWER(mnl.sanc_type) like '%s-%' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM)=lower(mc.frst_nm||' '||mc.last_nm))) "
                    + " AND TRUNC(mnl.dob) = TRUNC(mc.dob) "
                    + " WHERE mc.crnt_rec_flg = 1 AND la.loan_app_seq =:loanAppSeq and mnl.cnic_num is null";
            Query q = em.createNativeQuery(query).setParameter("loanAppSeq", loan.getLoanAppSeq());
            List<Object[]> result = q.getResultList();
            if (result != null && result.size() > 0) {
                MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
                mtchdClnt.setPortSeq(loan.getPortSeq());
                mtchdClnt.setAmlSrcAgy(nacta.getRefCdSeq());
                mtchdClnt.setCnicNum(clnt.getCnicNum());
                mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mtchdClnt.setCrtdDt(Instant.now());
                mtchdClnt.setAmlSrcAgy(1l);
                mtchdClnt.setLoanAppSeq(loan.getLoanAppSeq());
                mtchdClnt.setClntSrcFlg(0);
                mtchdClnt.setStpFlg(false);
                amlMtchdList.add(mtchdClnt);
                resp = resp + "Client Name Match found in NACTA/Sanction List. ";
            }

            String amlquery = "select mc.frst_nm||' '||mc.last_nm as clnt_nm \r\n" + "from mw_clnt mc\r\n"
                    + "join mw_aml_list aml on lower(trim(trim(aml.FRST_NM||' '||aml.SCND_NM)||' '||trim(aml.THRD_NM)))=lower(trim(trim(mc.frst_nm)||' '||trim(mc.last_nm))) and lower(aml.cntry) like '%pakistan%'\r\n"
                    + "where mc.cnic_num = :cnic_num and mc.crnt_rec_flg=1";
            Query amlq = em.createNativeQuery(amlquery).setParameter("cnic_num", clnt.getCnicNum());
            List<Object[]> amlresult = amlq.getResultList();
            if (amlresult != null && amlresult.size() > 0) {
                MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
                mtchdClnt.setPortSeq(loan.getPortSeq());
                mtchdClnt.setAmlSrcAgy(unsc.getRefCdSeq());
                mtchdClnt.setCnicNum(clnt.getCnicNum());
                mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mtchdClnt.setCrtdDt(Instant.now());
                mtchdClnt.setLoanAppSeq(loan.getLoanAppSeq());
                mtchdClnt.setClntSrcFlg(0);
                mtchdClnt.setStpFlg(false);
                amlMtchdList.add(mtchdClnt);
                resp = resp + "Client Name Match found in AML List. ";
            }
        }

        // Query Modified by Zohaib Asim - Dated 10-08-2021 - CR: Sanction List (Nacta Removed)
        // Sanction List Phase 2 - Name and DOB Comparison
        // + " AND lower(mnl.FATHER_NM) = lower(mc.fthr_frst_nm || ' ' || mc.fthr_last_nm)
        String clntRelQuery = "SELECT mc.rel_typ_flg, mc.cnic_num, ap.CO_BWR_ADDR_AS_CLNT_FLG, ap.REL_ADDR_AS_CLNT_FLG, " +
                " lower(mnl.DSTRCT), lower(mnl.PRVNCE), lower(md.dist_nm) as clnt_dist, lower(ms.st_nm) as clnt_prvnc,  mc.clnt_rel_seq\r\n"
                + " FROM mw_clnt_rel mc \r\n" + " join mw_loan_app ap on ap.loan_app_seq=mc.loan_app_seq and ap.crnt_rec_flg=1\r\n"
                + " join mw_addr_rel adrel on adrel.enty_key=ap.clnt_seq and adrel.enty_typ='Client' and adrel.crnt_rec_flg=1          \r\n"
                + " join mw_addr madd on madd.addr_seq=adrel.addr_seq and madd.crnt_rec_flg=1                    \r\n"
                + " join mw_city_uc_rel mcu on mcu.city_uc_rel_seq=madd.city_seq and mcu.crnt_rec_flg=1                    \r\n"
                + " join mw_uc mu on mu.uc_seq=mcu.uc_seq and mu.crnt_rec_flg=1                    \r\n"
                + " join mw_thsl mt on mt.thsl_seq=mu.thsl_seq and mt.crnt_rec_flg=1                    \r\n"
                + " join mw_dist md on md.dist_seq=mt.dist_seq and md.crnt_rec_flg=1                    \r\n"
                + " join mw_st ms on ms.st_seq=md.st_seq and ms.crnt_rec_flg=1\r\n"
                + " JOIN mw_sanc_list mnl ON LOWER (mnl.cntry) = 'pakistan' AND "
                + " ((LOWER(mnl.sanc_type) = 'nacta' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM) = lower(mc.frst_nm || ' ' || mc.last_nm) ) \r\n"
                + " OR (LOWER(mnl.sanc_type) like '%s-%' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM)=lower(mc.frst_nm||' '||mc.last_nm))) "
                + " AND TRUNC(mnl.dob) = TRUNC(mc.dob) "
                + " WHERE mc.crnt_rec_flg = 1 AND mc.loan_app_seq =:loanAppSeq and mnl.cnic_num is null";
        Query clntRelNacta = em.createNativeQuery(clntRelQuery).setParameter("loanAppSeq", loan.getLoanAppSeq());
        List<Object[]> clntRelNactaresult = clntRelNacta.getResultList();
        if (clntRelNactaresult != null && clntRelNactaresult.size() > 0) {
            for (Object[] obj : clntRelNactaresult) {
                String clntDist = obj[6].toString();
                String clntPrvnc = obj[7].toString();

                String nactaDist = obj[4].toString();
                String nactaPrvnc = obj[5].toString();

                BigDecimal bd = new BigDecimal("" + obj[0].toString());
                Long relTypFlg = bd.longValue();

                BigDecimal bdC = new BigDecimal("" + (obj[2] == null ? 0 : obj[2].toString()));
                int cbwrAddrAsClnt = bdC.intValue();

                BigDecimal bdR = new BigDecimal("" + (obj[3] == null ? 0 : obj[3].toString()));
                int relAddrAsClnt = bdR.intValue();

                String distQueryStr = "select lower(md.dist_nm), lower(ms.st_nm) from mw_addr_rel adrel\r\n"
                        + " join mw_addr madd on madd.addr_seq=adrel.addr_seq and madd.crnt_rec_flg=1\r\n"
                        + " join mw_city_uc_rel mcu on mcu.city_uc_rel_seq=madd.city_seq and mcu.crnt_rec_flg=1                    \r\n"
                        + " join mw_uc mu on mu.uc_seq=mcu.uc_seq and mu.crnt_rec_flg=1                    \r\n"
                        + " join mw_thsl mt on mt.thsl_seq=mu.thsl_seq and mt.crnt_rec_flg=1                    \r\n"
                        + " join mw_dist md on md.dist_seq=mt.dist_seq and md.crnt_rec_flg=1                    \r\n"
                        + " join mw_st ms on ms.st_seq=md.st_seq and ms.crnt_rec_flg=1\r\n"
                        + " where adrel.enty_key=:entyKey and adrel.enty_typ=:entyTyp and adrel.crnt_rec_flg=1";
                if (relTypFlg.longValue() == 1 || relTypFlg.longValue() == 2 || (relTypFlg.longValue() == 3 && cbwrAddrAsClnt == 1)
                        || (relTypFlg.longValue() == 4 && relAddrAsClnt == 1)) {
                    // Match with Client
                    if (clntDist.equals(nactaDist) && clntPrvnc.equals(nactaPrvnc)) {
                        resp = logInAmlMtchd(relTypFlg.intValue(), resp, "NACTA");
                        amlMtchdList.add(getAmlMtchdObjForRel(loan.getLoanAppSeq(), loan.getPortSeq(), nacta.getRefCdSeq(),
                                obj[1] == null ? 0l : Long.parseLong(obj[1].toString()), relTypFlg.intValue()));
                    }

                } else if (relTypFlg.longValue() == 3 && cbwrAddrAsClnt != 1) {
                    BigDecimal bdSeq = new BigDecimal("" + obj[8].toString());
                    Long clntRelSeq = bdSeq.longValue();
                    Query distQuery = em.createNativeQuery(distQueryStr).setParameter("entyKey", clntRelSeq).setParameter("entyTyp",
                            "CoBorrower");
                    List<Object[]> clntDistPrvncObjList = distQuery.getResultList();
                    if (clntDistPrvncObjList.size() > 0) {
                        Object[] clntDistPrvncObj = clntDistPrvncObjList.get(0);
                        String cDist = clntDistPrvncObj[0] == null ? "" : clntDistPrvncObj[0].toString();
                        String cPrvnc = clntDistPrvncObj[1] == null ? "" : clntDistPrvncObj[1].toString();
                        if (cDist.equals(nactaDist) && cPrvnc.equals(nactaPrvnc)) {
                            resp = logInAmlMtchd(relTypFlg.intValue(), resp, "NACTA");
                            amlMtchdList.add(getAmlMtchdObjForRel(loan.getLoanAppSeq(), loan.getPortSeq(), nacta.getRefCdSeq(),
                                    obj[1] == null ? 0l : Long.parseLong(obj[1].toString()), relTypFlg.intValue()));
                        }
                    }
                } else if (relTypFlg.longValue() == 4 && relAddrAsClnt != 1) {
                    BigDecimal bdSeq = new BigDecimal("" + obj[8].toString());
                    Long clntRelSeq = bdSeq.longValue();
                    Query distQuery = em.createNativeQuery(distQueryStr).setParameter("entyKey", clntRelSeq).setParameter("entyTyp",
                            "ClientRel");
                    List<Object[]> clntDistPrvncObjList = distQuery.getResultList();
                    if (clntDistPrvncObjList.size() > 0) {
                        Object[] clntDistPrvncObj = clntDistPrvncObjList.get(0);
                        String cDist = clntDistPrvncObj[0] == null ? "" : clntDistPrvncObj[0].toString();
                        String cPrvnc = clntDistPrvncObj[1] == null ? "" : clntDistPrvncObj[1].toString();
                        if (cDist.equals(nactaDist) && cPrvnc.equals(nactaPrvnc)) {
                            resp = logInAmlMtchd(relTypFlg.intValue(), resp, "NACTA");
                            amlMtchdList.add(getAmlMtchdObjForRel(loan.getLoanAppSeq(), loan.getPortSeq(), nacta.getRefCdSeq(),
                                    obj[1] == null ? 0l : Long.parseLong(obj[1].toString()), relTypFlg.intValue()));

                        }
                    }
                }

            }
        }

        String clntRelAmlListQuery = "select mc.REL_TYP_FLG, mc.cnic_num, mc.frst_nm||' '||mc.last_nm as clnt_nm                                          from mw_clnt_rel mc  \r\n"
                + " join mw_aml_list aml on lower(trim(trim(aml.FRST_NM||' '||aml.SCND_NM)||' '||trim(aml.THRD_NM)))=lower(trim(trim(mc.frst_nm)||' '||trim(mc.last_nm))) and lower(aml.cntry) like '%pakistan%' \r\n"
                + " where mc.loan_app_seq = :loanAppSeq and mc.crnt_rec_flg=1";
        Query clntRelAml = em.createNativeQuery(clntRelAmlListQuery).setParameter("loanAppSeq", loan.getLoanAppSeq());
        List<Object[]> clntRelAmlresult = clntRelAml.getResultList();
        if (clntRelAmlresult != null && clntRelAmlresult.size() > 0) {
            for (Object[] obj : clntRelAmlresult) {
                BigDecimal bd = new BigDecimal("" + obj[0].toString());
                Long relTypFlg = bd.longValue();
                resp = logInAmlMtchd(relTypFlg.intValue(), resp, "AML");
                amlMtchdList.add(getAmlMtchdObjForRel(loan.getLoanAppSeq(), loan.getPortSeq(), nacta.getRefCdSeq(),
                        obj[1] == null ? 0l : Long.parseLong(obj[1].toString()), relTypFlg.intValue()));

            }
        }

        // Update AmlMtchdClntSeq
        while (amlMtchdList.size() > nactaDelSeqList.size()) {
            long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
            nactaDelSeqList.add(amlMtchdClntSeq);
        }
        for (int i = 0; i < amlMtchdList.size(); i++) {
            amlMtchdList.get(i).setAmlMtchdClntSeq(nactaDelSeqList.get(i));
        }

        mwAmlMtchdClntRepository.save(amlMtchdList);
        return resp;
    }

    public Map amlMtchd(long loanAppSeq) {
        Map<String, String> resp = new HashMap<String, String>();
        List<MwAmlMtchdClnt> mtchdClnt = mwAmlMtchdClntRepository.findAllByLoanAppSeq(loanAppSeq);
        if (mtchdClnt.size() == 0) {
            resp.put("matched", "false");
            return resp;
        }
        String mtchStr = "Substantiation has been done against ";
        String mtchEnty = "";
        String mtchName = "";
        String srcStr = "";
        int i = 0;
        for (MwAmlMtchdClnt mtch : mtchdClnt) {
            MwRefCdVal src = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(mtch.getAmlSrcAgy(), true);
            srcStr = srcStr + (srcStr.length() == 0 ? "" : "/") + ((src == null) ? "" : src.getRefCdDscr());
            if (mtchdClnt.size() > 0 && i > 0) {
                mtchEnty = mtchEnty + "/";
                mtchName = mtchName + ", ";
            }
            switch (mtch.getClntSrcFlg()) {
                case 0:
                    MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mtch.getLoanAppSeq(), true);
                    if (loan == null)
                        break;
                    MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
                    if (clnt == null)
                        break;
                    mtchName = mtchName + clnt.getFrstNm() + " " + (clnt.getLastNm() == null ? "" : clnt.getLastNm());
                    mtchEnty = mtchEnty + "Client";
                    break;
                default:
                    MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(mtch.getLoanAppSeq(),
                            mtch.getClntSrcFlg(), true);
                    if (rel != null) {
                        mtchName = mtchName + rel.getFrstNm() + " " + (rel.getLastNm() == null ? "" : rel.getLastNm());
                        mtchEnty = mtchEnty + getEntyTyp(mtch.getClntSrcFlg());
                    }
                    break;
            }
            i++;
        }
        mtchStr = mtchStr + mtchEnty + " " + mtchName + " and it is hereby confirmed that person is not the accused.";

        resp.put("matched", "true");
        resp.put("source", srcStr);
        resp.put("reason", mtchStr);
        return resp;
    }

    public String getEntyTyp(int relTypFlg) {
        String resp = "";
        switch (relTypFlg) {
            case 1:
                resp = "Nominee";
                break;
            case 2:
                resp = "Next of Kin";
                break;
            case 3:
                resp = "Co-borrower";
                break;
            case 4:
                resp = "Client Relative";
                break;
            case 5:
                resp = "Loan User";
                break;
            default:
                log.debug("Invalid relTypFlg");
        }

        return resp;
    }

    public String logInAmlMtchd(int relTypFlg, String resp, String src) {
        switch (relTypFlg) {
            case 1:
                resp = resp + "Nominee Name Match found in " + src + " List. ";
                break;
            case 2:
                resp = resp + "Next of Kin Name Match found in " + src + " List. ";
                break;
            case 3:
                resp = resp + "Co-borrower Name Match found in " + src + " List. ";
                break;
            case 4:
                resp = resp + "Client Relative Name Match found in " + src + " List. ";
                break;
            case 5:
                resp = resp + "Loan User Name Match found in " + src + " List. ";
                break;
            default:
                log.debug("Invalid relTypFlg");
        }

        return resp;
    }

    public MwAmlMtchdClnt getAmlMtchdObjForRel(long loanAppSeq, long portSeq, long nactaSeq, long cnic, int relTypFlg) {
        MwAmlMtchdClnt mtchdClnt = new MwAmlMtchdClnt();
        mtchdClnt.setPortSeq(portSeq);
        mtchdClnt.setAmlSrcAgy(nactaSeq);
        mtchdClnt.setCnicNum(cnic);
        mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        mtchdClnt.setCrtdDt(Instant.now());
        mtchdClnt.setLoanAppSeq(loanAppSeq);
        mtchdClnt.setClntSrcFlg(relTypFlg);
        mtchdClnt.setStpFlg(false);
        return mtchdClnt;
    }

    public void nactaAmlCheck(long cnicNum, long portSeq, long loanAppSeq) {
        // NACTA LIST
        MwRefCdVal nacta = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
        MwRefCdVal unsc = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1641");
        String query = "select mc.frst_nm||' '||mc.last_nm as clnt_nm, mc.fthr_frst_nm||' '||mc.fthr_last_nm as fthr_nm, md.dist_nm, ms.st_nm\r\n"
                + "from mw_clnt mc\r\n"
                + "join mw_addr_rel adrel on adrel.enty_key=mc.clnt_seq and adrel.enty_typ='Client' and adrel.crnt_rec_flg=1\r\n"
                + "join mw_addr madd on madd.addr_seq=adrel.addr_seq and madd.crnt_rec_flg=1\r\n"
                + "join mw_city_uc_rel mcu on mcu.city_uc_rel_seq=madd.city_seq and mcu.crnt_rec_flg=1\r\n"
                + "join mw_uc mu on mu.uc_seq=mcu.uc_seq and mu.crnt_rec_flg=1\r\n"
                + "join mw_thsl mt on mt.thsl_seq=mu.thsl_seq and mt.crnt_rec_flg=1\r\n"
                + "join mw_dist md on md.dist_seq=mt.dist_seq and md.crnt_rec_flg=1\r\n"
                + "join mw_st ms on ms.st_seq=md.st_seq and ms.crnt_rec_flg=1\r\n"
                + " JOIN mw_sanc_list mnl ON LOWER (mnl.cntry) = 'pakistan' AND "
                + " ((LOWER(mnl.sanc_type) = 'nacta' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM) = lower(mc.frst_nm || ' ' || mc.last_nm) \r\n"
                + " AND lower(mnl.FATHER_NM) = lower(mc.fthr_frst_nm || ' ' || mc.fthr_last_nm) and lower(mnl.DSTRCT)=lower(md.dist_nm)) \r\n"
                + " OR (LOWER(mnl.sanc_type) like '%s-%' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM)=lower(mc.frst_nm||' '||mc.last_nm) "
                + " AND TRUNC(mnl.dob) = TRUNC(mc.dob) )) "
                + "where mc.cnic_num=:cnic_num and mc.crnt_rec_flg=1";
        Query q = em.createNativeQuery(query).setParameter("cnic_num", cnicNum);
        List<Object[]> result = q.getResultList();
        if (result != null && result.size() > 0) {
            MwAmlMtchdClnt mtchdClnt = mwAmlMtchdClntRepository.findOneByLoanAppSeq(loanAppSeq);
            if (mtchdClnt == null) {
                mtchdClnt = new MwAmlMtchdClnt();
                long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
                mtchdClnt.setAmlMtchdClntSeq(amlMtchdClntSeq);
            }
            mtchdClnt.setPortSeq(portSeq);
            mtchdClnt.setAmlSrcAgy(nacta.getRefCdSeq());
            mtchdClnt.setCnicNum(cnicNum);
            mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            mtchdClnt.setCrtdDt(Instant.now());
            mtchdClnt.setAmlSrcAgy(1l);
            mtchdClnt.setLoanAppSeq(loanAppSeq);
            mtchdClnt.setClntSrcFlg(0);
            mtchdClnt.setStpFlg(false);
            mwAmlMtchdClntRepository.save(mtchdClnt);
            return;
        }

        String amlquery = "select mc.frst_nm||' '||mc.last_nm as clnt_nm \r\n" + "from mw_clnt mc\r\n"
                + "join mw_aml_list aml on lower(trim(trim(aml.FRST_NM||' '||aml.SCND_NM)||' '||trim(aml.THRD_NM)))=lower(trim(trim(mc.frst_nm)||' '||trim(mc.last_nm))) and lower(aml.cntry) like '%pakistan%'\r\n"
                + "where mc.cnic_num = :cnic_num and mc.crnt_rec_flg=1";
        Query amlq = em.createNativeQuery(amlquery).setParameter("cnic_num", cnicNum);
        List<Object[]> amlresult = amlq.getResultList();
        if (amlresult != null && amlresult.size() > 0) {
            MwAmlMtchdClnt mtchdClnt = mwAmlMtchdClntRepository.findOneByLoanAppSeq(loanAppSeq);
            if (mtchdClnt == null) {
                mtchdClnt = new MwAmlMtchdClnt();
                long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
                mtchdClnt.setAmlMtchdClntSeq(amlMtchdClntSeq);
            }
            mtchdClnt.setPortSeq(portSeq);
            mtchdClnt.setAmlSrcAgy(unsc.getRefCdSeq());
            mtchdClnt.setCnicNum(cnicNum);
            mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            mtchdClnt.setCrtdDt(Instant.now());
            mtchdClnt.setLoanAppSeq(loanAppSeq);
            mtchdClnt.setClntSrcFlg(0);
            mtchdClnt.setStpFlg(false);
            mwAmlMtchdClntRepository.save(mtchdClnt);
            return;
        }

        String clntRelQuery = "select mc.REL_TYP_FLG, mc.cnic_num, mc.frst_nm||' '||mc.last_nm as clnt_nm, mc.fthr_frst_nm||' '||mc.fthr_last_nm as fthr_nm, md.dist_nm, ms.st_nm\r\n"
                + "from mw_clnt_rel mc\r\n" + "join mw_addr_rel adrel on adrel.enty_key=:loanAppSeq and adrel.enty_typ=\r\n"
                + "case when mc.rel_typ_flg=1 then 'Nominee' when mc.rel_typ_flg=2 then 'CoBorrower' when mc.rel_typ_flg=3 then 'NextOfKin' else 'ClientRel' end and adrel.crnt_rec_flg=1\r\n"
                + "join mw_addr madd on madd.addr_seq=adrel.addr_seq and madd.crnt_rec_flg=1\r\n"
                + "join mw_city_uc_rel mcu on mcu.city_uc_rel_seq=madd.city_seq and mcu.crnt_rec_flg=1\r\n"
                + "join mw_uc mu on mu.uc_seq=mcu.uc_seq and mu.crnt_rec_flg=1\r\n"
                + "join mw_thsl mt on mt.thsl_seq=mu.thsl_seq and mt.crnt_rec_flg=1\r\n"
                + "join mw_dist md on md.dist_seq=mt.dist_seq and md.crnt_rec_flg=1\r\n"
                + "join mw_st ms on ms.st_seq=md.st_seq and ms.crnt_rec_flg=1\r\n"
                + " JOIN mw_sanc_list mnl ON LOWER (mnl.cntry) = 'pakistan' AND "
                + " ((LOWER(mnl.sanc_type) = 'nacta' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM) = lower(mc.frst_nm || ' ' || mc.last_nm) \r\n"
                + " AND lower(mnl.FATHER_NM) = lower(mc.fthr_frst_nm || ' ' || mc.fthr_last_nm) and lower(mnl.DSTRCT)=lower(md.dist_nm)) \r\n"
                + " OR (LOWER(mnl.sanc_type) like '%s-%' AND lower(mnl.FIRST_NM||' '||mnl.LAST_NM)=lower(mc.frst_nm||' '||mc.last_nm) "
                + " AND TRUNC(mnl.dob) = TRUNC(mc.dob) )) "
                + "where mc.crnt_rec_flg=1\r\n" + "and mc.loan_app_seq=:loanAppSeq";
        Query clntRelNacta = em.createNativeQuery(clntRelQuery).setParameter("loanAppSeq", loanAppSeq);
        List<Object[]> clntRelNactaresult = clntRelNacta.getResultList();
        if (clntRelNactaresult != null && clntRelNactaresult.size() > 0) {
            Object[] obj = clntRelNactaresult.get(0);
            MwAmlMtchdClnt mtchdClnt = mwAmlMtchdClntRepository.findOneByLoanAppSeq(loanAppSeq);
            if (mtchdClnt == null) {
                mtchdClnt = new MwAmlMtchdClnt();
                long amlMtchdClntSeq = SequenceFinder.findNextVal("AML_MTCHD_CLNT_SEQ");
                mtchdClnt.setAmlMtchdClntSeq(amlMtchdClntSeq);
            }
            mtchdClnt.setPortSeq(portSeq);
            mtchdClnt.setAmlSrcAgy(nacta.getRefCdSeq());
            mtchdClnt.setCnicNum(obj[1] == null ? 0l : Long.parseLong(obj[1].toString()));
            mtchdClnt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            mtchdClnt.setCrtdDt(Instant.now());
            mtchdClnt.setLoanAppSeq(loanAppSeq);
            mtchdClnt.setClntSrcFlg(obj[0] == null ? 1 : Integer.parseInt(obj[0].toString()));
            mtchdClnt.setStpFlg(false);
            mwAmlMtchdClntRepository.save(mtchdClnt);
            return;
        }

    }

    @Transactional
    public void saveClientVerisys(ResponseEntity<Map> result, TabAppDto dto, String curUser) {

        String parmOutputProcedure = "";
        try {

            // call verisys proc for submitted app status only
            if (result.getBody().get("status").equals("0") && result.getBody().get("canProceed").equals("true") && dto.loan_info.loan_app_sts == 700) {
                StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("PRC_CNIC_VERISYS");
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
    public ResponseEntity<Map> submitApplication(TabAppDto dto, String curUser) throws ParseException, Exception {
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
            MwClnt exClnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(dto.client.basic_info.clnt_seq, true);
            if (exClnt == null) {
                exClnt = new MwClnt();
            }

            // Added by Yousaf Ali - Dated May-2022
            if (exClnt.getMembrshpDt() != null && !exClnt.getMembrshpDt().toString().isEmpty()) {
                exClnt.setMembrshpDt(exClnt.getMembrshpDt());
            }
            //

            exClnt = dto.client.basic_info.DtoToDomain(formatter, formatterDate, exClnt);
            mwClntRepository.save(exClnt);

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
            // dto.loan_info.loan_app_sts_sync = 1;
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

                List<ProductDto> pdtos = getProductsListingForClient(pdto);
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
            saveInsuranceInfo(dto.insurance_info, curUser);
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
            saveClientRel(dto.nominee, curUser);
        }

        if (dto.next_of_kin != null) {
            saveClientRel(dto.next_of_kin, curUser);
        }

        if (dto.coborrower != null) {
            if (dto.coborrower.basic_info != null) {
                saveClientRel(dto.coborrower.basic_info, curUser);
            }
            if (dto.coborrower.coborrower_address != null) {
                saveAddress(dto.coborrower.coborrower_address, curUser);
            }
        }

        if (dto.client_relative != null) {
            if (dto.client_relative.basic_info != null) {
                saveClientRel(dto.client_relative.basic_info, curUser);
            }
            if (dto.client_relative.client_relative_address != null) {
                saveAddress(dto.client_relative.client_relative_address, curUser);
            }
        }

        if (dto.business_appraisal != null) {
            saveBusinessAppraisal(dto.business_appraisal, curUser);
        }

        if (dto.home_imp_aprsl != null) {
            saveHomeAprsl(dto.home_imp_aprsl, curUser);
        }

        if (dto.loan_info != null) {
            savePsc(dto.psc, curUser);
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
                // Modified by Zohaib Asim - Dated 11-03-2021
                // Nominee MFCIB and Client MFCIB will be skipped
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
            saveSchoolAppraisal(dto.school_appraisal, curUser);
        }

        if (dto.school_information != null) {
            saveSchoolInformation(dto.school_information);
        }

        if (dto.anml_rgstr != null) {
            saveAnmlRgstr(dto.anml_rgstr, curUser);
        }

        if (dto.loan_info != null) {
            MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(dto.loan_info.loan_app_sts, true);
            if (val.getRefCd().equals("0007") || val.getRefCd().equals("0008")) {
                deleteApplication(dto.loan_info.loan_app_seq, curUser, val.getRefCdSeq(), dto.loan_info.cmnt);
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

        //
        if (sts != null) {
            if (discardAppFlg) {
                msg = "Dear User, As per the 30-day loan application policy, this application has expired and will be discarded. Please generate new application for the client";
            } else {
                msg = "Application Status Updated As: " + sts.getRefCdDscr();
            }
        }
        // End
        resp.put("message", msg);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Success", msg)).body(resp);
    }

    @Transactional
    public void saveAnmlRgstr(List<AnmlRgstrDto> dtos, String currUser) {
        if (dtos != null) {
            //inActive previous records animal tag against this loan
            if (dtos.get(0) != null && dtos.get(0).loan_app_seq != null) {
                List<MwAnmlRgstr> loan = mwAnmlRgstrRepository.findAllByLoanAppSeqAndCrntRecFlg(dtos.get(0).loan_app_seq, true);
                if (loan.size() > 0) {
                    loan.forEach(l -> {
                        l.setCrntRecFlg(false);
                        l.setDelFlg(true);
                        mwAnmlRgstrRepository.save(l);
                    });
                }
            }//End
            dtos.forEach(rgs -> {
                MwAnmlRgstr exRgstr = mwAnmlRgstrRepository.findOneByAnmlRgstrSeqAndCrntRecFlg(rgs.anml_rgstr_seq, true);
                if (exRgstr == null) {
                    exRgstr = new MwAnmlRgstr();
                }

                exRgstr = rgs.DtoToDomain(formatter, exRgstr);
                mwAnmlRgstrRepository.save(exRgstr);
            });
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

    //Overloaded by Rizwan Mahfooz on 22 August 2022 to get loan info as well
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


    //End

    @Transactional
    public MwClntRel saveClientRel(ClntRelDto dto, String curUser) throws ParseException {
        MwClntRel exRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loan_app_seq, dto.rel_typ_flg, true);
        if (exRel == null) {
            exRel = new MwClntRel();
        }
        exRel = dto.DtoToDomain(formatter, formatterDate, exRel);
        return mwClntRelRepository.save(exRel);
    }

    @Transactional
    public MwHmAprsl saveHomeAprsl(HmAprslDto dto, String curUser) throws ParseException {
        MwHmAprsl exAprsl = mwHmAprslRepository.findOneByBizAprslSeqAndCrntRecFlg(dto.biz_aprsl_seq, true);
        if (exAprsl == null) {
            exAprsl = new MwHmAprsl();
        }
        exAprsl = dto.DtoToDomain(formatter, formatterDate, curUser, exAprsl);
        return mwHmAprslRepository.save(exAprsl);
    }

    @Transactional
    public void saveBusinessAppraisal(BusinessAprslDto dto, String curUser) throws ParseException {
        if (dto.basic_info != null) {
            MwBizAprsl exAprsl = mwBizAprslRepository.findOneByBizAprslSeqAndCrntRecFlg(dto.basic_info.biz_aprsl_seq, true);
            if (exAprsl == null) {
                exAprsl = new MwBizAprsl();

            }
            exAprsl = dto.basic_info.DtoToDomain(formatter, exAprsl);
            mwBizAprslRepository.save(exAprsl);
        }
        if (dto.business_appraisal_address != null) {
            saveAddress(dto.business_appraisal_address, curUser);
        }

        if (dto.income_info != null) {
            if (dto.income_info.income_header != null) {
                MwBizAprslIncmHdr exHdr = mwBizAprslIncmHdrRepository
                        .findOneByMwBizAprslAndCrntRecFlg(dto.income_info.income_header.biz_aprsl_seq, true);
                if (exHdr == null) {
                    exHdr = new MwBizAprslIncmHdr();
                }
                exHdr = dto.income_info.income_header.DtotoDomain(formatter, exHdr);
                mwBizAprslIncmHdrRepository.save(exHdr);
            }

            if (dto.income_info.income_list != null) {
                dto.income_info.income_list.forEach(income -> {
                    MwBizAprslIncmDtl exDtl = mwBizAprslIncmDtlRepository
                            .findOneByIncmDtlSeqAndIncmCtgryKeyAndCrntRecFlg(income.incm_dtl_seq, income.incm_ctgry_key, true);
                    if (exDtl == null) {
                        exDtl = new MwBizAprslIncmDtl();
                    }
                    exDtl = income.DtoToDomain(formatter, exDtl);
                    mwBizAprslIncmDtlRepository.save(exDtl);
                });

            }

        }

        if (dto.expense_list != null) {
            if (dto.expense_list.size() > 0) {
                dto.expense_list.forEach(ex -> {
                    MwBizExpDtl exExpenseDtl = mwBizExpDtlRepository.findOneByExpDtlSeqAndExpCtgryKeyAndMwBizAprslAndExpTypKeyAndCrntRecFlg(ex.exp_dtl_seq,
                            ex.exp_ctgry_key, ex.biz_aprsl_seq, ex.exp_typ_key, true);
                    if (exExpenseDtl == null) {
                        exExpenseDtl = new MwBizExpDtl();
                    }
                    exExpenseDtl = ex.DtoToDomain(formatter, exExpenseDtl);
                    mwBizExpDtlRepository.save(exExpenseDtl);
                });
            }
        }

        if (dto.est_lvstk_fin != null) {
            dto.est_lvstk_fin.forEach(fin -> {
                MwBizAprslEstLvstkFin exFin = mwBizAprslEstLvstkFinRepository
                        .findOneByBizAprslEstLvstkFinSeqAndCrntRecFlg(fin.biz_aprsl_seq, true);
                if (exFin == null) {
                    exFin = new MwBizAprslEstLvstkFin();
                }
                exFin = fin.DtoToDomain(formatter, exFin);
                mwBizAprslEstLvstkFinRepository.save(exFin);
            });
        }
        if (dto.extng_lvstk != null) {
            dto.extng_lvstk.forEach(lvs -> {
                MwBizAprslExtngLvstk exLv = mwBizAprslExtngLvstkRepository.findOneByBizAprslExtngLvstkSeqAndCrntRecFlg(lvs.biz_aprsl_seq,
                        true);
                if (exLv == null) {
                    exLv = new MwBizAprslExtngLvstk();
                }
                exLv = lvs.DtoToDomain(formatter, exLv);
                mwBizAprslExtngLvstkRepository.save(exLv);
            });
        }
    }

    @Transactional
    public List<ProductDto> getProductsListingForClient(ProductDto dto) {

        List<MwPrd> mwPrds = new ArrayList<>();
        List<ProductDto> products = new ArrayList<>();
        MwPrd prodct = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(dto.prdSeq, true);

        if (prodct != null) {
            mwPrds.add(prodct);
        }

        String viewQuery = Queries.CLNT_LOAN_GTT;
        Query q = em.createNativeQuery(viewQuery).setParameter("clntSeq", dto.clntSeq).setParameter("loanAppSeq", dto.loanAppSeq);
        q.executeUpdate();
        List<MwRul> rules = mwRulRepository.findAllByRulCtgryKeyAndCrntRecFlg(1L, true);
        MwRul defaultRule = new MwRul();
        rules.forEach(rle -> {
            if (rle.getRulNm().toLowerCase().equals("default")) {
                defaultRule.setRulSeq(rle.getRulSeq());
                defaultRule.setRulNm(rle.getRulNm());
                defaultRule.setRulCrtraStr(rle.getRulCrtraStr());
                defaultRule.setRulCmnt(rle.getRulCmnt());
            }
        });
        mwPrds.forEach(prd -> {
            List<MwPrdPpalLmt> ppalLimits = mwPrdPpalLmtRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
            if (ppalLimits.size() > 0) {
                int size = products.size();
                ProductDto defaultProductDto = new ProductDto();
                ppalLimits.forEach(limit -> {
                    MwRul rule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(limit.getRulSeq(), true);
                    if (rule != null) {
                        String ruleQuery = Queries.checkRule + rule.getRulCrtraStr();
                        Query qr = em.createNativeQuery(ruleQuery);
                        List<Object[]> rulResult = qr.getResultList();

                        if (rulResult.size() > 0) {
                            BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                            if ((bd.longValue()) == 1) {
                                log.debug("mwPrdPpalLmtRepository SUBMIT APPLICATION || PPAL LIMIT SEQ", limit.getPrdPpalLmtSeq());
                                ProductDto prod = new ProductDto();
                                prod.condition = rule.getRulCrtraStr();
                                // prod.maxAmount = limit.getMaxAmt();
                                // prod.minAmount = limit.getMinAmt();
                                prod.productName = prd.getPrdNm();
                                prod.productSeq = prd.getPrdSeq();
                                prod.limitRule = rule.getRulSeq();

                                List<MwPrdLoanTrm> terms = mwPrdLoanTrmRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                                terms.forEach(term -> {
                                    MwRul termRule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(term.getRulSeq(), true);
                                    if (termRule != null) {
                                        String termRuleQuery = Queries.checkRule + termRule.getRulCrtraStr();
                                        Query qrt = em.createNativeQuery(termRuleQuery);
                                        List<Object[]> termRulResult = qrt.getResultList();

                                        if (termRulResult.size() > 0) {
                                            BigDecimal bdt = new BigDecimal("" + termRulResult.get(0));
                                            if ((bdt.longValue()) == 1) {
                                                log.debug("mwPrdPpalLmtRepository SUBMIT APPLICATION || LOAN TERM SEQ",
                                                        term.getPrdTrmSeq());
                                                prod.termRule = termRule.getRulSeq();
                                                MwRefCdVal refCdVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(term.getTrmKey(),
                                                        true);
                                                prod.installments = (refCdVal == null) ? 0 : Integer.parseInt(refCdVal.getRefCdDscr());
                                                return;
                                            }
                                        }
                                    }
                                });
                                if (prod.installments == 0 && defaultRule != null) {
                                    MwPrdLoanTrm term = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                                            defaultRule.getRulSeq(), true);
                                    if (term != null) {
                                        MwRefCdVal refCdVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(term.getTrmKey(), true);
                                        prod.installments = (refCdVal == null) ? 0 : Integer.parseInt(refCdVal.getRefCdDscr());
                                        prod.termRule = defaultRule.getRulSeq();
                                    }
                                }

                                if (rule.getRulNm().equals("Default")) {
                                    defaultProductDto.condition = rule.getRulCrtraStr();
                                    // defaultProductDto.maxAmount = limit.getMaxAmt();
                                    // defaultProductDto.minAmount = limit.getMinAmt();
                                    defaultProductDto.productName = prd.getPrdNm();
                                    defaultProductDto.productSeq = prd.getPrdSeq();
                                    defaultProductDto.prdRul = rule.getRulSeq();
                                    defaultProductDto.installments = prod.installments;
                                    defaultProductDto.limitRule = rule.getRulSeq();
                                    defaultProductDto.termRule = prod.termRule;
                                } else {
                                    products.add(prod);
                                }
                            }
                        }
                    }
                });
                if (products.size() == size) {
                    if (defaultProductDto.productSeq != null && defaultProductDto.productSeq > 0) {
                        products.add(defaultProductDto);
                    }
                }
            }
        });
        log.debug("PRODUCTSS", products.toString());
        return products;
    }

    @Transactional
    public void saveInsuranceInfo(InsuranceInfo dto, String curUser) throws ParseException {
        if (dto.insurance_info_header != null) {
            MwClntHlthInsr exinsr = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.insurance_info_header.loan_app_seq,
                    true);
            if (exinsr == null) {
                exinsr = new MwClntHlthInsr();
            }
            exinsr = dto.insurance_info_header.DtoToDomain(formatter, exinsr);
            mwClntHlthInsrRepository.save(exinsr);

            List<MwHlthInsrMemb> exMem = mwHlthInsrMembRepository
                    .findAllByLoanAppSeqAndCrntRecFlg(dto.insurance_info_header.loan_app_seq, true);
            exMem.forEach(mem -> {
                mem.setCrntRecFlg(false);
                mem.setDelFlg(true);
                mem.setEffEndDt(Instant.now());
                mem.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                mem.setLastUpdDt(Instant.now());
            });
            mwHlthInsrMembRepository.save(exMem);
            if (dto.insurance_members != null) {
                dto.insurance_members.forEach(member -> {
                    MwHlthInsrMemb mem = member.DtoToDomain(formatter, formatterDate);
                    mwHlthInsrMembRepository.save(mem);
                });
            }

        }
    }

    @Transactional
    public void savePsc(List<PSC> dtos, String curUser) {
        if (dtos != null) {
            dtos.forEach(dto -> {
                MwClntPsc exPsc =
                        mwClntPscRepository.findOneByPscSeqAndQstSeqAndAnswrSeqAndLoanAppSeqAndCrntRecFlg(dto.psc_seq, dto.qst_seq, dto.answr_seq, dto.loan_app_seq, true);
                if (exPsc == null) {
                    exPsc = new MwClntPsc();
                }
                exPsc = dto.DtoToDomain(formatter, exPsc);

                mwClntPscRepository.save(exPsc);
            });
        }
    }

    @Transactional
    public void saveSchoolAppraisal(SchoolAppraisalDto dto, String curUser) {
        if (dto.basic_info != null) {
            MwSchAprsl aprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(dto.basic_info.sch_aprsl_seq, true);
            if (aprsl == null) {
                aprsl = new MwSchAprsl();
            }
            aprsl = dto.basic_info.DtoToDomain(formatter, aprsl);
            mwSchAprslRepository.save(aprsl);

        }
        if (dto.grades != null) {
            dto.grades.forEach(grd -> {
                MwSchGrd exGrade = mwSchGrdRepository.findOneBySchGrdSeqAndCrntRecFlg(grd.sch_grd_seq, true);
                if (exGrade == null) {
                    exGrade = new MwSchGrd();
                }
                exGrade = grd.DtoToDomain(formatter, exGrade);
                mwSchGrdRepository.save(exGrade);
            });
        }

        if (dto.school_appraisal_address != null) {
            try {
                saveAddress(dto.school_appraisal_address, curUser);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (dto.attendance != null) {
            MwSchAtnd exAtnd = mwSchAtndRepository.findOneBySchAtndSeqAndCrntRecFlg(dto.attendance.sch_atnd_seq, true);
            if (exAtnd == null) {
                exAtnd = new MwSchAtnd();
            }
            exAtnd = dto.attendance.DtoToDomain(dto.attendance, formatter, exAtnd);
            mwSchAtndRepository.save(exAtnd);
        }

        if (dto.income_list != null) {
            dto.income_list.forEach(in -> {
                MwBizAprslIncmDtl exDtl = mwBizAprslIncmDtlRepository.findOneByIncmDtlSeqAndIncmCtgryKeyAndCrntRecFlg(in.incm_dtl_seq,
                        in.incm_ctgry_key, true);
                if (exDtl == null) {
                    exDtl = new MwBizAprslIncmDtl();
                }
                exDtl = in.DtoToDomain(formatter, exDtl);
                mwBizAprslIncmDtlRepository.save(exDtl);
            });
        }

        if (dto.expense_list != null) {
            dto.expense_list.forEach(ex -> {
                MwBizExpDtl exExpenseDtl = mwBizExpDtlRepository.findOneByExpDtlSeqAndExpCtgryKeyAndMwBizAprslAndExpTypKeyAndCrntRecFlg(ex.exp_dtl_seq,
                        ex.exp_ctgry_key, ex.biz_aprsl_seq, ex.exp_typ_key, true);
                if (exExpenseDtl == null) {
                    exExpenseDtl = new MwBizExpDtl();
                }
                exExpenseDtl = ex.DtoToDomain(formatter, exExpenseDtl);
                mwBizExpDtlRepository.save(exExpenseDtl);
            });
        }

    }

    @Transactional
    public void saveSchoolInformation(SchoolInformationDto dto) {
        if (dto.school_infrastructure != null) {
            MwSchAsts exAsts = mwSchAstsRepository.findOneBySchAstsSeqAndCrntRecFlg(dto.school_infrastructure.sch_asts_seq, true);
            if (exAsts == null) {
                exAsts = new MwSchAsts();
            }
            exAsts = dto.school_infrastructure.DtoToDomain(formatter, exAsts);
            mwSchAstsRepository.save(exAsts);
        }
        if (dto.school_questions != null) {
            dto.school_questions.forEach(ques -> {
                MwSchQltyChk exChk = mwSchQltyChkRepository.findOneBySchQltyChkSeqAndQstSeqAndCrntRecFlg(ques.sch_qlty_chk_seq,
                        ques.qst_seq, true);
                if (exChk == null) {
                    exChk = new MwSchQltyChk();
                }
                // Added by M. Naveed - Dated 08-04-2022 - Duplicate School Qualities
                else {
                    MwSchQltyChk update = mwSchQltyChkRepository.findOneBySchQltyChkSeqAndQstSeqAndAnswrSeqAndCrntRecFlg(ques.sch_qlty_chk_seq,
                            ques.qst_seq, ques.answr_seq, true);
                    if (update == null) {
                        exChk.setLastUpdDt(Instant.now());
                        exChk.setLastUpdBy(ques.last_upd_by);
                        exChk.setCrntRecFlg(false);
                        exChk.setDelFlg(true);
                        mwSchQltyChkRepository.save(exChk);
                    }
                }
                // End
                exChk = ques.DtoToDomain(formatter);
                try {

                    mwSchQltyChkRepository.save(exChk);
                } catch (Exception e) {
                    log.debug("\n\n\n\n\n\n\n\n\n error in school quality check");
                    e.printStackTrace();
                }

            });
        }
    }

    public SyncDto getCompleteDataForTab(List<Long> ports, List<Long> sts) {
        final SyncDto dto = new SyncDto();

        // ------------ MW_CLNT ------------//
        List<MwClnt> clnts = mwClntRepository.findAllByPortKeyInAndStsIn(ports, sts);
        if (clnts != null && clnts.size() > 0) {
            dto.mw_clnt = new ArrayList<>();
            clnts.forEach(clnt -> {
                if (ports.contains(clnt.getPortKey()) && clnt.getFrstNm() != null) {
                    ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
                    clntDto.DomainToDto(clnt);
                    dto.mw_clnt.add(clntDto);
                }
            });
        }

        // ------------- MW_ADDR_REL ----------- //
        List<MwAddrRel> addrRels = getCompleteAddrRels(ports, sts);
        if (addrRels != null && addrRels.size() > 0) {
            addrRels.forEach(rel -> {
                AddrRelDto addressRelDto = new AddrRelDto();
                addressRelDto.DomainToDto(rel);
                if (dto.mw_addr_rel == null)
                    dto.mw_addr_rel = new ArrayList<>();
                dto.mw_addr_rel.add(addressRelDto);
            });
        }

        // ----------- MW_ADDR ------------ //
        List<MwAddr> addrs = getCompleteAddr(ports, sts);
        if (addrs != null && addrs.size() > 0) {
            addrs.forEach(addr -> {
                AddressDto addressDto = new AddressDto();
                addressDto.DomainToDto(addr);
                if (dto.mw_addr == null)
                    dto.mw_addr = new ArrayList<>();
                dto.mw_addr.add(addressDto);
            });
        }
        // ------------ MW_CNIC_TKN --------------//
        List<MwCnicTkn> tkns = mwClntCnicTknRepository.findAllTknForLoanForPorts(ports, sts);
        tkns.forEach(tkn -> {
            ClientCnicToken tdto = new ClientCnicToken();
            tdto.DomainToDto(tkn);
            if (dto.mw_cnic_tkn == null)
                dto.mw_cnic_tkn = new ArrayList<>();
            dto.mw_cnic_tkn.add(tdto);
        });

        // ------------ MW_CLNT_PERM_ADDR --------------//
        List<MwClntPermAddr> perms = mwClntPermAddrRepository.findAllClntPermAddrForClntForPorts(ports, sts);
        if (perms != null && perms.size() > 0) {
            perms.forEach(perm -> {
                ClientPermAddressDto pdto = new ClientPermAddressDto();
                pdto.DomainToDto(perm);
                if (dto.mw_clnt_perm_addr == null)
                    dto.mw_clnt_perm_addr = new ArrayList<>();
                dto.mw_clnt_perm_addr.add(pdto);
            });
        }

        // ---------- MW_LOAN_APP ----------- //
        List<MwLoanApp> loans = mwLoanAppRepository.findAllLoansForPorts(ports, sts);
        List<Long> loanSeqs = new ArrayList<>();
        if (loans != null && loans.size() > 0) {
            loans.forEach(loan -> {
                if (loan.getPrdSeq() != null) {
                    LoanAppDto loanDto = new LoanAppDto();
                    loanDto.DomainToDto(loan);
                    loanDto.verisys_status = loan.getVerisys_status();
                    if (dto.mw_loan_app == null)
                        dto.mw_loan_app = new ArrayList<>();
                    dto.mw_loan_app.add(loanDto);
                    loanSeqs.add(loan.getLoanAppSeq());
                }
            });
        }

        // // ---------- MW_LOAN_APP_CRDT_SCR ----------- //
        List<MwLoanAppCrdtScr> scrs = mwLoanAppCrdtScrRepository.findAllLoansCrdtScrForPorts(ports, sts);
        scrs.forEach(scr -> {
            LoanAppCrdtScrDto sdto = new LoanAppCrdtScrDto();
            sdto.DomainToDto(scr);
            if (dto.mw_loan_app_crdt_scr == null)
                dto.mw_loan_app_crdt_scr = new ArrayList<>();
            dto.mw_loan_app_crdt_scr.add(sdto);
        });
        // ---------- MW_MFCIB_OTH_OUTSD_LOAN --------- //
        List<MwMfcibOthOutsdLoan> otherLoans = mwMfcibOthOutsdLoanRepository.findAllMFCIBForLoanForPorts(ports, sts);
        if (otherLoans != null && otherLoans.size() > 0) {
            otherLoans.forEach(loan -> {
                OtherLoanDto oDto = new OtherLoanDto();
                oDto.DomainToDto(loan);
                if (dto.mw_mfcib_oth_outsd_loan == null)
                    dto.mw_mfcib_oth_outsd_loan = new ArrayList<>();
                dto.mw_mfcib_oth_outsd_loan.add(oDto);
            });
        }

        // // ---------- MW_CLNT_HLTH_INSR ---------- //
        List<MwClntHlthInsr> insrs = mwClntHlthInsrRepository.findAllClntHlthInsrForLoanForPorts(ports, sts);
        if (insrs != null && insrs.size() > 0) {
            insrs.forEach(insr -> {
                InsuranceInfoHeader hdrDto = new InsuranceInfoHeader();
                hdrDto.DomainToDto(insr);
                if (dto.mw_clnt_hlth_insr == null)
                    dto.mw_clnt_hlth_insr = new ArrayList<>();
                dto.mw_clnt_hlth_insr.add(hdrDto);
            });
        }

        // // ----------- MW_HLTH_INSR_MEMB -------- //
        List<MwHlthInsrMemb> members = mwHlthInsrMembRepository.findAllMwHlthInsrMembForLoanForPorts(ports, sts);
        if (members != null && members.size() > 0) {
            members.forEach(member -> {
                InsuranceMember memDto = new InsuranceMember();
                memDto.DomainToDto(member);
                if (dto.mw_hlth_insr_memb == null)
                    dto.mw_hlth_insr_memb = new ArrayList<>();
                dto.mw_hlth_insr_memb.add(memDto);
            });
        }

        // // --------- MW_CLNT_REL --------- //
        List<MwClntRel> rels = mwClntRelRepository.findAllMwClntRelForLoanForPorts(ports, sts);
        if (rels != null && rels.size() > 0) {
            rels.forEach(rel -> {
                ClntRelDto rDto = new ClntRelDto();
                rDto.DomainToDto(rel);
                if (dto.mw_clnt_rel == null)
                    dto.mw_clnt_rel = new ArrayList<>();
                dto.mw_clnt_rel.add(rDto);
            });
        }

        // // ---------- MW_LOAN_UTL_PLAN ---------- //
        List<MwLoanUtlPlan> plans = mwLoanUtlPlanRepository.findAllMwLoanUtlPlanForLoanForPorts(ports, sts);
        if (plans != null && plans.size() > 0) {
            plans.forEach(plan -> {
                ExpextedLoanDto eDto = new ExpextedLoanDto();
                eDto.DomainToDto(plan);
                if (dto.mw_loan_utl_plan == null)
                    dto.mw_loan_utl_plan = new ArrayList<>();
                dto.mw_loan_utl_plan.add(eDto);
            });
        }

        // // --------- MW_BIZ_APRSL -------- //
        List<MwBizAprsl> bizAprsls = mwBizAprslRepository.findAllMwBizAprslForLoanForPorts(ports, sts);
        if (bizAprsls != null && bizAprsls.size() > 0) {
            bizAprsls.forEach(aprsl -> {
                BusinessAppraisalBasicInfo info = new BusinessAppraisalBasicInfo();
                info.DomainToDto(aprsl);
                if (dto.mw_biz_aprsl == null)
                    dto.mw_biz_aprsl = new ArrayList<>();
                dto.mw_biz_aprsl.add(info);
            });
        }

        // // ----------- MW_BIZ_APRSL_INCM_HDR ----------- //
        List<MwBizAprslIncmHdr> hdrs = mwBizAprslIncmHdrRepository.findAllMwBizAprslIncmHdrForLoanForPorts(ports, sts);
        if (hdrs != null && hdrs.size() > 0) {
            hdrs.forEach(hdr -> {
                BizAppIncmHdrDto hDto = new BizAppIncmHdrDto();
                hDto.DomainToDto(hdr);
                if (dto.mw_biz_aprsl_incm_hdr == null)
                    dto.mw_biz_aprsl_incm_hdr = new ArrayList<>();
                dto.mw_biz_aprsl_incm_hdr.add(hDto);
            });
        }
        //
        // // ---------- MW_BIZ_APRSL_INCM_DTL ---------- //
        // ------------ BUSINESS -------------- //
        List<MwBizAprslIncmDtl> dtls = mwBizAprslIncmDtlRepository.findAllMwBizAprslIncmHdrForBusinessForPorts(ports, sts);
        if (dtls != null && dtls.size() > 0) {
            dtls.forEach(dtl -> {
                BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                dDto.DomainToDto(dtl);
                if (dto.mw_biz_aprsl_incm_dtl == null)
                    dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                dto.mw_biz_aprsl_incm_dtl.add(dDto);
            });
        }
        // // ------------ SCHOOL -------------- //
        List<MwBizAprslIncmDtl> Sdtls = mwBizAprslIncmDtlRepository.findAllMwBizAprslIncmHdrForSchoolForPorts(ports, sts);
        if (Sdtls != null && Sdtls.size() > 0) {
            Sdtls.forEach(dtl -> {
                BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                dDto.DomainToDto(dtl);
                if (dto.mw_biz_aprsl_incm_dtl == null)
                    dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                dto.mw_biz_aprsl_incm_dtl.add(dDto);
            });
        }

        // // --------- MW_BIZ_EXP_DTL ---------- //
        // ------------ BUSINESS -------------- //
        List<MwBizExpDtl> exps = mwBizExpDtlRepository.findAllMwBizExpDtlForBusinessForPorts(ports, sts);
        if (exps != null && exps.size() > 0) {
            exps.forEach(ex -> {
                ExpenseDto eDto = new ExpenseDto();
                eDto.DomainToDto(ex);
                if (dto.mw_biz_exp_dtl == null)
                    dto.mw_biz_exp_dtl = new ArrayList<>();
                dto.mw_biz_exp_dtl.add(eDto);
            });
        }
        // // ------------ SCHOOL -------------- //
        exps = mwBizExpDtlRepository.findAllMwBizExpDtlForSchoolForPorts(ports, sts);
        if (exps != null && exps.size() > 0) {
            exps.forEach(ex -> {
                ExpenseDto eDto = new ExpenseDto();
                eDto.DomainToDto(ex);
                if (dto.mw_biz_exp_dtl == null)
                    dto.mw_biz_exp_dtl = new ArrayList<>();
                dto.mw_biz_exp_dtl.add(eDto);
            });
        }

        // --------- MW_CLNT_PSC -------- //
        List<MwClntPsc> pscs = mwClntPscRepository.findAllMwClntPscForLoanForPorts(ports, sts);
        if (pscs != null && pscs.size() > 0) {
            pscs.forEach(psc -> {
                PSC pDto = new PSC();
                pDto.DomainToDto(psc);
                if (dto.mw_clnt_psc == null)
                    dto.mw_clnt_psc = new ArrayList<>();
                dto.mw_clnt_psc.add(pDto);
            });
        }
        //
        // // --------- MW_SCH_APRSL ---------//
        List<MwSchAprsl> schAprsls = mwSchAprslRepository.findAllMwSchAprslForLoanForPorts(ports, sts);
        if (schAprsls != null && schAprsls.size() > 0) {
            schAprsls.forEach(aprsl -> {
                SchoolAppraisalBasicInfoDto sDto = new SchoolAppraisalBasicInfoDto();
                sDto.DomainToDto(aprsl);
                if (dto.mw_sch_aprsl == null)
                    dto.mw_sch_aprsl = new ArrayList<>();
                dto.mw_sch_aprsl.add(sDto);
            });
        }

        // // ---------- MW_SCH_GRD -------- //
        List<MwSchGrd> grds = mwSchGrdRepository.findAllMwSchGrdForLoanForPorts(ports, sts);
        if (grds != null && grds.size() > 0) {
            grds.forEach(grd -> {
                SchoolGradesDto gDto = new SchoolGradesDto();
                gDto.DomainToDto(grd);
                if (dto.mw_sch_grd == null)
                    dto.mw_sch_grd = new ArrayList<>();
                dto.mw_sch_grd.add(gDto);
            });
        }

        // // --------- MW_SCH_ATTND --------- //
        List<MwSchAtnd> atnds = mwSchAtndRepository.findAllMwSchAtndForLoanForPorts(ports, sts);
        if (atnds != null && atnds.size() > 0) {
            atnds.forEach(atnd -> {
                SchoolAttendanceDto gDto = new SchoolAttendanceDto();
                gDto.DomainToDto(atnd);
                if (dto.mw_sch_atnd == null)
                    dto.mw_sch_atnd = new ArrayList<>();
                dto.mw_sch_atnd.add(gDto);
            });
        }

        // // ---------- MW_SCH_ASTS -------- //
        List<MwSchAsts> asts = mwSchAstsRepository.findAllMwSchAstsForLoanForPorts(ports, sts);
        if (asts != null && asts.size() > 0) {
            asts.forEach(ast -> {
                SchoolInfrastructureDto aDto = new SchoolInfrastructureDto();
                aDto.DomainToDto(ast);
                if (dto.mw_sch_asts == null)
                    dto.mw_sch_asts = new ArrayList<>();
                dto.mw_sch_asts.add(aDto);
            });
        }
        // // ---------- MW_SCH_QLTY_CHK -------- //
        List<MwSchQltyChk> chks = mwSchQltyChkRepository.findAllMwSchQltyChkForLoanForPorts(ports, sts);
        if (chks != null && chks.size() > 0) {
            chks.forEach(chk -> {
                SchoolQuestionDto sDto = new SchoolQuestionDto();
                sDto.DomainToDto(chk);
                if (dto.mw_sch_qlty_chk == null)
                    dto.mw_sch_qlty_chk = new ArrayList<>();
                dto.mw_sch_qlty_chk.add(sDto);
            });
        }

        List<MwRefCdVal> vals = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> docsts = new ArrayList<>();
        vals.forEach(val -> {
            if (val.getRefCd().equals("0002") || val.getRefCd().equals("0003") || val.getRefCd().equals("0004")
                    || val.getRefCd().equals("1305")) {
                docsts.add(val.getRefCdSeq());
            }
        });
        // ---------- MW_LOAN_APP_DOC -------- //
        List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findAllMwLoanAppDocForLoanForPorts(ports, docsts);
        if (docs != null && docs.size() > 0) {
            docs.forEach(doc -> {
                DocDto dDto = new DocDto();
                dDto.DomainToDto(doc);
                if (dto.mw_loan_app_doc == null)
                    dto.mw_loan_app_doc = new ArrayList<>();
                dto.mw_loan_app_doc.add(dDto);
            });
        }

        // --------- MW_AMNL_RGSTR -------//
        List<Object[]> anmls = mwAnmlRgstrRepository.findAllMwAnmlRgstrForLoanForPortsWithoutPics(ports, sts);
        anmls.forEach(anml -> {
            AnmlRgstrDto adto = new AnmlRgstrDto();
            adto.DomainToDtoObj(anml);
            if (dto.mw_anml_rgstr == null)
                dto.mw_anml_rgstr = new ArrayList<>();
            dto.mw_anml_rgstr.add(adto);
        });

        // --------- MW_BIZ_APRSL_EXTNG_LVSTK -------//
        List<MwBizAprslExtngLvstk> lvs = mwBizAprslExtngLvstkRepository.findAllMwBizAprslExtngLvstkForLoanForPorts(ports, sts);
        lvs.forEach(lv -> {
            BizAprslExtngLvstkDto ldto = new BizAprslExtngLvstkDto();
            ldto.DomainToDto(lv);
            if (dto.mw_biz_aprsl_extng_lvstk == null)
                dto.mw_biz_aprsl_extng_lvstk = new ArrayList<>();
            dto.mw_biz_aprsl_extng_lvstk.add(ldto);
        });
        // --------- MW_BIZ_APRSL_EST_LVSTK_FIN -------//
        List<MwBizAprslEstLvstkFin> fins = mwBizAprslEstLvstkFinRepository.findAllMwBizAprslEstLvstkFinForLoanForPorts(ports, sts);
        fins.forEach(fin -> {
            BizAprslEstLvstkFinDto fdto = new BizAprslEstLvstkFinDto();
            fdto.DomainToDto(fin);
            if (dto.mw_biz_aprsl_est_lvstk_fin == null)
                dto.mw_biz_aprsl_est_lvstk_fin = new ArrayList<>();
            dto.mw_biz_aprsl_est_lvstk_fin.add(fdto);
        });

        // --------- MW_LOAN_APP_MNTRNG_CHKS -------//
        List<MwLoanAppMntrngChks> mchks = mwLoanAppMntrngChksRepository.findAllMwLoanAppMntrngChksForLoanForPorts(ports, sts);
        mchks.forEach(chk -> {
            LoanAppMntrngChks d = new LoanAppMntrngChks();
            d.DomainToDto(chk);
            if (dto.mw_loan_app_mntrng_chks == null)
                dto.mw_loan_app_mntrng_chks = new ArrayList<>();
            dto.mw_loan_app_mntrng_chks.add(d);
        });

        // ---------- MW_CNIC_UPD ----------//
        List<MwCnicUpd> cnicUpds = mwCnicUpdRepository.findAllMwCnicUpdForLoanForPorts(ports);
        cnicUpds.forEach(upd -> {
            CnicUpdDTO cnicUpdDto = new CnicUpdDTO();
            cnicUpdDto.DomainToDto(upd);
            if (dto.mw_cnic_upd == null)
                dto.mw_cnic_upd = new ArrayList<>();
            dto.mw_cnic_upd.add(cnicUpdDto);
        });
        return dto;
    }

    public List<MwAddrRel> getCompleteAddrRels(List<Long> ports, List<Long> sts) {
        List<MwAddrRel> rels = new ArrayList<>();
        // ------------- MW_CLNT_ADDR_REL ----------- //
        List<MwAddrRel> clntAddrRels = mwAddrRelRepository.findAllAddressRelsForClientForPorts(ports, sts);
        if (clntAddrRels != null && clntAddrRels.size() > 0) {
            rels.addAll(clntAddrRels);
        }
        // ------------- MW_CLNT_REL_ADDR_REL ----------- //
        List<MwAddrRel> clntRelsAddrRels = mwAddrRelRepository.findAllAddressRelsForClientRelsForPorts(ports, sts);
        if (clntRelsAddrRels != null && clntRelsAddrRels.size() > 0) {
            rels.addAll(clntRelsAddrRels);
        }
        // ------------- MW_BUSINESS_ADDR_REL ----------- //
        List<MwAddrRel> businessAddrRels = mwAddrRelRepository.findAllAddressRelsForBusinessForPorts(ports, sts);
        if (businessAddrRels != null && businessAddrRels.size() > 0) {
            rels.addAll(businessAddrRels);
        }
        // ------------- MW_SCHOOL_ADDR_REL ----------- //
        List<MwAddrRel> schoolAddrRels = mwAddrRelRepository.findAllAddressRelsForSchoolForPorts(ports, sts);
        if (schoolAddrRels != null && schoolAddrRels.size() > 0) {
            rels.addAll(schoolAddrRels);
        }
        return rels;
    }

    public List<MwAddrRel> getUpdatedAddrRels(List<Long> ports, Instant syncDate, String user, List<Long> sts) {
        List<MwAddrRel> rels = new ArrayList<>();
        // ------------- MW_CLNT_ADDR_REL ----------- //
        List<MwAddrRel> clntAddrRels = mwAddrRelRepository.findUpdatedAddressRelsForClientForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        if (clntAddrRels != null && clntAddrRels.size() > 0) {
            rels.addAll(clntAddrRels);
        }
        // ------------- MW_CLNT_REL_ADDR_REL ----------- //
        List<MwAddrRel> clntRelsAddrRels = mwAddrRelRepository.findUpdatedAddressRelsForClientRelsForPortsLastUpdatedbyNot(syncDate,
                ports, user, sts);
        if (clntRelsAddrRels != null && clntRelsAddrRels.size() > 0) {
            rels.addAll(clntRelsAddrRels);
        }
        // ------------- MW_BUSINESS_ADDR_REL ----------- //
        List<MwAddrRel> businessAddrRels = mwAddrRelRepository.findUpdatedAddressRelsForBusinessForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (businessAddrRels != null && businessAddrRels.size() > 0) {
            rels.addAll(businessAddrRels);
        }
        // ------------- MW_SCHOOL_ADDR_REL ----------- //
        List<MwAddrRel> schoolAddrRels = mwAddrRelRepository.findUpdatedAddressRelsForSchoolForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (schoolAddrRels != null && schoolAddrRels.size() > 0) {
            rels.addAll(schoolAddrRels);
        }
        return rels;
    }

    public List<MwAddr> getCompleteAddr(List<Long> ports, List<Long> sts) {
        List<MwAddr> addrs = new ArrayList<>();

        // ------------- MW_CLNT_ADDR -------------- //
        List<MwAddr> clntAddr = mwAddrRepository.findAllAddrForClientForPorts(ports, sts);
        if (clntAddr != null && clntAddr.size() > 0) {
            addrs.addAll(clntAddr);
        }

        // ------------- MW_CLNT_REL_ADDR -------------- //
        List<MwAddr> clntRelAddrs = mwAddrRepository.findAllAddrForClientRelForPorts(ports, sts);
        if (clntRelAddrs != null && clntRelAddrs.size() > 0) {
            addrs.addAll(clntRelAddrs);
        }

        // ------------- MW_BUSINESS_ADDR -------------- //
        List<MwAddr> businessAddrs = mwAddrRepository.findAllAddrForBusinessForPorts(ports, sts);
        if (businessAddrs != null && businessAddrs.size() > 0) {
            addrs.addAll(businessAddrs);
        }

        // ------------- MW_SCHOOL_ADDR -------------- //
        List<MwAddr> schoolAddrs = mwAddrRepository.findAllAddrForSchoolForPorts(ports, sts);
        if (schoolAddrs != null && schoolAddrs.size() > 0) {
            addrs.addAll(schoolAddrs);
        }
        return addrs;
    }

    public List<MwAddr> getUpdatedAddrs(List<Long> ports, Instant syncDate, String user, List<Long> sts) {
        List<MwAddr> addrs = new ArrayList<>();

        // ------------- MW_CLNT_ADDR -------------- //
        List<MwAddr> clntAddr = mwAddrRepository.findUpdatedAddrForClientForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (clntAddr != null && clntAddr.size() > 0) {
            addrs.addAll(clntAddr);
        }

        // ------------- MW_CLNT_REL_ADDR -------------- //
        List<MwAddr> clntRelAddrs = mwAddrRepository.findUpdatedAddrForClientRelForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (clntRelAddrs != null && clntRelAddrs.size() > 0) {
            addrs.addAll(clntRelAddrs);
        }

        // ------------- MW_BUSINESS_ADDR -------------- //
        List<MwAddr> businessAddrs = mwAddrRepository.findUpdatedAddrForBusinessForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (businessAddrs != null && businessAddrs.size() > 0) {
            addrs.addAll(businessAddrs);
        }

        // ------------- MW_SCHOOL_ADDR -------------- //
        List<MwAddr> schoolAddrs = mwAddrRepository.findUpdatedAddrForSchoolForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (schoolAddrs != null && schoolAddrs.size() > 0) {
            addrs.addAll(schoolAddrs);
        }

        return addrs;
    }

    public SyncDto traverseDataForTab(List<Long> ports, Instant syncDate, String user, List<Long> sts) {
        final SyncDto dto = new SyncDto();

        // ------------ MW_CLNT ------------//
        List<MwClnt> clnts = mwClntRepository
                .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortKeyInOrderByEffStartDt(syncDate, user, true, ports);
        // List< MwClnt > deleteTransferClnts = mwClntRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseAndPortKeyIn( syncDate,
        // "ex-transfer", false, ports );
        // deleteTransferClnts.forEach( clnt -> {
        // ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
        // clntDto.DomainToDto( clnt );
        // if ( dto.mw_clnt == null )
        // dto.mw_clnt = new ArrayList<>();
        // dto.mw_clnt.add( clntDto );
        // } );
        if (clnts != null && clnts.size() > 0) {
            dto.mw_clnt = new ArrayList<>();
            clnts.forEach(clnt -> {
                if (ports.contains(clnt.getPortKey()) && clnt.getFrstNm() != null) {
                    ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
                    clntDto.DomainToDto(clnt);
                    if (dto.mw_clnt == null)
                        dto.mw_clnt = new ArrayList<>();
                    dto.mw_clnt.add(clntDto);
                }
            });
        }

        // ------------- MW_ADDR_REL ----------- //
        List<MwAddrRel> addrRels = getUpdatedAddrRels(ports, syncDate, user, sts);
        if (addrRels != null && addrRels.size() > 0) {
            addrRels.forEach(rel -> {
                AddrRelDto addressRelDto = new AddrRelDto();
                addressRelDto.DomainToDto(rel);
                if (dto.mw_addr_rel == null)
                    dto.mw_addr_rel = new ArrayList<>();
                dto.mw_addr_rel.add(addressRelDto);
            });
        }

        // ----------- MW_ADDR ------------ //
        List<MwAddr> addrs = getUpdatedAddrs(ports, syncDate, user, sts);
        if (addrs != null && addrs.size() > 0) {
            addrs.forEach(addr -> {
                AddressDto addressDto = new AddressDto();
                addressDto.DomainToDto(addr);
                if (dto.mw_addr == null)
                    dto.mw_addr = new ArrayList<>();
                dto.mw_addr.add(addressDto);
            });
        }
        // ------------ MW_CNIC_TKN --------------//
        List<MwCnicTkn> tkns = mwClntCnicTknRepository.findUpdatedTknForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        tkns.forEach(tkn -> {
            ClientCnicToken tdto = new ClientCnicToken();
            tdto.DomainToDto(tkn);
            if (dto.mw_cnic_tkn == null)
                dto.mw_cnic_tkn = new ArrayList<>();
            dto.mw_cnic_tkn.add(tdto);
        });

        // ------------ MW_CLNT_PERM_ADDR --------------//
        List<MwClntPermAddr> perms = mwClntPermAddrRepository.findUpdatedClntPermAddrForClntForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (perms != null && perms.size() > 0) {
            perms.forEach(perm -> {
                ClientPermAddressDto pdto = new ClientPermAddressDto();
                pdto.DomainToDto(perm);
                if (dto.mw_clnt_perm_addr == null)
                    dto.mw_clnt_perm_addr = new ArrayList<>();
                dto.mw_clnt_perm_addr.add(pdto);
            });
        }
        // List< MwLoanApp > deleteTransferLoans = mwLoanAppRepository.findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseAndPortSeqIn( syncDate,
        // "ex-transfer", false, ports );
        //
        // deleteTransferLoans.forEach( loan -> {
        // if ( loan.getPrdSeq() != null ) {
        // LoanAppDto loanDto = new LoanAppDto();
        // loanDto.DomainToDto( loan );
        // if ( dto.mw_loan_app == null )
        // dto.mw_loan_app = new ArrayList<>();
        // dto.mw_loan_app.add( loanDto );
        // }
        // } );
        List<MwLoanApp> transferLoans = mwLoanAppRepository
                .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseAndCrntRecFlgAndPortSeqIn(syncDate, "transfer", true, ports);
        List<Long> loanSeqs = new ArrayList<>();

        transferLoans.forEach(loan -> {
            if (loan.getPrdSeq() != null) {
                LoanAppDto loanDto = new LoanAppDto();
                loanDto.DomainToDto(loan);
                if (dto.mw_loan_app == null)
                    dto.mw_loan_app = new ArrayList<>();
                dto.mw_loan_app.add(loanDto);
                //
                // // ----- MW_CLNT ----- //
                // MwClnt clnt = mwClntRepository.findOneByClntSeq( loan.getLoanAppSeq(), true );
                // if ( clnt != null ) {
                // ClntBasicInfoDto clntDto = new ClntBasicInfoDto();
                // clntDto.DomainToDto( clnt );
                // if ( dto.mw_clnt == null )
                // dto.mw_clnt = new ArrayList<>();
                // dto.mw_clnt.add( clntDto );
                // }
                // ------------- MW_ADDR_REL ----------- //
                MwAddrRel addrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(loan.getClntSeq(), "Client", true);
                if (addrRel != null) {
                    AddrRelDto addressRelDto = new AddrRelDto();
                    addressRelDto.DomainToDto(addrRel);
                    if (dto.mw_addr_rel == null)
                        dto.mw_addr_rel = new ArrayList<>();
                    dto.mw_addr_rel.add(addressRelDto);
                    // ----------- MW_ADDR ------------ //
                    MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addrRel.getAddrSeq(), true);
                    if (addr != null) {
                        AddressDto addressDto = new AddressDto();
                        addressDto.DomainToDto(addr);
                        if (dto.mw_addr == null)
                            dto.mw_addr = new ArrayList<>();
                        dto.mw_addr.add(addressDto);
                    }
                }

                // ------------ MW_CNIC_TKN --------------//
                MwCnicTkn tkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (tkn != null) {
                    ClientCnicToken tdto = new ClientCnicToken();
                    tdto.DomainToDto(tkn);
                    if (dto.mw_cnic_tkn == null)
                        dto.mw_cnic_tkn = new ArrayList<>();
                    dto.mw_cnic_tkn.add(tdto);
                }

                // ------------ MW_CLNT_PERM_ADDR --------------//
                MwClntPermAddr perm = mwClntPermAddrRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
                if (perm != null) {
                    ClientPermAddressDto pdto = new ClientPermAddressDto();
                    pdto.DomainToDto(perm);
                    if (dto.mw_clnt_perm_addr == null)
                        dto.mw_clnt_perm_addr = new ArrayList<>();
                    dto.mw_clnt_perm_addr.add(pdto);
                }

                // ---------- MW_LOAN_APP_CRDT_SCR ----------- //
                MwLoanAppCrdtScr scr = mwLoanAppCrdtScrRepository.findOneByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (scr != null) {
                    LoanAppCrdtScrDto sdto = new LoanAppCrdtScrDto();
                    sdto.DomainToDto(scr);
                    if (dto.mw_loan_app_crdt_scr == null)
                        dto.mw_loan_app_crdt_scr = new ArrayList<>();
                    dto.mw_loan_app_crdt_scr.add(sdto);
                }
                // ---------- MW_MFCIB_OTH_OUTSD_LOAN --------- //
                List<MwMfcibOthOutsdLoan> otherLoans = mwMfcibOthOutsdLoanRepository
                        .findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (otherLoans != null && otherLoans.size() > 0) {
                    otherLoans.forEach(oloan -> {
                        OtherLoanDto oDto = new OtherLoanDto();
                        oDto.DomainToDto(oloan);
                        if (dto.mw_mfcib_oth_outsd_loan == null)
                            dto.mw_mfcib_oth_outsd_loan = new ArrayList<>();
                        dto.mw_mfcib_oth_outsd_loan.add(oDto);
                    });
                }

                // // ---------- MW_CLNT_HLTH_INSR ---------- //
                MwClntHlthInsr insr = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (insr != null) {
                    InsuranceInfoHeader hdrDto = new InsuranceInfoHeader();
                    hdrDto.DomainToDto(insr);
                    if (dto.mw_clnt_hlth_insr == null)
                        dto.mw_clnt_hlth_insr = new ArrayList<>();
                    dto.mw_clnt_hlth_insr.add(hdrDto);
                }

                // // ----------- MW_HLTH_INSR_MEMB -------- //
                List<MwHlthInsrMemb> members = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (members != null && members.size() > 0) {
                    members.forEach(member -> {
                        InsuranceMember memDto = new InsuranceMember();
                        memDto.DomainToDto(member);
                        if (dto.mw_hlth_insr_memb == null)
                            dto.mw_hlth_insr_memb = new ArrayList<>();
                        dto.mw_hlth_insr_memb.add(memDto);
                    });
                }
                // // --------- MW_CLNT_REL --------- //
                List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (rels != null && rels.size() > 0) {
                    rels.forEach(rel -> {
                        ClntRelDto rDto = new ClntRelDto();
                        rDto.DomainToDto(rel);
                        if (dto.mw_clnt_rel == null)
                            dto.mw_clnt_rel = new ArrayList<>();
                        dto.mw_clnt_rel.add(rDto);
                        String entyTyp = "";
                        if (rel.getRelTypFlg().longValue() == 3)
                            entyTyp = Common.cobAddress;
                        if (rel.getRelTypFlg().longValue() == 4)
                            entyTyp = Common.relAddress;
                        if (rel.getRelTypFlg().longValue() == 3 || rel.getRelTypFlg().longValue() == 4) {
                            // ------------- MW_ADDR_REL ----------- //
                            MwAddrRel cAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(loan.getClntSeq(), entyTyp,
                                    true);
                            if (cAddrRel != null) {
                                AddrRelDto addressRelDto = new AddrRelDto();
                                addressRelDto.DomainToDto(cAddrRel);
                                if (dto.mw_addr_rel == null)
                                    dto.mw_addr_rel = new ArrayList<>();
                                dto.mw_addr_rel.add(addressRelDto);
                                // ----------- MW_ADDR ------------ //
                                MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addrRel.getAddrSeq(), true);
                                if (addr != null) {
                                    AddressDto addressDto = new AddressDto();
                                    addressDto.DomainToDto(addr);
                                    if (dto.mw_addr == null)
                                        dto.mw_addr = new ArrayList<>();
                                    dto.mw_addr.add(addressDto);
                                }
                            }
                        }
                    });
                }

                // // ---------- MW_LOAN_UTL_PLAN ---------- //
                List<MwLoanUtlPlan> plans = mwLoanUtlPlanRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (plans != null && plans.size() > 0) {
                    plans.forEach(plan -> {
                        ExpextedLoanDto eDto = new ExpextedLoanDto();
                        eDto.DomainToDto(plan);
                        if (dto.mw_loan_utl_plan == null)
                            dto.mw_loan_utl_plan = new ArrayList<>();
                        dto.mw_loan_utl_plan.add(eDto);
                    });
                }

                // // --------- MW_BIZ_APRSL -------- //
                MwBizAprsl aprsl = mwBizAprslRepository.findOneByMwLoanAppAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (aprsl != null) {
                    BusinessAppraisalBasicInfo info = new BusinessAppraisalBasicInfo();
                    info.DomainToDto(aprsl);
                    if (dto.mw_biz_aprsl == null)
                        dto.mw_biz_aprsl = new ArrayList<>();
                    dto.mw_biz_aprsl.add(info);

                    MwAddrRel cAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(loan.getClntSeq(),
                            Common.businessAddress, true);
                    if (cAddrRel != null) {
                        AddrRelDto addressRelDto = new AddrRelDto();
                        addressRelDto.DomainToDto(cAddrRel);
                        if (dto.mw_addr_rel == null)
                            dto.mw_addr_rel = new ArrayList<>();
                        dto.mw_addr_rel.add(addressRelDto);
                        // ----------- MW_ADDR ------------ //
                        MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addrRel.getAddrSeq(), true);
                        if (addr != null) {
                            AddressDto addressDto = new AddressDto();
                            addressDto.DomainToDto(addr);
                            if (dto.mw_addr == null)
                                dto.mw_addr = new ArrayList<>();
                            dto.mw_addr.add(addressDto);
                        }
                    }

                    // // ----------- MW_BIZ_APRSL_INCM_HDR ----------- //
                    MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByMwBizAprslAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
                    if (hdr != null) {
                        BizAppIncmHdrDto hDto = new BizAppIncmHdrDto();
                        hDto.DomainToDto(hdr);
                        if (dto.mw_biz_aprsl_incm_hdr == null)
                            dto.mw_biz_aprsl_incm_hdr = new ArrayList<>();
                        dto.mw_biz_aprsl_incm_hdr.add(hDto);

                        //
                        // // ---------- MW_BIZ_APRSL_INCM_DTL ---------- //
                        // ------------ BUSINESS -------------- //
                        List<MwBizAprslIncmDtl> dtls = mwBizAprslIncmDtlRepository
                                .findAllByMwBizAprslIncmHdrAndCrntRecFlg(hdr.getIncmHdrSeq(), true);
                        if (dtls != null && dtls.size() > 0) {
                            dtls.forEach(dtl -> {
                                BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                                dDto.DomainToDto(dtl);
                                if (dto.mw_biz_aprsl_incm_dtl == null)
                                    dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                                dto.mw_biz_aprsl_incm_dtl.add(dDto);
                            });
                        }
                    }
                    // // --------- MW_BIZ_EXP_DTL ---------- //
                    // ------------ BUSINESS -------------- //
                    List<MwBizExpDtl> exps = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
                    if (exps != null && exps.size() > 0) {
                        exps.forEach(ex -> {
                            ExpenseDto eDto = new ExpenseDto();
                            eDto.DomainToDto(ex);
                            if (dto.mw_biz_exp_dtl == null)
                                dto.mw_biz_exp_dtl = new ArrayList<>();
                            dto.mw_biz_exp_dtl.add(eDto);
                        });
                    }

                    // --------- MW_BIZ_APRSL_EXTNG_LVSTK -------//
                    List<MwBizAprslExtngLvstk> lvs = mwBizAprslExtngLvstkRepository
                            .findAllByBizAprslSeqAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
                    for (MwBizAprslExtngLvstk lv : lvs) {
                        BizAprslExtngLvstkDto ldto = new BizAprslExtngLvstkDto();
                        ldto.DomainToDto(lv);
                        if (dto.mw_biz_aprsl_extng_lvstk == null)
                            dto.mw_biz_aprsl_extng_lvstk = new ArrayList<>();
                        dto.mw_biz_aprsl_extng_lvstk.add(ldto);
                    }
                    // --------- MW_BIZ_APRSL_EST_LVSTK_FIN -------//
                    List<MwBizAprslEstLvstkFin> fins = mwBizAprslEstLvstkFinRepository
                            .findAllByBizAprslSeqAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
                    for (MwBizAprslEstLvstkFin fin : fins) {
                        BizAprslEstLvstkFinDto fdto = new BizAprslEstLvstkFinDto();
                        fdto.DomainToDto(fin);
                        if (dto.mw_biz_aprsl_est_lvstk_fin == null)
                            dto.mw_biz_aprsl_est_lvstk_fin = new ArrayList<>();
                        dto.mw_biz_aprsl_est_lvstk_fin.add(fdto);
                    }
                }

                // // --------- MW_SCH_APRSL ---------//
                MwSchAprsl schAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);

                if (schAprsl != null) {
                    SchoolAppraisalBasicInfoDto sDto = new SchoolAppraisalBasicInfoDto();
                    sDto.DomainToDto(schAprsl);
                    if (dto.mw_sch_aprsl == null)
                        dto.mw_sch_aprsl = new ArrayList<>();
                    dto.mw_sch_aprsl.add(sDto);

                    MwAddrRel cAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(loan.getClntSeq(),
                            Common.schApAddress, true);
                    if (cAddrRel != null) {
                        AddrRelDto addressRelDto = new AddrRelDto();
                        addressRelDto.DomainToDto(cAddrRel);
                        if (dto.mw_addr_rel == null)
                            dto.mw_addr_rel = new ArrayList<>();
                        dto.mw_addr_rel.add(addressRelDto);
                        // ----------- MW_ADDR ------------ //
                        MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addrRel.getAddrSeq(), true);
                        if (addr != null) {
                            AddressDto addressDto = new AddressDto();
                            addressDto.DomainToDto(addr);
                            if (dto.mw_addr == null)
                                dto.mw_addr = new ArrayList<>();
                            dto.mw_addr.add(addressDto);
                        }
                    }
                    // ------------ SCHOOL -------------- //
                    List<MwBizAprslIncmDtl> Sdtls = mwBizAprslIncmDtlRepository
                            .findAllByMwBizAprslIncmHdrAndCrntRecFlg(schAprsl.getSchAprslSeq(), true);
                    if (Sdtls != null && Sdtls.size() > 0) {
                        Sdtls.forEach(dtl -> {
                            BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                            dDto.DomainToDto(dtl);
                            if (dto.mw_biz_aprsl_incm_dtl == null)
                                dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                            dto.mw_biz_aprsl_incm_dtl.add(dDto);
                        });
                    }
                    // ------------ SCHOOL -------------- //
                    List<MwBizExpDtl> exps = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlg(schAprsl.getSchAprslSeq(), true);
                    if (exps != null && exps.size() > 0) {
                        exps.forEach(ex -> {
                            ExpenseDto eDto = new ExpenseDto();
                            eDto.DomainToDto(ex);
                            if (dto.mw_biz_exp_dtl == null)
                                dto.mw_biz_exp_dtl = new ArrayList<>();
                            dto.mw_biz_exp_dtl.add(eDto);
                        });
                    }

                    // // ---------- MW_SCH_GRD -------- //
                    List<MwSchGrd> grds = mwSchGrdRepository.findAllBySchGrdSeqAndCrntRecFlg(schAprsl.getSchAprslSeq(), true);
                    if (grds != null && grds.size() > 0) {
                        grds.forEach(grd -> {
                            SchoolGradesDto gDto = new SchoolGradesDto();
                            gDto.DomainToDto(grd);
                            if (dto.mw_sch_grd == null)
                                dto.mw_sch_grd = new ArrayList<>();
                            dto.mw_sch_grd.add(gDto);
                        });
                    }

                    // // --------- MW_SCH_ATTND --------- //
                    MwSchAtnd atnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(schAprsl.getSchAprslSeq(), true);
                    if (atnd != null) {
                        SchoolAttendanceDto gDto = new SchoolAttendanceDto();
                        gDto.DomainToDto(atnd);
                        if (dto.mw_sch_atnd == null)
                            dto.mw_sch_atnd = new ArrayList<>();
                        dto.mw_sch_atnd.add(gDto);
                    }
                    // // ---------- MW_SCH_QLTY_CHK -------- //
                    List<MwSchQltyChk> chks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(schAprsl.getSchAprslSeq(), true);
                    if (chks != null && chks.size() > 0) {
                        chks.forEach(chk -> {
                            SchoolQuestionDto ssDto = new SchoolQuestionDto();
                            ssDto.DomainToDto(chk);
                            if (dto.mw_sch_qlty_chk == null)
                                dto.mw_sch_qlty_chk = new ArrayList<>();
                            dto.mw_sch_qlty_chk.add(ssDto);
                        });
                    }

                }
                // // ---------- MW_SCH_ASTS -------- //
                MwSchAsts ast = mwSchAstsRepository.findOneBySchAstsSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (ast != null) {
                    SchoolInfrastructureDto aDto = new SchoolInfrastructureDto();
                    aDto.DomainToDto(ast);
                    if (dto.mw_sch_asts == null)
                        dto.mw_sch_asts = new ArrayList<>();
                    dto.mw_sch_asts.add(aDto);
                }
                // --------- MW_CLNT_PSC -------- //
                List<MwClntPsc> pscs = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (pscs != null && pscs.size() > 0) {
                    pscs.forEach(psc -> {
                        PSC pDto = new PSC();
                        pDto.DomainToDto(psc);
                        if (dto.mw_clnt_psc == null)
                            dto.mw_clnt_psc = new ArrayList<>();
                        dto.mw_clnt_psc.add(pDto);
                    });
                }

                // ---------- MW_LOAN_APP_DOC -------- //
                List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                if (docs != null && docs.size() > 0) {
                    docs.forEach(doc -> {
                        DocDto dDto = new DocDto();
                        dDto.DomainToDto(doc);
                        if (dto.mw_loan_app_doc == null)
                            dto.mw_loan_app_doc = new ArrayList<>();
                        dto.mw_loan_app_doc.add(dDto);
                    });
                }

                // --------- MW_AMNL_RGSTR -------//
                List<MwAnmlRgstr> anmls = mwAnmlRgstrRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(), true);
                anmls.forEach(anml -> {
                    AnmlRgstrDto adto = new AnmlRgstrDto();
                    adto.DomainToDto(anml);
                    if (dto.mw_anml_rgstr == null)
                        dto.mw_anml_rgstr = new ArrayList<>();
                    dto.mw_anml_rgstr.add(adto);
                });

                // --------- MW_LOAN_APP_MNTRNG_CHKS -------//
                List<MwLoanAppMntrngChks> mchks = mwLoanAppMntrngChksRepository.findAllByLoanAppSeqAndCrntRecFlg(loan.getLoanAppSeq(),
                        true);
                mchks.forEach(chk -> {
                    LoanAppMntrngChks d = new LoanAppMntrngChks();
                    d.DomainToDto(chk);
                    if (dto.mw_loan_app_mntrng_chks == null)
                        dto.mw_loan_app_mntrng_chks = new ArrayList<>();
                    dto.mw_loan_app_mntrng_chks.add(d);
                });

            }
        });

        // ---------- DISCARDED/Deffered APPS discardedSts
        List<MwLoanApp> disloans = mwLoanAppRepository
                .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortSeqInAndLoanAppStsInOrderByEffStartDt(syncDate, user,
                        false, ports, Arrays.asList(discardedSts, defferedSts, rejectedSts));

        if (disloans != null && disloans.size() > 0) {
            disloans.forEach(loan -> {
                if (loan.getPrdSeq() != null) {
                    LoanAppDto loanDto = new LoanAppDto();
                    loanDto.DomainToDto(loan);
                    if (dto.mw_loan_app == null)
                        dto.mw_loan_app = new ArrayList<>();
                    dto.mw_loan_app.add(loanDto);
                    loanSeqs.add(loan.getLoanAppSeq());
                }
            });
        }
        // ---------- MW_LOAN_APP ----------- //
        List<Long> newList = new ArrayList<Long>(sts);
        newList.add(complSts);
        List<MwLoanApp> loans = mwLoanAppRepository
                .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortSeqInAndLoanAppStsInOrderByEffStartDt(syncDate, user,
                        true, ports, newList);
        if (loans != null && loans.size() > 0) {
            loans.forEach(loan -> {
                if (loan.getPrdSeq() != null) {
                    LoanAppDto loanDto = new LoanAppDto();
                    loanDto.DomainToDto(loan);

                    Query qry = em.createNativeQuery(Queries.verisysStatus).setParameter("pLoanAppSeq", loan.getLoanAppSeq())
                            .setParameter("p_function", "T");
                    String versts = qry.getSingleResult().toString();
                    loanDto.verisys_status = versts;
                    if (dto.mw_loan_app == null)
                        dto.mw_loan_app = new ArrayList<>();
                    dto.mw_loan_app.add(loanDto);
                    loanSeqs.add(loan.getLoanAppSeq());
                }
            });
        }

        List<MwRefCdVal> svals = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true,
                true);
        List<Long> defAndRejsts = new ArrayList<>();
        svals.forEach(val -> {
            if (val.getRefCd().equals("0007") || val.getRefCd().equals("1285")) {
                defAndRejsts.add(val.getRefCdSeq());
            }
        });

        // List< MwLoanApp > defRejloans = mwLoanAppRepository
        // .findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortSeqInAndLoanAppStsInOrderByEffStartDt( syncDate, user,
        // false, ports, defAndRejsts );
        // if ( loans != null && loans.size() > 0 ) {
        // defRejloans.forEach( loan -> {
        // if ( loan.getPrdSeq() != null ) {
        // LoanAppDto loanDto = new LoanAppDto();
        // loanDto.DomainToDto( loan );
        // if ( dto.mw_loan_app == null )
        // dto.mw_loan_app = new ArrayList<>();
        // dto.mw_loan_app.add( loanDto );
        // }
        // } );
        // }
        // ---------- MW_LOAN_APP_CRDT_SCR ----------- //
        List<MwLoanAppCrdtScr> scrs = mwLoanAppCrdtScrRepository.findUpdatedLoansCrdtScrForPorts(ports, syncDate, user, sts);
        scrs.forEach(scr -> {
            LoanAppCrdtScrDto sdto = new LoanAppCrdtScrDto();
            sdto.DomainToDto(scr);
            if (dto.mw_loan_app_crdt_scr == null)
                dto.mw_loan_app_crdt_scr = new ArrayList<>();
            dto.mw_loan_app_crdt_scr.add(sdto);
        });
        // ---------- MW_MFCIB_OTH_OUTSD_LOAN --------- //
        List<MwMfcibOthOutsdLoan> otherLoans = mwMfcibOthOutsdLoanRepository.findUpdatedMFCIBForLoanForPortsLastUpdatedbyNot(ports,
                syncDate, user, sts);
        if (otherLoans != null && otherLoans.size() > 0) {
            otherLoans.forEach(loan -> {
                OtherLoanDto oDto = new OtherLoanDto();
                oDto.DomainToDto(loan);
                if (dto.mw_mfcib_oth_outsd_loan == null)
                    dto.mw_mfcib_oth_outsd_loan = new ArrayList<>();
                dto.mw_mfcib_oth_outsd_loan.add(oDto);
            });
        }

        // // ---------- MW_CLNT_HLTH_INSR ---------- //
        List<MwClntHlthInsr> insrs = mwClntHlthInsrRepository.findUpdatedClntHlthInsrForClntForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (insrs != null && insrs.size() > 0) {
            insrs.forEach(insr -> {
                InsuranceInfoHeader hdrDto = new InsuranceInfoHeader();
                hdrDto.DomainToDto(insr);
                if (dto.mw_clnt_hlth_insr == null)
                    dto.mw_clnt_hlth_insr = new ArrayList<>();
                dto.mw_clnt_hlth_insr.add(hdrDto);
            });
        }

        // // ----------- MW_HLTH_INSR_MEMB -------- //
        List<MwHlthInsrMemb> members = mwHlthInsrMembRepository.findUpdatedMwHlthInsrMembForClntForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (members != null && members.size() > 0) {
            members.forEach(member -> {
                InsuranceMember memDto = new InsuranceMember();
                memDto.DomainToDto(member);
                if (dto.mw_hlth_insr_memb == null)
                    dto.mw_hlth_insr_memb = new ArrayList<>();
                dto.mw_hlth_insr_memb.add(memDto);
            });
        }

        // // --------- MW_CLNT_REL --------- //
        List<MwClntRel> rels = mwClntRelRepository.findUpdatedMwClntRelForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (rels != null && rels.size() > 0) {
            rels.forEach(rel -> {
                ClntRelDto rDto = new ClntRelDto();
                rDto.DomainToDto(rel);
                if (dto.mw_clnt_rel == null)
                    dto.mw_clnt_rel = new ArrayList<>();
                dto.mw_clnt_rel.add(rDto);
            });
        }

        // // ---------- MW_LOAN_UTL_PLAN ---------- //
        List<MwLoanUtlPlan> plans = mwLoanUtlPlanRepository.findUpdatedMwLoanUtlPlanForLoanForPortsLastUpdatedbyNot(syncDate, ports,
                user, sts);
        if (plans != null && plans.size() > 0) {
            plans.forEach(plan -> {
                ExpextedLoanDto eDto = new ExpextedLoanDto();
                eDto.DomainToDto(plan);
                if (dto.mw_loan_utl_plan == null)
                    dto.mw_loan_utl_plan = new ArrayList<>();
                dto.mw_loan_utl_plan.add(eDto);
            });
        }

        // // --------- MW_BIZ_APRSL -------- //
        List<MwBizAprsl> bizAprsls = mwBizAprslRepository.findUpdatedMwBizAprslForLoanForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        if (bizAprsls != null && bizAprsls.size() > 0) {
            bizAprsls.forEach(aprsl -> {
                BusinessAppraisalBasicInfo info = new BusinessAppraisalBasicInfo();
                info.DomainToDto(aprsl);
                if (dto.mw_biz_aprsl == null)
                    dto.mw_biz_aprsl = new ArrayList<>();
                dto.mw_biz_aprsl.add(info);
            });
        }

        // // ----------- MW_BIZ_APRSL_INCM_HDR ----------- //
        List<MwBizAprslIncmHdr> hdrs = mwBizAprslIncmHdrRepository.findUpdatedMwBizAprslIncmHdrForLoanForPortsLastUpdatedbyNot(syncDate,
                ports, user, sts);
        if (hdrs != null && hdrs.size() > 0) {
            hdrs.forEach(hdr -> {
                BizAppIncmHdrDto hDto = new BizAppIncmHdrDto();
                hDto.DomainToDto(hdr);
                if (dto.mw_biz_aprsl_incm_hdr == null)
                    dto.mw_biz_aprsl_incm_hdr = new ArrayList<>();
                dto.mw_biz_aprsl_incm_hdr.add(hDto);
            });
        }
        //
        // // ---------- MW_BIZ_APRSL_INCM_DTL ---------- //
        // ------------ BUSINESS -------------- //
        List<MwBizAprslIncmDtl> dtls = mwBizAprslIncmDtlRepository
                .findUpdatedMwBizAprslIncmHdrForBusinessForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (dtls != null && dtls.size() > 0) {
            dtls.forEach(dtl -> {
                BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                dDto.DomainToDto(dtl);
                if (dto.mw_biz_aprsl_incm_dtl == null)
                    dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                dto.mw_biz_aprsl_incm_dtl.add(dDto);
            });
        }
        // ------------ SCHOOL -------------- //
        List<MwBizAprslIncmDtl> Sdtls = mwBizAprslIncmDtlRepository
                .findUpdatedMwBizAprslIncmHdrForSchoolForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (Sdtls != null && Sdtls.size() > 0) {
            Sdtls.forEach(dtl -> {
                BizAprslIncmDtlDto dDto = new BizAprslIncmDtlDto();
                dDto.DomainToDto(dtl);
                if (dto.mw_biz_aprsl_incm_dtl == null)
                    dto.mw_biz_aprsl_incm_dtl = new ArrayList<>();
                dto.mw_biz_aprsl_incm_dtl.add(dDto);
            });
        }

        // // --------- MW_BIZ_EXP_DTL ---------- //
        // ------------ BUSINESS -------------- //
        List<MwBizExpDtl> exps = mwBizExpDtlRepository.findUpdatedMwBizExpDtlForBusinessForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        if (exps != null && exps.size() > 0) {
            exps.forEach(ex -> {
                ExpenseDto eDto = new ExpenseDto();
                eDto.DomainToDto(ex);
                if (dto.mw_biz_exp_dtl == null)
                    dto.mw_biz_exp_dtl = new ArrayList<>();
                dto.mw_biz_exp_dtl.add(eDto);
            });
        }
        // ------------ SCHOOL -------------- //
        exps = mwBizExpDtlRepository.findUpdatedMwBizExpDtlForSchoolForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (exps != null && exps.size() > 0) {
            exps.forEach(ex -> {
                ExpenseDto eDto = new ExpenseDto();
                eDto.DomainToDto(ex);
                if (dto.mw_biz_exp_dtl == null)
                    dto.mw_biz_exp_dtl = new ArrayList<>();
                dto.mw_biz_exp_dtl.add(eDto);
            });
        }

        // --------- MW_CLNT_PSC -------- //
        List<MwClntPsc> pscs = mwClntPscRepository.findUpdatedMwClntPscForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (pscs != null && pscs.size() > 0) {
            pscs.forEach(psc -> {
                PSC pDto = new PSC();
                pDto.DomainToDto(psc);
                if (dto.mw_clnt_psc == null)
                    dto.mw_clnt_psc = new ArrayList<>();
                dto.mw_clnt_psc.add(pDto);
            });
        }

        // // --------- MW_SCH_APRSL ---------//
        List<MwSchAprsl> schAprsls = mwSchAprslRepository.findUpdatedMwSchAprslForLoanForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        if (schAprsls != null && schAprsls.size() > 0) {
            schAprsls.forEach(aprsl -> {
                SchoolAppraisalBasicInfoDto sDto = new SchoolAppraisalBasicInfoDto();
                sDto.DomainToDto(aprsl);
                if (dto.mw_sch_aprsl == null)
                    dto.mw_sch_aprsl = new ArrayList<>();
                dto.mw_sch_aprsl.add(sDto);
            });
        }

        // // ---------- MW_SCH_GRD -------- //
        List<MwSchGrd> grds = mwSchGrdRepository.findUpdatedMwSchGrdForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (grds != null && grds.size() > 0) {
            grds.forEach(grd -> {
                SchoolGradesDto gDto = new SchoolGradesDto();
                gDto.DomainToDto(grd);
                if (dto.mw_sch_grd == null)
                    dto.mw_sch_grd = new ArrayList<>();
                dto.mw_sch_grd.add(gDto);
            });
        }

        // // --------- MW_SCH_ATTND --------- //
        List<MwSchAtnd> atnds = mwSchAtndRepository.findUpdatedMwSchAtndForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (atnds != null && atnds.size() > 0) {
            atnds.forEach(atnd -> {
                SchoolAttendanceDto gDto = new SchoolAttendanceDto();
                gDto.DomainToDto(atnd);
                if (dto.mw_sch_atnd == null)
                    dto.mw_sch_atnd = new ArrayList<>();
                dto.mw_sch_atnd.add(gDto);
            });
        }

        // // ---------- MW_SCH_ASTS -------- //
        List<MwSchAsts> asts = mwSchAstsRepository.findUpdatedMwSchAstsForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        if (asts != null && asts.size() > 0) {
            asts.forEach(ast -> {
                SchoolInfrastructureDto aDto = new SchoolInfrastructureDto();
                aDto.DomainToDto(ast);
                if (dto.mw_sch_asts == null)
                    dto.mw_sch_asts = new ArrayList<>();
                dto.mw_sch_asts.add(aDto);
            });
        }

        // // ---------- MW_SCH_QLTY_CHK -------- //
        List<MwSchQltyChk> chks = mwSchQltyChkRepository.findUpdatedMwSchQltyChkForLoanForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        if (chks != null && chks.size() > 0) {
            chks.forEach(chk -> {
                SchoolQuestionDto sDto = new SchoolQuestionDto();
                sDto.DomainToDto(chk);
                if (dto.mw_sch_qlty_chk == null)
                    dto.mw_sch_qlty_chk = new ArrayList<>();
                dto.mw_sch_qlty_chk.add(sDto);
            });
        }
        // ---------- MW_LOAN_APP_DOC -------- //
        List<MwRefCdVal> vals = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> docsts = new ArrayList<>();
        vals.forEach(val -> {
            if (val.getRefCd().equals("0002") || val.getRefCd().equals("0003") || val.getRefCd().equals("0004")
                    || val.getRefCd().equals("1305")) {
                docsts.add(val.getRefCdSeq());
            }
        });
        List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findUpdatedMwLoanAppDocForLoanForPortsLastUpdatedbyNot(syncDate, ports, user,
                docsts);
        if (docs != null && docs.size() > 0) {
            docs.forEach(doc -> {
                DocDto dDto = new DocDto();
                dDto.DomainToDto(doc);
                if (dto.mw_loan_app_doc == null)
                    dto.mw_loan_app_doc = new ArrayList<>();
                dto.mw_loan_app_doc.add(dDto);
            });
        }

        // --------- MW_AMNL_RGSTR -------//
        List<MwAnmlRgstr> anmls = mwAnmlRgstrRepository.findUpdatedMwAnmlRgstrForLoanForPortsLastUpdatedbyNot(syncDate, ports, user,
                sts);
        anmls.forEach(anml -> {
            AnmlRgstrDto adto = new AnmlRgstrDto();
            adto.DomainToDto(anml);
            if (dto.mw_anml_rgstr == null)
                dto.mw_anml_rgstr = new ArrayList<>();
            dto.mw_anml_rgstr.add(adto);
        });
        // --------- MW_BIZ_APRSL_EXTNG_LVSTK -------//
        List<MwBizAprslExtngLvstk> lvs = mwBizAprslExtngLvstkRepository
                .findUpdatedMwBizAprslExtngLvstkForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        lvs.forEach(lv -> {
            BizAprslExtngLvstkDto ldto = new BizAprslExtngLvstkDto();
            ldto.DomainToDto(lv);
            if (dto.mw_biz_aprsl_extng_lvstk == null)
                dto.mw_biz_aprsl_extng_lvstk = new ArrayList<>();
            dto.mw_biz_aprsl_extng_lvstk.add(ldto);
        });
        // --------- MW_BIZ_APRSL_EST_LVSTK_FIN -------//
        List<MwBizAprslEstLvstkFin> fins = mwBizAprslEstLvstkFinRepository
                .findUpdatedMwBizAprslEstLvstkFinForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        fins.forEach(fin -> {
            BizAprslEstLvstkFinDto fdto = new BizAprslEstLvstkFinDto();
            fdto.DomainToDto(fin);
            if (dto.mw_biz_aprsl_est_lvstk_fin == null)
                dto.mw_biz_aprsl_est_lvstk_fin = new ArrayList<>();
            dto.mw_biz_aprsl_est_lvstk_fin.add(fdto);
        });

        // --------- MW_LOAN_APP_MNTRNG_CHKS -------//
        List<MwLoanAppMntrngChks> mchks = mwLoanAppMntrngChksRepository
                .findUpdatedMwLoanAppMntrngChksForLoanForPortsLastUpdatedbyNot(syncDate, ports, user, sts);
        mchks.forEach(chk -> {
            LoanAppMntrngChks d = new LoanAppMntrngChks();
            d.DomainToDto(chk);
            if (dto.mw_loan_app_mntrng_chks == null)
                dto.mw_loan_app_mntrng_chks = new ArrayList<>();
            dto.mw_loan_app_mntrng_chks.add(d);
        });

        // ---------- MW_CNIC_UPD ----------//
        List<MwCnicUpd> cnicUpds = mwCnicUpdRepository.findUpdatedMwCnicUpdForLoanForPortsLastUpdatedbyNot(syncDate, ports, user);
        cnicUpds.forEach(upd -> {
            CnicUpdDTO cnicUpdDto = new CnicUpdDTO();
            cnicUpdDto.DomainToDto(upd);
            if (dto.mw_cnic_upd == null)
                dto.mw_cnic_upd = new ArrayList<>();
            dto.mw_cnic_upd.add(cnicUpdDto);
        });

        return dto;
    }

    // Modified by Zohaib Asim - Dated 18-10-2021 - CR: MFCIB to Tasdeeq
    public Long tasdeeqReportData(Long docSeq, ResponseEntity rsp, ReportDataObjDto reportDto) {
        MwLoanAppDoc document = new MwLoanAppDoc();
        String cnicNum = reportDto.CNIC;
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        // Loan App Doc Sequence
        Long seq = 0L;
        Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                .setParameter("userId", user);
        Object tblSeqRes = qry.getSingleResult();

        if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
            seq = Long.parseLong(tblSeqRes.toString());
        }

        /*
        MwClnt clnt = null;
        Long seq = Long.parseLong(reportDto.applicationNumber + 6);
        if (reportDto.reqFor.equals("Client"))
            mwClntRepository.findOneByCnicNumAndCrntRecFlg(Long.parseLong(reportDto.CNIC), true);

        if (clnt != null) {

            String valquery = Queries.statusSeq;
            Query qr = em.createNativeQuery(valquery);
            List<Object[]> resultSet = qr.getResultList();

            long completedStatus = 0L;
            for (Object[] str : resultSet) {
                if (str[1].toString().toLowerCase().equals("completed"))
                    completedStatus = Long.valueOf(str[2].toString());
            }

            List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(clnt.getClntSeq(),
                    completedStatus);
            long loanCycle = 0L;
            if (loans.size() > 0) {
                if (loans.get(0) != null) {
                    loanCycle = loans.get(0).getLoanCyclNum() + 1;
                }
            }
            seq = Long.parseLong((Common.GenerateTableSequence(cnicNum, TableNames.MW_LOAN_APP_DOC, loanCycle) + ""));
        }*/

        document.setLoanAppDocSeq(seq);
        document.setLoanAppSeq(Long.valueOf(reportDto.applicationNumber));
        document.setDocSeq(Long.valueOf(docSeq));
        document.setCrntRecFlg(true);
        document.setCrtdBy("CB-" + user);
        document.setCrtdDt(Instant.now());
        document.setDelFlg(false);
        document.setDocImg(rsp.getBody().toString());
        document.setCnicNum(Long.parseLong(reportDto.CNIC));
        document.setCompanyNm(reportDto.companyNm);

        try {
            document.setEffStartDt(Instant.now());
            document.setLastUpdDt(Common.getZonedInstant(Instant.now()));
            document.setCrtdDt(Common.getZonedInstant(Instant.now()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            return -1L;
        }
        document.setLastUpdBy(user);
        mwLoanAppDocRepository.save(document);

        return seq;
    }

    @Transactional
    @Timed
    public String postTasdeeq(TasdeeqDto dto, String token) {
        ReportDataObjDto reportDto = dto.reportDataObj;
        Map<String, Object> respMapObj = new HashMap<>();
        ResponseEntity<String> response = null;
        //
        Long docSeq = 0L;
        boolean bolTasdeeqResp = false;
        //
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        //
        log.info("Tasdeeq : CNIC/Application -> " + dto.reportDataObj.CNIC
                + "/" + dto.reportDataObj.applicationNumber);

        // Verify Data for 30 Days
        Instant dateForComparison = Instant.now().minus(30, ChronoUnit.DAYS);
        List<MwLoanAppDoc> loanAppDocList = null;
        if (reportDto.reqFor.equals("Client")) {
            log.info("TasdeeqOfLast30Days Comparison - Client -> " + dateForComparison);
            docSeq = 0L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), 0L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), 0L, dateForComparison, "TASDEEQ");
            }
        } else if (reportDto.reqFor.equals("Nominee")) {
            log.info("TasdeeqOfLast30Days  Comparison - Nominee -> " + dateForComparison);
            docSeq = -1L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), -1L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), -1L, dateForComparison, "TASDEEQ");
            }
        } else if (reportDto.reqFor.equals("Pdc")) {
            log.info("TasdeeqOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
            docSeq = -2L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), -2L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), -2L, dateForComparison, "TASDEEQ");
            }
        }

        //
        if (loanAppDocList != null && loanAppDocList.size() > 0) {
            log.info("Tasdeeq: Database Object Returned");

            // Zohaib Asim - Dated 26-01-2022
            // In Case Loan App Seq is changed but Doc Image
            if (!dto.reportDataObj.applicationNumber.equals(loanAppDocList.get(0).getLoanAppSeq())) {
                // Existing Loan Detail
                /***MwLoanAppDoc exLoanAppDoc = loanAppDocList.get(0);

                 //
                 if ( exLoanAppDoc.getCrntRecFlg() == true){
                 // Existing Loan Detail
                 exLoanAppDoc.setCrntRecFlg(false);
                 exLoanAppDoc.setDelFlg(true);
                 exLoanAppDoc.setLastUpdDt(Instant.now());
                 exLoanAppDoc.setLastUpdBy(user);

                 mwLoanAppDocRepository.save(exLoanAppDoc);
                 }***/

                // Loan App Doc Sequence
                Long loanAppDocSeq = 0L;
                Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                        .setParameter("userId", user);
                Object tblSeqRes = qry.getSingleResult();

                if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                    loanAppDocSeq = Long.parseLong(tblSeqRes.toString());
                }

                // Loan App Doc Sequence
                /*Long loanAppDocSeq = Long.parseLong(dto.reportDataObj.applicationNumber + 6);
                MwClnt clnt = null;
                if (reportDto.reqFor.equals("Client"))
                    mwClntRepository.findOneByCnicNumAndCrntRecFlg(Long.parseLong(dto.reportDataObj.CNIC), true);
                //
                if (clnt != null) {
                    String valquery = Queries.statusSeq;
                    Query qr = em.createNativeQuery(valquery);
                    List<Object[]> resultSet = qr.getResultList();
                    long completedStatus = 0L;
                    for (Object[] str : resultSet) {
                        if (str[1].toString().toLowerCase().equals("completed"))
                            completedStatus = Long.valueOf(str[2].toString());
                    }
                    List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(clnt.getClntSeq(),
                            completedStatus);
                    long loanCycle = 0L;
                    if (loans.size() > 0) {
                        if (loans.get(0) != null) {
                            loanCycle = loans.get(0).getLoanCyclNum() + 1;
                        }
                    }
                    loanAppDocSeq = Long.parseLong((Common.GenerateTableSequence(dto.reportDataObj.CNIC, TableNames.MW_LOAN_APP_DOC, loanCycle) + ""));
                }*/

                // Details to Update
                MwLoanAppDoc mwLoanAppDoc = new MwLoanAppDoc();

                mwLoanAppDoc.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppDoc.setEffStartDt(loanAppDocList.get(0).getEffStartDt());
                mwLoanAppDoc.setDocSeq(loanAppDocList.get(0).getDocSeq());
                mwLoanAppDoc.setDocImg(loanAppDocList.get(0).getDocImg());
                mwLoanAppDoc.setCnicNum(loanAppDocList.get(0).getCnicNum());
                mwLoanAppDoc.setCompanyNm(loanAppDocList.get(0).getCompanyNm());
                mwLoanAppDoc.setLoanAppSeq(Long.parseLong(dto.reportDataObj.applicationNumber));
                mwLoanAppDoc.setCrntRecFlg(true);
                mwLoanAppDoc.setDelFlg(false);
                mwLoanAppDoc.setCrtdDt(Instant.now());
                mwLoanAppDoc.setCrtdBy(user);
                mwLoanAppDocRepository.save(mwLoanAppDoc);
            }
            // End

            return loanAppDocList.get(0).getDocImg();
        }

        // MFCIB Credentials
        MwMfcibCred mfcibAuthCred = mfcibCredRepository.findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg("TASDEEQ",
                "AUTH", true);

        // Header Information
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //
        headers.set("Authorization", "Bearer " + mfcibAuthCred.getAuthTkn());

        try {
            //
            HttpEntity<TasdeeqDto> request = new HttpEntity<>(dto, headers);

            // MFCIB Primary URL
            MwMfcibCred mfcibPrmry = mfcibCredRepository.findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg("TASDEEQ",
                    "PRIMARY", true);

            if (mfcibPrmry != null) {
                response = restTemplate.postForEntity(mfcibPrmry.getUrl(), request, String.class);
                // Test URL
                // response = restTemplate.postForEntity( "https://cib.tasdeeq.com:8888/TestCreditInformationReport/", request, String.class );
                // Production URL
                // response = restTemplate.postForEntity( "https://cib.tasdeeq.com:8888/CreditInformationOrderedReportUpdated/", request, String.class );
                //
                JSONObject result = new JSONObject(response.getBody());
                String statusCode = result.getString("statusCode");
                String msgCode = result.getString("messageCode");
                String msgDesc = result.getString("message");

                JSONObject data = null;
                if (statusCode.equals("111")) {
                    data = (JSONObject) result.get("data");
                    if (data != null && data.length() > 0) {
                        if (tasdeeqReportData(docSeq, response, reportDto) > 0) {
                            bolTasdeeqResp = true;
                            log.info("Tasdeeq: Successfully Executed");
                        }
                    } else {
                        respMapObj.put("statusCode", statusCode);
                        respMapObj.put("messageCode", msgCode);
                        respMapObj.put("message", msgDesc);
                        respMapObj.put("data", "{}");
                    }
                } else {
                    respMapObj.put("statusCode", statusCode);
                    respMapObj.put("messageCode", msgCode);
                    respMapObj.put("message", msgDesc);
                    respMapObj.put("data", "{}");

                    log.info("Tasdeeq: URL Response status/message/desc -> " + statusCode + "/" + msgCode + "/" + msgDesc);
                }
            }
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            log.error("Tasdeeq Exception in URL Response: " + ex.getMessage());

            // MFCIB Credentials
            if (mfcibAuthCred != null)
                authenticateTasdeeq(mfcibAuthCred.getUrl(), mfcibAuthCred.getAuthUser(), mfcibAuthCred.getAuthPass());
            else
                log.info("Invalid User Credentials. Please check DB User/Pass.");

            // Exception
            respMapObj.put("statusCode", "115");
            respMapObj.put("messageCode", "10000000");
            respMapObj.put("message", "Something went wrong. Please try again.");
            respMapObj.put("data", "{}");
        }

        // return ResponseEntity.ok().body(document.getDocImg());
        if (bolTasdeeqResp == true) {
            return response.getBody().toString();
        } else {
            return respMapObj.toString();
        }
    }

    /**
     * @Added, Naveed
     * @Date, 17-08-2022
     * @Description, SCR - CIRPLUS TASDEEQ
     */
    @Transactional
    @Timed
    public String postCIRPlusTasdeeq(TasdeeqDto dto, String token) {
        ReportDataObjDto reportDto = dto.reportDataObj;
        Map<String, Object> respMapObj = new HashMap<>();
        ResponseEntity<String> response = null;

        Long docSeq = 0L;
        boolean bolTasdeeqResp = false;

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("Tasdeeq CIRPLUS : CNIC/Application -> " + dto.reportDataObj.CNIC
                + "/" + dto.reportDataObj.applicationNumber);

        // Verify Data for 30 Days
        Instant dateForComparison = Instant.now().minus(30, ChronoUnit.DAYS);
        List<MwLoanAppDoc> loanAppDocList = null;
        if (reportDto.reqFor.equals("Client")) {
            log.info("TasdeeqOfLast30Days Comparison - Client -> " + dateForComparison);
            docSeq = 0L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), 0L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), 0L, dateForComparison, "TASDEEQ");
            }
        } else if (reportDto.reqFor.equals("Nominee")) {
            log.info("TasdeeqOfLast30Days  Comparison - Nominee -> " + dateForComparison);
            docSeq = -1L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), -1L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), -1L, dateForComparison, "TASDEEQ");
            }
        } else if (reportDto.reqFor.equals("Pdc")) {
            log.info("TasdeeqOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
            docSeq = -2L;
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(reportDto.CNIC), -2L, true, dateForComparison, "TASDEEQ");
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(reportDto.CNIC), -2L, dateForComparison, "TASDEEQ");
            }
        }

        //
        if (loanAppDocList != null && loanAppDocList.size() > 0) {
            log.info("Tasdeeq: Database Object Returned");

            // Zohaib Asim - Dated 26-01-2022
            // In Case Loan App Seq is changed but Doc Image
            if (!dto.reportDataObj.applicationNumber.equals(loanAppDocList.get(0).getLoanAppSeq())) {
                // Existing Loan Detail
                MwLoanAppDoc exLoanAppDoc = loanAppDocList.get(0);

                //
                if (exLoanAppDoc.getCrntRecFlg() == true) {
                    // Existing Loan Detail
                    exLoanAppDoc.setCrntRecFlg(false);
                    exLoanAppDoc.setDelFlg(true);
                    exLoanAppDoc.setLastUpdDt(Instant.now());
                    exLoanAppDoc.setLastUpdBy(user);

                    mwLoanAppDocRepository.save(exLoanAppDoc);
                }

                // Loan App Doc Sequence
                Long loanAppDocSeq = 0L;
                Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                        .setParameter("userId", user);
                Object tblSeqRes = qry.getSingleResult();

                if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                    loanAppDocSeq = Long.parseLong(tblSeqRes.toString());
                }

                // Details to Update
                MwLoanAppDoc mwLoanAppDoc = exLoanAppDoc;
                mwLoanAppDoc.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppDoc.setEffStartDt(loanAppDocList.get(0).getEffStartDt());
                mwLoanAppDoc.setDocSeq(loanAppDocList.get(0).getDocSeq());
                mwLoanAppDoc.setDocImg(loanAppDocList.get(0).getDocImg());
                mwLoanAppDoc.setCnicNum(loanAppDocList.get(0).getCnicNum());
                mwLoanAppDoc.setCompanyNm(loanAppDocList.get(0).getCompanyNm());
                mwLoanAppDoc.setLoanAppSeq(Long.parseLong(dto.reportDataObj.applicationNumber));
                mwLoanAppDoc.setCrntRecFlg(true);
                mwLoanAppDoc.setDelFlg(false);
                mwLoanAppDoc.setCrtdDt(Instant.now());
                mwLoanAppDoc.setCrtdBy(user);
                mwLoanAppDocRepository.save(mwLoanAppDoc);
            }
            // End

            return loanAppDocList.get(0).getDocImg();
        }

        // MFCIB Credentials
        MwMfcibCred mfcibAuthCred = mfcibCredRepository.findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg("TASDEEQ",
                "AUTH", true);

        // Header Information
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //
        headers.set("Authorization", "Bearer " + mfcibAuthCred.getAuthTkn());

        try {
            //
            HttpEntity<TasdeeqDto> request = new HttpEntity<>(dto, headers);

            // MFCIB Primary URL
            MwMfcibCred mfcibPrmry = mfcibCredRepository.findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg("TASDEEQ",
                    "PRIMARY", true);

            if (mfcibPrmry != null) {
                Date startDt = new Date();

                response = restTemplate.postForEntity(mfcibPrmry.getUrl(), request, String.class);
                log.info("TasdeeqRest: Call started at " + startDt + " ending at " + new Date() + ", CNIC: " + dto.reportDataObj.CNIC);

                JSONObject result = new JSONObject(response.getBody());
                String statusCode = result.getString("statusCode");
                String msgCode = result.getString("messageCode");
                String msgDesc = result.getString("message");

                JSONObject data = null;
                if (statusCode.equals("111")) {
                    data = (JSONObject) result.get("data");
                    if (data != null && data.length() > 0) {
                        if (tasdeeqReportData(docSeq, response, reportDto) > 0) {
                            bolTasdeeqResp = true;
                            log.info("Tasdeeq: Successfully Executed");
                        }
                    } else {
                        respMapObj.put("statusCode", statusCode);
                        respMapObj.put("messageCode", msgCode);
                        respMapObj.put("message", msgDesc);
                        respMapObj.put("data", "{}");
                    }
                } else {
                    respMapObj.put("statusCode", statusCode);
                    respMapObj.put("messageCode", msgCode);
                    respMapObj.put("message", msgDesc);
                    respMapObj.put("data", "{}");

                    log.info("Tasdeeq: URL Response status/message/desc -> " + statusCode + "/" + msgCode + "/" + msgDesc);
                }
            }
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            log.error("Tasdeeq Exception in URL Response: " + ex.getMessage());

            // MFCIB Credentials
            if (mfcibAuthCred != null)
                authenticateTasdeeq(mfcibAuthCred.getUrl(), mfcibAuthCred.getAuthUser(), mfcibAuthCred.getAuthPass());
            else
                log.info("Invalid User Credentials. Please check DB User/Pass.");

            // Exception
            respMapObj.put("statusCode", "115");
            respMapObj.put("messageCode", "10000000");
            respMapObj.put("message", "Something went wrong. Please try again.");
            respMapObj.put("data", "{}");
        }

        if (bolTasdeeqResp == true) {
            return response.getBody().toString();
        } else {
            return respMapObj.toString();
        }
    }

    /**
     * @Ended, Naveed
     * @Date, 17-08-2022
     * @Description, SCR - CIRPLUS TASDEEQ
     */

    public Map<String, Object> authenticateTasdeeq(String url, String username, String password) {
        Map<String, Object> respMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();

        //
        log.info("Tasdeeq Auth Resquest");

        //
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"UserName\":\"" + username + "\",\"Password\": \"" + password + "\"}", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        try {
            //log.info("Authentication JSON Body:" + response.getBody());

            JSONObject jsonRequest = new JSONObject(response.getBody());
            String msgCode = jsonRequest.getString("messageCode");
            String stsCode = jsonRequest.getString("statusCode");
            String msgDesc = jsonRequest.getString("message");

            //
            respMap.put("messageCode", msgCode);
            respMap.put("statusCode", stsCode);
            respMap.put("message", msgDesc);

            //
            log.info("Tasdeeq Auth Response: Message/Status/Desc -> " + msgCode + "/" + stsCode + "/"
                    + msgDesc);

            //
            if (stsCode.equals("111")) {
                JSONObject obj = jsonRequest.getJSONObject("data");
                if (obj != null) {
                    // return obj.getString( "auth_token" );
                    respMap.put("auth_token", obj.getString("auth_token"));
                    log.info("Tasdeeq Auth: Token successfully generated, " + obj.getString("auth_token"));

                    // Update Tasdeeq Authentication Token
                    MwMfcibCred mfcibAuthCred = mfcibCredRepository.findMwMfcibCredByCompanyNmAndCredTypAndCrntRecFlg("TASDEEQ",
                            "AUTH", true);

                    mfcibAuthCred.setAuthTkn(obj.getString("auth_token"));
                    //
                    Date expiryDate = DateUtils.addHours(new Date(), 8);
                    mfcibAuthCred.setAuthTknExpry(expiryDate);
                    mfcibCredRepository.save(mfcibAuthCred);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            log.error("Tasdeeq Auth Exception: " + e.getMessage());
        }
        return respMap;
    }

    @Timed
    public ResponseEntity<String> creditScoring(CreditScoringTabDto dto) {
        try {
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dto.portSeq, true);
            if (port == null)
                return ResponseEntity.badRequest().body("{\"error\":\"Port Not Found\"}");
            MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);
            if (brnch == null)
                return ResponseEntity.badRequest().body("{\"error\":\"Branch Not Found\"}");
            if (dto.clientSeq != null || dto.clientSeq != 0) {
                String clntDataBody = Common.getCreditScoringJSON(dto.clientSeq, port.getBrnchSeq(), dto.loanCylcNum,
                        dto.occupation, dto.businessDuration, dto.businessOwnership, dto.current_residency_yrs,
                        dto.current_residency_mnths, dto.ndi, dto.travelling_expense, dto.comitte_expense,
                        dto.food_expense, dto.earning_members, dto.mobile_expense, dto.rqstdLoanAmount,
                        dto.consumerRatio, dto.earnerRatio, dto.marriageExpense, dto.povertyScore,
                        dto.areaName, dto.productName, dto.totalExpense, dto.kszbInstallment, dto.dependants);

                // System.out.println( "client_data: \n" + clntDataBody );
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.set("Authorization", "1235f4409c6bfb139af0d1d451b1f137");

                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                map.add("party_id", "" + dto.clientSeq);
                map.add("branch_code", brnch.getBrnchNm());
                map.add("client_data", clntDataBody);
                log.info("Credit Scoring Request: " + map.toString());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

                ResponseEntity<String> response = restTemplate.postForEntity("http://34.217.47.237/get/category_weightage", request,
                        String.class);
                log.info("Credit Scoring Response: " + response.toString());
                return response;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("{\"error\":\"In-Valid Request\"}");
    }

    @Transactional
    public void deferLoanApplication(long loanAppSeq, String curUser) {
        MwRefCdVal val = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "1285");
        MwLoanApp exApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exApp != null) {
            exApp.setCrntRecFlg(false);
            exApp.setDelFlg(true);
            exApp.setLoanAppSts(val.getRefCdSeq());
            exApp.setLastUpdBy(curUser);
            exApp.setLastUpdDt(Instant.now());
            exApp.setLoanAppStsDt(Instant.now());
            mwLoanAppRepository.save(exApp);
        }
        MwLoanAppCrdtScr exScr = mwLoanAppCrdtScrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exScr != null) {
            exScr.setCrntRecFlg(false);
            exScr.setDelFlg(true);
            exScr.setEffEndDt(Instant.now());
            exScr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exScr.setLastUpdDt(Instant.now());
            mwLoanAppCrdtScrRepository.save(exScr);
        }

        MwClntHlthInsr exinsr = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exinsr != null) {
            exinsr.setCrntRecFlg(false);
            exinsr.setDelFlg(true);
            exinsr.setEffEndDt(Instant.now());
            exinsr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exinsr.setLastUpdDt(Instant.now());
            mwClntHlthInsrRepository.save(exinsr);
        }
        List<MwHlthInsrMemb> exMem = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        exMem.forEach(mem -> {
            mem.setCrntRecFlg(false);
            mem.setDelFlg(true);
            mem.setEffEndDt(Instant.now());
            mem.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            mem.setLastUpdDt(Instant.now());
        });
        mwHlthInsrMembRepository.save(exMem);

        List<MwMfcibOthOutsdLoan> exLoans = mwMfcibOthOutsdLoanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        exLoans.forEach(item -> {
            item.setCrntRecFlg(false);
            item.setDelFlg(true);
            item.setEffEndDt(Instant.now());
            item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            item.setLastUpdDt(Instant.now());
        });
        mwMfcibOthOutsdLoanRepository.save(exLoans);

        List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        rels.forEach(rel -> {

            if (rel.getRelTypFlg().longValue() == 3 || rel.getRelTypFlg().longValue() == 4) {
                String entyTyp = (rel.getRelTypFlg().longValue() == 3) ? Common.cobAddress : Common.relAddress;
                MwAddrRel adrel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(rel.getClntRelSeq(), entyTyp, true);
                if (adrel != null) {
                    adrel.setCrntRecFlg(false);
                    adrel.setDelFlg(true);
                    adrel.setEffEndDt(Instant.now());
                    adrel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    adrel.setLastUpdDt(Instant.now());
                    mwAddrRelRepository.save(adrel);

                    MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(adrel.getAddrSeq(), true);
                    if (addr != null) {
                        addr.setCrntRecFlg(false);
                        addr.setDelFlg(true);
                        addr.setEffEndDt(Instant.now());
                        addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        addr.setLastUpdDt(Instant.now());
                        mwAddrRepository.save(addr);
                    }
                }
            }
            rel.setCrntRecFlg(false);
            rel.setDelFlg(true);
            rel.setEffEndDt(Instant.now());
            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            rel.setLastUpdDt(Instant.now());
            mwClntRelRepository.save(rel);
        });
        MwBizAprsl aprsl = mwBizAprslRepository.findOneByMwLoanAppAndCrntRecFlg(loanAppSeq, true);
        if (aprsl != null) {

            MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByMwBizAprslAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
            if (hdr != null) {

                List<MwBizAprslIncmDtl> exBizIncmDtl = mwBizAprslIncmDtlRepository
                        .findAllByMwBizAprslIncmHdrAndEntyTypFlgAndCrntRecFlg(hdr.getIncmHdrSeq(), 2, true);
                exBizIncmDtl.forEach(item -> {
                    item.setCrntRecFlg(false);
                    item.setDelFlg(true);
                    item.setEffEndDt(Instant.now());
                    item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    item.setLastUpdDt(Instant.now());
                });
                mwBizAprslIncmDtlRepository.save(exBizIncmDtl);

                hdr.setCrntRecFlg(false);
                hdr.setDelFlg(true);
                hdr.setEffEndDt(Instant.now());
                hdr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                hdr.setLastUpdDt(Instant.now());
                mwBizAprslIncmHdrRepository.save(hdr);

                List<MwBizExpDtl> exBizExpenseDtls = mwBizExpDtlRepository
                        .findAllByMwBizAprslAndEntyTypFlgAndCrntRecFlg(hdr.getIncmHdrSeq(), 2, true);
                exBizExpenseDtls.forEach(item -> {
                    item.setCrntRecFlg(false);
                    item.setDelFlg(true);
                    item.setEffEndDt(Instant.now());
                    item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    item.setLastUpdDt(Instant.now());
                });
                mwBizExpDtlRepository.save(exBizExpenseDtls);
            }

            MwAddrRel rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(aprsl.getBizAprslSeq(), Common.businessAddress,
                    true);
            if (rel != null) {
                rel.setCrntRecFlg(false);
                rel.setDelFlg(true);
                rel.setEffEndDt(Instant.now());
                rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                rel.setLastUpdDt(Instant.now());
                mwAddrRelRepository.save(rel);

                MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(rel.getAddrSeq(), true);
                if (addr != null) {
                    addr.setCrntRecFlg(false);
                    addr.setDelFlg(true);
                    addr.setEffEndDt(Instant.now());
                    addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    addr.setLastUpdDt(Instant.now());
                    mwAddrRepository.save(addr);
                }
            }
            aprsl.setCrntRecFlg(false);
            aprsl.setDelFlg(true);
            aprsl.setEffEndDt(Instant.now());
            aprsl.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            aprsl.setLastUpdDt(Instant.now());
            mwBizAprslRepository.save(aprsl);
        }

        List<MwClntPsc> pscs = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        pscs.forEach(item -> {
            item.setCrntRecFlg(false);
            item.setDelFlg(true);
            item.setEffEndDt(Instant.now());
            item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            item.setLastUpdDt(Instant.now());
        });
        mwClntPscRepository.save(pscs);

        List<MwLoanUtlPlan> utils = mwLoanUtlPlanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        utils.forEach(item -> {
            item.setCrntRecFlg(false);
            item.setDelFlg(true);
            item.setEffEndDt(Instant.now());
            item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            item.setLastUpdDt(Instant.now());
        });
        mwLoanUtlPlanRepository.save(utils);

        List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        docs.forEach(item -> {
            item.setCrntRecFlg(false);
            item.setDelFlg(true);
            item.setEffEndDt(Instant.now());
            item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            item.setLastUpdDt(Instant.now());
        });
        mwLoanAppDocRepository.save(docs);

        MwSchAprsl exAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(loanAppSeq, true);
        if (exAprsl != null) {
            List<MwSchGrd> exGrades = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(exAprsl.getSchAprslSeq(), true);
            exGrades.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwSchGrdRepository.save(exGrades);

            MwSchAtnd exAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(exAprsl.getSchAprslSeq(), true);
            if (exAtnd != null) {
                exAtnd.setCrntRecFlg(false);
                exAtnd.setDelFlg(true);
                exAtnd.setEffEndDt(Instant.now());
                exAtnd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                exAtnd.setLastUpdDt(Instant.now());
                mwSchAtndRepository.save(exAtnd);
            }
            List<MwBizAprslIncmDtl> exDtl = mwBizAprslIncmDtlRepository
                    .findAllByMwBizAprslIncmHdrAndEntyTypFlgAndCrntRecFlg(exAprsl.getSchAprslSeq(), 2, true);
            exDtl.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwBizAprslIncmDtlRepository.save(exDtl);

            List<MwBizExpDtl> exExpenseDtls = mwBizExpDtlRepository
                    .findAllByMwBizAprslAndEntyTypFlgAndCrntRecFlg(exAprsl.getSchAprslSeq(), 2, true);
            exExpenseDtls.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwBizExpDtlRepository.save(exExpenseDtls);

            MwAddrRel rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(exAprsl.getSchAprslSeq(), Common.schApAddress,
                    true);
            if (rel != null) {
                rel.setCrntRecFlg(false);
                rel.setDelFlg(true);
                rel.setEffEndDt(Instant.now());
                rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                rel.setLastUpdDt(Instant.now());
                mwAddrRelRepository.save(rel);

                MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(rel.getAddrSeq(), true);
                if (addr != null) {
                    addr.setCrntRecFlg(false);
                    addr.setDelFlg(true);
                    addr.setEffEndDt(Instant.now());
                    addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    addr.setLastUpdDt(Instant.now());
                    mwAddrRepository.save(addr);
                }
            }
            exAprsl.setCrntRecFlg(false);
            exAprsl.setDelFlg(true);
            exAprsl.setEffEndDt(Instant.now());
            exAprsl.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exAprsl.setLastUpdDt(Instant.now());
            mwSchAprslRepository.save(exAprsl);

        }
        List<MwAnmlRgstr> exRgstr = mwAnmlRgstrRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        exRgstr.forEach(item -> {
            item.setCrntRecFlg(false);
            item.setDelFlg(true);
            item.setEffEndDt(Instant.now());
            item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            item.setLastUpdDt(Instant.now());
        });
        mwAnmlRgstrRepository.save(exRgstr);
    }

    @Transactional
    public void deleteApplication(long loanAppSeq, String curUser, long sts, String reason) {
        MwLoanApp exApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exApp != null) {
            exApp.setCrntRecFlg(false);
            exApp.setDelFlg(true);
            exApp.setLastUpdBy(curUser);
            exApp.setLastUpdDt(Instant.now());
            exApp.setLoanAppSts(sts);
            // Added By Naveed - Dated - 24-11-2021
            // Operation - SCR System Control
            // 30 days application policy
            exApp.setCmnt(reason);
            // Ended By Naveed - Dated - 24-11-2021
            exApp.setLoanAppStsDt(Instant.now());
            mwLoanAppRepository.save(exApp);
        }
        MwLoanAppCrdtScr exScr = mwLoanAppCrdtScrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exScr != null) {
            exScr.setCrntRecFlg(false);
            exScr.setDelFlg(true);
            exScr.setEffEndDt(Instant.now());
            exScr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exScr.setLastUpdDt(Instant.now());
            mwLoanAppCrdtScrRepository.save(exScr);
        }

        MwClntHlthInsr exinsr = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exinsr != null) {
            exinsr.setCrntRecFlg(false);
            exinsr.setDelFlg(true);
            exinsr.setEffEndDt(Instant.now());
            exinsr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exinsr.setLastUpdDt(Instant.now());
            mwClntHlthInsrRepository.save(exinsr);
        }
        List<MwHlthInsrMemb> exMem = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exMem != null) {
            exMem.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwHlthInsrMembRepository.save(exMem);
        }

        List<MwMfcibOthOutsdLoan> exLoans = mwMfcibOthOutsdLoanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exLoans != null) {
            exLoans.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwMfcibOthOutsdLoanRepository.save(exLoans);
        }

        List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        rels.forEach(rel -> {
            if (rel.getRelTypFlg().longValue() == 3 || rel.getRelTypFlg().longValue() == 4) {
                String entyTyp = (rel.getRelTypFlg().longValue() == 3) ? Common.cobAddress : Common.relAddress;
                MwAddrRel adrel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(rel.getClntRelSeq(), entyTyp, true);
                if (adrel != null) {
                    adrel.setCrntRecFlg(false);
                    adrel.setDelFlg(true);
                    adrel.setEffEndDt(Instant.now());
                    adrel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    adrel.setLastUpdDt(Instant.now());
                    mwAddrRelRepository.save(adrel);

                    MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(adrel.getAddrSeq(), true);
                    if (addr != null) {
                        addr.setCrntRecFlg(false);
                        addr.setDelFlg(true);
                        addr.setEffEndDt(Instant.now());
                        addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        addr.setLastUpdDt(Instant.now());
                        mwAddrRepository.save(addr);
                    }
                }
            }
            rel.setCrntRecFlg(false);
            rel.setDelFlg(true);
            rel.setEffEndDt(Instant.now());
            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            rel.setLastUpdDt(Instant.now());
            mwClntRelRepository.save(rel);

        });
        MwBizAprsl aprsl = mwBizAprslRepository.findOneByMwLoanAppAndCrntRecFlg(loanAppSeq, true);
        if (aprsl != null) {
            MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByMwBizAprslAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
            if (hdr != null) {
                List<MwBizAprslIncmDtl> exBizIncmDtl = mwBizAprslIncmDtlRepository
                        .findAllByMwBizAprslIncmHdrAndEntyTypFlgAndCrntRecFlg(hdr.getIncmHdrSeq(), 2, true);
                if (exBizIncmDtl != null) {
                    exBizIncmDtl.forEach(item -> {
                        item.setCrntRecFlg(false);
                        item.setDelFlg(true);
                        item.setEffEndDt(Instant.now());
                        item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        item.setLastUpdDt(Instant.now());
                    });
                    mwBizAprslIncmDtlRepository.save(exBizIncmDtl);
                }

                List<MwBizExpDtl> exBizExpenseDtls = mwBizExpDtlRepository
                        .findAllByMwBizAprslAndEntyTypFlgAndCrntRecFlg(hdr.getIncmHdrSeq(), 2, true);
                if (exBizExpenseDtls != null) {
                    exBizExpenseDtls.forEach(item -> {
                        item.setCrntRecFlg(false);
                        item.setDelFlg(true);
                        item.setEffEndDt(Instant.now());
                        item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        item.setLastUpdDt(Instant.now());
                    });
                    mwBizExpDtlRepository.save(exBizExpenseDtls);
                }
                hdr.setCrntRecFlg(false);
                hdr.setDelFlg(true);
                hdr.setEffEndDt(Instant.now());
                hdr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                hdr.setLastUpdDt(Instant.now());
                mwBizAprslIncmHdrRepository.save(hdr);

            }

            MwAddrRel rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(aprsl.getBizAprslSeq(), Common.businessAddress,
                    true);
            if (rel != null) {
                rel.setCrntRecFlg(false);
                rel.setDelFlg(true);
                rel.setEffEndDt(Instant.now());
                rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                rel.setLastUpdDt(Instant.now());
                mwAddrRelRepository.save(rel);

                MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(rel.getAddrSeq(), true);
                if (addr != null) {
                    addr.setCrntRecFlg(false);
                    addr.setDelFlg(true);
                    addr.setEffEndDt(Instant.now());
                    addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    addr.setLastUpdDt(Instant.now());
                    mwAddrRepository.save(addr);
                }
            }
            aprsl.setCrntRecFlg(false);
            aprsl.setDelFlg(true);
            aprsl.setEffEndDt(Instant.now());
            aprsl.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            aprsl.setLastUpdDt(Instant.now());
            mwBizAprslRepository.save(aprsl);

        }

        List<MwClntPsc> pscs = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (pscs != null) {
            pscs.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwClntPscRepository.save(pscs);
        }

        List<MwLoanUtlPlan> utils = mwLoanUtlPlanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (utils != null) {
            utils.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwLoanUtlPlanRepository.save(utils);
        }

        List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (docs != null) {
            docs.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwLoanAppDocRepository.save(docs);
        }

        MwSchAprsl exAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(loanAppSeq, true);
        if (exAprsl != null) {
            List<MwSchGrd> exGrades = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(exAprsl.getSchAprslSeq(), true);
            if (exGrades != null) {
                exGrades.forEach(item -> {
                    item.setCrntRecFlg(false);
                    item.setDelFlg(true);
                    item.setEffEndDt(Instant.now());
                    item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    item.setLastUpdDt(Instant.now());
                });
                mwSchGrdRepository.save(exGrades);
            }
            MwSchAtnd exAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(exAprsl.getSchAprslSeq(), true);
            if (exAtnd != null) {
                exAtnd.setCrntRecFlg(false);
                exAtnd.setDelFlg(true);
                exAtnd.setEffEndDt(Instant.now());
                exAtnd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                exAtnd.setLastUpdDt(Instant.now());
                mwSchAtndRepository.save(exAtnd);
            }

            // Added by Zohaib Asim - Dated 23-12-2021 - on Discard Application following tables was not updated
            // School Assets
            MwSchAsts schAsts = mwSchAstsRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(loanAppSeq, false, true);
            if (schAsts != null) {
                schAsts.setCrntRecFlg(false);
                schAsts.setDelFlg(true);
                schAsts.setEffEndDt(Instant.now());
                schAsts.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                schAsts.setLastUpdDt(Instant.now());
                mwSchAstsRepository.save(schAsts);
            }

            // School Quality Check
            List<MwSchQltyChk> schQltyChkList = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(exAprsl.getSchAprslSeq(), true);
            if (schQltyChkList.size() > 0) {
                schQltyChkList.forEach(obj -> {
                    obj.setCrntRecFlg(false);
                    obj.setDelFlg(true);
                    obj.setEffEndDt(Instant.now());
                    obj.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    obj.setLastUpdDt(Instant.now());
                });
                mwSchQltyChkRepository.save(schQltyChkList);
            }
            // End

            List<MwBizAprslIncmDtl> exDtl = mwBizAprslIncmDtlRepository
                    .findAllByMwBizAprslIncmHdrAndEntyTypFlgAndCrntRecFlg(exAprsl.getSchAprslSeq(), 2, true);

            if (exDtl != null) {
                exDtl.forEach(item -> {
                    item.setCrntRecFlg(false);
                    item.setDelFlg(true);
                    item.setEffEndDt(Instant.now());
                    item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    item.setLastUpdDt(Instant.now());
                });
                mwBizAprslIncmDtlRepository.save(exDtl);
            }

            List<MwBizExpDtl> exExpenseDtls = mwBizExpDtlRepository
                    .findAllByMwBizAprslAndEntyTypFlgAndCrntRecFlg(exAprsl.getSchAprslSeq(), 2, true);
            if (exExpenseDtls != null) {
                exExpenseDtls.forEach(item -> {
                    item.setCrntRecFlg(false);
                    item.setDelFlg(true);
                    item.setEffEndDt(Instant.now());
                    item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    item.setLastUpdDt(Instant.now());
                });
                mwBizExpDtlRepository.save(exExpenseDtls);
            }
            MwAddrRel rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(exAprsl.getSchAprslSeq(), Common.schApAddress,
                    true);
            if (rel != null) {
                rel.setCrntRecFlg(false);
                rel.setDelFlg(true);
                rel.setEffEndDt(Instant.now());
                rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                rel.setLastUpdDt(Instant.now());
                mwAddrRelRepository.save(rel);

                MwAddr addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(rel.getAddrSeq(), true);
                if (addr != null) {
                    addr.setCrntRecFlg(false);
                    addr.setDelFlg(true);
                    addr.setEffEndDt(Instant.now());
                    addr.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    addr.setLastUpdDt(Instant.now());
                    mwAddrRepository.save(addr);
                }
            }

            exAprsl.setCrntRecFlg(false);
            exAprsl.setDelFlg(true);
            exAprsl.setEffEndDt(Instant.now());
            exAprsl.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exAprsl.setLastUpdDt(Instant.now());
            mwSchAprslRepository.save(exAprsl);

        }
        List<MwAnmlRgstr> exRgstr = mwAnmlRgstrRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (exRgstr != null) {
            exRgstr.forEach(item -> {
                item.setCrntRecFlg(false);
                item.setDelFlg(true);
                item.setEffEndDt(Instant.now());
                item.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                item.setLastUpdDt(Instant.now());
            });
            mwAnmlRgstrRepository.save(exRgstr);
        }

    }

    public SyncDto getCompleteMntrngChksForBranch(SyncDto dto, long branchSeq) {
        List<BigInteger> mntrngSeqs = new ArrayList<>();
        List<MwMntrngChksAdc> adcs = mwMntrngChksAdcRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
        adcs.forEach(adc -> {
            MntrngChksAdcDto mdto = new MntrngChksAdcDto();
            mdto.DomainToDto(adc);
            if (dto.mw_mntrng_chks_adc == null)
                dto.mw_mntrng_chks_adc = new ArrayList<>();
            dto.mw_mntrng_chks_adc.add(mdto);
            mntrngSeqs.add(adc.getMntrngChksSeq());
        });
        List<MwMntrngChksAdcQstnr> qstnr = mwMntrngChksAdcQstnrRepository.findAllByMntrngChksSeqInAndCrntRecFlg(mntrngSeqs, true);
        qstnr.forEach(adc -> {
            MntrngChksAdcQstnrDto mdto = new MntrngChksAdcQstnrDto();
            mdto.DomainToDto(adc);
            if (dto.mw_mntrng_chks_adc_qstnr == null)
                dto.mw_mntrng_chks_adc_qstnr = new ArrayList<>();
            dto.mw_mntrng_chks_adc_qstnr.add(mdto);
        });
        return dto;
    }

    public long saveCnicUpd(CnicUpdDTO dto, String type) {
        MwCnicUpd cnicUpd = mwCnicUpdRepository.findOneByLoanAppSeq(dto.loan_app_seq);
        if (cnicUpd == null) {
            cnicUpd = new MwCnicUpd();
            long cnicUpdSeq = SequenceFinder.findNextVal("CNIC_UPD_SEQ");
            cnicUpd.setCnicUpdSeq(cnicUpdSeq);
        }
        cnicUpd.setLoanAppSeq(dto.loan_app_seq);
        if (dto.cnic_bck_pic != null)
            cnicUpd.setCnicBckPic(dto.cnic_bck_pic);
        if (dto.cnic_frnt_pic != null)
            cnicUpd.setCnicFrntPic(dto.cnic_frnt_pic);
        if (dto.cnic_expry_dt != null) {
            try {
                Instant expryDt = new SimpleDateFormat("dd-MM-yyyy").parse(dto.cnic_expry_dt).toInstant();
                log.info("CNIC ExpiryDate: " + expryDt);
                cnicUpd.setCnicExpryDt((dto.cnic_expry_dt == null) ? null
                        : expryDt);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        cnicUpd.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        cnicUpd.setCrtdDt(Instant.now());
        cnicUpd.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        cnicUpd.setLastUpdDt(Instant.now());
        cnicUpd.setUpdSts(dto.upd_sts);
        cnicUpd.setCmnt(dto.cmnt);
        //Updated by Areeba - Dated - 24-3-2022
        cnicUpd.setCnicNum(dto.cnic_num == null ? null : dto.cnic_num);
        //Ended by Areeba
        MwRefCdVal apprSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0004");

        if (dto.upd_sts.longValue() == apprSts.getRefCdSeq().longValue()) {
            List<MwLoanAppDoc> docs = new ArrayList<>();
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loan_app_seq, true);
            //
            /*MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg( dto.loan_app_seq, true );
            if ( loan != null ) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg( loan.getClntSeq(), true );
                if ( clnt != null ) {
                    clnt.setCnicExpryDt( cnicUpd.getCnicExpryDt() );
                    clnt.setLastUpdBy( SecurityContextHolder.getContext().getAuthentication().getName() );
                    clnt.setLastUpdDt( Instant.now() );
                    mwClntRepository.save( clnt );
                }
            }*/
            //Updated by Areeba - Dated - 24-3-2022
            if (type.equals("Nominee")) {
                MwClntRel clntRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(dto.loan_app_seq, 1, dto.cnic_num, true);
                if (clntRel != null) {
                    clntRel.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                    clntRel.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    clntRel.setLastUpdDt(Instant.now());
                    mwClntRelRepository.save(clntRel);
                }
                // ADDED BY YOUSAF TO FIX CLNT NOM AND PDC CNIC PICS 25-05-2022
                MwLoanAppDoc cnicFrntDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 5, true); //NOM CNIC FRONT
                MwLoanAppDoc updFrntDoc = new MwLoanAppDoc();
                MwLoanAppDoc cnicBckDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 6, true); // NOM CNIC BACK
                MwLoanAppDoc updBckDoc = new MwLoanAppDoc();
                if (cnicFrntDoc != null) {
                    cnicFrntDoc.setCrntRecFlg(false);
                    cnicFrntDoc.setDelFlg(true);
                    cnicFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicFrntDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicFrntDoc);
                }
                updFrntDoc.setCrntRecFlg(true);
                updFrntDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setCrtdDt(Instant.now());
                updFrntDoc.setDelFlg(false);
                updFrntDoc.setDocImg(cnicUpd.getCnicFrntPic());
                updFrntDoc.setDocSeq(2l);
                updFrntDoc.setEffStartDt(Instant.now());
                updFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setLastUpdDt(Instant.now());
                updFrntDoc.setLoanAppDocSeq((cnicFrntDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicFrntDoc.getLoanAppDocSeq()));
                updFrntDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updFrntDoc);

                if (cnicBckDoc != null) {
                    cnicBckDoc.setCrntRecFlg(false);
                    cnicBckDoc.setDelFlg(true);
                    cnicBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicBckDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicBckDoc);
                }
                updBckDoc.setCrntRecFlg(true);
                updBckDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setCrtdDt(Instant.now());
                updBckDoc.setDelFlg(false);
                updBckDoc.setDocImg(cnicUpd.getCnicBckPic());
                updBckDoc.setDocSeq(3l);
                updBckDoc.setEffStartDt(Instant.now());
                updBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setLastUpdDt(Instant.now());
                updBckDoc.setLoanAppDocSeq((cnicBckDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicBckDoc.getLoanAppDocSeq()));
                updBckDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updBckDoc);
            } else if (type.equals("Co-Borrower")) {
                MwClntRel clntRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(dto.loan_app_seq, 3, dto.cnic_num, true);
                if (clntRel != null) {
                    clntRel.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                    clntRel.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    clntRel.setLastUpdDt(Instant.now());
                    mwClntRelRepository.save(clntRel);
                }
                // ADDED BY YOUSAF TO FIX CLNT NOM AND PDC CNIC PICS 25-05-2022
                MwLoanAppDoc cnicFrntDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 8, true); //NOM CNIC FRONT
                MwLoanAppDoc updFrntDoc = new MwLoanAppDoc();
                MwLoanAppDoc cnicBckDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 9, true); // NOM CNIC BACK
                MwLoanAppDoc updBckDoc = new MwLoanAppDoc();
                if (cnicFrntDoc != null) {
                    cnicFrntDoc.setCrntRecFlg(false);
                    cnicFrntDoc.setDelFlg(true);
                    cnicFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicFrntDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicFrntDoc);
                }
                updFrntDoc.setCrntRecFlg(true);
                updFrntDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setCrtdDt(Instant.now());
                updFrntDoc.setDelFlg(false);
                updFrntDoc.setDocImg(cnicUpd.getCnicFrntPic());
                updFrntDoc.setDocSeq(2l);
                updFrntDoc.setEffStartDt(Instant.now());
                updFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setLastUpdDt(Instant.now());
                updFrntDoc.setLoanAppDocSeq((cnicFrntDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicFrntDoc.getLoanAppDocSeq()));
                updFrntDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updFrntDoc);

                if (cnicBckDoc != null) {
                    cnicBckDoc.setCrntRecFlg(false);
                    cnicBckDoc.setDelFlg(true);
                    cnicBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicBckDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicBckDoc);
                }
                updBckDoc.setCrntRecFlg(true);
                updBckDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setCrtdDt(Instant.now());
                updBckDoc.setDelFlg(false);
                updBckDoc.setDocImg(cnicUpd.getCnicBckPic());
                updBckDoc.setDocSeq(3l);
                updBckDoc.setEffStartDt(Instant.now());
                updBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setLastUpdDt(Instant.now());
                updBckDoc.setLoanAppDocSeq((cnicBckDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicBckDoc.getLoanAppDocSeq()));
                updBckDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updBckDoc);
            } else if (type.equals("Next of Kin")) {
                MwClntRel clntRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(dto.loan_app_seq, 2, dto.cnic_num, true);
                if (clntRel != null) {
                    clntRel.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                    clntRel.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    clntRel.setLastUpdDt(Instant.now());
                    mwClntRelRepository.save(clntRel);
                }
                // ADDED BY YOUSAF TO FIX CLNT NOM AND PDC CNIC PICS 25-05-2022
                MwLoanAppDoc cnicFrntDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 11, true); //NOM CNIC FRONT
                MwLoanAppDoc updFrntDoc = new MwLoanAppDoc();
                MwLoanAppDoc cnicBckDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 12, true); // NOM CNIC BACK
                MwLoanAppDoc updBckDoc = new MwLoanAppDoc();
                if (cnicFrntDoc != null) {
                    cnicFrntDoc.setCrntRecFlg(false);
                    cnicFrntDoc.setDelFlg(true);
                    cnicFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicFrntDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicFrntDoc);
                }
                updFrntDoc.setCrntRecFlg(true);
                updFrntDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setCrtdDt(Instant.now());
                updFrntDoc.setDelFlg(false);
                updFrntDoc.setDocImg(cnicUpd.getCnicFrntPic());
                updFrntDoc.setDocSeq(2l);
                updFrntDoc.setEffStartDt(Instant.now());
                updFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setLastUpdDt(Instant.now());
                updFrntDoc.setLoanAppDocSeq((cnicFrntDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicFrntDoc.getLoanAppDocSeq()));
                updFrntDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updFrntDoc);

                if (cnicBckDoc != null) {
                    cnicBckDoc.setCrntRecFlg(false);
                    cnicBckDoc.setDelFlg(true);
                    cnicBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicBckDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicBckDoc);
                }
                updBckDoc.setCrntRecFlg(true);
                updBckDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setCrtdDt(Instant.now());
                updBckDoc.setDelFlg(false);
                updBckDoc.setDocImg(cnicUpd.getCnicBckPic());
                updBckDoc.setDocSeq(3l);
                updBckDoc.setEffStartDt(Instant.now());
                updBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setLastUpdDt(Instant.now());
                updBckDoc.setLoanAppDocSeq((cnicBckDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicBckDoc.getLoanAppDocSeq()));
                updBckDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updBckDoc);
            } else {
                MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loan_app_seq, true);
                if (loan != null) {
                    MwClnt clnt = mwClntRepository.findOneByClntSeqAndCnicNumAndCrntRecFlg(loan.getClntSeq(), dto.cnic_num, true);
                    if (clnt != null) {
                        clnt.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                        clnt.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                        clnt.setLastUpdDt(Instant.now());
                        mwClntRepository.save(clnt);
                    }
                }
                // ADDED BY YOUSAF TO FIX CLNT NOM AND PDC CNIC PICS
                MwLoanAppDoc cnicFrntDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 2, true); //NOM CNIC FRONT
                MwLoanAppDoc updFrntDoc = new MwLoanAppDoc();
                MwLoanAppDoc cnicBckDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(dto.loan_app_seq, 3, true); // NOM CNIC BACK
                MwLoanAppDoc updBckDoc = new MwLoanAppDoc();
                if (cnicFrntDoc != null) {
                    cnicFrntDoc.setCrntRecFlg(false);
                    cnicFrntDoc.setDelFlg(true);
                    cnicFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicFrntDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicFrntDoc);
                }
                updFrntDoc.setCrntRecFlg(true);
                updFrntDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setCrtdDt(Instant.now());
                updFrntDoc.setDelFlg(false);
                updFrntDoc.setDocImg(cnicUpd.getCnicFrntPic());
                updFrntDoc.setDocSeq(2l);
                updFrntDoc.setEffStartDt(Instant.now());
                updFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updFrntDoc.setLastUpdDt(Instant.now());
                updFrntDoc.setLoanAppDocSeq((cnicFrntDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicFrntDoc.getLoanAppDocSeq()));
                updFrntDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updFrntDoc);

                if (cnicBckDoc != null) {
                    cnicBckDoc.setCrntRecFlg(false);
                    cnicBckDoc.setDelFlg(true);
                    cnicBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    cnicBckDoc.setLastUpdDt(Instant.now());
                    docs.add(cnicBckDoc);
                }
                updBckDoc.setCrntRecFlg(true);
                updBckDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setCrtdDt(Instant.now());
                updBckDoc.setDelFlg(false);
                updBckDoc.setDocImg(cnicUpd.getCnicBckPic());
                updBckDoc.setDocSeq(3l);
                updBckDoc.setEffStartDt(Instant.now());
                updBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                updBckDoc.setLastUpdDt(Instant.now());
                updBckDoc.setLoanAppDocSeq((cnicBckDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                        : cnicBckDoc.getLoanAppDocSeq()));
                updBckDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
                docs.add(updBckDoc);
            }
            mwLoanAppDocRepository.save(docs);
        }
        mwCnicUpdRepository.save(cnicUpd);
        return cnicUpd.getCnicUpdSeq();
    }

    public String verifyRecommendedAmountForKrkSubmit(TabAppDto dto) {
        try {
            if (dto.loan_info.prd_seq == 33 || dto.loan_info.prd_seq == 34) {
                Object res = em.createNativeQuery(Queries.krkRecommendedAmntQuery).setParameter("cnic_num", dto.client.basic_info.cnic_num)
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


    public String verifyRecommendedAmountForKrk(krkRecAmntDto dto) {
        try {
            Object res = em.createNativeQuery(Queries.krkRecommendedAmntQuery).setParameter("cnic_num", dto.cnicNum).setParameter("rcmnd_amt", dto.rcmndAmnt).getSingleResult();
            return res.toString();
        } catch (NoResultException e) {
            log.debug("NoResultException in  verifyRecommendedAmountForKrk:", e.getMessage());
            return "Client is not eligible for this product";
        } catch (Exception e) {
            log.debug("Exception in  verifyRecommendedAmountForKrk:", e.getMessage());
            return "Some error occurred";
        }
    }

    // Added by Zohaib Asim - Dated 27-08-2021 - CR: MFCIB Verification
    public String verifyMfcibAgnstLoanOrClnt(Long clntSeq, Long loanAppSeq, Long prdSeq) {
        Query qry = em.createNativeQuery("SELECT FN_VERIFY_MFCIB(" + clntSeq +
                ", " + loanAppSeq + ", " + prdSeq + ") FROM DUAL");
        String fnResp = qry.getSingleResult().toString();
        log.info("Verify MFCIB FN: " + fnResp);
        return fnResp;
    }
    // End by Zohaib Asim


    /*Added By Rizwan Mahfooz - Dated 24-1-2022
     * To update geo tags for those clients who does not have correct geo location in database*/
    public long updateGeoTag(String Addr_seq, Double Latitude, Double Longitude) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            MwAddr mwAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(Long.parseLong(Addr_seq), true);
            if (mwAddr != null) {
                mwAddr.setLatitude(Latitude);
                mwAddr.setLongitude(Longitude);
                mwAddr.lastUpdBy(userId);
                mwAddr.lastUpdDt(Instant.now());
                return mwAddrRepository.save(mwAddr).getAddrSeq();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /*Added By Rizwan Mahfooz - Dated 24-1-2022
     * DB FUNCTION TO PERFORM DATA SYNC*/
    @Transactional
    public Map<String, Object> getTabletDataForDeviceRegistered(String mac, Boolean completeDataRequest) {
        log.info("DataSync-> mac: " + mac + ", CompleteData: " + completeDataRequest);

        ObjectMapper mapper = new ObjectMapper();
        String request = "";
        Map<String, Object> mapRespObj = new HashMap<>();

        //
        if (completeDataRequest) {
            request = "1";
        } else {
            request = "0";
        }
        //
        try {
            Query qry = em.createNativeQuery("select FN_DATA_SYNC (\'" + mac + "\'," + request + ") from dual");
            Object fnResp = qry.getSingleResult();

            if (fnResp != null) {
                Clob clob = (Clob) fnResp;
                mapRespObj = (Map<String, Object>) mapper.readValue(clob.getSubString(1, (int) clob.length()), Object.class);
            }
        } catch (Exception ex) {
            log.error("DataSyncException: " + ex.getMessage());
            mapRespObj.put("error", ex.getMessage());
            //ex.printStackTrace();
        }

        return mapRespObj;

    }


    /*Added By Rizwan Mahfooz - Dated 15-FEB-2022
     * To send cnic expiry pictures*/
    public Map<String, Object> sendExpiryPictures(String loanAppSeq) {
        log.info("sendExpiryPictures-> LoanSeq: " + loanAppSeq);

        try {
            MwCnicUpd cnic_upd = mwCnicUpdRepository.findOneByLoanAppSeq(Long.parseLong(loanAppSeq));
            if (cnic_upd != null) {
//                JSONObject object = new JSONObject();
                Map<String, Object> object = new HashMap<String, Object>();
                object.put("loan_app_seq", cnic_upd.getLoanAppSeq());
                object.put("cnic_expry_dt", new SimpleDateFormat("dd-MM-yyyy").format(Date.from(cnic_upd.getCnicExpryDt())));
                object.put("cnic_frnt_pic", cnic_upd.getCnicFrntPic());
                object.put("cnic_bck_pic", cnic_upd.getCnicBckPic());
                object.put("crtd_by", cnic_upd.getCrtdBy());
                object.put("crtd_dt", cnic_upd.getCrtdDt());
                object.put("last_upd_by", cnic_upd.getLastUpdBy());
                object.put("last_upd_dt", cnic_upd.getLastUpdDt());
                object.put("cmnt", cnic_upd.getCmnt());
                object.put("upd_sts", cnic_upd.getUpdSts());

                log.info("CNIC ExpiryDate: " + object.get("cnic_expry_dt"));
                return object;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*Added By Rizwan Mahfooz - Dated 21-2-2022
     * To update phone numbers for clients*/

    public String updatePhoneNumber(String Phone_number, String client_seq) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("updatePhoneNumber-> PhoneNo: " + Phone_number + ", ClntSeq: " + client_seq);
        try {
            MwClnt mwClnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(Long.parseLong(client_seq), true);
            if (mwClnt != null) {
                mwClnt.setPhNum(Phone_number);
                mwClnt.lastUpdBy(userId);
                mwClnt.lastUpdDt(Instant.now());
                return mwClntRepository.save(mwClnt).getPhNum();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*Added By Rizwan Mahfooz - Dated 08-03-2022
     * To update geo tags for branches in database*/
    public long updateGeoTagForBranch(String Addr_seq, Double Latitude, Double Longitude) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            MwAddr mwAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(Long.parseLong(Addr_seq), true);
            if (mwAddr != null) {
                mwAddr.setLatitude(Latitude);
                mwAddr.setLongitude(Longitude);
                mwAddr.lastUpdBy(userId);
                mwAddr.lastUpdDt(Instant.now());
                return mwAddrRepository.save(mwAddr).getAddrSeq();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /* Added by Areeba Naveed
       Dated:   17-06-2022
       Reason:  Parsing Tasdeeq - Response */
    @Transactional
    @Timed
    public String postTasdeeqWithParsing(JSONObject rptJsonObj, Long loanAppDocSeq) {
        //
        List<MwLoanAppCrdtSumry> loanAppCrdtSumryList = new ArrayList<>();
        List<MwLoanAppMfcibData> loanAppMfcibDataList = new ArrayList<>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        //
        try {
            JSONObject root = rptJsonObj.getJSONObject("data");
            if (root != null) {
//                //---------------------------------------------------------------------------------------------------------
//                // 1. summaryOverdue_24M
//                //---------------------------------------------------------------------------------------------------------
                JSONArray summaryOverdues = new JSONArray();
                Object sumOverdueObj = root.has("summaryOverdue_24M") ? root.get("summaryOverdue_24M") : null;
                if (sumOverdueObj instanceof JSONArray) {
                    // JSON Array
                    summaryOverdues = (JSONArray) sumOverdueObj;
                    for (int index = 0; index < summaryOverdues.length(); index++) {
                        JSONObject jsonObject = summaryOverdues.getJSONObject(index);
                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("summaryOverdue_24M");
                        mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90_24M") ? String.valueOf(jsonObject.get("PLUS_90_24M")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60_24M") ? String.valueOf(jsonObject.get("PLUS_60_24M")) : "");
                        mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30_24M") ? String.valueOf(jsonObject.get("PLUS_30_24M")) : "");
                        mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150_24M") ? String.valueOf(jsonObject.get("PLUS_150_24M")) : "");
                        mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120_24M") ? String.valueOf(jsonObject.get("PLUS_120_24M")) : "");
                        mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180_24M") ? String.valueOf(jsonObject.get("PLUS_180_24M")) : "");
                        mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (sumOverdueObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) sumOverdueObj;
                    // summaryArr.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("summaryOverdue_24M");
                    mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90_24M") ? String.valueOf(jsonObject.get("PLUS_90_24M")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60_24M") ? String.valueOf(jsonObject.get("PLUS_60_24M")) : "");
                    mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30_24M") ? String.valueOf(jsonObject.get("PLUS_30_24M")) : "");
                    mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150_24M") ? String.valueOf(jsonObject.get("PLUS_150_24M")) : "");
                    mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120_24M") ? String.valueOf(jsonObject.get("PLUS_120_24M")) : "");
                    mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180_24M") ? String.valueOf(jsonObject.get("PLUS_180_24M")) : "");
                    mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("summaryOverdue_24M");
                root.put("summaryOverdue_24M", summaryOverdues);

                //---------------------------------------------------------------------------------------------------------
                // 2. loanDetails
                //---------------------------------------------------------------------------------------------------------
                JSONArray loanDetails = new JSONArray();
                Object loanDetailsObj = root.has("loanDetails") ? root.get("loanDetails") : null;
                if (loanDetailsObj instanceof JSONArray) {
                    // JSON Array
                    loanDetails = (JSONArray) loanDetailsObj;
                    for (int index = 0; index < loanDetails.length(); index++) {
                        JSONObject jsonObject = loanDetails.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("loanDetails");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("LOAN_SERIAL_NUMBER") ? String.valueOf(jsonObject.get("LOAN_SERIAL_NUMBER")) : "");
                        mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_ID") ? String.valueOf(jsonObject.get("LOAN_ID")) : "");
                        mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90") ? String.valueOf(jsonObject.get("PLUS_90")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60") ? String.valueOf(jsonObject.get("PLUS_60")) : "");
                        mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30") ? String.valueOf(jsonObject.get("PLUS_30")) : "");
                        mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150") ? String.valueOf(jsonObject.get("PLUS_150")) : "");
                        mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120") ? String.valueOf(jsonObject.get("PLUS_120")) : "");
                        mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180") ? String.valueOf(jsonObject.get("PLUS_180")) : "");
                        mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                        mwLoanAppMfcibData.setLatePayment1To15(jsonObject.has("LATE_PAYMENT_1TO15") ? String.valueOf(jsonObject.get("LATE_PAYMENT_1TO15")) : "");
                        mwLoanAppMfcibData.setLatePayment16To20(jsonObject.has("LATE_PAYMENT_16TO20") ? String.valueOf(jsonObject.get("LATE_PAYMENT_16TO20")) : "");
                        mwLoanAppMfcibData.setLatePayment21To29(jsonObject.has("LATE_PAYMENT_21TO29") ? String.valueOf(jsonObject.get("LATE_PAYMENT_21TO29")) : "");
                        mwLoanAppMfcibData.setLatePayment30(jsonObject.has("LATE_PAYMENT_30") ? String.valueOf(jsonObject.get("LATE_PAYMENT_30")) : "");
                        mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("COLLATERAL_AMOUNT") ? String.valueOf(jsonObject.get("COLLATERAL_AMOUNT")) : "");
                        mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LOAN_LIMIT") ? String.valueOf(jsonObject.get("LOAN_LIMIT")) : "");
                        mwLoanAppMfcibData.setAcctTy(jsonObject.has("LOAN_TYPE") ? String.valueOf(jsonObject.get("LOAN_TYPE")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("BANK_NAME") ? String.valueOf(jsonObject.get("BANK_NAME")) : "");
                        mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_APPLICATION") ? String.valueOf(jsonObject.get("DATE_OF_APPLICATION")) : "");
                        mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                        mwLoanAppMfcibData.setRescheduleDt(jsonObject.has("RESTRUCTURING_DATE") ? String.valueOf(jsonObject.get("RESTRUCTURING_DATE")) : "");
                        mwLoanAppMfcibData.setAssocTy(jsonObject.has("CLASSIFICATION_TYPE") ? String.valueOf(jsonObject.get("CLASSIFICATION_TYPE")) : "");
                        mwLoanAppMfcibData.setStatusDt(jsonObject.has("POSITION_AS_OF") ? String.valueOf(jsonObject.get("POSITION_AS_OF")) : "");
                        mwLoanAppMfcibData.setBalance(jsonObject.has("OUTSTANDING_BALANCE") ? String.valueOf(jsonObject.get("OUTSTANDING_BALANCE")) : "");
                        mwLoanAppMfcibData.setLastPymt(jsonObject.has("LOAN_LAST_PAYMENT_AMOUNT") ? String.valueOf(jsonObject.get("LOAN_LAST_PAYMENT_AMOUNT")) : "");
                        mwLoanAppMfcibData.setAcctSts(jsonObject.has("LOAN_ACCOUNT_STATUS") ? String.valueOf(jsonObject.get("LOAN_ACCOUNT_STATUS")) : "");
                        mwLoanAppMfcibData.setSecure(jsonObject.has("SECURED_UNSECURED") ? String.valueOf(jsonObject.get("SECURED_UNSECURED")) : "");
                        mwLoanAppMfcibData.setMinAmtDue(jsonObject.has("MINIMUM_AMOUNT_DUE") ? String.valueOf(jsonObject.get("MINIMUM_AMOUNT_DUE")) : "");
                        mwLoanAppMfcibData.setRepaymentFreq(jsonObject.has("REPAYMENT_FREQUENCY") ? String.valueOf(jsonObject.get("REPAYMENT_FREQUENCY")) : "");
                        mwLoanAppMfcibData.setOpenDt(jsonObject.has("FACILITY_DATE") ? String.valueOf(jsonObject.get("FACILITY_DATE")) : "");
                        mwLoanAppMfcibData.setDateOfLastPaymentMade(jsonObject.has("DATE_OF_LAST_PAYMENT_MADE") ? String.valueOf(jsonObject.get("DATE_OF_LAST_PAYMENT_MADE")) : "");
                        mwLoanAppMfcibData.setClassificationNature(jsonObject.has("CLASSIFICATION_NATURE") ? String.valueOf(jsonObject.get("CLASSIFICATION_NATURE")) : "");
                        mwLoanAppMfcibData.setLitigationAmt(jsonObject.has("LITIGATION_AMOUNT") ? String.valueOf(jsonObject.get("LITIGATION_AMOUNT")) : "");
                        mwLoanAppMfcibData.setBouncedRepaymentCheques(jsonObject.has("BOUNCED_REPAYMENT_CHEQUES") ? String.valueOf(jsonObject.get("BOUNCED_REPAYMENT_CHEQUES")) : "");
                        mwLoanAppMfcibData.setSecurityCollateral(jsonObject.has("SECURITY_COLLATERAL") ? String.valueOf(jsonObject.get("SECURITY_COLLATERAL")) : "");
                        mwLoanAppMfcibData.setRestructuringAmt(jsonObject.has("RESTRUCTURING_AMOUNT") ? String.valueOf(jsonObject.get("RESTRUCTURING_AMOUNT")) : "");
                        mwLoanAppMfcibData.setWriteoffTyp(jsonObject.has("WRITEOFF_TYPE") ? String.valueOf(jsonObject.get("WRITEOFF_TYPE")) : "");
                        mwLoanAppMfcibData.setWriteOffAmt(jsonObject.has("WRITE_OFF_AMOUNT") ? String.valueOf(jsonObject.get("WRITE_OFF_AMOUNT")) : "");
                        mwLoanAppMfcibData.setWriteoffDt(jsonObject.has("WRITEOFF_DATE") ? String.valueOf(jsonObject.get("WRITEOFF_DATE")) : "");
                        mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RECOVERY_DATE") ? String.valueOf(jsonObject.get("RECOVERY_DATE")) : "");
                        mwLoanAppMfcibData.setRecoveryAmount(jsonObject.has("RECOVERY_AMOUNT") ? String.valueOf(jsonObject.get("RECOVERY_AMOUNT")) : "");
                        mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                        mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                        mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (loanDetailsObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) loanDetailsObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("loanDetails");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("LOAN_SERIAL_NUMBER") ? String.valueOf(jsonObject.get("LOAN_SERIAL_NUMBER")) : "");
                    mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_ID") ? String.valueOf(jsonObject.get("LOAN_ID")) : "");
                    mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90") ? String.valueOf(jsonObject.get("PLUS_90")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60") ? String.valueOf(jsonObject.get("PLUS_60")) : "");
                    mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30") ? String.valueOf(jsonObject.get("PLUS_30")) : "");
                    mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150") ? String.valueOf(jsonObject.get("PLUS_150")) : "");
                    mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120") ? String.valueOf(jsonObject.get("PLUS_120")) : "");
                    mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180") ? String.valueOf(jsonObject.get("PLUS_180")) : "");
                    mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                    mwLoanAppMfcibData.setLatePayment1To15(jsonObject.has("LATE_PAYMENT_1TO15") ? String.valueOf(jsonObject.get("LATE_PAYMENT_1TO15")) : "");
                    mwLoanAppMfcibData.setLatePayment16To20(jsonObject.has("LATE_PAYMENT_16TO20") ? String.valueOf(jsonObject.get("LATE_PAYMENT_16TO20")) : "");
                    mwLoanAppMfcibData.setLatePayment21To29(jsonObject.has("LATE_PAYMENT_21TO29") ? String.valueOf(jsonObject.get("LATE_PAYMENT_21TO29")) : "");
                    mwLoanAppMfcibData.setLatePayment30(jsonObject.has("LATE_PAYMENT_30") ? String.valueOf(jsonObject.get("LATE_PAYMENT_30")) : "");
                    mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("COLLATERAL_AMOUNT") ? String.valueOf(jsonObject.get("COLLATERAL_AMOUNT")) : "");
                    mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LOAN_LIMIT") ? String.valueOf(jsonObject.get("LOAN_LIMIT")) : "");
                    mwLoanAppMfcibData.setAcctTy(jsonObject.has("LOAN_TYPE") ? String.valueOf(jsonObject.get("LOAN_TYPE")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("BANK_NAME") ? String.valueOf(jsonObject.get("BANK_NAME")) : "");
                    mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_APPLICATION") ? String.valueOf(jsonObject.get("DATE_OF_APPLICATION")) : "");
                    mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                    mwLoanAppMfcibData.setRescheduleDt(jsonObject.has("RESTRUCTURING_DATE") ? String.valueOf(jsonObject.get("RESTRUCTURING_DATE")) : "");
                    mwLoanAppMfcibData.setAssocTy(jsonObject.has("CLASSIFICATION_TYPE") ? String.valueOf(jsonObject.get("CLASSIFICATION_TYPE")) : "");
                    mwLoanAppMfcibData.setStatusDt(jsonObject.has("POSITION_AS_OF") ? String.valueOf(jsonObject.get("POSITION_AS_OF")) : "");
                    mwLoanAppMfcibData.setBalance(jsonObject.has("OUTSTANDING_BALANCE") ? String.valueOf(jsonObject.get("OUTSTANDING_BALANCE")) : "");
                    mwLoanAppMfcibData.setLastPymt(jsonObject.has("LOAN_LAST_PAYMENT_AMOUNT") ? String.valueOf(jsonObject.get("LOAN_LAST_PAYMENT_AMOUNT")) : "");
                    mwLoanAppMfcibData.setAcctSts(jsonObject.has("LOAN_ACCOUNT_STATUS") ? String.valueOf(jsonObject.get("LOAN_ACCOUNT_STATUS")) : "");
                    mwLoanAppMfcibData.setSecure(jsonObject.has("SECURED_UNSECURED") ? String.valueOf(jsonObject.get("SECURED_UNSECURED")) : "");
                    mwLoanAppMfcibData.setMinAmtDue(jsonObject.has("MINIMUM_AMOUNT_DUE") ? String.valueOf(jsonObject.get("MINIMUM_AMOUNT_DUE")) : "");
                    mwLoanAppMfcibData.setRepaymentFreq(jsonObject.has("REPAYMENT_FREQUENCY") ? String.valueOf(jsonObject.get("REPAYMENT_FREQUENCY")) : "");
                    mwLoanAppMfcibData.setOpenDt(jsonObject.has("FACILITY_DATE") ? String.valueOf(jsonObject.get("FACILITY_DATE")) : "");
                    mwLoanAppMfcibData.setDateOfLastPaymentMade(jsonObject.has("DATE_OF_LAST_PAYMENT_MADE") ? String.valueOf(jsonObject.get("DATE_OF_LAST_PAYMENT_MADE")) : "");
                    mwLoanAppMfcibData.setClassificationNature(jsonObject.has("CLASSIFICATION_NATURE") ? String.valueOf(jsonObject.get("CLASSIFICATION_NATURE")) : "");
                    mwLoanAppMfcibData.setLitigationAmt(jsonObject.has("LITIGATION_AMOUNT") ? String.valueOf(jsonObject.get("LITIGATION_AMOUNT")) : "");
                    mwLoanAppMfcibData.setBouncedRepaymentCheques(jsonObject.has("BOUNCED_REPAYMENT_CHEQUES") ? String.valueOf(jsonObject.get("BOUNCED_REPAYMENT_CHEQUES")) : "");
                    mwLoanAppMfcibData.setSecurityCollateral(jsonObject.has("SECURITY_COLLATERAL") ? String.valueOf(jsonObject.get("SECURITY_COLLATERAL")) : "");
                    mwLoanAppMfcibData.setRestructuringAmt(jsonObject.has("RESTRUCTURING_AMOUNT") ? String.valueOf(jsonObject.get("RESTRUCTURING_AMOUNT")) : "");
                    mwLoanAppMfcibData.setWriteoffTyp(jsonObject.has("WRITEOFF_TYPE") ? String.valueOf(jsonObject.get("WRITEOFF_TYPE")) : "");
                    mwLoanAppMfcibData.setWriteOffAmt(jsonObject.has("WRITE_OFF_AMOUNT") ? String.valueOf(jsonObject.get("WRITE_OFF_AMOUNT")) : "");
                    mwLoanAppMfcibData.setWriteoffDt(jsonObject.has("WRITEOFF_DATE") ? String.valueOf(jsonObject.get("WRITEOFF_DATE")) : "");
                    mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RECOVERY_DATE") ? String.valueOf(jsonObject.get("RECOVERY_DATE")) : "");
                    mwLoanAppMfcibData.setRecoveryAmount(jsonObject.has("RECOVERY_AMOUNT") ? String.valueOf(jsonObject.get("RECOVERY_AMOUNT")) : "");
                    mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                    mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                    mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("loanDetails");
                root.put("loanDetails", loanDetails);

                //---------------------------------------------------------------------------------------------------------
                // 3. creditHistory
                //---------------------------------------------------------------------------------------------------------
                JSONArray credHist = new JSONArray();
                Object credHistObj = root.has("creditHistory") ? root.get("creditHistory") : null;
                if (credHistObj instanceof JSONArray) {
                    // JSON Array
                    credHist = (JSONArray) credHistObj;
                    for (int index = 0; index < credHist.length(); index++) {
                        JSONObject jsonObject = credHist.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("creditHistory");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("LOAN_SERIAL_NUMBER") ? String.valueOf(jsonObject.get("LOAN_SERIAL_NUMBER")) : "");
                        mwLoanAppMfcibData.setStsMnth(jsonObject.has("MONTH_NAME") ? String.valueOf(jsonObject.get("MONTH_NAME")) : "");
                        mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90") ? String.valueOf(jsonObject.get("PLUS_90")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60") ? String.valueOf(jsonObject.get("PLUS_60")) : "");
                        mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30") ? String.valueOf(jsonObject.get("PLUS_30")) : "");
                        mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150") ? String.valueOf(jsonObject.get("PLUS_150")) : "");
                        mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120") ? String.valueOf(jsonObject.get("PLUS_120")) : "");
                        mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180") ? String.valueOf(jsonObject.get("PLUS_180")) : "");
                        mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                        mwLoanAppMfcibData.setLatePmtDays(jsonObject.has("LATE_PMT_DAYS") ? String.valueOf(jsonObject.get("LATE_PMT_DAYS")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (credHistObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) credHistObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("creditHistory");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("LOAN_SERIAL_NUMBER") ? String.valueOf(jsonObject.get("LOAN_SERIAL_NUMBER")) : "");
                    mwLoanAppMfcibData.setStsMnth(jsonObject.has("MONTH_NAME") ? String.valueOf(jsonObject.get("MONTH_NAME")) : "");
                    mwLoanAppMfcibData.setP90(jsonObject.has("PLUS_90") ? String.valueOf(jsonObject.get("PLUS_90")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("PLUS_60") ? String.valueOf(jsonObject.get("PLUS_60")) : "");
                    mwLoanAppMfcibData.setP30(jsonObject.has("PLUS_30") ? String.valueOf(jsonObject.get("PLUS_30")) : "");
                    mwLoanAppMfcibData.setP150(jsonObject.has("PLUS_150") ? String.valueOf(jsonObject.get("PLUS_150")) : "");
                    mwLoanAppMfcibData.setP120(jsonObject.has("PLUS_120") ? String.valueOf(jsonObject.get("PLUS_120")) : "");
                    mwLoanAppMfcibData.setP180(jsonObject.has("PLUS_180") ? String.valueOf(jsonObject.get("PLUS_180")) : "");
                    mwLoanAppMfcibData.setMfiDefault(jsonObject.has("MFI_DEFAULT") ? String.valueOf(jsonObject.get("MFI_DEFAULT")) : "");
                    mwLoanAppMfcibData.setLatePmtDays(jsonObject.has("LATE_PMT_DAYS") ? String.valueOf(jsonObject.get("LATE_PMT_DAYS")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("creditHistory");
                root.put("creditHistory", credHist);

                //---------------------------------------------------------------------------------------------------------
                // 4. personalGuarantees
                //---------------------------------------------------------------------------------------------------------
                JSONArray persGrnty = new JSONArray();
                Object persGrntyObj = root.has("personalGuarantees") ? root.get("personalGuarantees") : null;
                if (persGrntyObj instanceof JSONArray) {
                    // JSON Array
                    persGrnty = (JSONArray) persGrntyObj;
                    for (int index = 0; index < persGrnty.length(); index++) {
                        JSONObject jsonObject = persGrnty.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("personalGuarantees");
                        mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("PRINCIPAL_BORROWER_CNIC") ? String.valueOf(jsonObject.get("PRINCIPAL_BORROWER_CNIC")) : ""); // PRINCIPAL_BORROWER_CNIC
                        mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : ""); //no
                        mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : ""); //no
                        mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : ""); //no
                        mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : ""); //no
                        mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : ""); //no
                        mwLoanAppMfcibData.setCoBrwrNm(jsonObject.has("PRINCIPAL_BORROWER_NAME") ? String.valueOf(jsonObject.get("PRINCIPAL_BORROWER_NAME")) : "");
                        mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                        mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                        mwLoanAppMfcibData.setInvocationDt(jsonObject.has("DATE_OF_INVOCATION") ? String.valueOf(jsonObject.get("DATE_OF_INVOCATION")) : "");
                        mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                        mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : ""); //no
                        mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : ""); //no
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (persGrntyObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) persGrntyObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("personalGuarantees");
                    mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("PRINCIPAL_BORROWER_CNIC") ? String.valueOf(jsonObject.get("PRINCIPAL_BORROWER_CNIC")) : ""); // PRINCIPAL_BORROWER_CNIC
                    mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : ""); //no
                    mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : ""); //no
                    mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : ""); //no
                    mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : ""); //no
                    mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : ""); //no
                    mwLoanAppMfcibData.setCoBrwrNm(jsonObject.has("PRINCIPAL_BORROWER_NAME") ? String.valueOf(jsonObject.get("PRINCIPAL_BORROWER_NAME")) : "");
                    mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                    mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                    mwLoanAppMfcibData.setInvocationDt(jsonObject.has("DATE_OF_INVOCATION") ? String.valueOf(jsonObject.get("DATE_OF_INVOCATION")) : "");
                    mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                    mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : ""); //no
                    mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : ""); //no
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("personalGuarantees");
                root.put("personalGuarantees", persGrnty);

                //---------------------------------------------------------------------------------------------------------
                // 5. coborrowerDetail
                //---------------------------------------------------------------------------------------------------------
                JSONArray cobrwrDtl = new JSONArray();
                Object cobrwrDtlObj = root.has("coborrowerDetail") ? root.get("coborrowerDetail") : null;
                if (cobrwrDtlObj instanceof JSONArray) {
                    // JSON Array
                    cobrwrDtl = (JSONArray) cobrwrDtlObj;
                    for (int index = 0; index < cobrwrDtl.length(); index++) {
                        JSONObject jsonObject = cobrwrDtl.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("coborrowerDetail");
                        mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                        mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : ""); //no
                        mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : ""); //no
                        mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                        mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : ""); //no
                        mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : ""); //no
                        mwLoanAppMfcibData.setCoBrwrNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                        mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                        mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                        mwLoanAppMfcibData.setInvocationDt(jsonObject.has("DATE_OF_INVOCATION") ? String.valueOf(jsonObject.get("DATE_OF_INVOCATION")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (cobrwrDtlObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) cobrwrDtlObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("coborrowerDetail");
                    mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : ""); //no
                    mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : ""); //no
                    mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                    mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : ""); //no
                    mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : ""); //no
                    mwLoanAppMfcibData.setCoBrwrNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                    mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                    mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                    mwLoanAppMfcibData.setInvocationDt(jsonObject.has("DATE_OF_INVOCATION") ? String.valueOf(jsonObject.get("DATE_OF_INVOCATION")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("coborrowerDetail");
                root.put("coborrowerDetail", cobrwrDtl);

                //---------------------------------------------------------------------------------------------------------
                // 6. additionalInformation
                //---------------------------------------------------------------------------------------------------------
                JSONArray additionalInfo = new JSONArray();
                Object additionalInfoObj = root.has("additionalInformation") ? root.get("additionalInformation") : null;
                if (additionalInfoObj instanceof JSONArray) {
                    // JSON Array
                    additionalInfo = (JSONArray) additionalInfoObj;
                    for (int index = 0; index < additionalInfo.length(); index++) {
                        JSONObject jsonObject = additionalInfo.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("additionalInformation");
                        mwLoanAppMfcibData.setLoanLess10K(jsonObject.has("nominee1_Nano_Loan_Count") ? String.valueOf(jsonObject.get("nominee1_Nano_Loan_Count")) : "");
                        mwLoanAppMfcibData.setCloseWithinMaturity(jsonObject.has("nominee1_Count_Completed_Within_Maturity") ? String.valueOf(jsonObject.get("nominee1_Count_Completed_Within_Maturity")) : "");
                        mwLoanAppMfcibData.setCloseAfterMaturity(jsonObject.has("nominee1_Count_Completed_After_Maturity") ? String.valueOf(jsonObject.get("nominee1_Count_Completed_After_Maturity")) : "");
                        mwLoanAppMfcibData.setMonth2430Plus(jsonObject.has("nominee1_Payments_PLUS_30") ? String.valueOf(jsonObject.get("nominee1_Payments_PLUS_30")) : "");
                        mwLoanAppMfcibData.setCsName(jsonObject.has("nominee1_NAME") ? String.valueOf(jsonObject.get("nominee1_NAME")) : "");
                        mwLoanAppMfcibData.setLoanCount(jsonObject.has("nominee1_Updated_Active_Accounts") ? String.valueOf(jsonObject.get("nominee1_Updated_Active_Accounts")) : "");
                        mwLoanAppMfcibData.setLoanOs(jsonObject.has("nominee1_Updated_Active_Balance") ? String.valueOf(jsonObject.get("nominee1_Updated_Active_Balance")) : "");
                        mwLoanAppMfcibData.setDefaultCount(jsonObject.has("nominee1_Count_Default_Loans") ? String.valueOf(jsonObject.get("nominee1_Count_Default_Loans")) : "");
                        mwLoanAppMfcibData.setDefaultOs(jsonObject.has("nominee1_Amount_Default_Loans") ? String.valueOf(jsonObject.get("nominee1_Amount_Default_Loans")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("nominee1_CNIC") ? String.valueOf(jsonObject.get("nominee1_CNIC")) : "");
                        mwLoanAppMfcibData.setCurrent60Plus(jsonObject.has("nominee1_Count_Overdue_Loans_60") ? String.valueOf(jsonObject.get("nominee1_Count_Overdue_Loans_60")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (additionalInfoObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) additionalInfoObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("additionalInformation");
                    mwLoanAppMfcibData.setLoanLess10K(jsonObject.has("nominee1_Nano_Loan_Count") ? String.valueOf(jsonObject.get("nominee1_Nano_Loan_Count")) : "");
                    mwLoanAppMfcibData.setCloseWithinMaturity(jsonObject.has("nominee1_Count_Completed_Within_Maturity") ? String.valueOf(jsonObject.get("nominee1_Count_Completed_Within_Maturity")) : "");
                    mwLoanAppMfcibData.setCloseAfterMaturity(jsonObject.has("nominee1_Count_Completed_After_Maturity") ? String.valueOf(jsonObject.get("nominee1_Count_Completed_After_Maturity")) : "");
                    mwLoanAppMfcibData.setMonth2430Plus(jsonObject.has("nominee1_Payments_PLUS_30") ? String.valueOf(jsonObject.get("nominee1_Payments_PLUS_30")) : "");
                    mwLoanAppMfcibData.setCsName(jsonObject.has("nominee1_NAME") ? String.valueOf(jsonObject.get("nominee1_NAME")) : "");
                    mwLoanAppMfcibData.setLoanCount(jsonObject.has("nominee1_Updated_Active_Accounts") ? String.valueOf(jsonObject.get("nominee1_Updated_Active_Accounts")) : "");
                    mwLoanAppMfcibData.setLoanOs(jsonObject.has("nominee1_Updated_Active_Balance") ? String.valueOf(jsonObject.get("nominee1_Updated_Active_Balance")) : "");
                    mwLoanAppMfcibData.setDefaultCount(jsonObject.has("nominee1_Count_Default_Loans") ? String.valueOf(jsonObject.get("nominee1_Count_Default_Loans")) : "");
                    mwLoanAppMfcibData.setDefaultOs(jsonObject.has("nominee1_Amount_Default_Loans") ? String.valueOf(jsonObject.get("nominee1_Amount_Default_Loans")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("nominee1_CNIC") ? String.valueOf(jsonObject.get("nominee1_CNIC")) : "");
                    mwLoanAppMfcibData.setCurrent60Plus(jsonObject.has("nominee1_Count_Overdue_Loans_60") ? String.valueOf(jsonObject.get("nominee1_Count_Overdue_Loans_60")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("additionalInformation");
                root.put("additionalInformation", additionalInfo);

                //---------------------------------------------------------------------------------------------------------
                // 7. detailsOfBankruptcyCases
                //---------------------------------------------------------------------------------------------------------
                JSONArray bnkrptcy = new JSONArray();
                Object bnkrptcyObj = root.has("detailsOfBankruptcyCases") ? root.get("detailsOfBankruptcyCases") : null;
                if (bnkrptcyObj instanceof JSONArray) {
                    // JSON Array
                    bnkrptcy = (JSONArray) bnkrptcyObj;
                    for (int index = 0; index < bnkrptcy.length(); index++) {
                        JSONObject jsonObject = bnkrptcy.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("detailsOfBankruptcyCases");
                        mwLoanAppMfcibData.setCourtNm(jsonObject.has("COURT_NAME") ? String.valueOf(jsonObject.get("COURT_NAME")) : "");
                        mwLoanAppMfcibData.setDclrtnDt(jsonObject.has("DECLARATION_DATE") ? String.valueOf(jsonObject.get("DECLARATION_DATE")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (bnkrptcyObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) bnkrptcyObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("detailsOfBankruptcyCases");
                    mwLoanAppMfcibData.setCourtNm(jsonObject.has("COURT_NAME") ? String.valueOf(jsonObject.get("COURT_NAME")) : "");
                    mwLoanAppMfcibData.setDclrtnDt(jsonObject.has("DECLARATION_DATE") ? String.valueOf(jsonObject.get("DECLARATION_DATE")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("detailsOfBankruptcyCases");
                root.put("detailsOfBankruptcyCases", bnkrptcy);

                //---------------------------------------------------------------------------------------------------------
                // 8. detailsOfLoansSettlement
                //---------------------------------------------------------------------------------------------------------
                JSONArray loanStlmnt = new JSONArray();
                Object loanStlmntObj = root.has("detailsOfLoansSettlement") ? root.get("detailsOfLoansSettlement") : null;
                if (loanStlmntObj instanceof JSONArray) {
                    // JSON Array
                    loanStlmnt = (JSONArray) loanStlmntObj;
                    for (int index = 0; index < loanStlmnt.length(); index++) {
                        JSONObject jsonObject = loanStlmnt.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("detailsOfLoansSettlement");
                        mwLoanAppMfcibData.setApplicationDt(jsonObject.has("APPROVAL_DATE") ? String.valueOf(jsonObject.get("APPROVAL_DATE")) : "");
                        mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                        mwLoanAppMfcibData.setStatusDt(jsonObject.has("DATE_OF_SETTLEMENT") ? String.valueOf(jsonObject.get("DATE_OF_SETTLEMENT")) : "");
                        mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RELATIONSHIP_DATE") ? String.valueOf(jsonObject.get("RELATIONSHIP_DATE")) : "");
                        mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                        mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                        mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                        mwLoanAppMfcibData.setTotalLimit(jsonObject.has("TOTAL_LIMIT") ? String.valueOf(jsonObject.get("TOTAL_LIMIT")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (loanStlmntObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) loanStlmntObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("detailsOfLoansSettlement");
                    mwLoanAppMfcibData.setApplicationDt(jsonObject.has("APPROVAL_DATE") ? String.valueOf(jsonObject.get("APPROVAL_DATE")) : "");
                    mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                    mwLoanAppMfcibData.setStatusDt(jsonObject.has("DATE_OF_SETTLEMENT") ? String.valueOf(jsonObject.get("DATE_OF_SETTLEMENT")) : "");
                    mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RELATIONSHIP_DATE") ? String.valueOf(jsonObject.get("RELATIONSHIP_DATE")) : "");
                    mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                    mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                    mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                    mwLoanAppMfcibData.setTotalLimit(jsonObject.has("TOTAL_LIMIT") ? String.valueOf(jsonObject.get("TOTAL_LIMIT")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("detailsOfLoansSettlement");
                root.put("detailsOfLoansSettlement", loanStlmnt);

                //---------------------------------------------------------------------------------------------------------
                // 9. creditEnquiry
                //---------------------------------------------------------------------------------------------------------
                JSONArray crdtEnqry = new JSONArray();
                Object crdtEnqryObj = root.has("creditEnquiry") ? root.get("creditEnquiry") : null;
                if (crdtEnqryObj instanceof JSONArray) {
                    // JSON Array
                    crdtEnqry = (JSONArray) crdtEnqryObj;
                    for (int index = 0; index < crdtEnqry.length(); index++) {
                        JSONObject jsonObject = crdtEnqry.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("creditEnquiry");
                        mwLoanAppMfcibData.setRefDt(jsonObject.has("DATE_OF_ENQUIRY") ? String.valueOf(jsonObject.get("DATE_OF_ENQUIRY")) : "");
                        mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("SR_NO") ? String.valueOf(jsonObject.get("SR_NO")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("FI_TYPE") ? String.valueOf(jsonObject.get("FI_TYPE")) : "");
                        mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_ENQUIRY") ? String.valueOf(jsonObject.get("DATE_OF_ENQUIRY")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (crdtEnqryObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) crdtEnqryObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("creditEnquiry");
                    mwLoanAppMfcibData.setRefDt(jsonObject.has("DATE_OF_ENQUIRY") ? String.valueOf(jsonObject.get("DATE_OF_ENQUIRY")) : "");
                    mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("SR_NO") ? String.valueOf(jsonObject.get("SR_NO")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("FI_TYPE") ? String.valueOf(jsonObject.get("FI_TYPE")) : "");
                    mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_ENQUIRY") ? String.valueOf(jsonObject.get("DATE_OF_ENQUIRY")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("creditEnquiry");
                root.put("creditEnquiry", crdtEnqry);

                //---------------------------------------------------------------------------------------------------------
                // 10. detailsOfStatusCreditApplication
                //---------------------------------------------------------------------------------------------------------
                JSONArray stsCrdtApp = new JSONArray();
                Object stsCrdtAppObj = root.has("detailsOfStatusCreditApplication") ? root.get("detailsOfStatusCreditApplication") : null;
                if (stsCrdtAppObj instanceof JSONArray) {
                    // JSON Array
                    stsCrdtApp = (JSONArray) stsCrdtAppObj;
                    for (int index = 0; index < stsCrdtApp.length(); index++) {
                        JSONObject jsonObject = stsCrdtApp.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("detailsOfStatusCreditApplication");
                        mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                        mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                        mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                        mwLoanAppMfcibData.setAcctSts(jsonObject.has("STATUS") ? String.valueOf(jsonObject.get("STATUS")) : "");
                        mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_APPLICATION") ? String.valueOf(jsonObject.get("DATE_OF_APPLICATION")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (stsCrdtAppObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) stsCrdtAppObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("detailsOfStatusCreditApplication");
                    mwLoanAppMfcibData.setProduct(jsonObject.has("PRODUCT") ? String.valueOf(jsonObject.get("PRODUCT")) : "");
                    mwLoanAppMfcibData.setFinancialInstitution(jsonObject.has("FINANCIAL_INSTITUTION") ? String.valueOf(jsonObject.get("FINANCIAL_INSTITUTION")) : "");
                    mwLoanAppMfcibData.setAmountOfFacility(jsonObject.has("AMOUNT_OF_FACILITY") ? String.valueOf(jsonObject.get("AMOUNT_OF_FACILITY")) : "");
                    mwLoanAppMfcibData.setAcctSts(jsonObject.has("STATUS") ? String.valueOf(jsonObject.get("STATUS")) : "");
                    mwLoanAppMfcibData.setApplicationDt(jsonObject.has("DATE_OF_APPLICATION") ? String.valueOf(jsonObject.get("DATE_OF_APPLICATION")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("detailsOfStatusCreditApplication");
                root.put("detailsOfStatusCreditApplication", stsCrdtApp);

                //---------------------------------------------------------------------------------------------------------
                // 6. personalInformation
                //---------------------------------------------------------------------------------------------------------
                JSONArray personalInfo = new JSONArray();
                Object personalInfoObj = root.has("personalInformation") ? root.get("personalInformation") : null;
                if (personalInfoObj instanceof JSONArray) {
                    // JSON Array
                    personalInfo = (JSONArray) personalInfoObj;
                    for (int index = 0; index < personalInfo.length(); index++) {
                        JSONObject jsonObject = personalInfo.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("personalInformation");
                        mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                        mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_OR_HUSBAND_NAME") ? String.valueOf(jsonObject.get("FATHER_OR_HUSBAND_NAME")) : "");
                        mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                        mwLoanAppMfcibData.setPassport(jsonObject.has("PASSPORT") ? String.valueOf(jsonObject.get("PASSPORT")) : "");
                        mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : "");
                        mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                        mwLoanAppMfcibData.setProfession(jsonObject.has("BUSINESS_OR_PROFESSION") ? String.valueOf(jsonObject.get("BUSINESS_OR_PROFESSION")) : "");
                        mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : "");
                        mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : "");
                        mwLoanAppMfcibData.setBrwrTyp(jsonObject.has("BORROWER_TYPE") ? String.valueOf(jsonObject.get("BORROWER_TYPE")) : "");
                        mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS")) : "");
                        mwLoanAppMfcibData.setCurRsdntalAddrsDt(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                        mwLoanAppMfcibData.setPrmntAddrsDt(jsonObject.has("PERMANENT_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setPrvRsdntalAddrs(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS")) : "");
                        mwLoanAppMfcibData.setPrvRsdntalAddrsDt(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER_OR_BUSINESS") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS")) : "");
                        mwLoanAppMfcibData.setEmployerBusinessDt(jsonObject.has("EMPLOYER_OR_BUSINESS_DATE") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS_DATE")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (personalInfoObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) personalInfoObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("personalInformation");
                    mwLoanAppMfcibData.setFirstNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                    mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_OR_HUSBAND_NAME") ? String.valueOf(jsonObject.get("FATHER_OR_HUSBAND_NAME")) : "");
                    mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setPassport(jsonObject.has("PASSPORT") ? String.valueOf(jsonObject.get("PASSPORT")) : "");
                    mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : "");
                    mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                    mwLoanAppMfcibData.setProfession(jsonObject.has("BUSINESS_OR_PROFESSION") ? String.valueOf(jsonObject.get("BUSINESS_OR_PROFESSION")) : "");
                    mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : "");
                    mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : "");
                    mwLoanAppMfcibData.setBrwrTyp(jsonObject.has("BORROWER_TYPE") ? String.valueOf(jsonObject.get("BORROWER_TYPE")) : "");
                    mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS")) : "");
                    mwLoanAppMfcibData.setCurRsdntalAddrsDt(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                    mwLoanAppMfcibData.setPrmntAddrsDt(jsonObject.has("PERMANENT_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setPrvRsdntalAddrs(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS")) : "");
                    mwLoanAppMfcibData.setPrvRsdntalAddrsDt(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER_OR_BUSINESS") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS")) : "");
                    mwLoanAppMfcibData.setEmployerBusinessDt(jsonObject.has("EMPLOYER_OR_BUSINESS_DATE") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS_DATE")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("personalInformation");
                root.put("personalInformation", personalInfo);

                //---------------------------------------------------------------------------------------------------------
                // 11. data
                //---------------------------------------------------------------------------------------------------------
                JSONArray data = new JSONArray();
                Object dataObj = root;
                if (dataObj instanceof JSONArray) {
                    // JSON Array
                    data = (JSONArray) dataObj;

                    for (int index = 0; index < data.length(); index++) {
                        JSONObject jsonObject = data.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1("data");
                        mwLoanAppMfcibData.setTagNm("data");
                        mwLoanAppMfcibData.setReportDt(jsonObject.has("reportDate") ? String.valueOf(jsonObject.get("reportDate")) : "");
                        mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                        mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_OR_HUSBAND_NAME") ? String.valueOf(jsonObject.get("FATHER_OR_HUSBAND_NAME")) : "");
                        mwLoanAppMfcibData.setProfession(jsonObject.has("BUSINESS_OR_PROFESSION") ? String.valueOf(jsonObject.get("BUSINESS_OR_PROFESSION")) : "");
                        mwLoanAppMfcibData.setRefNo(jsonObject.has("refNo") ? String.valueOf(jsonObject.get("refNo")) : "");
                        mwLoanAppMfcibData.setPassport(jsonObject.has("PASSPORT") ? String.valueOf(jsonObject.get("PASSPORT")) : "");
                        mwLoanAppMfcibData.setBrwrTyp(jsonObject.has("BORROWER_TYPE") ? String.valueOf(jsonObject.get("BORROWER_TYPE")) : "");
                        mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER_OR_BUSINESS") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS")) : "");
                        mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS")) : "");
                        mwLoanAppMfcibData.setCurRsdntalAddrsDt(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                        mwLoanAppMfcibData.setPrmntAddrsDt(jsonObject.has("PERMANENT_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setPrvRsdntalAddrs(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS")) : "");
                        mwLoanAppMfcibData.setPrvRsdntalAddrsDt(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS_DATE")) : "");
                        mwLoanAppMfcibData.setEmployerBusinessDt(jsonObject.has("EMPLOYER_OR_BUSINESS_DATE") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS_DATE")) : "");
                        mwLoanAppMfcibData.setStsMnth(jsonObject.has("MONTH_NAME") ? String.valueOf(jsonObject.get("MONTH_NAME")) : "");
                        mwLoanAppMfcibData.setEnquiryCount(jsonObject.has("noOfCreditEnquiry") ? String.valueOf(jsonObject.get("noOfCreditEnquiry")) : "");
                        mwLoanAppMfcibData.setLoanCount(jsonObject.has("noOfActiveAccounts") ? String.valueOf(jsonObject.get("noOfActiveAccounts")) : "");
                        mwLoanAppMfcibData.setLoanOs(jsonObject.has("totalOutstandingBalance") ? String.valueOf(jsonObject.get("totalOutstandingBalance")) : "");
                        mwLoanAppMfcibData.setMessage(jsonObject.has("disclaimerText") ? String.valueOf(jsonObject.get("disclaimerText")) : "");
                        mwLoanAppMfcibData.setComments(jsonObject.has("remarks") ? String.valueOf(jsonObject.get("remarks")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (dataObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) dataObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1("data");
                    mwLoanAppMfcibData.setTagNm("data");
                    mwLoanAppMfcibData.setReportDt(jsonObject.has("reportDate") ? String.valueOf(jsonObject.get("reportDate")) : "");
                    mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                    mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_OR_HUSBAND_NAME") ? String.valueOf(jsonObject.get("FATHER_OR_HUSBAND_NAME")) : "");
                    mwLoanAppMfcibData.setProfession(jsonObject.has("BUSINESS_OR_PROFESSION") ? String.valueOf(jsonObject.get("BUSINESS_OR_PROFESSION")) : "");
                    mwLoanAppMfcibData.setRefNo(jsonObject.has("refNo") ? String.valueOf(jsonObject.get("refNo")) : "");
                    mwLoanAppMfcibData.setPassport(jsonObject.has("PASSPORT") ? String.valueOf(jsonObject.get("PASSPORT")) : "");
                    mwLoanAppMfcibData.setBrwrTyp(jsonObject.has("BORROWER_TYPE") ? String.valueOf(jsonObject.get("BORROWER_TYPE")) : "");
                    mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER_OR_BUSINESS") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS")) : "");
                    mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS")) : "");
                    mwLoanAppMfcibData.setCurRsdntalAddrsDt(jsonObject.has("CURRENT_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("CURRENT_RESIDENTIAL_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                    mwLoanAppMfcibData.setPrmntAddrsDt(jsonObject.has("PERMANENT_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setPrvRsdntalAddrs(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS")) : "");
                    mwLoanAppMfcibData.setPrvRsdntalAddrsDt(jsonObject.has("PREVIOUS_RESIDENTIAL_ADDRESS_DATE") ? String.valueOf(jsonObject.get("PREVIOUS_RESIDENTIAL_ADDRESS_DATE")) : "");
                    mwLoanAppMfcibData.setEmployerBusinessDt(jsonObject.has("EMPLOYER_OR_BUSINESS_DATE") ? String.valueOf(jsonObject.get("EMPLOYER_OR_BUSINESS_DATE")) : "");
                    mwLoanAppMfcibData.setStsMnth(jsonObject.has("MONTH_NAME") ? String.valueOf(jsonObject.get("MONTH_NAME")) : "");
                    mwLoanAppMfcibData.setEnquiryCount(jsonObject.has("noOfCreditEnquiry") ? String.valueOf(jsonObject.get("noOfCreditEnquiry")) : "");
                    mwLoanAppMfcibData.setLoanCount(jsonObject.has("noOfActiveAccounts") ? String.valueOf(jsonObject.get("noOfActiveAccounts")) : "");
                    mwLoanAppMfcibData.setLoanOs(jsonObject.has("totalOutstandingBalance") ? String.valueOf(jsonObject.get("totalOutstandingBalance")) : "");
                    mwLoanAppMfcibData.setMessage(jsonObject.has("disclaimerText") ? String.valueOf(jsonObject.get("disclaimerText")) : "");
                    mwLoanAppMfcibData.setComments(jsonObject.has("remarks") ? String.valueOf(jsonObject.get("remarks")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                root.remove("data");
                root.put("data", data);

            }

            String statusCode = rptJsonObj.getString("statusCode");
            if (statusCode != null) {
                // MwLoanAppMfcibData Object Map
                MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppMfcibData.setLevel1("statusCode");
                mwLoanAppMfcibData.setTagNm("statusCode");
                mwLoanAppMfcibData.setTagVal(statusCode);
                mwLoanAppMfcibData.setCrntRecFlg(true);
                mwLoanAppMfcibData.setCrtdDt(Instant.now());
                mwLoanAppMfcibData.setCrtdBy(user);
                // List
                loanAppMfcibDataList.add(mwLoanAppMfcibData);
            }

            String messageCode = rptJsonObj.getString("messageCode");
            if (messageCode != null) {
                // MwLoanAppMfcibData Object Map
                MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppMfcibData.setLevel1("messageCode");
                mwLoanAppMfcibData.setTagNm("messageCode");
                mwLoanAppMfcibData.setTagVal(messageCode);
                mwLoanAppMfcibData.setCrntRecFlg(true);
                mwLoanAppMfcibData.setCrtdDt(Instant.now());
                mwLoanAppMfcibData.setCrtdBy(user);
                // List
                loanAppMfcibDataList.add(mwLoanAppMfcibData);
            }

            String message = rptJsonObj.getString("message");
            if (message != null) {
                // MwLoanAppMfcibData Object Map
                MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppMfcibData.setLevel1("message");
                mwLoanAppMfcibData.setTagNm("message");
                mwLoanAppMfcibData.setTagVal(message);
                mwLoanAppMfcibData.setCrntRecFlg(true);
                mwLoanAppMfcibData.setCrtdDt(Instant.now());
                mwLoanAppMfcibData.setCrtdBy(user);
                // List
                loanAppMfcibDataList.add(mwLoanAppMfcibData);
            }

            log.info("At line 3272.");
            // Save Complete Object
            mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
            mwLoanAppMfcibDataRepository.save(loanAppMfcibDataList);
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "Failure";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Failure";
        }
        return "Success";
    }
    // End

}
