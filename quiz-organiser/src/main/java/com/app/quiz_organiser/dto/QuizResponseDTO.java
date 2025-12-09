package com.app.quiz_organiser.dto;


import com.app.quiz_organiser.entity.Question;
import com.app.quiz_organiser.entity.QuestionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuizResponseDTO {

    private Long id;
    private String title;
    private List<QuestionDTO> questions;

    @Data
    @Builder
    public static class QuestionDTO{
        private Long id;
        private String text;
        private QuestionType questionType;
        private List<OptionDTO> optionDTO;
    }

    @Data
    @Builder
    public static class OptionDTO{
        private Long id;
        private String text;
    }
}
