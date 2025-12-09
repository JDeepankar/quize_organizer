package com.app.quiz_organiser.repository;

import com.app.quiz_organiser.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
