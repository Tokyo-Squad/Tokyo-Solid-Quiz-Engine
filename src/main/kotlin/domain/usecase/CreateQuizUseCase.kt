package org.example.domain.usecase

import org.example.data.utils.DataException
import org.example.data.utils.StorageError
import org.example.data.utils.ValidationError
import org.example.domain.repository.QuizRepository
import org.example.domain.utils.DomainException
import org.example.domain.utils.QuizCreationFailed
import org.example.domain.utils.QuizUnknownError
import org.example.domain.utils.QuizValidationFailed
import java.util.UUID

class CreateQuizUseCase(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(title: String): UUID {
        validateQuiz(title)

        return try {
            quizRepository.createQuiz(title)
        } catch (e: DataException) {
            throw mapDataExceptionToDomain(e, title)
        }
    }

    private fun validateQuiz(title: String) {
        if (title.isBlank()) {
            throw QuizValidationFailed("Quiz title cannot be blank")
        }
    }

    private fun mapDataExceptionToDomain(e: DataException, quizId: String): DomainException = when (e) {
        is StorageError -> QuizCreationFailed("Could not create quiz $quizId", e)
        is ValidationError -> QuizValidationFailed("Invalid quiz data: ${e.message}")
        else -> QuizUnknownError("Unexpected error while creating quiz $quizId", e)
    }
}