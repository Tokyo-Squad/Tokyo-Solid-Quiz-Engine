package org.example.domain.state

import java.util.UUID

class InMemoryQuizStateManager : QuizStateManager {
    private var currentQuizId: UUID? = null
    private val answers = mutableMapOf<UUID, Any>()

    override fun setCurrentQuiz(quizId: UUID) {
        currentQuizId = quizId
        answers.clear()
    }

    override fun getCurrentQuizId(): UUID? = currentQuizId

    override fun addAnswer(questionId: UUID, answer: Any) {
        answers[questionId] = answer
    }

    override fun getAnswers(): Map<UUID, Any> = answers.toMap()

    override fun clearState() {
        currentQuizId = null
        answers.clear()
    }
}