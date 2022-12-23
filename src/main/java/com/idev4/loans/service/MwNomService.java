package com.idev4.loans.service;

import com.idev4.loans.domain.MwAddr;
import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.domain.MwNom;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.repository.MwAddrRelRepository;
import com.idev4.loans.repository.MwAddrRepository;
import com.idev4.loans.repository.MwNomRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import com.idev4.loans.web.rest.util.SequenceFinder;
import com.idev4.loans.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing MwNom.
 */
@Service
@Transactional
public class MwNomService {

    private final Logger log = LoggerFactory.getLogger(MwNomService.class);

    private final MwNomRepository mwNomRepository;

    private final EntityManager em;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    public MwNomService(MwNomRepository mwNomRepository, MwAddrRelRepository mwAddrRelRepository, MwAddrRepository mwAddrRepository,
                        EntityManager em) {
        this.mwNomRepository = mwNomRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.em = em;
    }

    /**
     * Save a mwNom.
     *
     * @param mwNom the entity to save
     * @return the persisted entity
     */
    public MwNom save(MwNom mwNom) {
        log.debug("Request to save MwNom : {}", mwNom);
        return mwNomRepository.save(mwNom);
    }

    /**
     * Get all the mwNoms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwNom> findAll(Pageable pageable) {
        log.debug("Request to get all MwNoms");
        return mwNomRepository.findAll(pageable);
    }

    /**
     * Get one mwNom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwNom findOne(Long id) {
        log.debug("Request to get MwNom : {}", id);
        return mwNomRepository.findOne(id);
    }

    public NomineeDto getNomineeOfLoanApplication(long loanSeq) {

        MwNom nominee = mwNomRepository.findOneByLoanAppSeqAndCrntRecFlg(loanSeq, true);
        NomineeDto mwnominee = new NomineeDto();
        if (nominee == null)
            return mwnominee;

        MwAddrRel addRel = null;
        MwAddr addr = null;

        addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(nominee.getNomSeq(), "Nominee", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        mwnominee.cnicExpryDate = Date.from(nominee.getCnicExpryDt());
        mwnominee.cnicNum = nominee.getCnicNum();
        mwnominee.dob = Date.from(nominee.getDob());
        mwnominee.firstName = nominee.getFrstNm();
        mwnominee.genderKey = nominee.getGndrKey();
        mwnominee.lastName = nominee.getLastNm();
        mwnominee.loanAppSeq = nominee.getLoanAppSeq();
        mwnominee.maritalStatusKey = nominee.getMrtlStsKey();
        mwnominee.nomineeId = nominee.getNomId();
        mwnominee.nomineeSeq = nominee.getNomSeq();
        mwnominee.occupationKey = nominee.getOccKey();
        mwnominee.phone = nominee.getPhNum();
        mwnominee.relationKey = (nominee.getRelWithClntKey() == null) ? 0 : nominee.getRelWithClntKey();

        if (addr != null) {
            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();

            for (Object[] comb : cityCombinations) {

                mwnominee.country = Long.valueOf(comb[0].toString());
                mwnominee.countryName = comb[1].toString();
                mwnominee.province = Long.valueOf(comb[2].toString());
                mwnominee.provinceName = comb[3].toString();
                mwnominee.district = Long.valueOf(comb[4].toString());
                mwnominee.districtName = comb[5].toString();
                mwnominee.tehsil = Long.valueOf(comb[6].toString());
                mwnominee.tehsilName = comb[7].toString();
                mwnominee.uc = Long.valueOf(comb[8].toString());
                mwnominee.ucName = comb[9].toString() + " " + comb[10].toString();
                mwnominee.city = Long.valueOf(comb[11].toString());
                mwnominee.cityName = comb[12].toString();
            }

            mwnominee.addresSeq = addr.getAddrSeq();
            mwnominee.houseNum = addr.getHseNum();
            mwnominee.sreet_area = addr.getStrt();
            mwnominee.community = (addr.getCmntySeq()) != null ? addr.getCmntySeq() : 0;
            mwnominee.village = addr.getVlg();
            mwnominee.otherDetails = addr.getOthDtl();
            mwnominee.lat = addr.getLatitude();
            mwnominee.lon = addr.getLongitude();
        }

        return mwnominee;
    }

    @Transactional
    public long addNomineeForClient(NomineeDto dto, String currUser) {
        MwNom nominee = mwNomRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (nominee != null)
            return nominee.getNomSeq();
        long seq = SequenceFinder.findNextVal(Sequences.NOM_SEQ);
        MwNom nom = new MwNom();
        Instant currIns = Instant.now();
        nom.setCnicExpryDt(dto.cnicExpryDate.toInstant());
        nom.setCnicNum(dto.cnicNum);
        nom.setCrntRecFlg(true);
        nom.setCrtdBy("w-" + currUser);
        nom.setCrtdDt(currIns);
        nom.setDelFlg(false);
        nom.setDob(dto.dob.toInstant());
        nom.setEffStartDt(currIns);
        nom.setFrstNm(dto.firstName);
        nom.setGndrKey(dto.genderKey);
        nom.setLastNm(dto.lastName);
        nom.setLastUpdBy("w-" + currUser);
        nom.setLastUpdDt(currIns);
        nom.setLoanAppSeq(dto.loanAppSeq);
        nom.setMrtlStsKey(dto.maritalStatusKey);
        nom.setNomId("00000" + dto.loanAppSeq);
        nom.setNomSeq(seq);
        nom.setOccKey(dto.occupationKey);
        nom.setPhNum(dto.phone);
        nom.setRelWithClntKey(dto.relationKey);
        nom.setCoBwrSanFlg(dto.isSAN);
        // nom.setCoBwrSanFlg(Boolean);
        Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
        return mwNomRepository.save(nom).getNomSeq();
    }

    @Transactional
    public long updateNomineeForClient(NomineeDto dto, String currUser) {
        MwNom exNom = mwNomRepository.findOneByNomSeqAndCrntRecFlg(dto.nomineeSeq, true);
        Instant currIns = Instant.now();
        if (exNom == null)
            return 0;

        exNom.setLastUpdBy("w-" + currUser);
        exNom.setLastUpdDt(currIns);
        exNom.setCnicExpryDt(dto.cnicExpryDate.toInstant());
        exNom.setCnicNum(dto.cnicNum);
        exNom.setDob(dto.dob.toInstant());
        exNom.setFrstNm(dto.firstName);
        exNom.setGndrKey(dto.genderKey);
        exNom.setLastNm(dto.lastName);
        exNom.setLoanAppSeq(exNom.getLoanAppSeq());
        exNom.setMrtlStsKey(dto.maritalStatusKey);
        exNom.setNomSeq(dto.nomineeSeq);
        exNom.setOccKey(dto.occupationKey);
        exNom.setPhNum(dto.phone);
        exNom.setRelWithClntKey(dto.relationKey);
        exNom.setCoBwrSanFlg(dto.isSAN);

        return mwNomRepository.save(exNom).getNomSeq();

        // exNom.setLastUpdBy( "w-" + currUser );
        // exNom.setLastUpdDt( currIns );
        // exNom.setCrntRecFlg( false );
        // exNom.setEffEndDt( currIns );
        // mwNomRepository.save( exNom );
        // MwNom nom = new MwNom();
        //
        // nom.setCnicExpryDt( dto.cnicExpryDate.toInstant() );
        // nom.setCnicNum( dto.cnicNum );
        // nom.setCrntRecFlg( true );
        // nom.setCrtdBy( "w-" + currUser );
        // nom.setCrtdDt( currIns );
        // nom.setDelFlg( false );
        // nom.setDob( dto.dob.toInstant() );
        // nom.setEffStartDt( currIns );
        // nom.setFrstNm( dto.firstName );
        // nom.setGndrKey( dto.genderKey );
        // nom.setLastNm( dto.lastName );
        // nom.setLastUpdBy( "w-" + currUser );
        // nom.setLastUpdDt( currIns );
        // nom.setLoanAppSeq( exNom.getLoanAppSeq() );
        // nom.setMrtlStsKey( dto.maritalStatusKey );
        // nom.setNomId( "00000" + dto.loanAppSeq );
        // nom.setNomSeq( dto.nomineeSeq );
        // nom.setOccKey( dto.occupationKey );
        // nom.setPhNum( dto.phone );
        // nom.setRelWithClntKey( dto.relationKey );
        // nom.setCoBwrSanFlg( dto.isSAN );
        //
        // return mwNomRepository.save( nom ).getNomSeq();
    }
}
