package org.example.domain.usecase

import org.example.data.utils.Unknown
import org.example.domain.model.Answer
import org.example.domain.state.QuizStateManager
import org.example.domain.utils.AnswerValidationFailed
import org.example.domain.utils.DomainException
import java.util.UUID

class AnswerQuestionUseCase(
    private val quizStateManager: QuizStateManager
) {
    operator fun <T> invoke(answer: Answer<T>) {
        validateAnswer(answer)
        try {
            quizStateManager.addAnswer(answer.questionId, answer.selectedOption as Any)
        } catch (e: Exception) {
            throw mapException(e)
        }
    }

    private fun <T> validateAnswer(answer: Answer<T>) {
        if (answer.questionId == UUID(0, 0)) {
            throw AnswerValidationFailed("Question ID cannot be blank")
        }
    }

    private fun mapException(e: Exception): DomainException = when (e) {
        is AnswerValidationFailed -> e
        else -> throw Unknown("Unexpected error while answering question", e)
    }
}