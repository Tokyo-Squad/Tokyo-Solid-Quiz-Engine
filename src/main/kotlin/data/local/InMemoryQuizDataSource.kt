package data.local

import org.example.data.repository.QuizDataSource
import org.example.domain.model.Quiz
import java.util.UUID

class InMemoryQuizDataSource() : QuizDataSource {

    private val quizzes = mutableListOf<Quiz>()

    override fun getQuizById(quizId: UUID): Quiz? {
        return quizzes.firstOrNull { it.id == quizId }
    }

    override fun saveQuiz(quiz: Quiz): UUID {
        quizzes.removeAll { it.id == quiz.id }
        quizzes.add(quiz)
        return quiz.id
    }
}