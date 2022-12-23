package com.idev4.loans.service;

import com.idev4.loans.domain.MwHmAprsl;
import com.idev4.loans.dto.HmAppraisalDto;
import com.idev4.loans.repository.MwHmAprslRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwHmAprslService.
 */
@Service
@Transactional
public class MwHmAprslService {

    private final Logger log = LoggerFactory.getLogger(MwHmAprslService.class);

    private final MwHmAprslRepository mwHmAprslRepository;

    public MwHmAprslService(MwHmAprslRepository mwHmAprslRepository) {
        this.mwHmAprslRepository = mwHmAprslRepository;
    }

    /**
     * Save a MwHmAprsl.
     * <p>
     * the entity to save
     *
     * @return the persisted entity
     */
    public MwHmAprsl save(MwHmAprsl mwHmAprsl) {
        log.debug("Request to save MwHmAprsl : {}", mwHmAprsl);
        return mwHmAprslRepository.save(mwHmAprsl);
    }

    /**
     * Get all the MwHmAprsl.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwHmAprsl> findAll(Pageable pageable) {
        log.debug("Request to get all MwHmAprsles");
        return mwHmAprslRepository.findAll(pageable);
    }

    /**
     * Get one MwHmAprsl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwHmAprsl findOne(Long id) {
        log.debug("Request to get MwHmAprsl : {}", id);
        return mwHmAprslRepository.findOne(id);
    }

    public HmAppraisalDto addNewHmAprsl(HmAppraisalDto dto, String currUser) {

        Instant currIns = Instant.now();

        MwHmAprsl mwHmAprsl = new MwHmAprsl();
        mwHmAprsl.setHmAprslSeq(dto.getHmAprslSeq());
        mwHmAprsl.setBizAprslSeq(dto.getBizAprslSeq());
        mwHmAprsl.setYrOfCnstrctn(dto.getYrOfCnstrctn());
        mwHmAprsl.setNoOfFlrs(dto.getNoOfFlrs());
        mwHmAprsl.setPlotAreaInMrla(dto.getPlotAreaInMrla());
        mwHmAprsl.setNoOfRooms(dto.getNoOfRooms());
        mwHmAprsl.setNoOfBdroom(dto.getNoOfBdroom());
        mwHmAprsl.setNoOfWshroom(dto.getNoOfWshroom());
        mwHmAprsl.setIsKtchnSeprt(dto.getIsKtchnSeprt());
        mwHmAprsl.setCrtdDt(currIns);
        mwHmAprsl.setCrtdBy(currUser);
        mwHmAprsl.setLastUpdDt(currIns);
        mwHmAprsl.setLastUpdBy(currUser);
        mwHmAprsl.setCrntRecFlg(true);
        mwHmAprslRepository.save(mwHmAprsl);

        return dto;
    }

    public HmAppraisalDto getHmAppraisal(Long seq) {
        HmAppraisalDto dto = new HmAppraisalDto();
        MwHmAprsl mwHmAprsl = mwHmAprslRepository.findOneByBizAprslSeqAndCrntRecFlg(seq, true);

        if (mwHmAprsl != null) {
            dto.setHmAprslSeq(mwHmAprsl.getHmAprslSeq());
            dto.setBizAprslSeq(mwHmAprsl.getBizAprslSeq());
            dto.setYrOfCnstrctn(mwHmAprsl.getYrOfCnstrctn());
            dto.setPlotAreaInMrla(mwHmAprsl.getPlotAreaInMrla());
            dto.setNoOfFlrs(mwHmAprsl.getNoOfFlrs());
            dto.setNoOfRooms(mwHmAprsl.getNoOfRooms());
            dto.setNoOfBdroom(mwHmAprsl.getNoOfBdroom());
            dto.setNoOfWshroom(mwHmAprsl.getNoOfWshroom());
            dto.setIsKtchnSeprt(mwHmAprsl.getIsKtchnSeprt());
        }
        return dto;
    }

    public HmAppraisalDto updateHmAppraisal(HmAppraisalDto dto, String currUser) {

        Instant currIns = Instant.now();

        MwHmAprsl mwHmAprsl = mwHmAprslRepository.findOneByHmAprslSeqAndBizAprslSeqAndCrntRecFlg(dto.getHmAprslSeq(), dto.getBizAprslSeq(), true);
        if (mwHmAprsl != null) {
            mwHmAprsl.setYrOfCnstrctn(dto.getYrOfCnstrctn());
            mwHmAprsl.setNoOfFlrs(dto.getNoOfFlrs());
            mwHmAprsl.setPlotAreaInMrla(dto.getPlotAreaInMrla());
            mwHmAprsl.setNoOfRooms(dto.getNoOfRooms());
            mwHmAprsl.setNoOfBdroom(dto.getNoOfBdroom());
            mwHmAprsl.setNoOfWshroom(dto.getNoOfWshroom());
            mwHmAprsl.setIsKtchnSeprt(dto.getIsKtchnSeprt());
            mwHmAprsl.setLastUpdDt(currIns);
            mwHmAprsl.setLastUpdBy(currUser);
            mwHmAprslRepository.save(mwHmAprsl);
        }
        return dto;
    }
}
