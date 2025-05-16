package org.example.domain.usecase

import org.example.domain.model.Question
import java.util.UUID

class StartQuizUseCase {
    operator fun invoke(quizId: UUID): List<Question<*>> {
        TODO("implementation")
    }
}