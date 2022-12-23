package com.idev4.loans.service;

import com.idev4.loans.domain.MwAnswr;
import com.idev4.loans.domain.MwQst;
import com.idev4.loans.domain.MwRefCdVal;
import com.idev4.loans.dto.AnswerDto;
import com.idev4.loans.dto.QuestionDto;
import com.idev4.loans.dto.SchoolQuestionsDto;
import com.idev4.loans.repository.MwAnswrRepository;
import com.idev4.loans.repository.MwQstRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MwQst.
 */
@Service
@Transactional
public class MwQstService {

    private final Logger log = LoggerFactory.getLogger(MwQstService.class);

    private final MwQstRepository mwQstRepository;

    private final MwAnswrRepository mwAnswrRepository;

    private final MwRefCdValService mwRefCdValService;

    public MwQstService(MwQstRepository mwQstRepository, MwAnswrRepository mwAnswrRepository, MwRefCdValService mwRefCdValService) {
        this.mwQstRepository = mwQstRepository;
        this.mwAnswrRepository = mwAnswrRepository;
        this.mwRefCdValService = mwRefCdValService;
    }

    /**
     * Save a mwQst.
     *
     * @param mwQst the entity to save
     * @return the persisted entity
     */
    public MwQst save(MwQst mwQst) {
        log.debug("Request to save MwQst : {}", mwQst);
        return mwQstRepository.save(mwQst);
    }

    /**
     * Get all the mwQsts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwQst> findAll(Pageable pageable) {
        log.debug("Request to get all MwQsts");
        return mwQstRepository.findAll(pageable);
    }

    /**
     * Get one mwQst by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwQst findOne(Long id) {
        log.debug("Request to get MwQst : {}", id);
        return mwQstRepository.findOneByQstSeqAndCrntRecFlg(id, true);
    }

    // public MwQst cra

    public MwQst updateQst(MwQst qst) {
        MwQst mwQst = findOne(qst.getQstSeq());
        if (mwQst != null) {
            mwQst.setCrntRecFlg(false);
            mwQst.setDelFlg(true);
            mwQst.setEffEndDt(Instant.now());
            mwQst.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            mwQst.setLastUpdDt(Instant.now());
            save(mwQst);
        }
        return save(qst);
    }

    public List<QuestionDto> getAllActiveQuestions() {

        List<MwQst> questions = mwQstRepository.findAllByQstnrSeqAndCrntRecFlg(1, true);
        List<Long> qstSeqs = questions.stream().map(qst -> qst.getQstSeq()).collect(Collectors.toList());
        List<MwAnswr> answers = mwAnswrRepository.findByQstSeqInAndCrntRecFlg(qstSeqs, true);
        List<QuestionDto> dtos = new ArrayList<QuestionDto>();
        questions.forEach(q -> {
            QuestionDto dto = new QuestionDto();
            dto.questionKey = q.getQstSeq();
            dto.questionString = q.getQstStr();
            List<AnswerDto> ansDtos = new ArrayList<>();
            answers.forEach(a -> {
                if (a.getQstSeq() == q.getQstSeq()) {
                    AnswerDto ansDto = new AnswerDto();
                    ansDto.answerKey = a.getAnswrSeq();
                    ansDto.answerString = a.getAnswrStr();
                    ansDto.answerScore = a.getAnswrScore();
                    ansDtos.add(ansDto);
                }
            });
            dto.answers = ansDtos;
            dtos.add(dto);
        });

        return dtos;
    }

    public List<SchoolQuestionsDto> getAllQuestionsForByType(long typeSeq) {
        List<MwQst> mwQuestions = mwQstRepository.findAllByDelFlgAndCrntRecFlgAndQstnrSeqOrderByQstSortOrdr(false, true, typeSeq);
        List<SchoolQuestionsDto> questions = new ArrayList<SchoolQuestionsDto>();

        mwQuestions.forEach(mwQuestion -> {

            QuestionDto dto = new QuestionDto();
            dto.questionString = mwQuestion.getQstStr();
            dto.questionKey = mwQuestion.getQstSeq();
            dto.questionCategoryKey = mwQuestion.getQstCtgryKey();
            if (mwQuestion.getQstCtgryKey() != null) {
                MwRefCdVal val = mwRefCdValService.findOne(mwQuestion.getQstCtgryKey());
                if (val != null) {
                    dto.questionCategory = val.getRefCdDscr();
                    int i = -1;
                    for (int z = 0; z < questions.size(); z++) {
                        SchoolQuestionsDto q = questions.get(z);
                        if (q.key == mwQuestion.getQstCtgryKey()) {
                            i = z;
                            questions.get(z).questions.add(dto);
                        }
                    }
                    if (i == -1) {
                        SchoolQuestionsDto question = new SchoolQuestionsDto();
                        question.key = mwQuestion.getQstCtgryKey();
                        question.group = val.getRefCdDscr();
                        question.questions = new ArrayList<>();
                        question.groupSortOrder = val.getSortOrder();
                        question.questions.add(dto);
                        questions.add(question);
                    }
                }
            }
        });

        questions.sort((SchoolQuestionsDto a, SchoolQuestionsDto b) -> a.groupSortOrder.compareTo(b.groupSortOrder));

        return questions;
    }

    public List<QuestionDto> getAllActiveQuestionsByType(long type) {

        List<MwQst> questions = mwQstRepository.findAllByDelFlgAndCrntRecFlgAndQstTypKey(false, true, type);
        List<Long> qstSeqs = questions.stream().map(qst -> qst.getQstSeq()).collect(Collectors.toList());
        List<QuestionDto> dtos = new ArrayList<QuestionDto>();

        if (type == 202) {
            List<MwAnswr> answers = mwAnswrRepository.findByQstSeqInAndCrntRecFlg(qstSeqs, true);
            questions.forEach(q -> {
                QuestionDto dto = new QuestionDto();
                dto.questionCategoryKey = q.getQstCtgryKey();
                dto.questionCategory = (mwRefCdValService.findOne(q.getQstCtgryKey())).getRefCdDscr();
                dto.questionKey = q.getQstSeq();
                dto.questionString = q.getQstStr();
                List<AnswerDto> ansDtos = new ArrayList<>();
                answers.forEach(a -> {
                    if (a.getQstSeq() == q.getQstSeq()) {
                        AnswerDto ansDto = new AnswerDto();
                        ansDto.answerKey = a.getAnswrSeq();
                        ansDto.answerString = a.getAnswrStr();
                        ansDto.answerScore = a.getAnswrScore();
                        ansDtos.add(ansDto);
                    }
                });
                dto.answers = ansDtos;
                dtos.add(dto);
            });
        } else if (type == 203) {
            questions.forEach(q -> {
                QuestionDto dto = new QuestionDto();
                dto.questionKey = q.getQstSeq();
                dto.questionCategoryKey = (q.getQstCtgryKey() == null) ? 0L : q.getQstCtgryKey();
                dto.questionCategory = (mwRefCdValService.findOne(q.getQstCtgryKey())).getRefCdDscr();
                dto.questionString = q.getQstStr();
                dtos.add(dto);
            });
        }
        return dtos;
    }

    public QuestionDto mapToDto(MwQst qst) {
        QuestionDto dto = new QuestionDto();
        dto.questionString = qst.getQstStr();
        dto.questionKey = qst.getQstSeq();
        return dto;
    }
}
