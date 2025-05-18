package org.example.domain.repository

import org.example.domain.model.Quiz
import java.util.UUID

interface QuizRepository {
    fun createQuiz(title: String): UUID
    fun getQuizById(id: UUID): Quiz
}