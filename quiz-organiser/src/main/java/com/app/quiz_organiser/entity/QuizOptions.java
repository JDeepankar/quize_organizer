package com.app.quiz_organiser.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="quiz_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question  question;

    @Column(nullable = false)
    private String text;

    private boolean correctAnswer;

}
