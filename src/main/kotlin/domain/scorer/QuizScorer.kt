package org.example.domain.scorer

import org.example.domain.model.Quiz
import java.util.UUID

interface QuizScorer {
    fun calculateScore(quiz: Quiz, userAnswers: Map<UUID, Any>): Int
}