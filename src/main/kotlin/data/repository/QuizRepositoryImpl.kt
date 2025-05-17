package org.example.data.repository

import org.example.domain.model.Quiz
import org.example.domain.repository.QuizRepository
import java.util.UUID

class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource
) : QuizRepository {

    override fun createQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override fun getQuizById(id: UUID): Quiz {
        TODO("Not yet implemented")
    }
}