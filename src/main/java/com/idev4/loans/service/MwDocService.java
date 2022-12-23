package com.idev4.loans.service;

import com.idev4.loans.domain.MwDoc;
import com.idev4.loans.dto.DocsDto;
import com.idev4.loans.repository.MwDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

/**
 * Service Implementation for managing MwDoc.
 */
@Service
@Transactional
public class MwDocService {

    private final Logger log = LoggerFactory.getLogger(MwDocService.class);

    private final MwDocRepository mwDocRepository;

    public MwDocService(MwDocRepository mwDocRepository) {
        this.mwDocRepository = mwDocRepository;
    }

    /**
     * Save a mwDoc.
     *
     * @param mwDoc the entity to save
     * @return the persisted entity
     */
    public MwDoc save(MwDoc mwDoc) {
        log.debug("Request to save MwDoc : {}", mwDoc);
        return mwDocRepository.save(mwDoc);
    }

    /**
     * Get all the mwDocs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwDoc> findAll(Pageable pageable) {
        log.debug("Request to get all MwDocs");
        return mwDocRepository.findAll(pageable);
    }

    /**
     * Get one mwDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwDoc findOne(Long id) {
        log.debug("Request to get MwDoc : {}", id);
        return mwDocRepository.findOne(id);
    }

    public long saveNewDocByUser(MultipartFile file, DocsDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwDoc doc = new MwDoc();
        doc.setCrntRecFlg(true);
        doc.setCrtdBy("w-" + currUser);
        doc.setCrtdDt(currIns);
        doc.setDelFlg(false);
        doc.setDocCtgryKey(dto.docCategoryKey);
        doc.setDocId("000000");
        doc.setDocNm(file.getName());
        doc.setDocTypKey(dto.docTypeKey);
        doc.setEffStartDt(currIns);
        doc.setLastUpdBy("w-" + currUser);
        doc.setLastUpdDt(currIns);

        return save(doc).getDocSeq();
    }
}
