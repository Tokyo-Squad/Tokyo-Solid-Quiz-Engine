package org.example.data.repository

import org.example.domain.model.Quiz
import java.util.UUID

interface QuizDataSource {
    fun getQuizById(quizId: UUID): Quiz?
    fun saveQuiz(quiz: Quiz): UUID
}