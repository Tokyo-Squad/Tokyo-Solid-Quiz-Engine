package org.example.domain.usecase

import org.example.domain.model.UserAnswer
import java.util.UUID

class AnswerQuestionUseCase {
    operator fun invoke(
        quizId: UUID,
        questionId: UUID,
        answer: Any
    ): UserAnswer {
        return UserAnswer(
            quizId = quizId,
            questionId = questionId,
            selectedAnswer = answer,
            answers = TODO()
        )
    }
}