package org.example.domain.usecase

import org.example.domain.model.Quiz
import org.example.domain.repository.QuizRepository

class CreateQuizUseCase(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(title: String): Quiz {
        TODO("implementation")
    }
}