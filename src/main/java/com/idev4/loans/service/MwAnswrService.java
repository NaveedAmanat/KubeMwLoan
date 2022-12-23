package com.idev4.loans.service;

import com.idev4.loans.domain.MwAnswr;
import com.idev4.loans.repository.MwAnswrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MwAnswrService {

    private final Logger log = LoggerFactory.getLogger(MwAnswrService.class);

    private final MwAnswrRepository mwAnswrRepository;

    public MwAnswrService(MwAnswrRepository mwAnswrRepository) {
        this.mwAnswrRepository = mwAnswrRepository;
    }

    /**
     * Save a mwAnswr.
     *
     * @param mwAnswr the entity to save
     * @return the persisted entity
     */
    public MwAnswr save(MwAnswr mwAnswr) {
        log.debug("Request to save MwAnswr : {}", mwAnswr);
        return mwAnswrRepository.save(mwAnswr);
    }

    /**
     * Get all the mwAnswrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAnswr> findAll(Pageable pageable) {
        log.debug("Request to get all MwAnswrs");
        return mwAnswrRepository.findAll(pageable);
    }

    /**
     * Get one mwAnswr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAnswr findOne(Long id) {
        log.debug("Request to get MwAnswr : {}", id);
        return mwAnswrRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<MwAnswr> findAllByQstSeq(Long qstSeq) {
        return mwAnswrRepository.findAllByQstSeqAndCrntRecFlg(qstSeq, true);
    }

    /*public List<QuestionDto> getAllActiveQuestions(){
    	return mwAnswrRepository.findAllByDelFlgAndCrntRecFlg(false, true).stream().map(this::mapToDto).collect(Collectors.toList());
    }
    
    public QuestionDto mapToDto(MwAnswr ans) {
    	QuestionDto dto = new QuestionDto();
    	dto.answerString = ans.getAnswrStr();
    	dto.answerKey = ans.getAnswrSeq();
    	return dto;
    }*/
}
