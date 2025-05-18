package org.example.domain.usecase

import org.example.data.utils.DataException
import org.example.data.utils.StorageError
import org.example.data.utils.ValidationError
import org.example.domain.model.Quiz
import org.example.domain.repository.QuizRepository
import org.example.domain.utils.DomainException
import org.example.domain.utils.QuizCreationFailed
import org.example.domain.utils.QuizUnknownError
import org.example.domain.utils.QuizValidationFailed
import java.util.UUID

class CreateQuizUseCase(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(quiz: Quiz) {
        validateQuiz(quiz)

        try {
            quizRepository.createQuiz(quiz)
        } catch (e: DataException) {
            throw mapDataExceptionToDomain(e, quiz.id)
        }
    }

    private fun validateQuiz(quiz: Quiz) {
        if (quiz.title.isBlank()) {
            throw QuizValidationFailed("Quiz title cannot be blank")
        }

        if (quiz.questions.isEmpty()) {
            throw QuizValidationFailed("Quiz must have at least one question")
        }

    }

    private fun mapDataExceptionToDomain(e: DataException, quizId: UUID): DomainException = when (e) {
        is StorageError -> QuizCreationFailed("Could not create quiz $quizId", e)
        is ValidationError -> QuizValidationFailed("Invalid quiz data: ${e.message}")
        else -> QuizUnknownError("Unexpected error while creating quiz $quizId", e)
    }
}