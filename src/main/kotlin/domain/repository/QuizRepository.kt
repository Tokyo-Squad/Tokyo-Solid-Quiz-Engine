package org.example.domain.repository

import org.example.domain.model.Quiz
import java.util.UUID

interface QuizRepository {
    fun createQuiz(quiz: Quiz)
    fun getQuizById(id: UUID): Quiz
}