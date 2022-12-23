package com.idev4.loans.service;

import com.idev4.loans.domain.MwDoc;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.domain.MwLoanAppDoc;
import com.idev4.loans.domain.MwLoanAppVerisys;
import com.idev4.loans.repository.MwDocRepository;
import com.idev4.loans.repository.MwLoanAppDocRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.repository.MwLoanAppVerisysRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwLoanAppDoc.
 */
@Service
@Transactional
public class MwLoanAppDocService {

    private final Logger log = LoggerFactory.getLogger(MwLoanAppDocService.class);

    private final MwLoanAppDocRepository mwLoanAppDocRepository;

    private final MwLoanAppVerisysRepository mwLoanAppVerisysRepository;

    private final MwDocRepository mwDocRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    public MwLoanAppDocService(MwLoanAppDocRepository mwLoanAppDocRepository, MwDocRepository mwDocRepository,
                               MwLoanAppRepository mwLoanAppRepository, MwLoanAppVerisysRepository mwLoanAppVerisysRepository) {
        this.mwLoanAppDocRepository = mwLoanAppDocRepository;
        this.mwDocRepository = mwDocRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwLoanAppVerisysRepository = mwLoanAppVerisysRepository;
    }

    /**
     * Save a mwLoanAppDoc.
     *
     * @param mwLoanAppDoc the entity to save
     * @return the persisted entity
     */
    public MwLoanAppDoc save(MwLoanAppDoc mwLoanAppDoc) {
        log.debug("Request to save MwLoanAppDoc : {}", mwLoanAppDoc);
        return mwLoanAppDocRepository.save(mwLoanAppDoc);
    }


    // Added by Areeba - Dated - 10-05-2022
    // Halaf Nama
    public Integer saveDoc(Long seq, String img, String currUser) {

        Instant currIns = Instant.now();
        MwLoanAppDoc loanAppDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(seq, 10, true);
        if (loanAppDoc == null) {
            MwLoanAppDoc loanAppDoc1 = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(seq, 1, true);
            if (loanAppDoc1 != null) {
                MwLoanAppDoc doc = new MwLoanAppDoc();
                doc.setLoanAppDocSeq(seq);
                doc.setEffStartDt(currIns);
                doc.setLoanAppSeq(seq);
                doc.setDocSeq(10L);
                doc.setDocImg(img);
                doc.setCrtdBy(currUser);
                doc.setCrtdDt(currIns);
                doc.setLastUpdBy(currUser);
                doc.setLastUpdDt(currIns);
                doc.setDelFlg(false);
                doc.setEffEndDt(null);
                doc.setCrntRecFlg(true);
                doc.setSyncFlg(null);
                doc.setCnicNum(loanAppDoc1.getCnicNum());
                doc.setCompanyNm(null);
                doc.setNomCnicNum(null);

                mwLoanAppDocRepository.save(doc);
            } else {
                return -1;
            }
        } else {
            loanAppDoc.setDocImg(img);
            loanAppDoc.setLastUpdBy(currUser);
            loanAppDoc.setLastUpdDt(currIns);

            mwLoanAppDocRepository.save(loanAppDoc);
        }
        return 1;
    }
    // Ended by Areeba


    /**
     * Get all the mwLoanAppDocs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwLoanAppDoc> findAll(Pageable pageable) {
        log.debug("Request to get all MwLoanAppDocs");
        return mwLoanAppDocRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<MwLoanAppDoc> findAllByLoanAppSeq(long seq) {
        log.debug("Request to get all MwLoanAppDocs for LoanAppSeq: ", seq);
        List<MwLoanAppDoc> docs = mwLoanAppDocRepository.findAllByLoanAppSeqAndCrntRecFlg(seq, true);
        docs.forEach(ldoc -> {
            MwDoc doc = mwDocRepository.findOneByDocSeqAndCrntRecFlg(ldoc.getDocSeq(), true);
            if (doc != null)
                ldoc.setCrtdBy(doc.getDocNm());
        });
        return docs;
    }

    /**
     * Get one mwLoanAppDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwLoanAppDoc findOne(Long id) {
        log.debug("Request to get MwLoanAppDoc : {}", id);
        return mwLoanAppDocRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<MwLoanAppDoc> findAllByClntSeqAndCycleNum(Long clntSeq, Long loanCycleNum, Long loanAppSeq) {
        log.debug("Request to get all MwLoanAppDocs for ClientSeq: ", clntSeq);
        // Modified by Rizwan Mahfooz - Dated 07-04-2022 - Loan App Seq
        MwLoanApp app = mwLoanAppRepository.findOneByClntSeqAndLoanCyclNumAndCrntRecFlgForConventionalProduct(clntSeq,
                loanCycleNum, loanAppSeq);
        // End
        List<MwLoanAppDoc> docs = new ArrayList<MwLoanAppDoc>();
        if (app != null) {
            docs = mwLoanAppDocRepository.findAllByLoanAppSeqAndCrntRecFlg(app.getLoanAppSeq(), true);
            // docs.forEach( ldoc -> {
            //
            // if ( ldoc.getDocImg() != null && ldoc.getD)
            // ldoc.setCrtdBy( doc.getDocNm() );
            // } );
        }
        return docs;
    }

    @Transactional(readOnly = true)
    public MwLoanAppVerisys findVerisysRecord(Long seq, String category) {
        List<MwLoanAppVerisys> document = mwLoanAppVerisysRepository.findOneByLoanAppSeqAndCnicCatOrderByVerSeqDesc(seq, category);
        return document != null && document.size() > 0 ? document.get(0) : null;
    }
}
