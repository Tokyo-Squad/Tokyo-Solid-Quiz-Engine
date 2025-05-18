package org.example.domain.state

import org.example.domain.model.UserAnswer
import java.util.UUID

interface QuizStateManager {
    fun setCurrentQuiz(quizId: UUID)
    fun getCurrentQuizId(): UUID?
    fun addAnswer(questionId: UUID, answer: Any)
    fun getAnswers(): UserAnswer
    fun clearState()
}
