package org.example.domain.state

import java.util.UUID

class QuizStateManagerImpl : QuizStateManager {

    override fun setCurrentQuiz(quizId: UUID) {
        TODO("Not yet implemented")
    }

    override fun getCurrentQuizId(): UUID? {
        TODO("Not yet implemented")
    }

    override fun addAnswer(questionId: UUID, answer: Any) {
        TODO("Not yet implemented")
    }

    override fun getAnswers(): Map<UUID, Any> {
        TODO("Not yet implemented")
    }

    override fun clearState() {
        TODO("Not yet implemented")
    }
}