package com.app.quiz_organiser.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizResultResponseDTO {

    private int score;
    private int totalQuestions;
    private List<QuestionResult> details;

    @Data
    @Builder
    public static class QuestionResult {
        private Long questionId;
        private boolean correct;
        private String correctAnswer;
        private String givenAnswer;
    }

}
