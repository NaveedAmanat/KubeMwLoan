package com.idev4.loans.service;

import com.idev4.loans.domain.*;
import com.idev4.loans.dto.*;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwBizAprsl.
 */
@Service
@Transactional
public class MwBizAprslService {

    private final Logger log = LoggerFactory.getLogger(MwBizAprslService.class);

    private final MwBizAprslRepository mwBizAprslRepository;

    private final MwLoanAppService mwLoanAppService;

    private final EntityManager em;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository;

    private final MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository;

    private final MwBizExpDtlRepository mwBizExpDtlRepository;

    private final MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository;

    private final MwBizAprslEstLvstkFinRepository mwBizAprslestLvstkFinRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwBizAprslService(MwBizAprslRepository mwBizAprslRepository, MwLoanAppService mwLoanAppService,
                             MwAddrRelRepository mwAddrRelRepository, MwAddrRepository mwAddrRepository, EntityManager em,
                             MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository, MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository,
                             MwBizExpDtlRepository mwBizExpDtlRepository, MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository,
                             MwBizAprslEstLvstkFinRepository mwBizAprslestLvstkFinRepository, MwLoanAppRepository mwLoanAppRepository,
                             MwClntRepository mwClntRepository) {
        this.mwBizAprslRepository = mwBizAprslRepository;
        this.mwLoanAppService = mwLoanAppService;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.em = em;
        this.mwBizAprslIncmHdrRepository = mwBizAprslIncmHdrRepository;
        this.mwBizAprslIncmDtlRepository = mwBizAprslIncmDtlRepository;
        this.mwBizExpDtlRepository = mwBizExpDtlRepository;
        this.mwBizAprslExtngLvstkRepository = mwBizAprslExtngLvstkRepository;
        this.mwBizAprslestLvstkFinRepository = mwBizAprslestLvstkFinRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwBizAprsl.
     *
     * @param mwBizAprsl the entity to save
     * @return the persisted entity
     */
    public MwBizAprsl save(MwBizAprsl mwBizAprsl) {
        log.debug("Request to save MwBizAprsl : {}", mwBizAprsl);
        return mwBizAprslRepository.save(mwBizAprsl);
    }

    /**
     * Get all the mwBizAprsls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBizAprsl> findAll(Pageable pageable) {
        log.debug("Request to get all MwBizAprsls");
        return mwBizAprslRepository.findAll(pageable);
    }

    /**
     * Get one mwBizAprsl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBizAprsl findOne(Long id) {
        log.debug("Request to get MwBizAprsl : {}", id);
        return mwBizAprslRepository.findOne(id);
    }

    public Map getBusinessApraisalByLoanApplication(long loanAppSeq) {
        Map<String, Object> data = new HashMap<String, Object>();

        List<IncomeDtlDto> primaryIncome = new ArrayList<>();
        List<IncomeDtlDto> secondaryIncome = new ArrayList<>();
        MwBizAprsl aprsl = mwBizAprslRepository.findOneByMwLoanAppAndCrntRecFlg(loanAppSeq, true);
        if (aprsl == null)
            return data;
        MwAddrRel addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(aprsl.getBizAprslSeq(), "Business", true);
        MwAddr addr = null;
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        BusinessAprsalDto aprsal = new BusinessAprsalDto();
        aprsal.loanAppSeq = loanAppSeq;
        aprsal.sectorKey = aprsl.getSectKey();
        aprsal.activityKey = aprsl.getActyKey();
        aprsal.businessDetailStr = aprsl.getBizDtlStr();
        aprsal.personRunningBusinessKey = aprsl.getPrsnRunTheBiz();
        aprsal.businessOwnerShip = aprsl.getBizOwn();
        aprsal.yearsInBusiness = aprsl.getYrsInBiz();
        aprsal.monthsInBusiness = aprsl.getMnthInBiz();

        aprsal.bizPhoneNum = (aprsl.getBizPhNum() == null) ? "" : aprsl.getBizPhNum();
        aprsal.bizPropertyOwnKey = (aprsl.getBizPropertyOwnKey() == null) ? 0L : aprsl.getBizPropertyOwnKey();
        aprsal.isbizAddrSAC = (aprsl.getBizAddrSameAsHomeFlg() == null) ? false : aprsl.getBizAddrSameAsHomeFlg();

        aprsal.bizAprslSeq = aprsl.getBizAprslSeq();

        if (addr != null) {
            aprsal.bizAddressSeq = addr.getAddrSeq();
            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();

            for (Object[] comb : cityCombinations) {

                aprsal.country = Long.valueOf(comb[0].toString());
                aprsal.countryName = comb[1].toString();
                aprsal.province = Long.valueOf(comb[2].toString());
                aprsal.provinceName = comb[3].toString();
                aprsal.district = Long.valueOf(comb[4].toString());
                aprsal.districtName = comb[5].toString();
                aprsal.tehsil = Long.valueOf(comb[6].toString());
                aprsal.tehsilName = comb[7].toString();
                aprsal.uc = Long.valueOf(comb[8].toString());
                aprsal.ucName = comb[9].toString() + " " + comb[10].toString();
                aprsal.city = Long.valueOf(comb[11].toString());
                aprsal.cityName = comb[12].toString();
            }

            aprsal.houseNum = addr.getHseNum();
            aprsal.sreet_area = addr.getStrt();
            aprsal.community = addr.getCmntySeq();
            aprsal.village = addr.getVlg();
            aprsal.otherDetails = addr.getOthDtl();
            aprsal.lat = addr.getLatitude();
            aprsal.lon = addr.getLongitude();
        }

        IncomeHdrDto hdrDto = new IncomeHdrDto();
        if (aprsl.getBizAprslSeq() != null && aprsl.getBizAprslSeq() > 0) {
            MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByMwBizAprslAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
            if (hdr != null) {
                aprsal.incomeHdrSeq = hdr.getIncmHdrSeq();
                hdrDto.bizAprslSeq = aprsl.getBizAprslSeq();
                hdrDto.incomeHdrSeq = hdr.getIncmHdrSeq();
                hdrDto.maxMonthSale = hdr.getMaxMnthSalAmt();
                hdrDto.maxSaleMonth = hdr.getMaxSalNumOfMnth();
                hdrDto.minMonthSale = hdr.getMinMnthSalAmt();
                hdrDto.minSaleMonth = hdr.getMinSalNumOfMnth();

                if (hdr.getIncmHdrSeq() != null) {
                    List<MwBizAprslIncmDtl> dtls = mwBizAprslIncmDtlRepository
                            .findAllByMwBizAprslIncmHdrAndCrntRecFlgAndEntyTypFlg(hdr.getIncmHdrSeq(), true, 1);
                    if (dtls != null && dtls.size() > 0) {
                        dtls.forEach((dt) -> {
                            IncomeDtlDto d = new IncomeDtlDto();
                            d.incomeAmount = dt.getIncmAmt();
                            d.incomeDtlSeq = String.valueOf(dt.getIncmDtlSeq());
                            d.incomeCategoryKey = dt.getIncmCtgryKey();
                            d.incomeTypeKey = dt.getIncmTypKey();
                            if (dt.getIncmCtgryKey() == 1) {
                                primaryIncome.add(d);
                            } else {
                                secondaryIncome.add(d);
                            }
                        });
                    }
                }
            }
        }

        aprsal.primaryIncome = primaryIncome;
        aprsal.secondaryIncome = secondaryIncome;
        data.put("BusinessIncome", hdrDto);

        List<BizExpDto> businessExpense = new ArrayList<>();
        List<BizExpDto> householdExpense = new ArrayList<>();

        if (aprsl.getBizAprslSeq() != null && aprsl.getBizAprslSeq() > 0) {

            List<MwBizExpDtl> expDtls = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlg(aprsl.getBizAprslSeq(), true,
                    1);
            if (expDtls != null && expDtls.size() > 0) {
                expDtls.forEach((dt) -> {
                    BizExpDto d = new BizExpDto();
                    d.bizExpSeq = dt.getExpDtlSeq();
                    d.expAmount = dt.getExpAmt();
                    d.expCategoryKey = dt.getExpCtgryKey();
                    d.expTypKey = dt.getExpTypKey();
                    if (dt.getExpCtgryKey() == 1) {
                        businessExpense.add(d);
                    } else {
                        householdExpense.add(d);
                    }
                });
            }
        }
        aprsal.businessExpense = businessExpense;
        aprsal.householdExpense = householdExpense;

        List<BizAprslExtngLvstkDto> existingLvStck = new ArrayList<>();

        if (aprsl.getBizAprslSeq() != null && aprsl.getBizAprslSeq() > 0) {
            List<MwBizAprslExtngLvstk> exstngLs = mwBizAprslExtngLvstkRepository
                    .findAllByBizAprslSeqAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
            if (exstngLs != null && exstngLs.size() > 0) {
                exstngLs.forEach((dtt) -> {
                    BizAprslExtngLvstkDto d = new BizAprslExtngLvstkDto();
                    d.anmlBrd = dtt.getAnmlBrd();
                    d.anmlHc = dtt.getAnmlHc();
                    d.anmlKnd = dtt.getAnmlKnd();
                    d.anmlTyp = dtt.getAnmlTyp();
                    d.estVal = dtt.getEstVal();
                    existingLvStck.add(d);
                });
            }
        }
        aprsal.extngLvStk = existingLvStck;

        List<BizAprslEstLvstkFinDto> estLvStk = new ArrayList<>();

        if (aprsl.getBizAprslSeq() != null && aprsl.getBizAprslSeq() > 0) {
            List<MwBizAprslEstLvstkFin> exstngLs = mwBizAprslestLvstkFinRepository
                    .findAllByBizAprslSeqAndCrntRecFlg(aprsl.getBizAprslSeq(), true);
            if (exstngLs != null && exstngLs.size() > 0) {
                exstngLs.forEach((dtt) -> {
                    BizAprslEstLvstkFinDto d = new BizAprslEstLvstkFinDto();
                    d.anmlBrd = dtt.getAnmlBrd();
                    d.anmlHc = dtt.getAnmlHc();
                    d.anmlKnd = dtt.getAnmlKnd();
                    d.anmlTyp = dtt.getAnmlTyp();
                    d.estVal = dtt.getEstVal();
                    d.amtByClient = dtt.getAmtByClient();
                    d.amtFin = dtt.getAmtFin();
                    estLvStk.add(d);
                });
            }
        }
        aprsal.estLvStk = estLvStk;

        data.put("BusinessApraisal", aprsal);
        return data;
    }

    public long addClientBusinessAprsl(BusinessAprsalDto dto, String currUser) {
        MwBizAprsl aprsl = mwBizAprslRepository.findOneByMwLoanAppAndCrntRecFlg(dto.loanAppSeq, true);
        if (aprsl != null)
            return updateClientBusinessAprsl(dto, currUser);

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (app != null) {
            Long cycleNum = app.getLoanCyclNum();
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                Long cnicNum = clnt.getCnicNum();

                MwBizAprsl bizAprsl = new MwBizAprsl();
                Instant currIns = Instant.now();
                bizAprsl.setBizAprslSeq(Common.GenerateTableSequence(cnicNum.toString(), TableNames.MW_BIZ_APRSL, cycleNum));
                bizAprsl.setActyKey(dto.activityKey);
                bizAprsl.setBizOwn(dto.businessOwnerShip);
                bizAprsl.setCrntRecFlg(true);
                bizAprsl.setCrtdBy("w-" + currUser);
                bizAprsl.setCrtdDt(currIns);
                bizAprsl.setDelFlg(false);
                bizAprsl.setEffStartDt(currIns);
                bizAprsl.setLastUpdBy("w-" + currUser);
                bizAprsl.setLastUpdDt(currIns);
                bizAprsl.setMnthInBiz(dto.monthsInBusiness);
                bizAprsl.setBizDtlStr(dto.businessDetailStr);

                bizAprsl.setMwLoanApp(dto.loanAppSeq);
                bizAprsl.setPrsnRunTheBiz(dto.personRunningBusinessKey);
                bizAprsl.setSectKey(dto.sectorKey);
                bizAprsl.setYrsInBiz(dto.yearsInBusiness);

                bizAprsl.setBizPhNum(dto.bizPhoneNum);
                bizAprsl.setBizAddrSameAsHomeFlg(dto.isbizAddrSAC);
                bizAprsl.setBizPropertyOwnKey(dto.bizPropertyOwnKey);
                bizAprsl.setSyncFlg(true);
                Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
                return mwBizAprslRepository.save(bizAprsl).getBizAprslSeq();
            }
        }
        return 0;
    }

    @Transactional
    public long updateClientBusinessAprsl(BusinessAprsalDto dto, String currUser) {

        MwBizAprsl mwBizAprsl = mwBizAprslRepository.findOneByBizAprslSeqAndCrntRecFlg(dto.bizAprslSeq, true);
        Instant currIns = Instant.now();
        if (mwBizAprsl == null)
            return 0;

        mwBizAprsl.setLastUpdBy(currUser);
        mwBizAprsl.setLastUpdDt(currIns);
        mwBizAprsl.setActyKey(dto.activityKey);
        mwBizAprsl.setBizOwn(dto.businessOwnerShip);
        mwBizAprsl.setMnthInBiz(dto.monthsInBusiness);
        mwBizAprsl.setBizDtlStr(dto.businessDetailStr);
        mwBizAprsl.setMwLoanApp(dto.loanAppSeq);
        mwBizAprsl.setPrsnRunTheBiz(dto.personRunningBusinessKey);
        mwBizAprsl.setSectKey(dto.sectorKey);
        mwBizAprsl.setYrsInBiz(dto.yearsInBusiness);
        mwBizAprsl.setBizPhNum(dto.bizPhoneNum);
        mwBizAprsl.setBizAddrSameAsHomeFlg(dto.isbizAddrSAC);
        mwBizAprsl.setBizPropertyOwnKey(dto.bizPropertyOwnKey);
        mwBizAprsl.setSyncFlg(true);

        //
        // exBizAprsl.setCrntRecFlg( false );
        // exBizAprsl.setLastUpdBy( "w-" + currUser );
        // exBizAprsl.setLastUpdDt( currIns );
        // exBizAprsl.setEffEndDt( currIns );
        // mwBizAprslRepository.save( exBizAprsl );
        //
        // MwBizAprsl bizAprsl = new MwBizAprsl();
        //
        // bizAprsl.setBizAprslSeq( dto.bizAprslSeq );
        // bizAprsl.setActyKey( dto.activityKey );
        // bizAprsl.setBizOwn( dto.businessOwnerShip );
        // bizAprsl.setCrntRecFlg( true );
        // bizAprsl.setCrtdBy( "w-" + currUser );
        // bizAprsl.setCrtdDt( currIns );
        // bizAprsl.setDelFlg( false );
        // bizAprsl.setEffStartDt( currIns );
        // bizAprsl.setLastUpdBy( "w-" + currUser );
        // bizAprsl.setLastUpdDt( currIns );
        // bizAprsl.setMnthInBiz( dto.monthsInBusiness );
        // bizAprsl.setBizDtlStr( dto.businessDetailStr );
        //
        // bizAprsl.setMwLoanApp( dto.loanAppSeq );
        // bizAprsl.setPrsnRunTheBiz( dto.personRunningBusinessKey );
        // bizAprsl.setSectKey( dto.sectorKey );
        // bizAprsl.setYrsInBiz( dto.yearsInBusiness );
        //
        // bizAprsl.setBizPhNum( dto.bizPhoneNum );
        // bizAprsl.setBizAddrSameAsHomeFlg( dto.isbizAddrSAC );
        // bizAprsl.setBizPropertyOwnKey( dto.bizPropertyOwnKey );
        // bizAprsl.setSyncFlg( true );

        return save(mwBizAprsl).getBizAprslSeq();
    }
}
