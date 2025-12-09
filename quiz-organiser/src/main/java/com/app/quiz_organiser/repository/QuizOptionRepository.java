package com.app.quiz_organiser.repository;

import com.app.quiz_organiser.entity.QuizOptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizOptionRepository extends JpaRepository<QuizOptions, Long> {
}
