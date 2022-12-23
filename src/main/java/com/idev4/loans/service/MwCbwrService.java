package com.idev4.loans.service;

import com.idev4.loans.domain.MwAddr;
import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.domain.MwCbwr;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.repository.MwAddrRelRepository;
import com.idev4.loans.repository.MwAddrRepository;
import com.idev4.loans.repository.MwCbwrRepository;
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
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing MwCbwr.
 */
@Service
@Transactional
public class MwCbwrService {

    private final Logger log = LoggerFactory.getLogger(MwCbwrService.class);

    private final MwCbwrRepository mwCbwrRepository;

    private final EntityManager em;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    public MwCbwrService(MwCbwrRepository mwCbwrRepository, MwAddrRelRepository mwAddrRelRepository, MwAddrRepository mwAddrRepository,
                         EntityManager em) {
        this.mwCbwrRepository = mwCbwrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.em = em;
    }

    /**
     * Save a mwCbwr.
     *
     * @param mwCbwr the entity to save
     * @return the persisted entity
     */
    public MwCbwr save(MwCbwr mwCbwr) {
        log.debug("Request to save MwCbwr : {}", mwCbwr);
        return mwCbwrRepository.save(mwCbwr);
    }

    /**
     * Get all the mwCbwrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCbwr> findAll(Pageable pageable) {
        log.debug("Request to get all MwCbwrs");
        return mwCbwrRepository.findAll(pageable);
    }

    /**
     * Get one mwCbwr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwCbwr> findOne(Long id) {
        log.debug("Request to get MwCbwr : {}", id);
        return Optional.of(mwCbwrRepository.findOne(id));
    }

    @Transactional
    public long addNewCbwr(NomineeDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.CBWR_SEQ);
        MwCbwr cbwr = new MwCbwr();
        Instant currIns = Instant.now();
        cbwr.setCbwrId("00000" + seq);
        cbwr.setCbwrSeq(seq);
        cbwr.setCnicEpryDt(dto.cnicExpryDate.toInstant());
        cbwr.setCnicNum(dto.cnicNum);
        cbwr.setCrntRecFlg(true);
        cbwr.setCrtdBy(currUser);
        cbwr.setCrtdDt(currIns);
        cbwr.setDelFlg(false);
        cbwr.setDob(dto.dob.toInstant());
        cbwr.setEffStartDt(currIns);
        cbwr.setFrstNm(dto.firstName);
        cbwr.setGndrKey(dto.genderKey);
        cbwr.setLastNm(dto.lastName);
        cbwr.setLastUpdBy(currUser);
        cbwr.setLastUpdDt(currIns);
        cbwr.setLoanAppSeq(dto.loanAppSeq);
        cbwr.setOccKey(dto.occupationKey);
        cbwr.setPhNum(dto.phone);
        cbwr.setRelWthClntKey(dto.relationKey);
        cbwr.setResTypKey(dto.residenceKey);
        Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
        return mwCbwrRepository.save(cbwr).getCbwrSeq();
    }

    @Transactional
    public long updateExistingCbwr(NomineeDto dto, String currUser) {
        MwCbwr exCbwr = mwCbwrRepository.findOneByCbwrSeqAndCrntRecFlg(dto.coBorrowerSeq, true);
        Instant currIns = Instant.now();
        if (exCbwr == null)
            return 0;

        exCbwr.setLastUpdBy(currUser);
        exCbwr.setLastUpdDt(currIns);
        exCbwr.setCbwrSeq(dto.coBorrowerSeq);
        exCbwr.setCnicEpryDt(dto.cnicExpryDate.toInstant());
        exCbwr.setCnicNum(dto.cnicNum);
        exCbwr.setDob(dto.dob.toInstant());
        exCbwr.setFrstNm(dto.firstName);
        exCbwr.setGndrKey(dto.genderKey);
        exCbwr.setLastNm(dto.lastName);
        exCbwr.setLoanAppSeq(dto.loanAppSeq);
        exCbwr.setOccKey(dto.occupationKey);
        exCbwr.setPhNum(dto.phone);
        exCbwr.setRelWthClntKey(dto.relationKey);
        exCbwr.setResTypKey(dto.residenceKey);

        return mwCbwrRepository.save(exCbwr).getCbwrSeq();

        // exCbwr.setLastUpdBy(currUser);
        // exCbwr.setLastUpdDt(currIns);
        // exCbwr.setCrntRecFlg(false);
        // exCbwr.setEffEndDt(currIns);
        // mwCbwrRepository.save(exCbwr);
        //
        // MwCbwr cbwr = new MwCbwr();
        // cbwr.setCbwrId("00000"+dto.coBorrowerSeq);
        // cbwr.setCbwrSeq(dto.coBorrowerSeq);
        // cbwr.setCnicEpryDt(dto.cnicExpryDate.toInstant());
        // cbwr.setCnicNum(dto.cnicNum);
        // cbwr.setCrntRecFlg(true);
        // cbwr.setCrtdBy(currUser);
        // cbwr.setCrtdDt(currIns);
        // cbwr.setDelFlg(false);
        // cbwr.setDob(dto.dob.toInstant());
        // cbwr.setEffStartDt(currIns);
        // cbwr.setFrstNm(dto.firstName);
        // cbwr.setGndrKey(dto.genderKey);
        // cbwr.setLastNm(dto.lastName);
        // cbwr.setLastUpdBy(currUser);
        // cbwr.setLastUpdDt(currIns);
        // cbwr.setLoanAppSeq(dto.loanAppSeq);
        // cbwr.setOccKey(dto.occupationKey);
        // cbwr.setPhNum(dto.phone);
        // cbwr.setRelWthClntKey(dto.relationKey);
        // cbwr.setResTypKey(dto.residenceKey);
        //
        // return mwCbwrRepository.save(cbwr).getCbwrSeq();
    }

    public NomineeDto getExistingCbwrByApplication(long loanAppSeq) {
        MwCbwr exCbwr = mwCbwrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        NomineeDto dto = new NomineeDto();

        if (exCbwr == null)
            return dto;

        dto.coBorrowerId = exCbwr.getCbwrId();
        dto.coBorrowerSeq = exCbwr.getCbwrSeq();
        dto.cnicExpryDate = Date.from(exCbwr.getCnicEpryDt());
        dto.cnicNum = exCbwr.getCnicNum();

        dto.dob = Date.from(exCbwr.getDob());

        dto.firstName = exCbwr.getFrstNm();
        dto.genderKey = exCbwr.getGndrKey();
        dto.lastName = exCbwr.getLastNm();

        dto.loanAppSeq = exCbwr.getLoanAppSeq();
        dto.occupationKey = exCbwr.getOccKey();
        dto.phone = exCbwr.getPhNum();
        dto.relationKey = (exCbwr.getRelWthClntKey() == null) ? 0 : exCbwr.getRelWthClntKey();
        dto.residenceKey = (exCbwr.getResTypKey() == null) ? 0 : exCbwr.getRelWthClntKey();

        MwAddrRel addRel = null;
        MwAddr addr = null;

        addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(exCbwr.getCbwrSeq(), "CoBorrower", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

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
}
