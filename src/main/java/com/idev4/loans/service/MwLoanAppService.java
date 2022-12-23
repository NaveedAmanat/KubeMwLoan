package com.idev4.loans.service;

import com.idev4.loans.domain.*;
import com.idev4.loans.dto.FormDto;
import com.idev4.loans.dto.LoanAppDto;
import com.idev4.loans.dto.PostingCheckListDto;
import com.idev4.loans.dto.tab.LoanAppMntrngChks;
import com.idev4.loans.dto.tab.ProductDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MwLoanApp.
 */
@Service
@Transactional
public class MwLoanAppService {

    public final MwLoanUtlPlanRepository mwLoanUtlPlanRepository;
    private final Logger log = LoggerFactory.getLogger(MwLoanAppService.class);
    private final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private final MwLoanAppRepository mwLoanAppRepository;
    private final MwFormRepository mwFormRepository;
    private final MwPrdFormRelRepository mwPrdFormRelRepository;
    private final MwPrdRepository mwPrdRepository;
    private final MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository;
    private final MwClntRelRepository mwClntRelRepository;
    private final MwPrdChrgRepository mwPrdChrgRepository;
    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;
    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;
    private final MwPrdSgrtInstRepository mwPrdSgrtInstRepository;
    private final MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository;
    private final MwLoanAppPpalStngsRepository mwLoanAppPpalStngsRepository;
    private final MwRefCdValRepository mwRefCdValRepository;
    private final EntityManager em;
    private final MwPrdAdvRulRepository mwPrdAdvRulRepository;
    private final MwRulRepository mwRulRepository;
    private final MwClntRepository mwClntRepository;
    private final MwClntCnicTknRepository mwClntCnicTknRepository;
    private final MwAsocPrdRelRepository mwAsocPrdRelRepository;
    private final MwLoanAppMntrngChksRepository mwLoanAppMntrngChksRepository;
    private final MwAddrRepository mwAddrRepository;
    private final MwAddrRelRepository mwAddrRelRepository;
    @Autowired
    MwSancListRepository mwSancListRepository;

    @Autowired
    MwClntTagListRepository mwClntTagListRepository;

    @Autowired
    MwLoanAppVerisysRepository mwLoanAppVerisysRepository;

    @Autowired
    MwBizAprslRepository mwBizAprslRepository;

    @Autowired
    MwTabService mwTabService;

    @Autowired
    MwLoanAppDocRepository mwLoanAppDocRepository;

    public MwLoanAppService(MwLoanAppRepository mwLoanAppRepository, EntityManager em,
                            MwFormRepository mwFormRepository, MwPrdFormRelRepository mwPrdFormRelRepository,
                            MwPrdRepository mwPrdRepository, MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository,
                            MwClntRelRepository mwClntRelRepository, MwPrdChrgRepository mwPrdChrgRepository,
                            MwClntRepository mwClntRepository, MwPrdLoanTrmRepository mwPrdLoanTrmRepository,
                            MwPrdPpalLmtRepository mwPrdPpalLmtRepository, MwPrdSgrtInstRepository mwPrdSgrtInstRepository,
                            MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository,
                            MwLoanAppPpalStngsRepository mwLoanAppPpalStngsRepository, MwRefCdValRepository mwRefCdValRepository,
                            MwPrdAdvRulRepository mwPrdAdvRulRepository, MwRulRepository mwRulRepository,
                            MwClntCnicTknRepository mwClntCnicTknRepository, MwAsocPrdRelRepository mwAsocPrdRelRepository,
                            MwLoanAppMntrngChksRepository mwLoanAppMntrngChksRepository, MwAddrRepository mwAddrRepository,
                            MwAddrRelRepository mwAddrRelRepository, MwLoanUtlPlanRepository mwLoanUtlPlanRepository) {
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwFormRepository = mwFormRepository;
        this.mwPrdFormRelRepository = mwPrdFormRelRepository;
        this.em = em;
        this.mwPrdRepository = mwPrdRepository;
        this.mwLoanFormCmplFlgRepository = mwLoanFormCmplFlgRepository;
        this.mwClntRelRepository = mwClntRelRepository;
        this.mwPrdChrgRepository = mwPrdChrgRepository;
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
        this.mwPrdSgrtInstRepository = mwPrdSgrtInstRepository;
        this.mwLoanAppChrgStngsRepository = mwLoanAppChrgStngsRepository;
        this.mwLoanAppPpalStngsRepository = mwLoanAppPpalStngsRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwPrdAdvRulRepository = mwPrdAdvRulRepository;
        this.mwRulRepository = mwRulRepository;
        this.mwClntRepository = mwClntRepository;
        this.mwClntCnicTknRepository = mwClntCnicTknRepository;
        this.mwAsocPrdRelRepository = mwAsocPrdRelRepository;
        this.mwLoanAppMntrngChksRepository = mwLoanAppMntrngChksRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwLoanUtlPlanRepository = mwLoanUtlPlanRepository;
    }

    /**
     * Save a mwLoanApp.
     *
     * @param mwLoanApp the entity to save
     * @return the persisted entity
     */
    public MwLoanApp save(MwLoanApp mwLoanApp) {
        log.debug("Request to save MwLoanApp : {}", mwLoanApp);
        return mwLoanAppRepository.save(mwLoanApp);
    }

    /**
     * Get all the mwLoanApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwLoanApp> findAll(Pageable pageable) {
        log.debug("Request to get all MwLoanApps");
        return mwLoanAppRepository.findAll(pageable);
    }

    /**
     * Get one mwLoanApp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwLoanApp findOne(Long id) {
        log.debug("Request to get MwLoanApp : {}", id);
        return mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwLoanApp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwLoanApp : {}", id);
        MwLoanApp exLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(id, true);
        if (exLoanApp != null) {
            exLoanApp.setCrntRecFlg(false);
            exLoanApp.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            exLoanApp.setDelFlg(true);
            exLoanApp.setLastUpdDt(Instant.now());
            exLoanApp.setEffEndDt(Instant.now());
            mwLoanAppRepository.save(exLoanApp);
        }

        List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(id, true);
        rels.forEach(rel -> {
            rel.setCrntRecFlg(false);
            rel.setDelFlg(true);
            rel.setEffEndDt(Instant.now());
            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            rel.setLastUpdDt(Instant.now());
            mwClntRelRepository.save(rel);
        });
        mwClntRelRepository.save(rels);
    }

    public Map findLoanApplication(Long appSeq) {
        log.debug("Request to get MwLoanApp : {}", appSeq);
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(appSeq, true);
        if (app == null)
            return null;
        LoanAppDto dto = new LoanAppDto();
        dto.loanAppSeq = app.getLoanAppSeq();
        dto.recAmount = (app.getRcmndLoanAmt() == null) ? 0 : app.getRcmndLoanAmt();
        dto.reqAmount = (app.getRqstdLoanAmt() == null) ? 0 : app.getRqstdLoanAmt();
        dto.approvedAmount = (app.getAprvdLoanAmt() == null) ? 0 : app.getAprvdLoanAmt();
        dto.tblScrn = (app.getTblScrn() == null) ? false : app.getTblScrn();
        dto.loanCyclNum = (app.getLoanCyclNum() == null) ? 1L : app.getLoanCyclNum();
        dto.clientSeq = app.getClntSeq();
        dto.pscScore = (app.getPscScore() == null) ? 0L : app.getPscScore();
        dto.startDate = Date.from(app.getEffStartDt());
        dto.comment = app.getCmnt();
        dto.relAddrAsClntFlg = (app.getRelAddrAsClntFLg() == null) ? false : app.getRelAddrAsClntFLg();
        dto.loan_app_sts_seq = app.getLoanAppSts();
        dto.loan_app_sts_dt = Date.from(app.getLoanAppStsDt());

        List<MwLoanApp> loans = mwLoanAppRepository.findLoansByClntSeq(app.getClntSeq());

        if (loans.size() > 0) {
            if (loans.get(0) != null) {
                dto.previousAmount = Long.parseLong(mwLoanAppRepository.findPreviousLoanAmount(loans.get(0).getClntSeq(), loans.get(0).getLoanCyclNum()));
                dto.previousPscScore = loans.get(0).getPscScore();
                dto.previousLoanCyclNum = loans.get(0).getLoanCyclNum();
                dto.loanProdDesc = mwLoanAppRepository.findProductOfPreviousLoan(loans.get(0).getLoanAppSeq(), loans.get(0).getLoanCyclNum());
                dto.remainingInstallments = Long.parseLong(mwLoanAppRepository.findRemainingInstallmentOfPreviousLoan(loans.get(0).getLoanAppSeq()));
                dto.remainingOutstandingOfPreviousLoan = mwLoanAppRepository.findRemainingOutstandingOfPreviousLoan(loans.get(0).getLoanAppSeq());
            }
        }
        if (app.getClntSeq() != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null)
                dto.totIncmOfErngMemb = clnt.getTotIncmOfErngMemb();
        }

        if (app.prdSeq != null) {
            dto.loanProd = app.prdSeq;
            MwPrd mwPrd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.prdSeq, true);
            if (mwPrd != null) {
                dto.loanProdGrp = mwPrd.getPrdGrpSeq();
            }
        }

        MwCnicTkn tkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (tkn != null) {
            dto.tokenDate = Date.from(tkn.getCnicTknExpryDt());
            dto.tokenNum = tkn.getCnicTknNum();
        }

        // List< MwPrdFormRel > rels =
        // mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg( dto.loanProd, true );
        // List< Long > formIds = rels.stream().map( form -> form.getFormSeq()
        // ).collect( Collectors.toList() );
        List<MwForm> forms = mwFormRepository.findAllOrderedFormsForPrd(dto.loanProd);

        List<MwLoanFormCmplFlg> complForms = mwLoanFormCmplFlgRepository.findAllByLoanAppSeqAndCrntRecFlg(appSeq, true);
        List<Long> complFormIds = complForms.stream().map(form -> form.getFormSeq()).collect(Collectors.toList());
        List<FormDto> formDtos = new ArrayList();
        forms.forEach(form -> {
            FormDto formDto = new FormDto();
            formDto.formId = form.getFormId();
            formDto.formNm = form.getFormNm();
            formDto.formSeq = form.getFormSeq();
            formDto.formUrl = form.getFormUrl();
            if (complFormIds.contains(form.getFormSeq()))
                formDto.isSaved = true;
            formDtos.add(formDto);
        });

        Map<String, Object> loanAppData = new HashMap<>();
        loanAppData.put("loanApp", dto);
        loanAppData.put("forms", formDtos);
        return loanAppData;
    }

    public Map getUserDisbursedLoans(long clntSeq) {
        MwClnt clnt = new MwClnt();
        clnt.setClntSeq(clntSeq);
        Map<Long, Double> disbursedLoans = new HashMap<Long, Double>();
        /*
         * List<MwLoanApp> userLoans = mwLoanAppRepository.findAllByMwClnt(clnt);
         * Map<Long,Double> disbursedLoans = new HashMap<Long,Double>();
         * userLoans.forEach((loan)->{ if(loan.getAprvdLoanAmt()>0 &&
         * loan.getRejectionReasonCd()==null) {
         * disbursedLoans.put(loan.getLoanCyclNum(), loan.getAprvdLoanAmt()); } });
         */

        return disbursedLoans;
    }

    public Map addNewLoanApp(LoanAppDto dto, String currUser) {

        Map<String, Object> calcData = new HashMap<>();

        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
        if (clnt == null)
            return null;

        MwLoanApp loanApp = new MwLoanApp();
        Instant currIns = Instant.now();

        List<MwLoanApp> userLoans = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlg(dto.clientSeq, true);
        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();

        long actSeq = 366;
        long inActSeq = 0;
        for (Object[] st : result) {
            if (st[1].toString().toLowerCase().equals("draft")) {
                actSeq = Long.valueOf(st[2].toString());
            } else
                inActSeq = Long.valueOf(st[2].toString());
        }
        boolean multiLoan = true;

        if (userLoans != null) {
            for (Iterator iterator = userLoans.iterator(); iterator.hasNext(); ) {
                MwLoanApp app = (MwLoanApp) iterator.next();
                if (mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true) != null)
                    if (!mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true).isMultiLoanFlg())
                        multiLoan = false;
            }
        }
        long seq = Common.GenerateLoanAppSequence(clnt.getCnicNum().toString(), userLoans.size() + 0L + "",
                TableNames.MW_LOAN_APP);

        if (multiLoan) {
            loanApp.setLoanAppSeq(seq);
            loanApp.setPrntLoanAppSeq(seq);
            loanApp.setPrdSeq(dto.loanProd);
            loanApp.setLoanAppSts(actSeq);
            loanApp.setCrntRecFlg(true);
            loanApp.setCrtdBy(currUser);
            loanApp.setCrtdDt(currIns);
            loanApp.setDelFlg(false);
            loanApp.setEffStartDt(currIns);
            loanApp.setLastUpdBy("w-" + currUser);
            loanApp.setLastUpdDt(currIns);
            loanApp.setLoanCyclNum(userLoans.size() + 0L);
            loanApp.clntSeq = dto.clientSeq;
            loanApp.setRcmndLoanAmt(dto.recAmount);
            loanApp.setRqstdLoanAmt(dto.reqAmount);
            loanApp.setPortSeq(dto.portfolioSeq);
            loanApp.setLoanId(String.format("%04d", seq));
            loanApp.setLoanAppStsDt(Instant.now());
            loanApp.setSyncFlg(true);
            long loanSeq = mwLoanAppRepository.save(loanApp).getLoanAppSeq();

            calcData.put("loanSeq", Double.valueOf(loanSeq));

            List<MwPrdFormRel> rels = mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg(dto.loanProd, true);
            List<Long> formIds = rels.stream().map(form -> form.getFormSeq()).collect(Collectors.toList());
            List<MwForm> forms = mwFormRepository.findAllByFormSeqInAndCrntRecFlg(formIds, true);

            calcData.put("forms", forms);
        }
        MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(dto.loanProd, true);

        if (dto.prdRul > 0) {
            MwPrdLoanTrm trm = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(), dto.prdRul,
                    true);
            if (trm != null) {
                MwPrdPpalLmt lmt = mwPrdPpalLmtRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                        dto.prdRul, true);
                List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                        .findAllBySgrtEntySeqAndCrntRecFlg(lmt.getPrdPpalLmtSeq(), true);

                // List<String> stngIds =
                // sgrtInst.stream().map(stng->""+stng.getInstNum()).collect(Collectors.toList());
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
                stng.setLoanAppSeq(loanApp.getLoanAppSeq());
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

        List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(dto.loanProd, true, false);

        charges.forEach(charge -> {
            List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                    .findAllBySgrtEntySeqAndCrntRecFlg(charge.getPrdChrgSeq(), true);
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
            stng.setLoanAppSeq(loanApp.getLoanAppSeq());
            stng.setNumOfInstSgrt(charge.getSgrtInstNum());
            stng.setPrdChrgSeq(charge.getPrdChrgSeq());
            stng.setPrdSeq(prd.getPrdSeq());
            stng.setRndngFlg(charge.getAdjustRoundingFlg());
            stng.setSgrtInst(stngInsts);
            stng.setUpfrontFlg(charge.getUpfrontFlg());

            mwLoanAppChrgStngsRepository.save(stng);
        });

        return calcData;
    }

    @Transactional
    public Map<String, Object> updateNewLoanApp(LoanAppDto dto, String currUser) {
        MwLoanApp mwLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        Instant currIns = Instant.now();
        if (mwLoanApp == null)
            return null;

        mwLoanApp.setLastUpdDt(currIns);
        mwLoanApp.setLastUpdBy(currUser);
        mwLoanApp.setClntSeq(dto.clientSeq);
        mwLoanApp.setRcmndLoanAmt(dto.recAmount);
        mwLoanApp.setRqstdLoanAmt(dto.reqAmount);
        mwLoanApp.setAprvdLoanAmt(dto.approvedAmount);
        mwLoanApp.setPrdSeq(dto.loanProd);
        mwLoanApp.setTblScrn(dto.tblScrn);
        mwLoanApp.setLoanCyclNum(dto.loanCyclNum);
        mwLoanApp.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        mwLoanApp.setLastUpdDt(Instant.now());

        // exLoanApp.setLastUpdBy( "w-" + currUser );
        // exLoanApp.setLastUpdDt( currIns );
        // exLoanApp.setCrntRecFlg( false );
        // exLoanApp.setEffEndDt( currIns );
        // mwLoanAppRepository.save( exLoanApp );
        // MwLoanApp loanApp = new MwLoanApp();
        // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg( dto.clientSeq,
        // true );
        // if ( clnt != null ) {
        // clnt.setBizDtl( dto.bizDtl );
        // clnt.setTotIncmOfErngMemb( dto.totIncmOfErngMemb );
        // mwClntRepository.save( clnt );
        // }
        // loanApp.setLoanAppSeq( exLoanApp.getLoanAppSeq() );
        // loanApp.setPrntLoanAppSeq( exLoanApp.getPrntLoanAppSeq() );
        // loanApp.setPrdSeq( dto.loanProd );
        // loanApp.setLoanAppSts( exLoanApp.getLoanAppSts() );
        // loanApp.setLoanAppStsDt( exLoanApp.getLoanAppStsDt() );
        // loanApp.setCrntRecFlg( true );
        // loanApp.setCrtdBy( currUser );
        // loanApp.setCrtdDt( currIns );
        // loanApp.setDelFlg( false );
        // loanApp.setEffStartDt( currIns );
        // loanApp.setLastUpdBy( "w-" + currUser );
        // loanApp.setLastUpdDt( currIns );
        // loanApp.setLoanCyclNum( exLoanApp.getLoanCyclNum() );
        // loanApp.clntSeq = dto.clientSeq;
        // loanApp.setRcmndLoanAmt( dto.recAmount );
        // loanApp.setRqstdLoanAmt( dto.reqAmount );
        // loanApp.setAprvdLoanAmt( dto.approvedAmount );
        // loanApp.setTblScrn( dto.tblScrn );
        // loanApp.setPortSeq( exLoanApp.getPortSeq() );
        // loanApp.setLoanId( exLoanApp.getLoanId() );
        // loanApp.setSyncFlg( true );

        mwLoanAppRepository.save(mwLoanApp);

        // List< MwPrdFormRel > rels =
        // mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg( dto.loanProd, true );
        // List< Long > formIds = rels.stream().map( form -> form.getFormSeq()
        // ).collect( Collectors.toList() );
        List<MwForm> forms = mwFormRepository.findAllOrderedFormsForPrd(dto.loanProd);

        List<MwLoanFormCmplFlg> complForms = mwLoanFormCmplFlgRepository
                .findAllByLoanAppSeqAndCrntRecFlg(mwLoanApp.getLoanAppSeq(), true);
        List<Long> complFormIds = complForms.stream().map(form -> form.getFormSeq()).collect(Collectors.toList());
        List<FormDto> formDtos = new ArrayList<>();
        forms.forEach(form -> {
            FormDto formDto = new FormDto();
            formDto.formId = form.getFormId();
            formDto.formNm = form.getFormNm();
            formDto.formSeq = form.getFormSeq();
            formDto.formUrl = form.getFormUrl();
            if (complFormIds.contains(form.getFormSeq()))
                formDto.isSaved = true;
            formDtos.add(formDto);
        });

        MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(dto.loanProd, true);

        if (dto.termRule > 0) {
            MwLoanAppPpalStngs exStng = mwLoanAppPpalStngsRepository.findOneByLoanAppSeq(mwLoanApp.getLoanAppSeq());
            long stngsSeq;
            if (exStng != null) {
                stngsSeq = exStng.getLoanAppPpalStngsSeq();
                mwLoanAppPpalStngsRepository.delete(exStng);
            } else {
                stngsSeq = SequenceFinder.findNextVal(Sequences.LOAN_APP_PPAL_STNGS_SEQ);
            }
            MwPrdLoanTrm trm = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                    dto.termRule, true);
            if (trm != null) {
                MwPrdPpalLmt lmt = mwPrdPpalLmtRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                        dto.limitRule, true);
                List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                        .findAllBySgrtEntySeqAndCrntRecFlg(lmt.getPrdPpalLmtSeq(), true);

                // List<String> stngIds =
                // sgrtInst.stream().map(stng->""+stng.getInstNum()).collect(Collectors.toList());
                String stngInsts = "";
                for (int i = 0; i < sgrtInst.size(); i++) {
                    if (i == 0) {
                        stngInsts = stngInsts + sgrtInst.get(i).getInstNum();
                    } else {
                        stngInsts = stngInsts + "," + sgrtInst.get(i).getInstNum();
                    }
                }
                MwLoanAppPpalStngs stng = new MwLoanAppPpalStngs();
                stng.setLoanAppPpalStngsSeq(stngsSeq);
                stng.setIrrFlg(prd.getIrrFlg());
                stng.setIrrRate(prd.getIrrVal());
                stng.setLoanAppSeq(mwLoanApp.getLoanAppSeq());
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

        List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(dto.loanProd, true, false);
        List<MwLoanAppChrgStngs> exCharges = mwLoanAppChrgStngsRepository
                .findAllByLoanAppSeq(mwLoanApp.getLoanAppSeq());
        mwLoanAppChrgStngsRepository.delete(exCharges);
        charges.forEach(charge -> {
            List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                    .findAllBySgrtEntySeqAndCrntRecFlg(charge.getPrdChrgSeq(), true);
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
            stng.setLoanAppSeq(mwLoanApp.getLoanAppSeq());
            stng.setNumOfInstSgrt(charge.getSgrtInstNum());
            stng.setPrdChrgSeq(charge.getPrdChrgSeq());
            stng.setPrdSeq(prd.getPrdSeq());
            stng.setRndngFlg(charge.getAdjustRoundingFlg());
            stng.setSgrtInst(stngInsts);
            stng.setUpfrontFlg(charge.getUpfrontFlg());
            stng.setTypSeq(charge.getChrgTypSeq());

            mwLoanAppChrgStngsRepository.save(stng);
        });

        Map<String, Object> loanAppData = new HashMap<>();
        loanAppData.put("loanApp", dto);
        loanAppData.put("loanAppSeq", dto.loanAppSeq);
        loanAppData.put("forms", formDtos);
        return loanAppData;
        // return mwLoanAppRepository.save(loanApp).getLoanAppSeq();
    }

    public MwLoanApp findLoanByClient(long clientSeq) {
        return mwLoanAppRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
    }

    public Map<String, String> locationInfoForSubmit(long portKey) {
        Map<String, String> resp = new HashMap<>();

        String query = Queries.loanApplocation + portKey;
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();

        if (result != null) {

            Object[] res = result.get(0);

            if (res != null) {

                resp.put("portName", res[0].toString());
                resp.put("branchCode", res[1].toString());
                resp.put("branchName", res[2].toString());
                resp.put("areaName", res[3].toString());
                resp.put("regName", res[4].toString());

            }

        }
        return resp;
    }

    public Map<String, String> submitApplication(LoanAppDto mwLoanApp, String currUser) {
        Map<String, String> resp = new HashMap<String, String>();

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        if (app == null)
            return null;

        MwClntRel cob = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(mwLoanApp.loanAppSeq, 3, true);
        if (cob != null) {
            MwRefCdVal relWithClnt = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(cob.getRelWthClntKey(), true);
            if (relWithClnt.getRefCd().equals("0032")) {
                List<MwSancList> nactaRelList = mwSancListRepository.findAllByCnicNumAndCntryAndCrntRecFlg(cob.getCnicNum() + "",
                        "Pakistan", 1L);
                MwClntTagList nactaRelTag = mwClntTagListRepository
                        .findOneByCnicNumAndTagsSeqAndDelFlg(cob.getCnicNum(), 6l, false);
                if (nactaRelList.size() > 0 || nactaRelTag != null) {
                    resp.put("status", "5");
                    resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
                    resp.put("canProceed", "false");
                    return resp;
                }
            }
        }

        long submittedStatusCode = 700l;
        List<MwRefCdVal> vals = mwRefCdValRepository
                .findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> apprvd_dsbrsd_sts = new ArrayList<>();
        for (MwRefCdVal val : vals) {
            if (val.getRefCd().equals("0002") || val.getRefCd().equals("0004") || val.getRefCd().equals("0005")) {
                apprvd_dsbrsd_sts.add(val.getRefCdSeq());
            }
            if (val.getRefCd().equals("0002"))
                submittedStatusCode = val.getRefCdSeq();
        }

        boolean flag = mwTabService.nactaVerification(app.getLoanAppSeq());
        if (flag) {
            resp.put("status", "3");
            MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
            resp.put("loan_app_sts", "" + nactaRefCdVal.getRefCdSeq());
            resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
            resp.put("canProceed", "true");
            return resp;
        }
        if (mwLoanApp.loanAppSeq > 0) {
            List<MwLoanApp> exLoans = mwLoanAppRepository
                    .findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(mwLoanApp.loanAppSeq, apprvd_dsbrsd_sts, true);
            if (exLoans != null && exLoans.size() > 0) {
                log.info("SUBMIT APPLICATION ", "This Loan is already in Disbursed/Approved/Active Status");
                resp.put("status", "2");
                resp.put("canProceed", "false");
                resp.put("message", "This Loan is already in Disbursed/Approved/Active Status");
                return resp;
            }
        }
        String ruleCheck = "";
        List<MwPrdAdvRul> advRules = mwPrdAdvRulRepository.findAllByPrdSeqAndCrntRecFlg(mwLoanApp.loanProd, true);
        for (int i = 0; i < advRules.size(); i++) {
            MwPrdAdvRul rule = advRules.get(i);
            String ruleQuery = Queries.advRule + mwLoanApp.loanAppSeq + " and ";
            if (rule.getRulSeq() != null) {
                MwRul rul = mwRulRepository.findOneByRulSeqAndCrntRecFlg(rule.getRulSeq(), true);
                if (rul != null) {
                    ruleQuery = ruleQuery + rul.getRulCrtraStr();
                    Query qr = em.createNativeQuery(ruleQuery);
                    List<Object[]> rulResult = qr.getResultList();
                    if (rulResult.size() > 0) {
                        BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                        if ((bd.longValue()) == 1) {
                        } else {
                            ruleCheck = rul.getRulCmnt();
                            break;
                        }

                    } else {
                        ruleCheck = rul.getRulCmnt();
                        break;
                    }
                }
            }
        }

        if (ruleCheck.length() == 0) {
            app.setLastUpdBy("w-" + currUser);
            app.lastUpdDt(Instant.now());
            app.setLoanAppSts(submittedStatusCode);
            app.setCmnt(mwLoanApp.comment);
            app.setLoanAppStsDt(Instant.now());
            app.setSyncFlg(true);
            mwLoanAppRepository.save(app);
        } else {
            resp.put("status", "1");
            resp.put("canProceed", "false");
            resp.put("message", ruleCheck);
            return resp;
        }
        resp.put("status", "0");
        resp.put("canProceed", "true");
        resp.put("message", "Application Submitted");
        return resp;

    }

    public String ruleCheck(Long loanAppSeq) {
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        if (app == null)
            return "Loan App Not Found";

        List<MwPrdAdvRul> advRules = mwPrdAdvRulRepository.findAllByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true);
        String ruleCheck = "";
        for (int i = 0; i < advRules.size(); i++) {
            MwPrdAdvRul rule = advRules.get(i);
            String ruleQuery = Queries.advRule + app.getLoanAppSeq() + " and ";
            if (rule.getRulSeq() != null) {
                MwRul rul = mwRulRepository.findOneByRulSeqAndCrntRecFlg(rule.getRulSeq(), true);
                if (rul != null) {
                    ruleQuery = ruleQuery + rul.getRulCrtraStr();
                    Query qr = em.createNativeQuery(ruleQuery);
                    List<Object[]> rulResult = qr.getResultList();
                    if (rulResult.size() > 0) {
                        BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                        if ((bd.longValue()) == 1) {
                        } else {
                            ruleCheck = rul.getRulCmnt();
                            break;
                        }

                    } else {
                        ruleCheck = rul.getRulCmnt();
                        break;
                    }
                }
            }
        }
        ;
        if (ruleCheck.equalsIgnoreCase("")) {
            ruleCheck = "pass";
        }
        return ruleCheck;

    }

    // Modified By Naveed - Date - 24-02-2022
    // return proper response instead of just string
    public Map<String, String> submitAssocApplication(LoanAppDto mwLoanApp, String currUser) {
        String ruleCheck = "";
        Map<String, String> resp = new HashMap<>();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.prntLoanAppSeq, true);
        if (app == null)
            return null;

        Query odQuery = em.createNativeQuery("select get_od_info(:loanAppSeq,sysdate,'p') from dual")
                .setParameter("loanAppSeq", app.getLoanAppSeq());
        Object oDObj = null;
        List odResults = odQuery.getResultList();
        if (!odResults.isEmpty()) {
            oDObj = (Object) odResults.get(0);
            if (oDObj != null) {
                if (!oDObj.toString().equals("null")) {
                    resp.put("warning", "This Client is in Over Due.");
                    return resp;
                }
            }
        }

        List<MwLoanApp> apps = mwLoanAppRepository.findAllByPrntLoanAppSeqAndClntSeqAndCrntRecFlg(app.getLoanAppSeq(),
                app.getClntSeq(), true);
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getPrntLoanAppSeq().longValue() != apps.get(i).getLoanAppSeq().longValue()) {
                resp.put("warning", "Already has a Associate Product Assigned");
                return resp;
            }
        }

        MwRefCdVal activeSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0005");
        MwRefCdVal apprvdSeq = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0004");

        if (app.getLoanAppSts().longValue() != activeSts.getRefCdSeq().longValue()) {
            resp.put("warning", "Parent Loan is not an active Loan");
            return resp;
        }

        MwPrd pPrd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true);
        if (pPrd == null) {
            resp.put("warning", "Parent Product Not Found");
            return resp;
        }
        ;

        MwAsocPrdRel asocPrdRel = mwAsocPrdRelRepository.findOneByAsocPrdSeqAndPrdSeqAndCrntRecFlg(mwLoanApp.loanProd,
                app.getPrdSeq(), true);
        if (asocPrdRel == null) {
            resp.put("warning", "Associate Product Relation Not Found");
            return resp;
        }
        ;

        MwPrd asocPrd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(mwLoanApp.loanProd, true);
        if (asocPrd == null) {
            resp.put("warning", "Associate Product Not Found");
            return resp;
        }
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
        if (clnt == null) {
            resp.put("warning", "Client Not Found");
            return resp;
        }

        // Zohaib Asim - Dated 15-Nov-22 - Common Sequence for Loan Application
        Long seq = 0L;
        Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "SUBMIT_APP")
                .setParameter("userId", currUser);
        Object tblSeqRes = qry.getSingleResult();

        if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
            seq = Long.parseLong(tblSeqRes.toString());
        }

        MwLoanApp newApp = new MwLoanApp();

        // Common.GenerateLoanAppSequence(clnt.getCnicNum().toString(), app.getLoanCyclNum() + "", TableNames.MW_LOAN_APP_ASSOC)
        newApp.setLoanAppSeq(seq);
        newApp.setLoanAppId(newApp.getLoanAppSeq());
        // newApp.setLoanAppId(String.format("%04d", newApp.getLoanAppSeq()));
        newApp.setAprvdLoanAmt(mwLoanApp.approvedAmount);
        newApp.setClntSeq(mwLoanApp.clientSeq);
        newApp.setCrntRecFlg(true);
        newApp.setCrtdBy(currUser);
        newApp.setCrtdDt(Instant.now());
        newApp.setDelFlg(false);
        newApp.setEffStartDt(Instant.now());
        newApp.setLoanAppSts(apprvdSeq.getRefCdSeq());
        newApp.setLoanAppStsDt(Instant.now());
        newApp.setLoanCyclNum(app.getLoanCyclNum());
        newApp.setLoanId(String.format("%04d", newApp.getLoanAppSeq()));
        newApp.setPortSeq(app.getPortSeq());
        newApp.setPrntLoanAppSeq(app.getPrntLoanAppSeq());
        newApp.setPscScore(app.getPscScore());
        newApp.setRcmndLoanAmt(mwLoanApp.approvedAmount);
        newApp.setRqstdLoanAmt(app.getRqstdLoanAmt());
        newApp.setSyncFlg(true);
        newApp.setTblScrn(app.getTblScrn());
        newApp.setPrdSeq(mwLoanApp.loanProd);
        newApp.setLastUpdDt(Instant.now());
        newApp.setLastUpdBy("w-" + currUser);
        mwLoanAppRepository.save(newApp);

        List<MwPrdAdvRul> advRules = mwPrdAdvRulRepository.findAllByPrdSeqAndCrntRecFlg(mwLoanApp.loanProd, true);
        for (int i = 0; i < advRules.size(); i++) {
            MwPrdAdvRul rule = advRules.get(i);
            String ruleQuery = Queries.advRule + newApp.getPrntLoanAppSeq() + " and ";
            if (rule.getRulSeq() != null) {
                MwRul rul = mwRulRepository.findOneByRulSeqAndCrntRecFlg(rule.getRulSeq(), true);
                if (rul != null) {
                    ruleQuery = ruleQuery + rul.getRulCrtraStr();
                    Query qr = em.createNativeQuery(ruleQuery);
                    List<Object[]> rulResult = qr.getResultList();
                    if (rulResult.size() > 0) {
                        BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                        if ((bd.longValue()) == 1) {
                        } else {
                            ruleCheck = rul.getRulCmnt();
                            break;
                        }

                    } else {
                        ruleCheck = rul.getRulCmnt();
                        break;
                    }
                }
            }
        }
        ;
        if (ruleCheck.equalsIgnoreCase("")) {

            if (mwLoanApp.termRule > 0) {
                MwPrdLoanTrm trm = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(mwLoanApp.loanProd,
                        mwLoanApp.termRule, true);
                if (trm != null) {
                    MwPrdPpalLmt lmt = mwPrdPpalLmtRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(mwLoanApp.loanProd,
                            mwLoanApp.limitRule, true);
                    List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                            .findAllBySgrtEntySeqAndCrntRecFlg(lmt.getPrdPpalLmtSeq(), true);
                    String stngInsts = "";
                    for (int i = 0; i < sgrtInst.size(); i++) {
                        if (i == 0) {
                            stngInsts = stngInsts + sgrtInst.get(i).getInstNum();
                        } else {
                            stngInsts = stngInsts + "," + sgrtInst.get(i).getInstNum();
                        }
                    }
                    MwLoanAppPpalStngs stng = mwLoanAppPpalStngsRepository.findOneByLoanAppSeq(newApp.getLoanAppSeq());
                    if (stng == null) {
                        stng = new MwLoanAppPpalStngs();
                        stng.setLoanAppPpalStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_PPAL_STNGS_SEQ));
                    }
                    stng.setIrrFlg(asocPrd.getIrrFlg());
                    stng.setIrrRate(asocPrd.getIrrVal());
                    stng.setLoanAppSeq(newApp.getLoanAppSeq());
                    MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(trm.getTrmKey(), true);
                    stng.setNumOfInst((val == null) ? 0 : Long.parseLong(val.getRefCdDscr()));
                    stng.setNumOfInstSgrt(sgrtInst.size() + 0L);
                    stng.setPrdSeq(asocPrd.getPrdSeq());
                    MwRefCdVal freq = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(trm.getPymtFreqKey(), true);
                    stng.setPymtFreq((freq == null) ? 0 : Long.parseLong(freq.getRefCdDscr()));
                    stng.setRndngAdjInst(asocPrd.getRndngAdj().doubleValue());
                    stng.setRndngScl(asocPrd.getRndngScl());
                    stng.setSgrtInst(stngInsts);
                    mwLoanAppPpalStngsRepository.save(stng);
                }
            }

            List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(mwLoanApp.loanProd,
                    true, false);
            List<MwLoanAppChrgStngs> exCharges = mwLoanAppChrgStngsRepository
                    .findAllByLoanAppSeq(newApp.getLoanAppSeq());
            mwLoanAppChrgStngsRepository.delete(exCharges);
            charges.forEach(charge -> {
                List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                        .findAllBySgrtEntySeqAndCrntRecFlg(charge.getPrdChrgSeq(), true);
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
                stng.setLoanAppSeq(newApp.getLoanAppSeq());
                stng.setNumOfInstSgrt(charge.getSgrtInstNum());
                stng.setPrdChrgSeq(charge.getPrdChrgSeq());
                stng.setPrdSeq(asocPrd.getPrdSeq());
                stng.setRndngFlg(charge.getAdjustRoundingFlg());
                stng.setSgrtInst(stngInsts);
                stng.setUpfrontFlg(charge.getUpfrontFlg());
                stng.setTypSeq(charge.getChrgTypSeq());
                mwLoanAppChrgStngsRepository.save(stng);
            });
            resp.put("success", "Application Submitted");
            return resp;
        } else {
            MwLoanApp dApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(newApp.getLoanAppSeq(), true);
            dApp.setCrntRecFlg(false);
            if (dApp != null)
                mwLoanAppRepository.save(dApp);
        }
        resp.put("warning", ruleCheck);
        return resp;
    }
    // Endded By Naveed - Date - 24-02-2022

    public void cancelLoan(LoanAppDto mwLoanApp, String currUser) {

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        if (app == null)
            return;
        app.setCrntRecFlg(false);
        app.setDelFlg(true);
        app.setEffEndDt(Instant.now());
        app.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        app.setLastUpdDt(Instant.now());
        mwLoanAppRepository.save(app);

        List<MwClntRel> rels = mwClntRelRepository.findAllByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        rels.forEach(rel -> {
            rel.setCrntRecFlg(false);
            rel.setDelFlg(true);
            rel.setEffEndDt(Instant.now());
            rel.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            rel.setLastUpdDt(Instant.now());
        });
        mwClntRelRepository.save(rels);
    }

    public MwLoanApp rejectLoanApplication(LoanAppDto mwLoanApp, String currUser) {
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        if (app == null)
            return null;

        List<MwRefCdVal> vals = mwRefCdValRepository
                .findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> apprvd_dsbrsd_sts = new ArrayList<>();
        vals.forEach(val -> {
            if (val.getRefCd().equals("0009") || val.getRefCd().equals("0004") || val.getRefCd().equals("0005")) {
                apprvd_dsbrsd_sts.add(val.getRefCdSeq());
            }
        });
        if (mwLoanApp.loanAppSeq > 0) {
            List<MwLoanApp> exLoans = mwLoanAppRepository
                    .findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(mwLoanApp.loanAppSeq, apprvd_dsbrsd_sts, true);
            if (exLoans != null && exLoans.size() > 0) {
                log.info("SUBMIT APPLICATION ", "This Loan is already in Disbursed/Approved/Active Status");
                return null;
            }
        }

        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();

        long actSeq = 0;
        long inActSeq = 0;
        for (Object[] st : result) {
            if (st[1].toString().toLowerCase().equals("rejected")) {
                actSeq = Long.valueOf(st[2].toString());
            } else
                inActSeq = Long.valueOf(st[2].toString());
        }

        mwTabService.deleteApplication(mwLoanApp.loanAppSeq, "w-" + currUser, actSeq, mwLoanApp.comment);
        return app;

    }

    public ResponseEntity<?> approveLoanApplication(LoanAppDto mwLoanApp, String currUser) {

        Map<String, String> resp = new HashMap<String, String>();

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        if (app == null)
            return null;

        Query qry = em.createNativeQuery(Queries.verisysBmApprovalFunc).setParameter("P_LOAN_APP_SEQ", mwLoanApp.loanAppSeq);
        String versts = qry.getSingleResult().toString();
        if (versts != null && versts.equals("0")) {
            resp.put("status", "2");
            resp.put("canProceed", "false");
            resp.put("message", "NADRA verification is pending");
            return ResponseEntity.ok().body(resp);
        }

        List<Object> isAppraisal = em.createNativeQuery(Queries.IS_APPRAISAL_BY_LOAN_APP_SEQ)
                .setParameter("loanAppSeq", mwLoanApp.loanAppSeq).getResultList();
        if (!isAppraisal.isEmpty()) {
            long ndi = new BigDecimal(em.createNativeQuery(Queries.NADI_BY_LOAN_APP_SEQ)
                    .setParameter("loanAppSeq", mwLoanApp.loanAppSeq).getSingleResult().toString()).longValue();
            if (ndi <= 0) {
                resp.put("status", "2");
                resp.put("canProceed", "false");
                resp.put("message", "NDI should be greater then zero");
                return ResponseEntity.ok().body(resp);
            }
        }
        MwClntRel cob = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(mwLoanApp.loanAppSeq, 3, true);
        if (cob != null) {
            MwRefCdVal relWithClnt = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(cob.getRelWthClntKey(), true);
            if (relWithClnt.getRefCd().equals("0032")) {
                List<MwSancList> nactaRelList = mwSancListRepository.findAllByCnicNumAndCntryAndCrntRecFlg(cob.getCnicNum() + "",
                        "Pakistan", 1L);
                MwClntTagList nactaRelTag = mwClntTagListRepository
                        .findOneByCnicNumAndTagsSeqAndDelFlg(cob.getCnicNum(), 6l, false);
                if (nactaRelList.size() > 0 || nactaRelTag != null) {
                    resp.put("status", "5");
                    resp.put("message", "CoBorrower Tagged by AML Nacta List.");
                    resp.put("canProceed", "false");
                    return ResponseEntity.ok().body(resp);
                }
            }
        }

        List<MwRefCdVal> vals = mwRefCdValRepository
                .findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(106L, true, true);
        List<Long> apprvd_dsbrsd_sts = new ArrayList<>();
        // Added by Zohaib Asim - Dated 08-10-2021
        List<Long> otherLoanStsList = new ArrayList<>();

        long approveStsCode = 702;
        for (MwRefCdVal val : vals) {
            if (val.getRefCd().equals("0009") || val.getRefCd().equals("0004") || val.getRefCd().equals("0005")) {
                apprvd_dsbrsd_sts.add(val.getRefCdSeq());
            }
            // Added by Zohaib Asim - Dated 08-10-2021 - Approval Restriction
            // AML Match
            else if (val.getRefCd().equals("1682")) {
                otherLoanStsList.add(val.getRefCdSeq());
            }
            // End by Zohaib Asim
            if (val.getRefCd().equals("0004"))
                approveStsCode = val.getRefCdSeq();
        }
        if (mwLoanApp.loanAppSeq > 0) {
            List<MwLoanApp> exLoans = mwLoanAppRepository
                    .findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(mwLoanApp.loanAppSeq, apprvd_dsbrsd_sts, true);
            if (exLoans != null && exLoans.size() > 0) {
                resp.put("status", "2");
                resp.put("canProceed", "false");
                resp.put("message", "This Loan is already in Disbursed/Approved/Active Status");
                return ResponseEntity.ok().body(resp);
            }

            // Added by Zohaib Asim - Dated 08-10-2021 - Approval Restriction for NACTA
            exLoans.clear();
            exLoans = mwLoanAppRepository
                    .findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(mwLoanApp.loanAppSeq, otherLoanStsList, true);
            if (exLoans != null && exLoans.size() > 0) {
                resp.put("status", "3");
                resp.put("canProceed", "false");
                resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
                return ResponseEntity.ok().body(resp);
            }
            // End by Zohaib Asim
        }
        boolean flag = mwTabService.nactaVerification(app.getLoanAppSeq());
        if (flag) {
            resp.put("status", "3");
            MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
            resp.put("loan_app_sts", "" + nactaRefCdVal.getRefCdSeq());
            resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }

        // Added By Naveed - Dated - 24-11-2021
        // Operation - SCR System Control
        if ((ChronoUnit.DAYS.between(app.getEffStartDt(), Instant.now())) > 30) {
            resp.put("status", "1");
            resp.put("canProceed", "false");
            resp.put("message", "30-day loan application policy");
            return ResponseEntity.ok().body(resp);
        }
        // Ended By Naveed - Dated - 24-11-2021

        // Added by Zohaib Asim - Dated 26-08-2021 - MFCIB Verification
		/*String verifyMfcib = mwTabService.verifyMfcibAgnstLoanOrClnt(app.getClntSeq(),
				app.getLoanAppSeq(),
				app.getPrdSeq());
		if ( verifyMfcib.contains("FAILED") ){
			resp.put( "status", "5" );
			resp.put( "loan_app_sts", "" + app.getLoanAppSts() );
			resp.put( "message", "MFCIB not done" );
			resp.put( "canProceed", "false" );
			return ResponseEntity.ok().body( resp );
		}*/
        // End by Zohaib Asim

        if (app.getPrdSeq() != null) {
            MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true);

            ProductDto pdto = new ProductDto();
            pdto.clntSeq = app.getClntSeq();
            pdto.loanAppSeq = app.getLoanAppSeq();
            pdto.prdSeq = app.getPrdSeq();

            List<ProductDto> pdtos = mwTabService.getProductsListingForClient(pdto);
            ProductDto rdto = null;

            if (pdtos.size() > 0) {
                rdto = pdtos.get(0);
                log.debug("SUBMIT APPLICATION", rdto.toString());
            }
            if (rdto != null && rdto.termRule > 0) {
                MwPrdLoanTrm trm = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(app.getPrdSeq(),
                        rdto.termRule, true);
                if (trm != null) {
                    List<MwLoanAppPpalStngs> exPplStng = mwLoanAppPpalStngsRepository
                            .findAllByLoanAppSeq(app.getLoanAppSeq());
                    if (exPplStng != null)
                        mwLoanAppPpalStngsRepository.delete(exPplStng);

                    MwPrdPpalLmt lmt = mwPrdPpalLmtRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                            rdto.limitRule, true);
                    List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                            .findAllBySgrtEntySeqAndCrntRecFlg(lmt.getPrdPpalLmtSeq(), true);
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
                    stng.setLoanAppSeq(app.getLoanAppSeq());
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

            List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(app.getPrdSeq(), true,
                    false);

            List<MwLoanAppChrgStngs> exChrgStngs = mwLoanAppChrgStngsRepository
                    .findAllByLoanAppSeq(app.getLoanAppSeq());
            if (exChrgStngs != null)
                mwLoanAppChrgStngsRepository.delete(exChrgStngs);
            charges.forEach(charge -> {
                List<MwPrdSgrtInst> sgrtInst = mwPrdSgrtInstRepository
                        .findAllBySgrtEntySeqAndCrntRecFlg(charge.getPrdChrgSeq(), true);
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
                stng.setLoanAppSeq(app.getLoanAppSeq());
                stng.setNumOfInstSgrt(charge.getSgrtInstNum());
                stng.setPrdChrgSeq(charge.getPrdChrgSeq());
                stng.setPrdSeq(prd.getPrdSeq());
                stng.setRndngFlg(charge.getAdjustRoundingFlg());
                stng.setSgrtInst(stngInsts);
                stng.setUpfrontFlg(charge.getUpfrontFlg());
                stng.setTypSeq(charge.getChrgTypSeq());
                mwLoanAppChrgStngsRepository.save(stng);
            });

        }

        app.setLastUpdBy("w-" + currUser);
        app.lastUpdDt(Instant.now());
        app.setLoanAppSts(approveStsCode);
        // app.setAprvdLoanAmt(mwLoanApp.approvedAmount);
        app.setLoanAppStsDt(Instant.now());
        app.setSyncFlg(true);

        mwLoanAppRepository.save(app);

        String nactaNameCheck = mwTabService.nactaNameMatch(app.getLoanAppSeq());
        if (nactaNameCheck.length() > 0) {
            resp.put("status", "4");
            resp.put("message", nactaNameCheck);
            resp.put("canProceed", "true");
            return ResponseEntity.ok().body(resp);
        }

        resp.put("status", "0");
        resp.put("canProceed", "true");
        resp.put("message", "Application Approved");
        return ResponseEntity.ok().body(resp);
    }

    public MwLoanApp sendBackLoanApplication(LoanAppDto mwLoanApp, String currUser) {
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loanAppSeq, true);
        if (app == null)
            return null;
        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();

        long actSeq = 0;
        long inActSeq = 0;
        for (Object[] st : result) {
            if (st[1].toString().toLowerCase().equals("need more clarification")) {
                actSeq = Long.valueOf(st[2].toString());
            } else
                inActSeq = Long.valueOf(st[2].toString());
        }

        app.setLastUpdBy("w-" + currUser);
        app.lastUpdDt(Instant.now());
        app.setLoanAppSts(actSeq);
        app.setCmnt(mwLoanApp.comment);
        app.setLoanAppStsDt(Instant.now());
        app.setSyncFlg(true);
        return mwLoanAppRepository.save(app);

    }

    public ResponseEntity updateLoanUtlChk(LoanAppDto mwLoanApp, String currUser) {
        if (mwLoanApp.loan_app_seq == null || mwLoanApp.loan_app_seq.longValue() == 0L)
            return ResponseEntity.badRequest().body("{\"error\":\"Loan App Seq Not Found.\"}");

        // if ( mwLoanApp.loan_utl_sts_seq == null ||
        // mwLoanApp.loan_utl_sts_seq.longValue() == 0L )
        // return ResponseEntity.badRequest().body( "{\"error\":\"Loan Utl Sts Seq Not
        // Found.\"}" );

        // if ( mwLoanApp.loan_utl_cmnt == null || mwLoanApp.loan_utl_cmnt.length() == 0
        // )
        // return ResponseEntity.badRequest().body( "{\"error\":\"Comment Not Found.\"}"
        // );
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwLoanApp.loan_app_seq, true);
        if (app == null)
            return ResponseEntity.badRequest().body("{\"error\":\"Loan Application Not Found.\"}");
        app.setLoanUtlStsSeq(mwLoanApp.loan_utl_sts_seq);
        app.setLoanUtlCmnt(mwLoanApp.loan_utl_cmnt);
        app.setLastUpdBy("w-" + currUser);
        app.setLastUpdDt(Instant.now());
        if (mwLoanApp.latitude != null && mwLoanApp.longitude != null) {
            MwAddrRel rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(app.getClntSeq(),
                    Common.clntAddress, true);
            if (rel != null) {
                MwAddr exAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(rel.getAddrSeq(), true);
                if (exAddr != null) {
                    exAddr.setLastUpdBy("w-" + currUser);
                    exAddr.setLastUpdDt(Instant.now());
                    exAddr.setLatitude(mwLoanApp.latitude);
                    exAddr.setLongitude(mwLoanApp.longitude);
                    mwAddrRepository.save(exAddr);
                }
            }
        }

        if (mwLoanApp.loan_app_mntrng_chk != null)
            saveMntryChk(mwLoanApp.loan_app_mntrng_chk, currUser);

        if (mwLoanApp.loan_util_plan != null) {
            mwLoanApp.loan_util_plan.forEach(utl -> {
                MwLoanUtlPlan exUtil = mwLoanUtlPlanRepository
                        .findOneByLoanUtlPlanSeqAndCrntRecFlg(utl.loan_utl_plan_seq, true);
                if (exUtil == null) {
                    exUtil = new MwLoanUtlPlan();
                }

                exUtil = utl.DtoToDomain(formatter, exUtil);
                mwLoanUtlPlanRepository.save(exUtil);
            });
        }

        return ResponseEntity.ok().body("{\"body\":\"Updated Loan Util Check\"}");
    }

    public ResponseEntity saveMntryChk(LoanAppMntrngChks dto, String cUser) {
        if (dto.loan_app_seq == null)
            return ResponseEntity.badRequest().body("{\"error\":\"loan_app_seq Not Found.\"}");

        MwLoanApp loanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loan_app_seq, true);
        if (loanApp == null)
            return ResponseEntity.badRequest().body("{\"error\":\"Loan Application Not Found.\"}");

        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loanApp.getClntSeq(), true);
        if (clnt == null)
            return ResponseEntity.badRequest().body("{\"error\":\"Clnt Not Found.\"}");

        MwLoanAppMntrngChks exChk = mwLoanAppMntrngChksRepository
                .findOneByLoanAppSeqAndChkFlgAndCrntRecFlg(dto.loan_app_seq, Integer.valueOf(dto.chk_flg), true);

        Long seq = Common.GenerateLoanAppSequence(clnt.getCnicNum() + "", loanApp.getLoanCyclNum() + "",
                TableNames.MW_LOAN_APP_MNTRNG_CHKS);

        // MODIFIED BY RIZWAN/YOUSAF DATED: 10-OCT-2022
//		if (exChk == null){
//			// Modified by Zohaib Asim - Dated 26-05-2022 - Monitoring Seq
////			exChk = mwLoanAppMntrngChksRepository.findOneByLoanAppMntrngChksSeqAndCrntRecFlg(seq, true);
//			exChk = mwLoanAppMntrngChksRepository.findOneByLoanAppSeqAndChkFlgAndCrntRecFlg(dto.loan_app_seq, Long.valueOf(dto.chk_flg),true);
//		}

        if (exChk == null) {
            exChk = new MwLoanAppMntrngChks();
        }
        exChk.setActnTkn(dto.actn_tkn);
        exChk.setCmnt(dto.cmnt);
        exChk.setCrntRecFlg(true);
        exChk.setCrtdBy(cUser);
        exChk.setCrtdDt(Instant.now());
        exChk.setDelFlg(false);
        exChk.setEffStartDt(Instant.now());
        exChk.setLastUpdBy("w-" + cUser);
        exChk.setLoanAppMntrngChksSeq(seq);
        exChk.setLastUpdDt(Instant.now());
        exChk.setLoanAppSeq(dto.loan_app_seq);
        exChk.setRsn(dto.rsn);
        exChk.setChkFlg(dto.chk_flg);
        mwLoanAppMntrngChksRepository.save(exChk);
        return ResponseEntity.ok().body("{\"body\":\"Updated Loan Mntry Check\"}");
    }

    /*
     * Added by Naveed - Date 11-05-2022
     * Save Disburse posting checklist user response
     * */
    public ResponseEntity disbursePostingCheckList(PostingCheckListDto checkListDto, long loanAppSeq, String user) {

        MwLoanApp mwLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (mwLoanApp == null) {
            return ResponseEntity.ok().body("{\"warning\":\"Loan Application Not Found\"}");
        }

        mwLoanApp.setPdcFlg(checkListDto.pdcFlg);
        mwLoanApp.setOrgnlCnicFlg(checkListDto.orgnlCnicFlg);
        mwLoanApp.setMfiActvLoansFlg(checkListDto.mfiActvLoansFlg);
        mwLoanApp.setVerisysFlg(checkListDto.verisysFlg);
        mwLoanApp.setClntAgremntFlg(checkListDto.clntAgremntFlg);
        mwLoanApp.setClntDueDtAgremntFlg(checkListDto.clntDueDtAgremntFlg);
        mwLoanApp.setLastUpdBy(user);
        mwLoanApp.setLastUpdDt(Instant.now());

        mwLoanAppRepository.save(mwLoanApp);

        return ResponseEntity.ok().body("{\"success\":\"Disbursement Posting Check Listing Updated\"}");
    }

    // get disburse posting CheckList by loanId
    public ResponseEntity getDisbursePostingCheckList(long loanAppSeq) {

        MwLoanApp mwLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (mwLoanApp == null) {
            return ResponseEntity.ok().body("{\"warning\":\"Loan Application Not Found\"}");
        }

        PostingCheckListDto listDto = new PostingCheckListDto();

        listDto.pdcFlg = mwLoanApp.getPdcFlg() == null ? false : mwLoanApp.getPdcFlg();
        listDto.orgnlCnicFlg = mwLoanApp.getOrgnlCnicFlg() == null ? false : mwLoanApp.getOrgnlCnicFlg();
        listDto.mfiActvLoansFlg = mwLoanApp.getMfiActvLoansFlg() == null ? false : mwLoanApp.getMfiActvLoansFlg();
        listDto.verisysFlg = mwLoanApp.getVerisysFlg() == null ? false : mwLoanApp.getVerisysFlg();
        listDto.clntAgremntFlg = mwLoanApp.getClntAgremntFlg() == null ? false : mwLoanApp.getClntAgremntFlg();
        listDto.clntDueDtAgremntFlg = mwLoanApp.getClntDueDtAgremntFlg() == null ? false : mwLoanApp.getClntDueDtAgremntFlg();


        return ResponseEntity.ok().body(listDto);
    }
    // End by Naveed
}
