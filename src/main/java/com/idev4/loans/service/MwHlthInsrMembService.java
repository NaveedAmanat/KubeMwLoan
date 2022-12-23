package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwHlthInsrMemb;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.HlthInsrMemberDto;
import com.idev4.loans.repository.MwClntHlthInsrRepository;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwHlthInsrMembRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing MwHlthInsrMemb.
 */
@Service
@Transactional
public class MwHlthInsrMembService {

    private final Logger log = LoggerFactory.getLogger(MwHlthInsrMembService.class);

    private final MwHlthInsrMembRepository mwHlthInsrMembRepository;

    private final MwClntHlthInsrRepository mwClntHlthInsrRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwHlthInsrMembService(MwHlthInsrMembRepository mwHlthInsrMembRepository, MwClntHlthInsrRepository mwClntHlthInsrRepository,
                                 MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {
        this.mwHlthInsrMembRepository = mwHlthInsrMembRepository;
        this.mwClntHlthInsrRepository = mwClntHlthInsrRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwHlthInsrMemb.
     *
     * @param mwHlthInsrMemb the entity to save
     * @return the persisted entity
     */
    public MwHlthInsrMemb save(MwHlthInsrMemb mwHlthInsrMemb) {
        log.debug("Request to save MwHlthInsrMemb : {}", mwHlthInsrMemb);
        return mwHlthInsrMembRepository.save(mwHlthInsrMemb);
    }

    /**
     * Get all the mwHlthInsrMembs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwHlthInsrMemb> findAll(Pageable pageable) {
        log.debug("Request to get all MwHlthInsrMembs");
        return mwHlthInsrMembRepository.findAll(pageable);
    }

    /**
     * Get one mwHlthInsrMemb by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    // @Transactional ( readOnly = true )
    // public MwHlthInsrMemb findOne( Long id ) {
    // log.debug( "Request to get MwHlthInsrMemb : {}", id );
    // return mwHlthInsrMembRepository.find( id );
    // }

    /**
     * Delete the mwHlthInsrMemb by id.
     *
     * @param id the id of the entity
     */
    // Edited by Areeba - Added loanAppSeq param (Production Issue)
    public void delete(Long id, Long loanAppSeq) {
        log.debug("Request to delete MwHlthInsrMemb : {}", id);
        MwHlthInsrMemb memb = mwHlthInsrMembRepository.findOneByHlthInsrMembSeqAndLoanAppSeqAndCrntRecFlg(id, loanAppSeq, true);
        if (memb == null)
            return;
        memb.setCrntRecFlg(false);
        memb.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        memb.setDelFlg(true);
        memb.setLastUpdDt(Instant.now());
        memb.setEffEndDt(Instant.now());
        mwHlthInsrMembRepository.save(memb);
    }

    public List<MwHlthInsrMemb> getAllByLoanAppSeq(Long loanAppSeq) {
        return mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
    }

    public List<HlthInsrMemberDto> findAllByLoanAppSeq(Long loanAppSeq) {
        List<HlthInsrMemberDto> dtos = new ArrayList<HlthInsrMemberDto>();
        List<MwHlthInsrMemb> membs = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        if (membs != null && membs.size() > 0) {
            for (MwHlthInsrMemb m : membs) {
                HlthInsrMemberDto dto = new HlthInsrMemberDto();
                dto.dob = Date.from(m.getDob());
                dto.hlthInsrMemberSeq = m.getHlthInsrMembSeq();
                dto.memberCnicNum = m.getMembCnicNum();
                dto.memberName = m.getMembNm();
                dto.loanAppSeq = loanAppSeq;
                dto.genderKey = m.getGndrKey();
                dto.maritalStatusKey = m.getMrtlStsKey();
                dto.relKey = m.getRelKey();
                dtos.add(dto);
            }
        }
        return dtos;

    }

    public long addInsrMember(HlthInsrMemberDto dto, String currUser) {
        List<MwHlthInsrMemb> mems = mwHlthInsrMembRepository.findAllByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        int length = 0;
        if (mems != null && mems.size() > 0)
            length = mems.size();
        MwHlthInsrMemb insrMemb = new MwHlthInsrMemb();
        Instant currIns = Instant.now();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                long seq = Long.parseLong(
                        (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_HLTH_INSR_MEMB, app.getLoanCyclNum())
                                + "").concat(length + ""));
                insrMemb.setHlthInsrMembSeq(seq);
                insrMemb.setCrntRecFlg(true);
                insrMemb.setCrtdBy("w-" + currUser);
                insrMemb.setCrtdDt(currIns);
                insrMemb.setDelFlg(false);
                insrMemb.setDob(dto.dob.toInstant());
                insrMemb.setEffStartDt(currIns);
                insrMemb.setGndrKey(dto.genderKey);
                insrMemb.setLastUpdBy("w-" + currUser);
                insrMemb.setLastUpdDt(currIns);
                insrMemb.setMembCnicNum(dto.memberCnicNum);
                insrMemb.setMembNm(dto.memberName);
                insrMemb.setMrtlStsKey(dto.maritalStatusKey);
                insrMemb.setLoanAppSeq(dto.loanAppSeq);
                insrMemb.setRelKey(dto.relKey);
                insrMemb.setSyncFlg(true);
                return mwHlthInsrMembRepository.save(insrMemb).getHlthInsrMembSeq();
            }
        }
        return 0;
    }

    @Transactional
    public long updateInsrMember(HlthInsrMemberDto dto, String currUser) {
        //Edited by Areeba (Production Issue)
        MwHlthInsrMemb mwInsrMemb = mwHlthInsrMembRepository.findOneByHlthInsrMembSeqAndLoanAppSeqAndCrntRecFlg(dto.hlthInsrMemberSeq, dto.loanAppSeq, true);
        Instant currIns = Instant.now();
        if (mwInsrMemb == null)
            return 0;

        mwInsrMemb.setLastUpdDt(currIns);
        mwInsrMemb.setLastUpdBy(currUser);
        mwInsrMemb.setDob(dto.dob.toInstant());
        mwInsrMemb.setMembCnicNum(dto.memberCnicNum);
        mwInsrMemb.setMembNm(dto.memberName);
        mwInsrMemb.setMrtlStsKey(dto.maritalStatusKey);
        mwInsrMemb.setRelKey(dto.relKey);
        mwInsrMemb.setGndrKey(dto.genderKey);

        // exInsrMemb.setLastUpdBy( "w-" + currUser );
        // exInsrMemb.setLastUpdDt( currIns );
        // exInsrMemb.setCrntRecFlg( false );
        // exInsrMemb.setEffEndDt( currIns );
        // mwHlthInsrMembRepository.save( exInsrMemb );
        //
        // MwHlthInsrMemb insrMemb = new MwHlthInsrMemb();
        //
        // insrMemb.setHlthInsrMembSeq( exInsrMemb.getHlthInsrMembSeq() );
        // insrMemb.setCrntRecFlg( true );
        // insrMemb.setCrtdBy( "w-" + currUser );
        // insrMemb.setCrtdDt( currIns );
        // insrMemb.setDelFlg( false );
        // insrMemb.setDob( dto.dob.toInstant() );
        // insrMemb.setEffStartDt( currIns );
        // insrMemb.setGndrKey( dto.genderKey );
        // insrMemb.setLastUpdBy( "w-" + currUser );
        // insrMemb.setLastUpdDt( currIns );
        // insrMemb.setMembCnicNum( dto.memberCnicNum );
        // insrMemb.setMembNm( dto.memberName );
        // insrMemb.setMrtlStsKey( dto.maritalStatusKey );
        // insrMemb.setLoanAppSeq( dto.loanAppSeq );
        // insrMemb.setRelKey( dto.relKey );
        // insrMemb.setSyncFlg( true );
        return mwHlthInsrMembRepository.save(mwInsrMemb).getHlthInsrMembSeq();
    }

}
