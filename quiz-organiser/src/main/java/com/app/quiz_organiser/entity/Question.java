package com.app.quiz_organiser.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quize_id")
    private Quiz quiz;

    @Column(nullable=false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private QuestionType questionType;

    private Boolean correctAnswer;

    private String correctText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizOptions> quizOptions = new ArrayList<>();
}
