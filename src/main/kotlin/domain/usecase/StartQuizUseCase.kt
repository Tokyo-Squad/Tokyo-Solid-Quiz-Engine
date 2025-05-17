package org.example.domain.usecase

import org.example.data.utils.NotFound
import org.example.domain.model.Question
import org.example.domain.repository.QuizRepository
import org.example.domain.utils.QuizNotFound
import java.util.UUID

class StartQuizUseCase(private val quizRepository: QuizRepository) {
    operator fun invoke(quizId: UUID): List<Question<*>> {
        return try {
            quizRepository.getQuizById(quizId).questions
        } catch (e: NotFound) {
            throw QuizNotFound("Quiz with ID $quizId not found")
        }
    }
}