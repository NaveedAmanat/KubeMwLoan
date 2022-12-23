package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwClntPermAddr;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.PersonalInfoDto;
import com.idev4.loans.repository.MwClntPermAddrRepository;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwClntPermAddr.
 */
@Service
@Transactional
public class MwClntPermAddrService {

    private final Logger log = LoggerFactory.getLogger(MwClntPermAddrService.class);

    private final MwClntPermAddrRepository mwClntPermAddrRepository;

    private final MwClntRepository mwClntRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    public MwClntPermAddrService(MwClntPermAddrRepository mwClntPermAddrRepository, MwClntRepository mwClntRepository,
                                 MwLoanAppRepository mwLoanAppRepository) {
        this.mwClntPermAddrRepository = mwClntPermAddrRepository;
        this.mwClntRepository = mwClntRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
    }

    /**
     * Save a mwClntPermAddr.
     *
     * @param mwClntPermAddr the entity to save
     * @return the persisted entity
     */
    public MwClntPermAddr save(MwClntPermAddr mwClntPermAddr) {
        log.debug("Request to save MwClntPermAddr : {}", mwClntPermAddr);
        return mwClntPermAddrRepository.save(mwClntPermAddr);
    }

    /**
     * Get all the mwClntPermAddrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClntPermAddr> findAll(Pageable pageable) {
        log.debug("Request to get all MwClntPermAddrs");
        return mwClntPermAddrRepository.findAll(pageable);
    }

    /**
     * Get one mwClntPermAddr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClntPermAddr findOne(Long id) {
        log.debug("Request to get MwClntPermAddr : {}", id);
        return mwClntPermAddrRepository.findOneByClntSeqAndCrntRecFlg(id, true);
    }

    public long addPermAddress(PersonalInfoDto dto, String currUser) {

        MwClntPermAddr exClntPermAddr = findOne(dto.clientSeq);

        if (exClntPermAddr != null)
            return updatePermAddress(dto, currUser);

        MwClntPermAddr mwClntPermAddr = new MwClntPermAddr();
        Instant curIns = Instant.now();
        long seq = 0;
        if (exClntPermAddr == null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
            if (clnt == null)
                return 0;
            MwLoanApp app = mwLoanAppRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
            if (app == null)
                return 0;
            seq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_CLNT_PERM_ADDR, app.getLoanCyclNum());

        }

        if (dto.isPermAddress)
            return 0L;

        if (seq == 0)
            return 0L;
        mwClntPermAddr.setClntPermAddrSeq(seq);
        mwClntPermAddr.setClntSeq(dto.clientSeq);
        mwClntPermAddr.setCrntRecFlg(true);
        mwClntPermAddr.setDelFlg(false);
        mwClntPermAddr.setCrtdBy(currUser);
        mwClntPermAddr.setCrtdDt(curIns);
        mwClntPermAddr.setEffStartDt(curIns);
        mwClntPermAddr.setPermAddrStr(dto.permAddress);
        mwClntPermAddr.setLastUpdBy("w-" + currUser);
        mwClntPermAddr.setLastUpdDt(Instant.now());
        return save(mwClntPermAddr).getClntPermAddrSeq();
    }

    public long updatePermAddress(PersonalInfoDto dto, String currUser) {
        // java.util.List<MwClntPermAddr> exClntPermAddrList = mwClntPermAddrRepository.findByClntSeqAndCrntRecFlg(dto.clientSeq, true);
        Instant curIns = Instant.now();

        MwClntPermAddr exClntPermAddr = mwClntPermAddrRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
        // if(!exClntPermAddrList.isEmpty()) {
        // exClntPermAddr = exClntPermAddrList.get(0);

        // if ( exClntPermAddr != null ) {
        // exClntPermAddr.setEffEndDt( curIns );
        // exClntPermAddr.setCrntRecFlg( false );
        // exClntPermAddr.setLastUpdBy( "w-" + currUser );
        // exClntPermAddr.setLastUpdDt( curIns );
        // exClntPermAddr.setDelFlg( true );
        //
        // save( exClntPermAddr );
        // }
        // }
        MwClntPermAddr clntPermAddr = new MwClntPermAddr();

        if (dto.isPermAddress) {
            return 0L;
        }

        if (exClntPermAddr != null) {
            exClntPermAddr.setLastUpdDt(curIns);
            exClntPermAddr.setLastUpdBy(currUser);
            exClntPermAddr.setClntPermAddrSeq(exClntPermAddr.getClntPermAddrSeq());
            exClntPermAddr.setClntSeq(exClntPermAddr.getClntSeq());
            exClntPermAddr.setPermAddrStr(dto.permAddress);
            return save(exClntPermAddr).getClntPermAddrSeq();

            // clntPermAddr.setClntPermAddrSeq( exClntPermAddr.getClntPermAddrSeq() );
            // clntPermAddr.setClntSeq( exClntPermAddr.getClntSeq() );
        } else {
            long seq = 0;
            if (exClntPermAddr == null) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
                if (clnt == null)
                    return 0;
                MwLoanApp app = mwLoanAppRepository.findOneByClntSeqAndCrntRecFlg(dto.clientSeq, true);
                if (app == null)
                    return 0;
                seq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_CLNT_PERM_ADDR, app.getLoanCyclNum());
            }
            clntPermAddr.setClntPermAddrSeq(seq);
            clntPermAddr.setClntSeq(dto.clientSeq);
        }
        if (clntPermAddr.getClntPermAddrSeq() == 0)
            return 0L;
        clntPermAddr.setCrntRecFlg(true);
        clntPermAddr.setDelFlg(false);
        clntPermAddr.setCrtdBy(currUser);
        clntPermAddr.setCrtdDt(curIns);
        clntPermAddr.setEffStartDt(curIns);
        clntPermAddr.setPermAddrStr(dto.permAddress);
        clntPermAddr.setLastUpdBy(currUser);
        clntPermAddr.setLastUpdDt(Instant.now());
        return save(clntPermAddr).getClntPermAddrSeq();
    }
}
