package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwClntPsc;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.ClientPscDto;
import com.idev4.loans.repository.MwClntPscRepository;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwClntPsc.
 */
@Service
@Transactional
public class MwClntPscService {

    private final Logger log = LoggerFactory.getLogger(MwClntPscService.class);

    private final MwClntPscRepository mwClntPscRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final EntityManager em;

    private final MwClntRepository mwClntRepository;

    public MwClntPscService(MwClntPscRepository mwClntPscRepository, EntityManager em,
                            MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {
        this.mwClntPscRepository = mwClntPscRepository;
        this.em = em;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwClntPsc.
     *
     * @param mwClntPsc the entity to save
     * @return the persisted entity
     */
    public MwClntPsc save(MwClntPsc mwClntPsc) {
        log.debug("Request to save MwClntPsc : {}", mwClntPsc);
        return mwClntPscRepository.save(mwClntPsc);
    }

    /**
     * Get all the mwClntPscs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClntPsc> findAll(Pageable pageable) {
        log.debug("Request to get all MwClntPscs");
        return mwClntPscRepository.findAll(pageable);
    }

    /**
     * Get one mwClntPsc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClntPsc findOne(Long id) {
        log.debug("Request to get MwClntPsc : {}", id);
        return mwClntPscRepository.findOne(id);
    }

    @Transactional
    public void saveClientQuestionsAnswer(List<ClientPscDto> dtos, String currUser) {
        Instant currIns = Instant.now();
        List<MwClntPsc> pscs = new ArrayList<>();
        if (dtos.size() <= 0)
            return;

        List<MwClntPsc> clntpscs = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(dtos.get(0).loanAppSeq, true);
        if (clntpscs.size() > 0) {
            updateClientQuestionsAnswer(dtos, currUser);
            return;
        }
//		
//		if (clntpscs != null && clntpscs.size() > 0) {
//			clntpscs.forEach(clntpsc -> {
//				clntpsc.setCrntRecFlg(false);
//				clntpsc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
//				clntpsc.setDelFlg(true);
//				clntpsc.setLastUpdDt(Instant.now());
//				clntpsc.setEffEndDt(Instant.now());
//			});
//			mwClntPscRepository.save(clntpscs);
//		}

        MwLoanApp ap = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dtos.get(0).loanAppSeq, true);
        if (ap != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(ap.getClntSeq(), true);
            if (clnt != null) {

                List<MwClntPsc> listPsc = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(ap.getLoanAppSeq(), true);
                mwClntPscRepository.delete(listPsc);

                dtos.forEach(dto -> {
                    MwClntPsc psc = new MwClntPsc();
					/*long seq = Common.GenerateLoanAppSequenceWithClntSeq(clnt.getCnicNum().toString(),
							ap.getLoanCyclNum().toString(), TableNames.MW_CLNT_PSC);*/
                    long seq = ap.getLoanAppSeq();
                    psc.setPscSeq(seq);
                    psc.setAnswrSeq(dto.answerSeq);
                    psc.setCrntRecFlg(true);
                    psc.setCrtdBy("w-" + currUser);
                    psc.setCrtdDt(currIns);
                    psc.setDelFlg(false);
                    psc.setEffStartDt(currIns);
                    psc.setLastUpdBy("w-" + currUser);
                    psc.setLastUpdDt(currIns);
                    psc.setLoanAppSeq(dto.loanAppSeq);
                    psc.setQstSeq(dto.questionSeq);
                    psc.setSyncFlg(true);
                    pscs.add(psc);
                });
                if (dtos.size() > 0) {
                    ClientPscDto dto = dtos.get(0);
                    if (dto.loanAppSeq > 0) {
                        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
                        if (app != null) {
                            app.setPscScore(dto.pscScore);
                            mwLoanAppRepository.save(app);
                        }
                    }
                }
            }
        }
        Common.updateFormComplFlag(dtos.get(0).formSeq, dtos.get(0).loanAppSeq, currUser);
        mwClntPscRepository.save(pscs);
    }

    public List<ClientPscDto> getLoanAppSeqQuestionsAnswer(long loanAppSeq) {
        List<MwClntPsc> pscs = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);

        List<ClientPscDto> dtos = new ArrayList<>();
        pscs.forEach(psc -> {
            ClientPscDto dto = new ClientPscDto();
            dto.questionSeq = psc.getQstSeq();
            dto.answerSeq = psc.getAnswrSeq();
            dto.loanAppSeq = psc.getLoanAppSeq();
            dto.pscSeq = psc.getPscSeq();
            dtos.add(dto);
        });

        return dtos;
    }

    public void updateClientQuestionsAnswer(List<ClientPscDto> dtos, String currUser) {
        Instant currIns = Instant.now();
        MwLoanApp ap = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dtos.get(0).loanAppSeq, true);
        if (ap == null)
            return;
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(ap.getClntSeq(), true);
        if (clnt == null)
            return;
        if (dtos.size() > 0) {
            List<MwClntPsc> pscs = new ArrayList<>();
            dtos.forEach(dto -> {

                List<MwClntPsc> listPsc = mwClntPscRepository.findAllByLoanAppSeqAndCrntRecFlg(ap.getLoanAppSeq(), true);
                mwClntPscRepository.delete(listPsc);

                //MwClntPsc psc = mwClntPscRepository.findOneByLoanAppSeqAndQstSeqAndCrntRecFlg(dto.loanAppSeq, dto.questionSeq, true);
                MwClntPsc psc = mwClntPscRepository.findOneByPscSeqAndCrntRecFlg(dto.pscSeq, true);
                if (psc == null) {
                    psc = new MwClntPsc();
					/*long seq = Common.GenerateLoanAppSequenceWithClntSeq(clnt.getCnicNum().toString(),
							ap.getLoanCyclNum().toString(), TableNames.MW_CLNT_PSC);*/
                    long seq = ap.getLoanAppSeq();
                    psc.setPscSeq(seq);
                    psc.setCrntRecFlg(true);
                    psc.setCrtdDt(currIns);
                    psc.setDelFlg(false);
                    psc.setCrtdBy("w-" + currUser);
                    psc.setEffStartDt(currIns);
                }
                psc.setAnswrSeq(dto.answerSeq);
                psc.setLastUpdBy("w-" + currUser);
                psc.setLastUpdDt(currIns);
                psc.setLoanAppSeq(dto.loanAppSeq);
                psc.setQstSeq(dto.questionSeq);
                psc.setSyncFlg(true);
                pscs.add(psc);
            });
            mwClntPscRepository.save(pscs);
            if (dtos.size() > 0) {
                ClientPscDto dto = dtos.get(0);
                if (dto.loanAppSeq > 0) {
                    MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
                    if (app != null) {
                        app.setPscScore(dto.pscScore);
                        mwLoanAppRepository.save(app);
                    }
                }
            }
        }

        //////////////////////////////////////////////////////////
        // Instant currIns = Instant.now();
        // List< Long > ids = dtos.stream().map( d -> d.pscSeq ).collect(
        ////////////////////////////////////////////////////////// Collectors.toList()
        ////////////////////////////////////////////////////////// );
        // List< MwClntPsc > pscs = mwClntPscRepository.findAll( ids );
        // List< MwClntPsc > newPscs = new ArrayList<>();
        // Map< Long, MwClntPsc > pscsMap = pscs.stream().collect( Collectors.toMap(
        ////////////////////////////////////////////////////////// MwClntPsc::getPscSeq,
        ////////////////////////////////////////////////////////// Function.identity() )
        ////////////////////////////////////////////////////////// );
        // pscs = null;
        // List< MwClntPsc > pscs2 = new ArrayList<>();
        // dtos.forEach( dto -> {
        // MwClntPsc exPsc = pscsMap.get( dto.pscSeq );
        // exPsc.setLastUpdBy( currUser );
        // exPsc.setLastUpdDt( currIns );
        // exPsc.setCrntRecFlg( false );
        // exPsc.setEffEndDt( currIns );
        // pscs2.add( exPsc );
        //
        // MwClntPsc psc = new MwClntPsc();
        // psc.setPscSeq( dto.pscSeq );
        // psc.setAnswrSeq( dto.answerSeq );
        // psc.setCrntRecFlg( true );
        // psc.setCrtdBy( currUser );
        // psc.setCrtdDt( currIns );
        // psc.setDelFlg( false );
        // psc.setEffStartDt( currIns );
        // psc.setLastUpdBy( currUser );
        // psc.setLastUpdDt( currIns );
        // psc.setLoanAppSeq( dto.loanAppSeq );
        // psc.setQstSeq( dto.questionSeq );
        // psc.setSyncFlg( true );
        // newPscs.add( psc );
        // } );
        // mwClntPscRepository.save( pscs2 );
        // mwClntPscRepository.save( newPscs );

        return;
    }
}
