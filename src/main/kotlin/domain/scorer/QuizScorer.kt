package org.example.domain.scorer

import org.example.domain.model.Quiz
import org.example.domain.model.UserAnswer
import java.util.UUID

interface QuizScorer {
    fun calculateScore(quiz: Quiz, userAnswers: UserAnswer): Int
}