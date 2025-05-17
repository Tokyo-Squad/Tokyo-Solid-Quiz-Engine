package org.example.domain.usecase

import org.example.domain.model.QuizReport
import org.example.domain.model.UserAnswer
import org.example.domain.repository.QuizRepository
import org.example.domain.state.QuizStateManager

class SubmitQuizUseCase(
    private val repository: QuizRepository,
    private val quizStateManger : QuizStateManager
) {
    operator fun invoke(userAnswer: UserAnswer): QuizReport {
        return QuizReport("title" , 1,1,0,1)
    }
}
