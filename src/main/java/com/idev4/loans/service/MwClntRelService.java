package com.idev4.loans.service;

import com.idev4.loans.domain.*;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.dto.tab.PrevInsuranceMemDto;
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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwClntRel.
 */
@Service
@Transactional
public class MwClntRelService {

    private static MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository;
    private final Logger log = LoggerFactory.getLogger(MwClntRelService.class);

    private final MwClntRelRepository mwClntRelRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwClntRepository mwClntRepository;

    private final MwLoanAppRepository mwLoanAppRepository;
    private final MwClntHlthInsrRepository mwClntHlthInsrRepository;
    private final MwHlthInsrPlanRepository mwHlthInsrPlanRepository;
    private final MwHlthInsrMembRepository mwHlthInsrMembRepository;
    private final EntityManager em;
    DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");

    public MwClntRelService(MwClntRelRepository mwClntRelRepository, MwAddrRelRepository mwAddrRelRepository,
                            MwAddrRepository mwAddrRepository, EntityManager em, MwClntRepository mwClntRepository, MwLoanAppRepository mwLoanAppRepository,
                            MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository, MwClntHlthInsrRepository mwClntHlthInsrRepository,
                            MwHlthInsrPlanRepository mwHlthInsrPlanRepository, MwHlthInsrMembRepository mwHlthInsrMembRepository) {
        this.mwClntRelRepository = mwClntRelRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.em = em;
        this.mwClntRepository = mwClntRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwLoanFormCmplFlgRepository = mwLoanFormCmplFlgRepository;
        this.mwClntHlthInsrRepository = mwClntHlthInsrRepository;
        this.mwHlthInsrPlanRepository = mwHlthInsrPlanRepository;
        this.mwHlthInsrMembRepository = mwHlthInsrMembRepository;
    }

    /**
     * Save a mwClntRel.
     *
     * @param mwClntRel the entity to save
     * @return the persisted entity
     */
    public MwClntRel save(MwClntRel mwClntRel) {
        log.debug("Request to save MwClntRel : {}", mwClntRel);
        return mwClntRelRepository.save(mwClntRel);
    }

    /**
     * Get all the mwClntRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClntRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwClntRels");
        return mwClntRelRepository.findAll(pageable);
    }

    /**
     * Get one mwClntRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClntRel findOne(Long id) {
        log.debug("Request to get MwClntRel : {}", id);
        return mwClntRelRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public MwClntRel findOneByClntRelSeq(Long id) {
        log.debug("Request to get MwClntRel : {}", id);
        return mwClntRelRepository.findOneByClntRelSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public MwClntRel findOneBy(Long id) {
        log.debug("Request to get MwClntRel : {}", id);
        return mwClntRelRepository.findOne(id);
    }

    public long saveClientRelative(NomineeDto dto, String currUser) {
        MwClntRel clntRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loanAppSeq, dto.typFlg, true);

        if (clntRel != null) {
            MwClntRel clientRel;
            clntRel.setCrtdDt(Instant.now());
            clntRel.setCnicNum(dto.cnicNum);

            if (dto.cnicExpryDate != null)
                clntRel.setCnicExpryDt(dto.cnicExpryDate.toInstant());
            if (dto.cnicIssueDate != null)
                clntRel.setCnicIssueDt(dto.cnicIssueDate.toInstant());

            clntRel.setCoBwrSanFlg(dto.isSAN);
            clntRel.setDob(dto.dob.toInstant());
            clntRel.setFrstNm(dto.firstName);
            clntRel.setLastNm(dto.lastName);
            clntRel.setFthr_frst_nm(dto.fatherFirstName == null ? "N/A" : dto.fatherFirstName);
            clntRel.setFthr_last_nm(dto.fatherLastName == null ? "N/A" : dto.fatherLastName);
            clntRel.setGndrKey(dto.genderKey);
            clntRel.setLastUpdBy("w-" + currUser);
            clntRel.setLastUpdDt(Instant.now());
            clntRel.setLoanAppSeq(dto.loanAppSeq);
            clntRel.setMrtlStsKey(dto.maritalStatusKey);
            clntRel.setNomFthrSpzFlg(dto.fatherSpzFlag);
            clntRel.setOccKey(dto.occupationKey);
            clntRel.setPhNum(dto.phone);
            clntRel.setRelTypFlg(dto.typFlg);
            clntRel.setRelWthClntKey(dto.relationKey);
            clntRel.setDelFlg(false);
            clntRel.setCrntRecFlg(true);
            clntRel.setSyncFlg(true);
            mwClntRelRepository.save(clntRel);
            // if(dto.typFlg == 1) {
            // if(dto.isSAN) {
            // MwClntRel clnt = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loanAppSeq, 2, true);
            // if(clnt!=null)
            // mwClntRelRepository.delete(clnt);
            // Common.removeComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
            // }else {
            // Common.updateFormComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
            // }
            // }
            // MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
            // if(loan!=null) {
            // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
            // if(clnt!=null) {
            // clnt.setCoBwrSanFlg(dto.isSAN);
            // mwClntRepository.save(clnt);
            // }
            // }

            Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            return clntRel.getClntRelSeq();

        }
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);

        if (app != null) {
            if (dto.typFlg == Common.Coborrower) {
                app.setRelAddrAsClntFLg(dto.relAddrAsClntFlg);
            }
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                long seq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_MFCIB_OTH_OUTSD_LOAN,
                        app.getLoanCyclNum());

                MwClntRel clientRel = new MwClntRel();

                clientRel.setClntRelSeq(seq);
                clientRel.setEffStartDt(Instant.now());
                clientRel.setCrtdBy("w-" + currUser);
                clientRel.setCrtdDt(Instant.now());
                clientRel.setCnicNum(dto.cnicNum);
                if (dto.cnicExpryDate != null)
                    clientRel.setCnicExpryDt(dto.cnicExpryDate.toInstant());
                if (dto.cnicIssueDate != null)
                    clientRel.setCnicIssueDt(dto.cnicIssueDate.toInstant());
                clientRel.setCoBwrSanFlg(dto.isSAN);
                clientRel.setDob(dto.dob.toInstant());
                clientRel.setFrstNm(dto.firstName);
                clientRel.setLastNm(dto.lastName);
                clientRel.setFthr_frst_nm(dto.fatherFirstName == null ? "N/A" : dto.fatherFirstName);
                clientRel.setFthr_last_nm(dto.fatherLastName == null ? "N/A" : dto.fatherLastName);
                clientRel.setGndrKey(dto.genderKey);
                clientRel.setLastUpdBy("w-" + currUser);
                clientRel.setLastUpdDt(Instant.now());
                clientRel.setLoanAppSeq(dto.loanAppSeq);
                clientRel.setMrtlStsKey(dto.maritalStatusKey);
                clientRel.setNomFthrSpzFlg(dto.fatherSpzFlag);
                clientRel.setOccKey(dto.occupationKey);
                clientRel.setPhNum(dto.phone);
                clientRel.setRelTypFlg(dto.typFlg);
                clientRel.setRelWthClntKey(dto.relationKey);
                clientRel.setDelFlg(false);
                clientRel.setCrntRecFlg(true);
                clientRel.setSyncFlg(true);
                // if(dto.typFlg == 1) {
                // if(dto.isSAN) {
                // MwClntRel clnt = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loanAppSeq, 2, true);
                // if(clnt!=null)
                // mwClntRelRepository.delete(clnt);
                // Common.removeComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
                // }else {
                // Common.updateFormComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
                // }
                // }
                // MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
                // if(loan!=null) {
                // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
                // if(clnt!=null) {
                // clnt.setCoBwrSanFlg(dto.isSAN);
                // mwClntRepository.save(clnt);
                // }
                // }
                Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
                mwClntRelRepository.save(clientRel);
                return clientRel.getClntRelSeq();
            }
        }
        return 0;
    }

    public long updateClientRelative(NomineeDto dto, String currUser) {

        //MwClntRel mwClntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlgAndCrntRecFlg( dto.clntRelSeq, dto.typFlg, true );
        MwClntRel mwClntRel = mwClntRelRepository.findOneByClntRelSeqAndRelTypFlgAndCrntRecFlg(dto.clntRelSeq, dto.typFlg, true);

        if (mwClntRel == null)
            return 0L;

        mwClntRel.setLastUpdDt(Instant.now());
        mwClntRel.setLastUpdBy(currUser);
        mwClntRel.setCnicNum(dto.cnicNum);
        if (dto.cnicExpryDate != null)
            mwClntRel.setCnicExpryDt(dto.cnicExpryDate.toInstant());
        if (dto.cnicIssueDate != null)
            mwClntRel.setCnicIssueDt(dto.cnicIssueDate.toInstant());

        mwClntRel.setCoBwrSanFlg(dto.isSAN);
        mwClntRel.setDob(dto.dob.toInstant());

        //Modified by Areeba - Next of Kin changes - 17-8-2022
//        java.util.Date dateOfBirth = null;
//        try {
//            dateOfBirth = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1690");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        mwClntRel.setDob( dto.dob == null ? dateOfBirth.toInstant() : dto.dob.toInstant() );
        mwClntRel.setFrstNm(dto.firstName);
        mwClntRel.setLastNm(dto.lastName);
        mwClntRel.setFthr_frst_nm(dto.fatherFirstName);
        mwClntRel.setFthr_last_nm(dto.fatherLastName);
//        mwClntRel.setFthr_frst_nm( dto.fatherFirstName == null ? "N/A" : dto.fatherFirstName );
//        mwClntRel.setFthr_last_nm( dto.fatherLastName == null ? "N/A" : dto.fatherLastName );
        mwClntRel.setGndrKey(dto.genderKey);
        mwClntRel.setLoanAppSeq(dto.loanAppSeq);
//        mwClntRel.setMrtlStsKey( dto.maritalStatusKey == null ? 0 : dto.maritalStatusKey );
        mwClntRel.setMrtlStsKey(dto.maritalStatusKey);
        mwClntRel.setNomFthrSpzFlg(dto.fatherSpzFlag);
        mwClntRel.setOccKey(dto.occupationKey); //0
//        mwClntRel.setOccKey( dto.occupationKey == null ? 0 : dto.occupationKey); //0
        //Ended by Areeba

        mwClntRel.setPhNum(dto.phone);
        mwClntRel.setRelWthClntKey(dto.relationKey);
        mwClntRel.setRelTypFlg(dto.typFlg);

        // clntRel.setCrntRecFlg( false );
        // clntRel.setDelFlg( true );
        // clntRel.setEffEndDt( Instant.now() );
        // clntRel.setLastUpdBy( "w-" + currUser );
        // clntRel.setLastUpdDt( Instant.now() );
        //
        // long seq = mwClntRelRepository.save( clntRel ).getClntRelSeq();
        //
        // MwClntRel clientRel = new MwClntRel();
        //
        // clientRel.setClntRelSeq( clntRel.getClntRelSeq() );
        // clientRel.setEffStartDt( Instant.now() );
        // clientRel.setCrtdBy( "w-" + currUser );
        // clientRel.setCrtdDt( Instant.now() );
        // clientRel.setCnicNum( dto.cnicNum );
        // if ( dto.cnicExpryDate != null )
        // clientRel.setCnicExpryDt( dto.cnicExpryDate.toInstant() );
        // clientRel.setCoBwrSanFlg( dto.isSAN );
        // clientRel.setDob( dto.dob.toInstant() );
        // clientRel.setFrstNm( dto.firstName );
        // clientRel.setLastNm( dto.lastName );
        // clientRel.setFthr_frst_nm( dto.fatherFirstName );
        // clientRel.setFthr_last_nm( dto.fatherLastName );
        // clientRel.setGndrKey( dto.genderKey );
        // clientRel.setLastUpdBy( "w-" + currUser );
        // clientRel.setLastUpdDt( Instant.now() );
        // clientRel.setLoanAppSeq( dto.loanAppSeq );
        // clientRel.setMrtlStsKey( dto.maritalStatusKey );
        // clientRel.setNomFthrSpzFlg( dto.fatherSpzFlag );
        // clientRel.setOccKey( dto.occupationKey );
        // clientRel.setPhNum( dto.phone );
        // clientRel.setRelWthClntKey( dto.relationKey );
        // clientRel.setRelTypFlg( dto.typFlg );
        // clientRel.setDelFlg( false );
        // clientRel.setCrntRecFlg( true );
        // clientRel.setSyncFlg( true );

        // if(dto.typFlg == 1) {
        // if(dto.isSAN) {
        // MwClntRel clntRl = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(dto.loanAppSeq, 2, true);
        // if(clntRel!=null)
        // mwClntRelRepository.delete(clntRl);
        // Common.removeComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
        // }else {
        // Common.updateFormComplFlag(dto.cobFormSeq,dto.loanAppSeq,currUser);
        // }
        // }
        // MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        // if(loan!=null) {
        // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(loan.getClntSeq(), true);
        // if(clnt!=null) {
        // clnt.setCoBwrSanFlg(dto.isSAN);
        // mwClntRepository.save(clnt);
        // }
        // }
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (app != null) {
            if (dto.typFlg == Common.Coborrower) {
                app.setRelAddrAsClntFLg(dto.relAddrAsClntFlg);
            }
        }
        return mwClntRelRepository.save(mwClntRel).getClntRelSeq();
    }

    public NomineeDto getClientRelativeInformation(long loanAppSeq, long typkey) {
        MwClntRel clntRel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(loanAppSeq, typkey, true);

        if (clntRel == null)
            return null;

        NomineeDto dto = new NomineeDto();
        dto.clntRelSeq = clntRel.getClntRelSeq();
        dto.cnicNum = clntRel.getCnicNum();
        dto.cnicIssueDate = (clntRel.getCnicIssueDt() == null) ? null : Date.from(clntRel.getCnicIssueDt());
        dto.cnicIssueDateStr = (clntRel.getCnicIssueDt() == null) ? null : Common.GetFormattedDateForTab(clntRel.getCnicIssueDt(), true);
        dto.cnicExpryDate = (clntRel.getCnicExpryDt() == null) ? null : Date.from(clntRel.getCnicExpryDt());
        dto.cnicExpryDateStr = (clntRel.getCnicExpryDt() == null) ? null
                : Common.GetFormattedDateForTab(clntRel.getCnicExpryDt(), true);
        dto.isSAN = clntRel.isCoBwrSanFlg();
        dto.dob = (clntRel.getDob() == null) ? null : Date.from(clntRel.getDob());
        dto.dobStr = (clntRel.getDob() == null) ? null : Common.GetFormattedDateForTab(clntRel.getDob(), true);
        dto.firstName = clntRel.getFrstNm();
        dto.lastName = clntRel.getLastNm();
        dto.fatherFirstName = clntRel.getFthr_frst_nm();
        dto.fatherLastName = clntRel.getFthr_last_nm();
        dto.genderKey = clntRel.getGndrKey();
        dto.maritalStatusKey = clntRel.getMrtlStsKey();
        dto.fatherSpzFlag = clntRel.isNomFthrSpzFlg();
        dto.occupationKey = clntRel.getOccKey();
        dto.phone = clntRel.getPhNum();
        dto.relationKey = clntRel.getRelWthClntKey();
        // dto.residenceKey = clntRel.getResTypKey();

        MwAddrRel addRel = null;
        MwAddr addr = null;

        String str = "";
        if (typkey == 3)
            str = Common.cobAddress;
        if (typkey == 4)
            str = Common.relAddress;

        addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(clntRel.getClntRelSeq(), str, true);
        if (addRel != null) {
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);
            dto.addressRelSeq = addRel.getAddrRelSeq();
        }
        if (addr != null) {
            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();
            for (Object[] comb : cityCombinations) {

                dto.country = Long.valueOf(comb[0].toString());
                dto.countryName = comb[1].toString();
                dto.province = Long.valueOf(comb[2].toString());
                dto.provinceName = comb[3].toString();
                dto.district = Long.valueOf(comb[4].toString());
                dto.districtName = comb[5].toString();
                dto.tehsil = Long.valueOf(comb[6].toString());
                dto.tehsilName = comb[7].toString();
                dto.uc = Long.valueOf(comb[8].toString());
                dto.ucName = comb[9].toString() + " " + comb[10].toString();
                dto.city = Long.valueOf(comb[11].toString());
                dto.cityName = comb[12].toString();
            }

            dto.addresSeq = addr.getAddrSeq();
            dto.houseNum = (addr.getHseNum() == null) ? "" : addr.getHseNum();
            dto.sreet_area = addr.getStrt();
            dto.community = (addr.getCmntySeq()) != null ? addr.getCmntySeq() : 0;
            dto.village = addr.getVlg();
            dto.otherDetails = addr.getOthDtl();
            dto.lat = addr.getLatitude();
            dto.lon = addr.getLongitude();
        }

        return dto;
    }

    public NomineeDto getClientRelativeInformationForPreviousLoan(long clntSeq, long typkey) {
        NomineeDto dto = null;
        List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(Long.valueOf(clntSeq), true);
        for (int i = 0; i < loans.size(); i++) {
            dto = getClientRelativeInformation(((MwLoanApp) loans.get(i)).getLoanAppSeq().longValue(), typkey);
            if (dto != null)
                return dto;
        }

        return dto;
    }

    public PrevInsuranceMemDto getPrevInsrPlan(long clntSeq, long cycNm) {
        PrevInsuranceMemDto dto = new PrevInsuranceMemDto();
        // String lnApp = Queries.insLnApp;

        MwLoanApp lnApp = mwLoanAppRepository.findOneByClntSeqAndLoanCyclNum(clntSeq, (cycNm - 1));
        if (lnApp != null) {
            MwClntHlthInsr clntHlth = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(lnApp.getLoanAppSeq(), true);

            if (clntHlth != null) {
                dto.exclCatKey = clntHlth.getExclCtgryKey();
                dto.hlthFlg = clntHlth.getHlthInsrFlg();
                dto.mnBrdErnr = clntHlth.getMainBreadEarnerNm();
                dto.planStsKey = clntHlth.getMwHlthInsrPlan();
                dto.relBrdErnr = clntHlth.getRelWthBreadEarnerKey();

                MwHlthInsrPlan hlthInsrPln = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(clntHlth.getMwHlthInsrPlan(),
                        true);

                if (hlthInsrPln != null) {
                    dto.anlPremAmnt = hlthInsrPln.getAnlPremAmt();

                    dto.hlthInsrPln = hlthInsrPln.getPlanNm();
                    dto.maxPlcyAmnt = hlthInsrPln.getMaxPlcyAmt();

                }
            }
            dto.insMem = getInsMem(lnApp.getLoanAppSeq());

        }

        return dto;

    }

    public List<InsrMemDto> getInsMem(Long lnSeq) {
        List<InsrMemDto> resp = new ArrayList<InsrMemDto>();

        List<MwHlthInsrMemb> mwHlthInsrMem = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(lnSeq, true);
        if (mwHlthInsrMem != null) {
            for (int i = 0; i < mwHlthInsrMem.size(); i++) {
                InsrMemDto insrMem1 = new InsrMemDto();
                insrMem1.crntRecFlg = mwHlthInsrMem.get(i).getCrntRecFlg();
                insrMem1.crtdBy = mwHlthInsrMem.get(i).getCrtdBy();
                insrMem1.crtdDt = mwHlthInsrMem.get(i).getCrtdDt();
                insrMem1.delFlg = mwHlthInsrMem.get(i).getDelFlg();
                insrMem1.dob = mwHlthInsrMem.get(i).getDob();
                insrMem1.effEndDt = mwHlthInsrMem.get(i).getEffEndDt();
                insrMem1.effStartDt = mwHlthInsrMem.get(i).getEffStartDt();
                insrMem1.gndrKey = mwHlthInsrMem.get(i).getGndrKey();
                insrMem1.hlthInsrMembSeq = mwHlthInsrMem.get(i).getHlthInsrMembSeq();
                insrMem1.lastUpdBy = mwHlthInsrMem.get(i).getLastUpdBy();
                insrMem1.lastUpdDt = mwHlthInsrMem.get(i).getLastUpdDt();
                insrMem1.loanAppSeq = mwHlthInsrMem.get(i).getLoanAppSeq();
                insrMem1.membCnicNum = mwHlthInsrMem.get(i).getMembCnicNum();
                insrMem1.memberId = mwHlthInsrMem.get(i).getMemberId();
                insrMem1.membNm = mwHlthInsrMem.get(i).getMembNm();
                insrMem1.mrtlStsKey = mwHlthInsrMem.get(i).getMrtlStsKey();
                insrMem1.relKey = mwHlthInsrMem.get(i).getRelKey();
                insrMem1.syncFlg = mwHlthInsrMem.get(i).getSyncFlg();

                resp.add(insrMem1);
            }

        }
        return resp;
    }

    public class InsrMemDto {

        public Long hlthInsrMembSeq;

        public Long membCnicNum;

        public String membNm;

        public Instant dob;

        public Long gndrKey;

        public Long relKey;

        public Long mrtlStsKey;

        public String crtdBy;

        public Instant crtdDt;

        public String lastUpdBy;

        public Instant lastUpdDt;

        public Boolean delFlg;

        public Instant effStartDt;

        public Instant effEndDt;

        public Boolean crntRecFlg;

        public Long loanAppSeq;

        public Boolean syncFlg;

        public Long memberId;

    }

}
