package org.example.domain.usecase

import org.example.domain.model.QuizReport
import org.example.domain.model.UserAnswer
import org.example.domain.repository.QuizRepository

class SubmitQuizUseCase(private val repository: QuizRepository) {
    operator fun invoke(userAnswer: UserAnswer): QuizReport {
        TODO("implementation")
    }
}
