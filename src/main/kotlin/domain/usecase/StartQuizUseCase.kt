package org.example.domain.usecase

import org.example.data.utils.DataException
import org.example.data.utils.NotFound
import org.example.data.utils.StorageError
import org.example.domain.model.Question
import org.example.domain.repository.QuizRepository
import org.example.domain.utils.*
import java.util.*

class StartQuizUseCase(private val quizRepository: QuizRepository) {
    operator fun invoke(quizId: UUID): List<Question<*>> {
        if (quizId == UUID(0, 0)) {
            throw QuizValidationFailed("Quiz ID cannot be blank or invalid")
        }
        return try {
            quizRepository.getQuizById(quizId).questions
        } catch (e: DataException) {
            throw mapDataExceptionToDomain(e, quizId)
        }
    }

    private fun mapDataExceptionToDomain(e: DataException, quizId: UUID): DomainException = when (e) {
        is NotFound -> QuizNotFound("Quiz with ID $quizId not found")
        is StorageError -> QuizStorageFailed("Could not retrieve quiz $quizId", e)
        else -> QuizUnknownError("Unexpected error for quiz $quizId", e)
    }
}