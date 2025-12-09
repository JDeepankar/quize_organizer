package com.app.quiz_organiser.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubmitQuizRequestDTO {

    private String candidateName;
    private List<AnswerDTO> answers;

    @Data
    public static class AnswerDTO {
        private Long questionId;
        private Long selectedOptionId;
        private Boolean booleanAnswer;
        private String textAnswer;
    }


}
