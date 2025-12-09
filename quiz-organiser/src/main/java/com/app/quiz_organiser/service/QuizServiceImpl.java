package com.app.quiz_organiser.service;


import com.app.quiz_organiser.dto.QuizCreateRequestDTO;
import com.app.quiz_organiser.dto.QuizResponseDTO;
import com.app.quiz_organiser.dto.QuizResultResponseDTO;
import com.app.quiz_organiser.dto.SubmitQuizRequestDTO;
import com.app.quiz_organiser.entity.Question;
import com.app.quiz_organiser.entity.QuestionType;
import com.app.quiz_organiser.entity.Quiz;
import com.app.quiz_organiser.entity.QuizOptions;
import com.app.quiz_organiser.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements  QuizService {

    private final QuizRepository quizRepository;


    @Override
    @Transactional
    public QuizResponseDTO createQuiz(QuizCreateRequestDTO quizCreateRequestDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateRequestDTO.getTitle());

        List<Question> questions  = new ArrayList<>();

        if(quizCreateRequestDTO.getQuestions() != null) {

            for(QuizCreateRequestDTO.QuestionCreateDTO qReq : quizCreateRequestDTO.getQuestions()){
                Question question = new Question();

                question.setQuiz(quiz);
                question.setText(qReq.getText());
                question.setQuestionType(qReq.getQuestionType());
                question.setCorrectAnswer(qReq.getCorrectAnswer());
                question.setCorrectText(qReq.getCorrectText());

                if(qReq.getQuestionType() == QuestionType.MCQ && qReq.getOptions() != null) {
                    List<QuizOptions> quizOptions = new ArrayList<>();
                    for(QuizCreateRequestDTO.OptionCreateDTO option : qReq.getOptions()) {
                        QuizOptions quizOption = new QuizOptions();
                        quizOption.setQuestion(question);
                        quizOption.setText(option.getText());
                        quizOption.setCorrectAnswer(Boolean.TRUE.equals(option.getCorrect()));
                        quizOptions.add(quizOption);
                    }
                    question.setQuizOptions(quizOptions);
                }
                questions.add(question);
            }

        }
        quiz.setQuestions(questions);
        Quiz saved =  quizRepository.save(quiz);
        return mapper(saved);
    }

    @Override
    public QuizResponseDTO getQuiz(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->new IllegalArgumentException("Quize not found with id - "+ quizId));
        return mapper(quiz);
    }

    @Override
    public QuizResultResponseDTO submitQuiz(Long quizId, SubmitQuizRequestDTO submitQuizRequestDTO) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("Quize not found with id - "+ quizId));

        Map<Long, SubmitQuizRequestDTO.AnswerDTO> answers = Optional.ofNullable(submitQuizRequestDTO.getAnswers())
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(SubmitQuizRequestDTO.AnswerDTO::getQuestionId, a -> a, (a,b) -> a));

        int total = quiz.getQuestions().size();
        int score  = 0;

        List<QuizResultResponseDTO.QuestionResult> details = new ArrayList<>();

        for(Question q : quiz.getQuestions()){
            SubmitQuizRequestDTO.AnswerDTO answer = answers.get(q.getId());
            boolean correct = false;
            String correctStr = null;
            String givenStr = null;

            if(answer != null){
                if(q.getQuestionType() == QuestionType.MCQ){

                    QuizOptions correctOpt = q.getQuizOptions().stream()
                            .filter(QuizOptions::isCorrectAnswer)
                            .findFirst()
                            .orElse(null);

                    QuizOptions selected = q.getQuizOptions().stream()
                            .filter(o -> Objects.equals(o.getId(), answer.getSelectedOptionId()))
                            .findFirst()
                            .orElse(null);

                    correct = correctOpt != null && selected != null && Objects.equals(correctOpt.getId(), selected.getId());

                    correctStr = correctOpt != null ? correctOpt.getText() : null;
                    givenStr = selected != null ? selected.getText() : null;
                } else if (q.getQuestionType() == QuestionType.TRUE_FALSE){
                    Boolean expected = q.getCorrectAnswer();
                    Boolean given  = answer.getBooleanAnswer();
                    if(expected != null && given != null){
                        correct = expected.equals(given);
                    }
                    correctStr =  String.valueOf(expected);
                    givenStr =  String.valueOf(given);

                } else if (q.getQuestionType() == QuestionType.TEXT){
                    String expected = q.getCorrectText();
                    String given  = answer.getTextAnswer();
                    if(expected != null && given != null){
                        correct = given.trim().equalsIgnoreCase(expected.trim())|| given.toLowerCase().contains(expected.toLowerCase());
                    }
                    correctStr =  expected;
                    givenStr =  given;
                }
            }
            if(correct) score++;
            details.add(QuizResultResponseDTO.QuestionResult.builder()
                    .questionId(q.getId())
                    .correct(correct)
                    .correctAnswer(correctStr)
                    . givenAnswer(givenStr)
                    .build());

        }

        return QuizResultResponseDTO.builder()
                .score(score)
                .totalQuestions(total)
                .details(details)
                .build();
    }

    private QuizResponseDTO mapper(Quiz quiz) {
        List<QuizResponseDTO.QuestionDTO> questions = quiz.getQuestions().stream()
                .map(q -> {
                    List<QuizResponseDTO.OptionDTO> opts = null;
                    if(q.getQuestionType() == QuestionType.MCQ){
                        opts = q.getQuizOptions().stream()
                                .map(o -> QuizResponseDTO.OptionDTO.builder()
                                        .id(o.getId())
                                        .text(o.getText())
                                        .build()).collect(Collectors.toList());
                    }
                    return QuizResponseDTO.QuestionDTO.builder()
                            .id(q.getId())
                            .text(q.getText())
                            .questionType(q.getQuestionType())
                            .optionDTO(opts)
                            .build();
                }).collect(Collectors.toList());

        return QuizResponseDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .questions(questions)
                .build();

    }
}
