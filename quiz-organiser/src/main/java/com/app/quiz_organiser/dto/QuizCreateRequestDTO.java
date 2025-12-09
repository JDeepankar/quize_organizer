package com.app.quiz_organiser.dto;

import com.app.quiz_organiser.entity.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuizCreateRequestDTO {

    private String title;
    private List<QuestionCreateDTO> questions;

    @Data
    public static class QuestionCreateDTO {
        private String text;
        private QuestionType questionType;
        private Boolean correctAnswer;
        private String correctText;
        private List<OptionCreateDTO> options;
    }

    @Data
    public static class OptionCreateDTO {
        private String text;
        private Boolean correct;
    }
}
