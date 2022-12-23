package com.idev4.loans.dto;

import java.util.List;

public class QuestionDto {

    public String questionCategory;
    public long questionCategoryKey;
    public String questionString;
    public long questionKey;
    public List<AnswerDto> answers;
    public long answerSeq;
}
