package com.app.quiz_organiser.controller;

import com.app.quiz_organiser.dto.QuizCreateRequestDTO;
import com.app.quiz_organiser.dto.QuizResponseDTO;
import com.app.quiz_organiser.dto.QuizResultResponseDTO;
import com.app.quiz_organiser.dto.SubmitQuizRequestDTO;
import com.app.quiz_organiser.service.QuizService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizCreateRequestDTO quizCreateRequestDTO){
        QuizResponseDTO response = quizService.createQuiz(quizCreateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDTO> getQuiz(@PathVariable Long quizId){
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<QuizResultResponseDTO> submitQuiz(@PathVariable Long quizId, @RequestBody SubmitQuizRequestDTO submitQuizRequestDTO){
        return ResponseEntity.ok(quizService.submitQuiz(quizId,submitQuizRequestDTO));
    }
}
