package com.app.quiz_organiser.service;


import com.app.quiz_organiser.dto.QuizCreateRequestDTO;
import com.app.quiz_organiser.dto.QuizResponseDTO;
import com.app.quiz_organiser.dto.QuizResultResponseDTO;
import com.app.quiz_organiser.dto.SubmitQuizRequestDTO;

public interface QuizService {

    public QuizResponseDTO createQuiz(QuizCreateRequestDTO quizCreateRequestDTO);
    public QuizResponseDTO getQuiz(Long quizId);
    public QuizResultResponseDTO submitQuiz(Long quizId, SubmitQuizRequestDTO submitQuizRequestDTO);
}
