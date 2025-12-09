package com.app.quiz_organiser.repository;

import com.app.quiz_organiser.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
