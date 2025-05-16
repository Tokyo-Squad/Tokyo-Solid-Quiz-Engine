package org.example.domain.state

import java.util.UUID

interface QuizStateManager {
    fun setCurrentQuiz(quizId: UUID)
    fun getCurrentQuizId(): UUID?
    fun addAnswer(questionId: UUID, answer: Any)
    fun getAnswers(): Map<UUID, Any>
    fun clearState()
}
